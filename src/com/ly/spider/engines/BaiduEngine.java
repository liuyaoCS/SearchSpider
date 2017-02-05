package com.ly.spider.engines;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;

import com.ly.spider.app.Config;

public class BaiduEngine extends Engine {

	
	public BaiduEngine(String baseUrl, String intputName, String pageName,
			String containerTag,String listTag,String itemATag,String itemSummaryTag,int[] steps) {
		super(baseUrl, intputName, pageName, containerTag, listTag,itemATag,itemSummaryTag,steps);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getPageUrl(String rawUrl) {
		// TODO Auto-generated method stub
		String pageurl="";		
		try {
			Response response = (Response) Jsoup.connect(rawUrl).timeout(Config.Timeout).execute();
			pageurl=response.url().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return pageurl;
	}
	

}
