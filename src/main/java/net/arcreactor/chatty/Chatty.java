package net.arcreactor.chatty;

import net.arcreactor.chatty.crytography.MapleEncryptionCodec;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/17/11
 * Time: 11:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class Chatty {

    public Chatty(){
        IoConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("aes", new ProtocolCodecFilter(new MapleEncryptionCodec()));
        connector.getFilterChain().addLast("to-packet", new MaplePacketFilter());
        connector.setHandler(new ChattyHandler());

        connector.connect(new InetSocketAddress("216.24.200.86", 8484));
    }
}
