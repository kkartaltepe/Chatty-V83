package net.arcreactor.chatty.packets.server;

import net.arcreactor.chatty.packets.AbstractPacket;
import net.arcreactor.chatty.tools.HexTool;
import org.apache.mina.core.buffer.IoBuffer;

import java.nio.ByteOrder;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/11
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnknownPacket extends AbstractPacket {
    final short HEADER;

    public UnknownPacket(byte[] content){
        IoBuffer packetBuffer = IoBuffer.wrap(content);
        packetBuffer.order(ByteOrder.LITTLE_ENDIAN);

        HEADER = packetBuffer.getShort();
        this.packet = packetBuffer.array().clone();
    }

    public short getHeader() {
        return HEADER;
    }
}
