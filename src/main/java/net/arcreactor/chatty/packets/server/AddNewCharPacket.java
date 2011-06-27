package net.arcreactor.chatty.packets.server;

import net.arcreactor.chatty.maple.MapleCharacter;
import net.arcreactor.chatty.packets.AbstractPacket;
import net.arcreactor.chatty.tools.HexTool;
import org.apache.mina.core.buffer.IoBuffer;

import java.nio.ByteOrder;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/26/11
 * Time: 9:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddNewCharPacket extends AbstractPacket {
    public static final short HEADER = 0x0E;
    MapleCharacter newChar;

    public AddNewCharPacket(byte[] content){
        packet = content.clone();
        IoBuffer buffer = IoBuffer.wrap(packet);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.getShort();  //Header
        buffer.get(); //Random Byte that no one loves...
        newChar = new MapleCharacter(buffer, true, true);
    }

    public MapleCharacter getNewChar(){
        return newChar;
    }


    @Override
    public short getHeader() {
        return HEADER;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
