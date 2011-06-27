package net.arcreactor.chatty.packets.server;

import net.arcreactor.chatty.packets.AbstractPacket;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/26/11
 * Time: 7:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerStatusPacket extends AbstractPacket {
    public static final short HEADER = 0x03;

    public ServerStatusPacket(byte[] content){
        packet = content.clone();
    }

    @Override
    public short getHeader() {
        return HEADER;
    }
}
