package net.arcreactor.chatty.packets.server;

import net.arcreactor.chatty.packets.Packet;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/11
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class PingPacket extends Packet {
    public static final short HEADER = 0x1100;

    public PingPacket(){
        IoBuffer buffer = IoBuffer.allocate(2);
        buffer.putShort(HEADER);
        packet = buffer.array().clone();
    }

    public PingPacket(byte[] packet){
        this.packet = packet.clone();
    }

    public short getHeader() {
        return HEADER;
    }
}
