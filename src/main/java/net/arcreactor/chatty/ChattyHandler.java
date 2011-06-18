package net.arcreactor.chatty;

import net.arcreactor.chatty.packets.Packet;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/11
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChattyHandler implements IoHandler {

    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("Created an IO Session with something!");
    }

    public void sessionOpened(IoSession session) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("Closed an IO Session with something!");
        session.close(true);
    }

    public void sessionIdle(IoSession session, IdleStatus idleStatus) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void exceptionCaught(IoSession session, Throwable throwable) throws Exception {
        throwable.printStackTrace();
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("Message Received!");
        Packet received = (Packet)message;
        System.out.println(received);
    }

    public void messageSent(IoSession session, Object message) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
