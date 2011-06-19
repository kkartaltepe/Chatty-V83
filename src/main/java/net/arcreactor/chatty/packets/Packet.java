package net.arcreactor.chatty.packets;

import com.sun.istack.internal.FinalArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/11
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Packet {
    protected byte[] packet;

    public byte[] getPacketBytes(){
        return packet.clone();
    }

    public abstract short getHeader();

    public String toString(){
        return "Header: " + getHeader() + ". Content length: " + (packet.length-2);
    }
}
