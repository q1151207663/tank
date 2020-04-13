package pers.tz.tank;

public class NullUtil {
	
	/**
	 * i 检查对象是否为null
	 * @param obj
	 * @return 为null返回true
	 */
	public static boolean checkNullObject(Object obj) {
		return obj==null?true:false;
	}
	
	
	/**
	 * i 检查单个string是否为null或为空串
	 * @param str
	 * @return 为null返回true
	 */
	public static boolean checkNullString(String str) {
		return str==null||str.equals("")?true:false;
	}
	
	
	/**
	 * i 检查多个string是否为null或为空串
	 * @param strs
	 * @return 为null返回true
	 */
	public static boolean checkNullStrings(String... strs) {
		for( String str : strs ) {
			if( str==null || str.equals("") ) {
				return true;
			}
		}
		return false;
	}
	
	
	
}
