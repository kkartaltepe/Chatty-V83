package net.arcreactor.chatty.packets.client;

import net.arcreactor.chatty.packets.AbstractPacket;
import org.apache.mina.core.buffer.IoBuffer;

import java.nio.ByteOrder;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/19/11
 * Time: 12:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoginPacket extends AbstractPacket {
    public static final short HEADER = 0x01;

    public LoginPacket(String username, String password){
        IoBuffer contents = IoBuffer.allocate(2+username.length()+2+password.length()+2);
        contents.order(ByteOrder.LITTLE_ENDIAN);

        contents.putShort(HEADER);
        try {
            contents.putPrefixedString(username, Charset.forName("US-ASCII").newEncoder());
            contents.putPrefixedString(password, Charset.forName("US-ASCII").newEncoder());
        } catch (CharacterCodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        packet = contents.flip().array().clone();
    }

    @Override
    public short getHeader() {
        return HEADER;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
