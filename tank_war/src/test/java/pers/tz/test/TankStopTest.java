package pers.tz.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import pers.tz.tank.Dir;
import pers.tz.tank.net.MsgType;
import pers.tz.tank.net.TankMsgDecoder;
import pers.tz.tank.net.TankMsgEncoder;
import pers.tz.tank.net.TankStartMovingMsg;
import pers.tz.tank.net.TankStopMsg;

class TankStopTest {

	@Test
	void encodeTest() {
		
		EmbeddedChannel ch = new EmbeddedChannel();
		UUID id = UUID.randomUUID();
		TankStopMsg tankMsg = new TankStopMsg(id ,5 ,10);
		
		ch.pipeline()
		.addLast(new TankMsgEncoder());
		
		ch.writeAndFlush(tankMsg);
		
		ByteBuf buf = (ByteBuf)ch.readOutbound();
		MsgType msgType = MsgType.values()[buf.readInt()];
		System.out.println(msgType);
		assertEquals(MsgType.TankStartMoving, msgType);
		
		int len = buf.readInt();//长度
		assertEquals(28, len);
		
		
		int x = buf.readInt();//4
		int y = buf.readInt();//4
		Dir dir = Dir.values()[ buf.readInt() ];//4
		UUID uuid = new UUID( buf.readLong() ,buf.readLong() );
////		TankMsg tankMsgNew = new TankMsg();
////		tankMsgNew.parse(buf.array());
//		
		assertEquals(5, x);
		assertEquals(10, y);
		assertEquals(Dir.UP, dir);
		assertEquals(id, uuid);
		
	}
	
	
	
	@Test
	void testDecoder() {
		EmbeddedChannel ch = new EmbeddedChannel();
		UUID id = UUID.randomUUID();
		TankStartMovingMsg tankMsg = new TankStartMovingMsg(id ,5 ,10 ,Dir.UP);
		
		ByteBuf buf = Unpooled.buffer();
		byte[] content = tankMsg.toBytes();
		
		buf.writeInt(tankMsg.getMsgType().ordinal());
		buf.writeInt( content.length );
		buf.writeBytes(content);
		
//		ByteBuf buf = Unpooled.copiedBuffer(tankMsg.toBytes());
		
		ch.pipeline()
		.addLast(new TankMsgDecoder());
		
		ch.writeAndFlush(buf);
		
		ch.writeInbound(buf.duplicate());
		
		TankStartMovingMsg tankMsgNew = (TankStartMovingMsg)ch.readInbound();
		
		assertEquals(5, tankMsgNew.x);
		assertEquals(10, tankMsgNew.y);
		assertEquals(Dir.UP, tankMsgNew.dir);
		assertEquals(id, tankMsgNew.id);
	}
	
	
	
	

}
