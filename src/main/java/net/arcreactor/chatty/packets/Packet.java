package net.arcreactor.chatty.packets;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/11
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Packet {
    public byte[] getPacketBytes();
    public short getHeader();
}
