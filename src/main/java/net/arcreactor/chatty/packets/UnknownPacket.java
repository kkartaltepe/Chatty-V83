package net.arcreactor.chatty.packets;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/11
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnknownPacket implements Packet{
    short header;
    byte[] packet;

    public UnknownPacket(byte[] packet){
        IoBuffer packetBuffer = IoBuffer.wrap(packet);
        header = packetBuffer.getShort();
        this.packet = packetBuffer.array().clone();
    }

    public short getHeader() {
        return header;
    }

    public byte[] getPacketBytes() {
        return packet.clone();
    }

    public String toString(){
        return "Header: " + header + ". Content length: " + (packet.length-2);
    }
}
