package net.arcreactor.chatty.crytography;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/11
 * Time: 1:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapleEncryptionEncoder implements ProtocolEncoder{

    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        MapleAESOFB aesEncryptor = (MapleAESOFB) session.getAttribute("aesEncryptor");

        if(aesEncryptor != null){
            byte[] input = (byte[]) message;
			byte[] unencrypted = new byte[input.length];
			System.arraycopy(input, 0, unencrypted, 0, input.length);

			byte[] ret = new byte[unencrypted.length + 4];
			byte[] header = aesEncryptor.getPacketHeader(unencrypted.length);
			MapleCustomEncryption.encryptData(unencrypted);

			synchronized (aesEncryptor) {
				aesEncryptor.crypt(unencrypted);

				System.arraycopy(header, 0, ret, 0, 4);
				System.arraycopy(unencrypted, 0, ret, 4, unencrypted.length);

				IoBuffer out_buffer = IoBuffer.wrap(ret);
				out.write(out_buffer);
			}
        }else{
            out.write(IoBuffer.wrap((byte[]) message));
        }
    }

    public void dispose(IoSession ioSession) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
