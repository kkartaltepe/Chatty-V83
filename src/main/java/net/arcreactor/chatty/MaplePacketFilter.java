package net.arcreactor.chatty;

import net.arcreactor.chatty.packets.Packet;
import net.arcreactor.chatty.packets.server.PingPacket;
import net.arcreactor.chatty.packets.server.UnknownPacket;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/11
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class MaplePacketFilter extends IoFilterAdapter {

    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        IoBuffer messageBuffer = IoBuffer.wrap((byte[])message);
        short header = messageBuffer.getShort();
        Packet packet = getPacketFromHeader(header, messageBuffer);

        nextFilter.messageReceived(session, packet);
    }


    private Packet getPacketFromHeader(short header, IoBuffer message) {
        switch(header){
            case PingPacket.HEADER: //ping header
                return new PingPacket(message.array());
            default:
                return new UnknownPacket(message.array());
        }
    }
}
