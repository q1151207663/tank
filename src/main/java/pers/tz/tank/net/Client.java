package pers.tz.tank.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import pers.tz.tank.TankFrame;

public class Client {

	public static final Client INSTANCE = new Client();
	private Channel channel;
	
	private Client() {}
	
	public void connect() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup(1);

		try {
			Bootstrap b = new Bootstrap();
			ChannelFuture future = b.group(group).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline()
							.addLast(new TankMsgEncoder())
							.addLast(new TankMsgDecoder())
							.addLast(new SimpleChannelInboundHandler<ObjectMsg>() {

								@Override
								public void channelRead0(ChannelHandlerContext ctx, ObjectMsg msg) throws Exception {
									
									/*
									 *  对于不同的消息体的拆包
									 * 
									 * 方案一：
									 * 首先约定各个消息体的前几个字节存放该消息体的总字节大小
									 * 然后再根据不同的消息体读取不同的字节数量
									 * 
									 * 方案二：
									 * 
									 */
									msg.handle();
									
//									if( msg.id.equals(TankFrame.INSTANCE.getMyTank().id) ||
//											TankFrame.INSTANCE.findByUUID(msg.id) != null ) return ;
//									
//									System.out.println(msg);
//									Tank tank = new Tank(msg);
//									TankFrame.INSTANCE.addTank(tank);
//								
//									ctx.writeAndFlush(new TankMsg(TankFrame.INSTANCE.getMyTank()));
								}

								@Override
								public void channelActive(ChannelHandlerContext ctx) throws Exception {
									System.out.println("client active:"+TankFrame.INSTANCE.getMyTank());
									ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMyTank()));
								}
							});
						}
					}).connect("localhost", 8889).sync();
			future.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
						System.out.println("connected!");
						channel = future.channel();
					}else
						System.out.println("no connected!");
				}
			});
			
			future.channel().closeFuture().sync();
			
		} finally {
			group.shutdownGracefully();
		}
	}
	
	public void sent(String msg) {
		ByteBuf buffer = Unpooled.copiedBuffer(msg.getBytes());
		channel.writeAndFlush(buffer);
	}
	
	public void sent(ObjectMsg msg) {
		channel.writeAndFlush(msg);
	}
	
	public void sentClose() {
		this.sent("_bye_");
	}

	public static Client getInstance() {
		return INSTANCE;
	}
}
