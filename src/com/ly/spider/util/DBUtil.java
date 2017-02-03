package com.ly.spider.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ly.spider.app.DataSource;
import com.ly.spider.bean.SearchItem;

public class DBUtil {
	private static String SQL="insert into page(pageurl,title,summary,content,queryword,updatetime,rank_baidu,rank_sogou,rank_360,rank_bing) "
			+ "values(?,?,?,?,?,?,?,?,?,?)";
	private static String CLEAR="delete from page";
	private static String ROLLBACK="alter table page AUTO_INCREMENT=1";
	private static String COUNT="select count(*) as num from page";
	public static int count(){
		int count=0;
		java.sql.Connection connection=null;
		PreparedStatement statement=null;
		try {
			connection = DataSource.getInstance().getConnection();
			//connection.setAutoCommit(false);
			statement=(PreparedStatement) connection.prepareStatement(COUNT);
			ResultSet rs=statement.executeQuery();
			if(rs.next()){
				count=rs.getInt("num");
			}
			statement.close();
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static void emptyTable(){
		java.sql.Connection connection=null;
		PreparedStatement statement=null;
		try {
			connection = DataSource.getInstance().getConnection();
			//connection.setAutoCommit(false);
			statement=(PreparedStatement) connection.prepareStatement(CLEAR);
			statement.executeUpdate();
			statement.close();
			System.out.println("clear page success");
			
			statement=(PreparedStatement) connection.prepareStatement(ROLLBACK);
			statement.executeUpdate();
			statement.close();
			System.out.println("id roll back to 1");
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void insertItem(SearchItem item){
		
		java.sql.Connection connection=null;
		PreparedStatement statement=null;
		try {
			connection = DataSource.getInstance().getConnection();
			//connection.setAutoCommit(false);
			//////////////过滤pageurl///////////////////
			String SEARCH="select * from page where pageurl=?";
			
			statement=(PreparedStatement) connection.prepareStatement(SEARCH);
			statement.setString(1, item.getPageurl());
			ResultSet rs=statement.executeQuery();
			if(rs.next()){
				
				if(item.getRank_baidu()>0 && rs.getInt("rank_baidu")==0){
					String UPDATE_BAIDU="update page set rank_baidu=? where pageurl=?";
					statement.close();
					statement=(PreparedStatement) connection.prepareStatement(UPDATE_BAIDU);
					statement.setInt(1, item.getRank_baidu());
					statement.setString(2, item.getPageurl());
					statement.executeUpdate();
				}else if(item.getRank_360()>0 && rs.getInt("rank_360")==0){
					String UPDATE_360="update page set rank_360=? where pageurl=?";
					statement.close();
					statement=(PreparedStatement) connection.prepareStatement(UPDATE_360);
					statement.setInt(1, item.getRank_360());
					statement.setString(2, item.getPageurl());
					statement.executeUpdate();
				}else if(item.getRank_sogou()>0 && rs.getInt("rank_sogou")==0){
					String UPDATE_SOGOU="update page set rank_sogou=? where pageurl=?";
					statement.close();
					statement=(PreparedStatement) connection.prepareStatement(UPDATE_SOGOU);
					statement.setInt(1, item.getRank_sogou());
					statement.setString(2, item.getPageurl());
					statement.executeUpdate();
				}else if(item.getRank_bing()>0 && rs.getInt("rank_bing")==0){
					String UPDATE_BING="update page set rank_bing=? where pageurl=?";
					statement.close();
					statement=(PreparedStatement) connection.prepareStatement(UPDATE_BING);
					statement.setInt(1, item.getRank_bing());
					statement.setString(2, item.getPageurl());
					statement.executeUpdate();
				}else{
					//nothing to do
				}
				statement.close();
				connection.close();
				return;
			}
			///////////////////////////////////////////
			statement.close();
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
