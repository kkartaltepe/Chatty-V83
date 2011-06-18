package net.arcreactor.chatty.crytography;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/11
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapleEncryptionCodec implements ProtocolCodecFactory {

   	private final ProtocolEncoder encoder;
	private final ProtocolDecoder decoder;

	public MapleEncryptionCodec() {
		encoder = new MapleEncryptionEncoder();
		decoder = new MapleEncryptionDecoder();
	}

    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return encoder;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
