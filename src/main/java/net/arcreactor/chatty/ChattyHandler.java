package net.arcreactor.chatty;

import com.sun.servicetag.SystemEnvironment;
import net.arcreactor.chatty.maple.MapleCharacter;
import net.arcreactor.chatty.packets.AbstractPacket;
import net.arcreactor.chatty.packets.client.CharListRequestPacket;
import net.arcreactor.chatty.packets.client.CreateCharPacket;
import net.arcreactor.chatty.packets.client.ServerStatusRequestPacket;
import net.arcreactor.chatty.packets.client.PongPacket;
import net.arcreactor.chatty.packets.server.*;
import net.arcreactor.chatty.packets.server.LoginStatusPacket.ResponseCode;
import net.arcreactor.chatty.tools.HexTool;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.omg.PortableInterceptor.SUCCESSFUL;
import sun.security.util.Cache;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/11
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChattyHandler extends IoHandlerAdapter {

    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("Created an IO Session with something!");
    }

    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("Closed an IO Session with something!");
        session.close(true);
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        AbstractPacket received = (AbstractPacket)message;
        switch(received.getHeader()){
            case PingPacket.HEADER:
                session.write(new PongPacket().getPacketBytes());
                System.out.println("Ping pong!");
                break;
            case LoginStatusPacket.HEADER:
                ResponseCode responseCode = ((LoginStatusPacket)received).getResponse();
                System.out.println("Login status: " + responseCode);
                if(responseCode == ResponseCode.SUCCESS)
                    session.write(new CharListRequestPacket(0,0).getPacketBytes());
//                    session.write(new CreateCharPacket("abcdfeef", "Adventurer", "female").getPacketBytes());
                break;
            case ServerStatusPacket.HEADER:
                break;
            case CharListPacket.HEADER:
                List<MapleCharacter> characterList = ((CharListPacket)received).getChars();
                System.out.println(characterList.size() + " Characters available on this server:");
                for(MapleCharacter character : characterList){
                    System.out.println("(" +character.getId() + ")" + character.getName());
                }
                break;
            case AddNewCharPacket.HEADER:
                AddNewCharPacket newCharPacket = (AddNewCharPacket) received;
                System.out.println("Created a new character: (" + newCharPacket.getNewChar().getId() + ")" + newCharPacket.getNewChar().getName());
                break;
            default:
                System.out.println(received);
                break;
        }
    }

    public void messageSent(IoSession session, Object message) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
