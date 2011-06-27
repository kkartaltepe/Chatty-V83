package net.arcreactor.chatty.packets.client;

import net.arcreactor.chatty.packets.AbstractPacket;
import org.apache.mina.core.buffer.IoBuffer;

import java.nio.ByteOrder;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/26/11
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerStatusRequestPacket extends AbstractPacket {
    public static final short HEADER = 0x06;

    public ServerStatusRequestPacket(){
        IoBuffer contents = IoBuffer.allocate(4);
        contents.order(ByteOrder.LITTLE_ENDIAN);

        contents.putShort(HEADER);
        contents.putShort((short)0);

        packet = contents.flip().array().clone();
    }

    @Override
    public short getHeader() {
        return HEADER;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
