package com.ly.spider.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ly.spider.app.DataSource;
import com.ly.spider.bean.SearchItem;

public class DBUtil {
	private static String SQL="insert into page(pageurl,title,summary,content,queryword,updatetime,rank_baidu,rank_sogou,rank_360,rank_bing) "
			+ "values(?,?,?,?,?,?,?,?,?,?)";
	public static void insertItem(SearchItem item){
		
		java.sql.Connection connection=null;
		PreparedStatement statement=null;
		try {
			connection = DataSource.getInstance().getConnection();
			//connection.setAutoCommit(false);
			statement=(PreparedStatement) connection.prepareStatement(SQL);
			
			Date date = new Date();//获得系统时间.
			String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			System.out.println(nowTime);
			
			String content = item.getContent();
			try {
				content = URLEncoder.encode(content, "utf8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			statement.setString(1, item.getPageurl());
			statement.setString(2, item.getTitle());
			statement.setString(3, item.getSummary());
			statement.setString(4, content);
			statement.setString(5, item.getQueryword());
			statement.setTimestamp(6, Timestamp.valueOf(nowTime));
			statement.setInt(7, item.getRank_baidu());
			statement.setInt(8, item.getRank_sogou());
			statement.setInt(9, item.getRank_360());
			statement.setInt(10, item.getRank_bing());
			
			statement.executeUpdate();
			
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}