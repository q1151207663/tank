package pers.tz.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
	static Properties prop = null ;
	
	static {
		prop = new Properties();
		try {
//			System.out.println(prop.getClass().getClassLoader());->null
			
			prop.load(PropertyMgr.class.getClassLoader().getResourceAsStream("folder/config"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String get(String key) {
		if( NullUtil.checkNullObject(prop) ) return null ;
		if( NullUtil.checkNullString(key) ) return null ;
		return prop.getProperty(key);
	}
}
