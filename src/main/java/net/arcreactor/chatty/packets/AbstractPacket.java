package net.arcreactor.chatty.packets;

import com.sun.istack.internal.FinalArrayList;
import net.arcreactor.chatty.tools.HexTool;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/11
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPacket {
    protected byte[] packet;

    public byte[] getPacketBytes(){
        return packet.clone();
    }

    public abstract short getHeader();

    public String toString(){
        return this.getClass().getSimpleName() + " Content: " + HexTool.toString(packet);
    }
}
