package pers.tz.tank.net;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TankMsgDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		/*
		 * TCP粘包拆包
		 * 判断每次传递过来的数据包大小超过消息体的大小之后在读，并且每次读消息体的大小个字节
		 * 
		 * 
		 * 对于不同的消息体的拆包
		 * 
		 * 首先约定各个消息体的前几个字节存放该消息体的总字节大小
		 * 然后再根据不同的消息体读取不同的字节数量
		 * 
		 * 此时消息的结构是前4个字节存放消息类型
		 * 后4个字节存放消息长度
		 * 
		 */
		if( in.readableBytes()<8 ) return;
		
		in.markReaderIndex();//记录从哪里开始读的
		
		MsgType msgType = MsgType.values()[ in.readInt() ];//消息类型
		int len = in.readInt();//消息长度
		
		//如果现在可读的字节小于消息的长度 ，则不读，并将netty的读指针位置重置
		if( in.readableBytes()<len ) {
			in.resetReaderIndex();
			return ;
		}
		
		byte[] bs = new byte[len];
		in.readBytes(bs);//读取消息
		
		ObjectMsg msg = null;
		
		/*
		 * i 用反射，增加新的消息，这里不需要修改代码
		 * 
		 * Class.forName(msg.toString + "Msg").constructor().newInstance();
		 * 
		 */
		switch( msgType ) {
		case TankJoin:
			msg = new TankJoinMsg();
			break;
		case TankStartMoving:
			msg = new TankStartMovingMsg();
			break;
		case TankStop:
			msg = new TankStopMsg();
			break ;
		default:
			break;
		}
		
		msg.parse(bs);
		out.add(msg);
		
//		TankMsg tankMsg = new TankMsg();
//		tankMsg.x = in.readInt();
//		tankMsg.y = in.readInt();
//		tankMsg.dir = Dir.values()[ in.readInt() ];
//		tankMsg.moving = in.readBoolean();//boolean一个字节，其实真实的数据只有1bit但是，网络上传输的基本单位是字节。
//		tankMsg.group = Group.values()[ in.readInt() ];
//		tankMsg.id = new UUID( in.readLong() ,in.readLong() );
		
		/*TankMsg tankMsg = new TankMsg();
		System.out.println( in.array().length );
		System.out.println( Arrays.toString(in.array()) );
		tankMsg.parse(in.array());*/
//		out.add(tankMsg);
	}


}
