package pers.tz.tank.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import pers.tz.tank.Dir;
import pers.tz.tank.Group;
import pers.tz.tank.Tank;
import pers.tz.tank.TankFrame;



/**
 * tank 加入的msg
 * @author Administrator
 *
 */
public class TankJoinMsg extends ObjectMsg{
	public int x ,y; 
	public Dir dir ; 
	public boolean moving ;
	public Group group ;
	public UUID id;//128位，16个字节
	public TankJoinMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.moving = moving;
		this.group = group;
		this.id = id;
	}
	public TankJoinMsg(Tank tank) {
		super();
		this.x = tank.getX();
		this.y = tank.getY();
		this.dir = tank.getDir();
		this.moving = tank.isMoving();
		this.group = tank.getGroup();
		this.id = tank.id;
	}
	public TankJoinMsg() {
		super();
	}
	
	public void parse(byte[] bytes) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
		//TODO:先读取TYPE信息，根据TYPE信息做不同的处理
		//略过消息类型
		
		try {
			this.x = dis.readInt();
//			System.out.println("x:"+x);
			this.y = dis.readInt();
			this.dir = Dir.values()[ dis.readInt() ];
			this.moving = dis.readBoolean();//boolean一个字节，其实真实的数据只有1bit但是，网络上传输的基本单位是字节。
			this.group = Group.values()[ dis.readInt() ];
			this.id = new UUID( dis.readLong() ,dis.readLong() );
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
				if(dis!=null) {
					dis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * i 将消息按照自己的规则 转换为byte[]
	 * i 读的时候按照同样的规则 将byte[] 转换为int、Boolean 等属性
	 * i 这就是所谓的自定义协议
	 * @return
	 */
	public byte[] toBytes() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		byte[] bs = null ;
		try {
			dos.writeInt(x);//4
			dos.writeInt(y);//4
			dos.writeInt(dir.ordinal());//4
			dos.writeBoolean(moving);//1
			dos.writeInt(group.ordinal());//4
			dos.writeLong(id.getMostSignificantBits());//64位，8个字节
			dos.writeLong(id.getLeastSignificantBits());//64位，8个字节
			dos.flush();
			
			bs = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if( baos!=null )
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				if( dos!=null )
					dos.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bs;
	}
	
	@Override
	public String toString() {
		return "TankMsg [x=" + x + ", y=" + y + ", dir=" + dir + ", moving=" + moving + ", group=" + group + ", id="
				+ id + "]";
	}
	
	public void handle() {
		/*
		 * 由于ChannelGroup不会排除自己，而是Set中所有的Channel都发一次
		 * 因此要client主动排除自己
		 * 
		 * 如果是自己的tank，就return
		 * 或者该tank在TankFrame的HashMap中已经存在，也return ，避免重复添加
		 */
		if( this.id.equals(TankFrame.INSTANCE.getMyTank().id) ||
				TankFrame.INSTANCE.findByUUID(this.id) != null ) return ;
		
		Tank tank = new Tank(this);//将新加入的tank消息转化成tank对象
		TankFrame.INSTANCE.addTank(tank);//将tank对象添加到HashMap
	
		
		/*
		 * 如果不加下面这行代码
		 * 对于前面的client能够获取新加入的client并刷新
		 * 而后加入的client无法获取前面加入的client信息
		 * 
		 * 因此要让每个client都重新发送一遍自己的信息
		 * (所有)的client都重发一遍。
		 * 
		 * 为什么不会死循环无限发，是因为前面的if判断
		 * TankFrame.INSTANCE.findByUUID(this.id) != null
		 * 
		 * 这里非常绕，仔细想
		 */
		Client.INSTANCE.sent(new TankJoinMsg(TankFrame.INSTANCE.getMyTank()));
		
	}
	@Override
	public MsgType getMsgType() {
		return MsgType.TankJoin;
	}
	
	
}
