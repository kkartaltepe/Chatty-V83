package net.arcreactor.chatty.maple;

import org.apache.mina.core.buffer.IoBuffer;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/26/11
 * Time: 8:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapleCharacter {
    public enum Gender{
        male,
        female
    }
    int id;
    String name;
    private Gender gender;
    byte skinColor;
    int face,hair;
    short level;
    int experience;
    short fame;
    int map;
    byte spawnPoint;



    public MapleCharacter(IoBuffer buffer, boolean addStats, boolean addLook){
        byte checkByte;
        id = buffer.getInt();
        try {
            name = buffer.getString(13, Charset.forName("US-ASCII").newDecoder()) + ",";
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }

        gender = buffer.get()==0?Gender.male:Gender.female; // my gender
        skinColor = buffer.get(); // my skin color
        face = buffer.getInt(); // my face
        hair = buffer.getInt(); // my hair
        buffer.getLong(); // 3 longs of 0's.... AKA RNG seed
        buffer.getLong();
        buffer.getLong();
        level = buffer.get(); // my level
        buffer.getShort(); //11 shorts for all ur stats, see MaplePacketHelper.AddCharStats
        buffer.getShort();
        buffer.getShort();
        buffer.getShort();
        buffer.getShort();
        buffer.getShort();
        buffer.getShort();
        buffer.getShort();
        buffer.getShort();
        buffer.getShort();
        buffer.getShort();
        experience = buffer.getInt(); // my exp
        fame = buffer.getShort(); // my fame
        buffer.getInt(); // gatachapon exp
        map = buffer.getInt(); //mapid where char is
        spawnPoint = buffer.get(); // my spawn point
        buffer.getInt(); // some zeros....
        //end of addStats begining of addLook
        buffer.get(); //gender again
        buffer.get(); //skin color again
        buffer.getInt(); // face type
        buffer.get(); //mega or not
        buffer.getInt(); // hair type
        checkByte = buffer.get();
        while((checkByte & 0xFF) != (0xFF & 0xFF)){	//while the byte doesnt mark the end of items.
            buffer.getInt();	// get the item that is here
            checkByte = buffer.get(); // get the next byte

        }
        //masked items start now
        checkByte = buffer.get();
        while((checkByte & 0xFF) != (0xFF & 0xFF)){	//while the byte doesnt mark the end of items.
            buffer.getInt();	// get the item that is here
            checkByte = buffer.get(); // get the next byte
        }
        buffer.getInt(); // a cash weapon if u have one
        buffer.getInt();
        buffer.getInt();
        buffer.getInt();
    }


}
