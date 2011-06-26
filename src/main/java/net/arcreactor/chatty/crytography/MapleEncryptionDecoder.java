package net.arcreactor.chatty.crytography;

import net.arcreactor.chatty.packets.client.LoginPacket;
import net.arcreactor.chatty.tools.HexTool;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/11
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapleEncryptionDecoder extends CumulativeProtocolDecoder {
    private static class DecoderState {
		public int packetlength = -1;
	}

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        MapleAESOFB aesDecryptor = (MapleAESOFB) session.getAttribute("aesDecryptor");
        DecoderState state = (DecoderState) session.getAttribute("DecoderState");
        if (state == null) {
			state = new DecoderState();
			session.setAttribute("DecoderState", state);
		}

        if (aesDecryptor != null){
			if (in.remaining() >= 4 && state.packetlength == -1) {
				int packetHeader = in.getInt();
				if (!aesDecryptor.checkPacket(packetHeader)) {
					System.err.println("Server failed packet check -> disconnecting");
					session.close(true);
					return false;
				}
				state.packetlength = MapleAESOFB.getPacketLength(packetHeader);
			} else if (in.remaining() < 4 && state.packetlength == -1) {
				System.err.println("decode... not enough data");
				return false;
			}

			if (in.remaining() >= state.packetlength) {
				byte decryptedPacket[] = new byte[state.packetlength];
				in.get(decryptedPacket, 0, state.packetlength);
				state.packetlength = -1;

				aesDecryptor.crypt(decryptedPacket);
				MapleCustomEncryption.decryptData(decryptedPacket);

				out.write(decryptedPacket);
				return true;
			} else {
				System.err.println("decode... not enough data to decode (need "+ state.packetlength + " )");
				return false;
			}
		}else if(in.remaining() < 14){
			System.err.println("decode... not enough data in hello");
			return false;
		}else{
				byte key[] = { 	0x13, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, (byte) 0xB4, 0x00, 0x00,
								0x00, 0x1B, 0x00, 0x00, 0x00, 0x0F, 0x00, 0x00, 0x00, 0x33, 0x00, 0x00, 0x00, 0x52, 0x00, 0x00, 0x00 };
				byte ivRecv[] = { 70, 114, 122, 82 };
				byte ivSend[] = { 82, 48, 120, 115 };

				int header = in.getShort();	//two byte header.
				short MAPLE_VERSION = (short)in.get();
				in.getShort();
				in.getShort(); //extra 4 for v83? bytes after maple version.

				in.get(ivRecv, 0, 4);
				in.get(ivSend, 0, 4);

				in.get(); //the extra 8 on the end.

				MapleAESOFB sendCypher = new MapleAESOFB(key, ivSend, (short) (0xFFFF - MAPLE_VERSION));
				MapleAESOFB recvCypher = new MapleAESOFB(key, ivRecv, MAPLE_VERSION);

                session.setAttribute("aesDecryptor", sendCypher);
                session.setAttribute("aesEncryptor", recvCypher);
                session.write(new LoginPacket("abcd","asdfgh").getPacketBytes());

				System.out.println("decoded hello: Maple V" + MAPLE_VERSION + " " + "RecvIV:" + (ivRecv[0] & 0xFF) + " " + (ivRecv[1] & 0xFF) + " " + (ivRecv[2] & 0xFF) + " " + (ivRecv[3] & 0xFF) + " " + " SendIV: " + (ivSend[0] & 0xFF) + " " + (ivSend[1] & 0xFF) + " " + (ivSend[2] & 0xFF) + " " + (ivSend[3] & 0xFF) + " Header: " + header);
				return true;
		}
    }
}
