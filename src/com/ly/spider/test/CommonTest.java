package com.ly.spider.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.ly.spider.app.Config;
import com.ly.spider.app.JsoupConn;
import com.ly.spider.engines.BaiduEngine;
import com.ly.spider.util.DBUtil;
import com.ly.spider.util.SecureUtil;


public class CommonTest {

	public static void main(String[] args) throws Exception {
		//testUrl();
		//testSogou();
		//testSo();
		//testBD();
		//System.out.println(DBUtil.count());
		BaiduEngine bEngine=new BaiduEngine(null, null, null, null, null, null, null, null);
		System.out.println(bEngine.getClass().getSimpleName());
	}

	private static void testBD(){
		//String url="https://www.baidu.com/s?wd=AI&pn=0";
		try {
			SecureUtil.trustAllHttpsCertificates();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url="https://www.baidu.com/s?wd=AI&pn=0";
		Document doc = null;
		try {
			doc = JsoupConn.getInstance(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(doc.html());
	}
	private static void testSo() {
		// TODO Auto-generated method stub
		String url="https://www.so.com/s?q=AI&pn=1";
	}


	private static void testSogou() {
		// TODO Auto-generated method stub
		String url="http://blog.sina.com.cn/s/blog_7ebc46500101gtff.html";
		Connection conn = Jsoup.connect(url);
		Document doc = null;
		try {
			doc = conn.timeout(Config.Timeout).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(doc.html());
	
		Elements container = doc.select("div#main div");
		
		System.out.println(container.html());
		
	}

	private static void testUrl() throws UnsupportedEncodingException {
		//		String textString="哈哈,<!__www--$$@&&>";
		//		String encodeString=URLEncoder.encode(textString, "utf8");
		//		System.out.println(URLDecoder.decode(encodeString, "utf8"));
				
				String url="http://www.so.com/link?url=http%3A%2F%2Fwww.acaicp.com%2Fgd11x5%2F&q=11&ts=1485311050&t=7290a5a018af6d686be79e77b808955&src=haosou";
				URI test=URI.create(url); 
				String rawQuery=test.getRawQuery();
				String[] querys=rawQuery.split("&");
				for(String query : querys){
					String[] kvs=query.split("=");
					//for()
					if(kvs.length>1 && kvs[0].equals("url")){
						System.out.println("url->"+URLDecoder.decode(kvs[1],"utf8"));
						break;
					}
				}
				
				
				Response response=null;
				try {
					response = (Response) Jsoup.connect(url).timeout(Config.Timeout).execute();
					String pageurl=response.url().toString();
					System.out.println(pageurl);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
}
