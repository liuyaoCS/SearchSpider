package com.ly.spider.app;

import java.beans.PropertyVetoException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSource {
	private static ComboPooledDataSource cpds;
	public static ComboPooledDataSource getInstance(){
		if(cpds==null){
			cpds=new ComboPooledDataSource(); 
			try {
				configMysql();
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cpds;
	}
	private  static void configMysql()throws PropertyVetoException{
		cpds=new ComboPooledDataSource();
		cpds.setDriverClass("com.mysql.jdbc.Driver");
//		cpds.setJdbcUrl("jdbc:mysql://localhost:3306/metasearch");
//		cpds.setUser("root");
//		cpds.setPassword("985910");
		cpds.setJdbcUrl("jdbc:mysql://10.10.65.253:3306/metasearch");
		cpds.setUser("metasearch_user");
		cpds.setPassword("EaO739ZXW8c13Y75UU476EN3");
	}
	/**
	 * 长时间不用记得释放数据库资源
	 */
	public static void releaseDS(){
		if(cpds!=null){
			cpds.close();
			cpds=null;
		}
	}
}
