package net.arcreactor.chatty.packets.server;

import net.arcreactor.chatty.packets.Packet;
import org.apache.mina.core.buffer.IoBuffer;

import java.nio.ByteOrder;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/11
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnknownPacket extends Packet {
    short header;

    public UnknownPacket(byte[] packet){
        IoBuffer packetBuffer = IoBuffer.wrap(packet);
        packetBuffer.order(ByteOrder.LITTLE_ENDIAN);

        header = packetBuffer.getShort();
        packet = packetBuffer.array().clone();
    }

    public short getHeader() {
        return header;
    }
}
