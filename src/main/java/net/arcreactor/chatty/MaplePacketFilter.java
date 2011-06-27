package net.arcreactor.chatty;

import net.arcreactor.chatty.packets.AbstractPacket;
import net.arcreactor.chatty.packets.server.*;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

import java.nio.ByteOrder;

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
        messageBuffer.order(ByteOrder.LITTLE_ENDIAN);

        short header = messageBuffer.getShort();
        AbstractPacket abstractPacket = getPacketFromHeader(header, messageBuffer);

        nextFilter.messageReceived(session, abstractPacket);
    }

    private AbstractPacket getPacketFromHeader(short header, IoBuffer message) {
        switch(header){
            case PingPacket.HEADER: //ping header
                return new PingPacket(message.array());
            case LoginStatusPacket.HEADER:
                return new LoginStatusPacket(message.array());
            case CharListPacket.HEADER:
                return new CharListPacket(message.array());
            case AddNewCharPacket.HEADER:
                return new AddNewCharPacket(message.array());
            default:
                return new UnknownPacket(message.array());
        }
    }
}
