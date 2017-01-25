package com.ly.spider.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.swing.text.html.HTML;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.ly.spider.app.Config;
import com.ly.spider.engines.BaiduEngine;


public class CommonTest {

	public static void main(String[] args) throws Exception {
		//testUrl();
		//testSogou();
		
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
