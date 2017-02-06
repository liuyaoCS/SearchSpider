package com.ly.spider.engines;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

public class SoEngine extends Engine {

	
	public SoEngine(String baseUrl, String intputName, String pageName,
			String containerTag,String listTag,String itemATag,String itemSummaryTag,int[] steps) {
		super(baseUrl, intputName, pageName, containerTag, listTag,itemATag,itemSummaryTag,steps);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getPageUrl(String rawUrl) {
		// TODO Auto-generated method stub
		String ret=rawUrl;
		String rawQuery=URI.create(rawUrl).getRawQuery();
		if(rawQuery==null){
			return null;
		}
		String[] querys=rawQuery.split("&");
		for(String query : querys){
			String[] kvs=query.split("=");
			if(kvs.length>1 && kvs[0].equals("url")){
				try {
					ret=URLDecoder.decode(kvs[1],"utf8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				break;
			}
		}
		return ret;
	}

	@Override
	protected String handleStyle(String content) {
		// TODO Auto-generated method stub
		String text=super.handleStyle(content);
		return text.replaceAll("<(span|/span).*?>", "")
				   .replaceAll("<img.+?/>", "");
	}
	

}
