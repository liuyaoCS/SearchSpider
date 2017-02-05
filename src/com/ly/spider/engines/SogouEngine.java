package com.ly.spider.engines;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;

import com.ly.spider.app.Config;

public class SogouEngine extends Engine {

	
	public SogouEngine(String baseUrl, String intputName, String pageName,
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
			String body=response.body();
			
			if(body.startsWith("<meta")){
				int start=body.indexOf("(\"")+2;
				int end=body.indexOf("\")");
				pageurl=body.substring(start, end);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return pageurl;
	}
	

}
