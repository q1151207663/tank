package pers.tz.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TankMsgEncoder extends MessageToByteEncoder<ObjectMsg> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ObjectMsg msg, ByteBuf out) throws Exception {
		/**
		 * 1.先写消息类型		4
		 * 2.再写消息长度		4
		 * 3.最后写消息数据	x
		 * 
		 * 其实还应该写一个消息的校验和
		 * 
		 */
		out.writeInt( msg.getMsgType().ordinal() );//ordinal() 位于枚举中的第几个元素
		byte[] content = msg.toBytes();
		out.writeInt(content.length);//长度
		out.writeBytes(content);//消息体
	}

}
