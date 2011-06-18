package net.arcreactor.chatty.crytography;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

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

        if (aesDecryptor == null) {
            if (in.remaining() < 14) {
//                log.warn("decode... not enough data in hello");
                return false;
            } else {
                byte key[] = {0x13, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, (byte) 0xB4, 0x00, 0x00,
                        0x00, 0x1B, 0x00, 0x00, 0x00, 0x0F, 0x00, 0x00, 0x00, 0x33, 0x00, 0x00, 0x00, 0x52, 0x00, 0x00, 0x00};
                byte ivRecv[] = {70, 114, 122, 82};
                byte ivSend[] = {82, 48, 120, 115};

                int header = in.getShort(); //two byte header.
                short MAPLE_VERSION = (short) in.get();
                in.getShort();
                in.getShort(); // bytes after maple version.   extra 4 for v83?

                in.get(ivRecv, 0, 4);
                in.get(ivSend, 0, 4);

                in.get(); //the extra 8 on the end.

                System.out.println("Creating AES encryption scheme");

                session.setAttribute("aesDecryptor", new MapleAESOFB(key, ivSend, (short) (0xFFFF - MAPLE_VERSION)));
                session.setAttribute("aesEncryptor", new MapleAESOFB(key, ivRecv, MAPLE_VERSION));
                return true;
            }
        }else{
            if (in.remaining() >= 4 && state.packetlength == -1) {
				int packetHeader = in.getInt();
				if (!aesDecryptor.checkPacket(packetHeader)) {
//					log.warn("Server failed packet check -> disconnecting");
					session.close(true);
                    return false;
				}
				state.packetlength = MapleAESOFB.getPacketLength(packetHeader);
			} else if (in.remaining() < 4 && state.packetlength == -1) {
//				log.trace("decode... not enough data");
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
//				log.trace("decode... not enough data to decode (need {})", state.packetlength);
                return false;
			}

        }
    }
}
