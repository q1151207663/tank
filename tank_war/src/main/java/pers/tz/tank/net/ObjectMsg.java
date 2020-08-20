package pers.tz.tank.net;


/**
 * i 所有消息都必须继承于ObjectMsg
 * @author Administrator
 *
 */
public abstract class ObjectMsg {
	
	public abstract void handle();
	public abstract byte[] toBytes();
	public abstract void parse(byte[] bytes);
	public abstract MsgType getMsgType();//区分消息类型
	
	
}
