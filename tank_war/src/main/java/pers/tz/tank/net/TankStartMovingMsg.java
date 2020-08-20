package pers.tz.tank.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import pers.tz.tank.Dir;
import pers.tz.tank.Tank;
import pers.tz.tank.TankFrame;

public class TankStartMovingMsg extends ObjectMsg{
	
	public UUID id ;
	public int x ,y;
	public Dir dir; 
	
	
	

	public TankStartMovingMsg() {
		super();
	}

	public TankStartMovingMsg(UUID id, int x, int y, Dir dir) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public TankStartMovingMsg(Tank tank) {
		super();
		this.x = tank.getX();
		this.y = tank.getY();
		this.dir = tank.getDir();
		this.id = tank.id;
	}

	@Override
	public void handle() {
		//如果是自己移动的消息就不做处理
		if( this.id.equals(TankFrame.INSTANCE.getMyTank().id) ) return ;
		
		//否则是别人移动 ，获取该tank
		Tank t = TankFrame.INSTANCE.findByUUID(id);
		if( t==null ) return ;
		
		//重新设置它的信息
		t.setMoving(true);
		t.setX(this.x);
		t.setY(this.y);
		t.setDir(this.dir);
		
	}

	@Override
	public byte[] toBytes() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		byte[] bs = null ;
		try {
			dos.writeInt(x);//4
			dos.writeInt(y);//4
			dos.writeInt(dir.ordinal());//4
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
	public void parse(byte[] bytes) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
		//TODO:先读取TYPE信息，根据TYPE信息做不同的处理
		//略过消息类型
		
		try {
			this.x = dis.readInt();
			this.y = dis.readInt();
			this.dir = Dir.values()[ dis.readInt() ];
			this.id = new UUID( dis.readLong() ,dis.readLong() );
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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

	@Override
	public MsgType getMsgType() {
		return MsgType.TankStartMoving;
	}

}
