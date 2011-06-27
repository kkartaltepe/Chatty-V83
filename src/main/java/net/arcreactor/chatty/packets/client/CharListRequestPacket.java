package net.arcreactor.chatty.packets.client;

import com.sun.xml.internal.ws.api.message.Header;
import net.arcreactor.chatty.packets.AbstractPacket;
import org.apache.mina.core.buffer.IoBuffer;

import java.nio.ByteOrder;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/26/11
 * Time: 7:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class CharListRequestPacket extends AbstractPacket{
    public static final short HEADER = 0x05;

    public CharListRequestPacket(int server, int channel) {
        IoBuffer content = IoBuffer.allocate(2+4+4);
        content.order(ByteOrder.LITTLE_ENDIAN);

        content.putShort(HEADER);
        content.putInt(server);
        content.putInt(channel);

        packet = content.flip().array().clone();
    }

    @Override
    public short getHeader() {
        return HEADER;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
