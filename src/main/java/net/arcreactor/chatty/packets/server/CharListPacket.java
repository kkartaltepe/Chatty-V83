package net.arcreactor.chatty.packets.server;

import net.arcreactor.chatty.maple.MapleCharacter;
import net.arcreactor.chatty.packets.AbstractPacket;
import org.apache.mina.core.buffer.IoBuffer;

import java.beans.beancontext.BeanContextMembershipEvent;
import java.nio.ByteOrder;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/26/11
 * Time: 7:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class CharListPacket extends AbstractPacket {
    public static final short HEADER = 0x0B;
    private List<MapleCharacter> charList;

    public CharListPacket(byte[] content){
        this.packet = content.clone();
        IoBuffer buffer = IoBuffer.wrap(this.packet);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.getShort(); // header
        buffer.get();//random byte....No one loves it
        byte numChars = buffer.get();
        charList = new ArrayList<MapleCharacter>();
        for(int i = 0; i < numChars; i++){
            charList.add(new MapleCharacter(buffer, true, true));
        }
        buffer.getInt(); //??
    }

    @Override
    public short getHeader() {
        return HEADER;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<MapleCharacter> getChars() {
        return charList;
    }
}
