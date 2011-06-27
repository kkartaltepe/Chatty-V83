package net.arcreactor.chatty.packets.server;

import net.arcreactor.chatty.packets.AbstractPacket;
import net.arcreactor.chatty.tools.HexTool;
import org.apache.mina.core.buffer.IoBuffer;

import java.nio.ByteOrder;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/19/11
 * Time: 1:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoginStatusPacket extends AbstractPacket {
    public enum ResponseCode{
        SUCCESS,
        NOPE1,
        NOPE2,
        NOPE3,
        INCORRECT_PASS,
        INVALID_ID,
        NOPE6,
        ALREADY_LOGGEDIN
    }
    public static final short HEADER = 0x0000;

    ResponseCode response;
    int userId;
    byte gender;
    boolean gm;
    String accountName;
    boolean picEnabled;

    public LoginStatusPacket(byte[] content){
        this.packet = content.clone();
        IoBuffer buffer = IoBuffer.wrap(this.packet);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.getShort(); //Header
        response = ResponseCode.values()[buffer.getInt()];
        buffer.getShort();
        if(response == ResponseCode.SUCCESS){
            userId = buffer.getInt();
            gender = buffer.get();
            gm = buffer.get() == 1;
            buffer.getShort();
            try {
                accountName = buffer.getPrefixedString(Charset.forName("US-ASCII").newDecoder());
            } catch (CharacterCodingException e) {
                e.printStackTrace();
            }
            buffer.getShort();
            buffer.getLong();
            buffer.getLong();
            buffer.getInt();
            picEnabled = buffer.get() == 1;
        }

    }


    public ResponseCode getResponse() {
        return response;
    }

    @Override
    public short getHeader() {
        return HEADER;
    }
}
