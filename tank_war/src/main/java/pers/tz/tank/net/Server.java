package pers.tz.tank.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {
	static ChannelGroup cg = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	public static void main(String[] args) {
		
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup(1);
		
		ServerBootstrap b = new ServerBootstrap();
		try {
			ChannelFuture f = b.group(bossGroup ,workerGroup)
				.channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.DEBUG))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline()
						.addLast(new TankMsgEncoder())
						.addLast(new TankMsgDecoder())
						.addLast(new SimpleChannelInboundHandler<ObjectMsg>() {
//							@Override
//							public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//								cg.add(ctx.channel());
//								//netty神坑
//								//writeAndFlush可以接收Object类型的参数
//								//但是只有传入ByteBuf才有效果
////								cg.writeAndFlush("cnm?");
//								System.out.println("client is added");
//							}
							
							@Override
							public void channelActive(ChannelHandlerContext ctx) throws Exception {
								cg.add(ctx.channel());
							}

							@Override
							protected void channelRead0(ChannelHandlerContext ctx, ObjectMsg msg) throws Exception {
								/*
								 * 不！会！排除发送的client它自己 
								 * 每个client都会转发一次
								 * 
								 * 因此要client自己对消息进行排除
								 */
								cg.writeAndFlush(msg);
								System.out.println("server get msg :"+msg);
								/*ByteBuf buf = (ByteBuf)msg;
								byte[] bs = new byte[buf.readableBytes()];
								buf.readBytes(bs);
								System.out.println(new String(bs ,CharsetUtil.UTF_8));
								if( "_bye_".equals(new String(bs ,CharsetUtil.UTF_8)) ) {
									cg.remove(ctx.channel());
									ctx.channel().close();
									return ;
								}
//								cg.writeAndFlush(bs);  X!!!!!! 必须要ByteBuf!!!
								cg.writeAndFlush(Unpooled.copiedBuffer(bs));*/
							}

							@Override
							public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
								cause.printStackTrace();
								cg.remove(ctx.channel());
								ctx.close();
							}
							
							
						});
					}
				})
				.bind(8889)
				.sync();
			
			System.out.println("server is started");
			f.channel().closeFuture().sync();//channel被close了 这行代码才会往下执行
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
