package com.ly.spider.engines;


public class BingEngine extends Engine {

	
	public BingEngine(String baseUrl, String intputName, String pageName,
			String containerTag,String listTag,String itemATag,String itemSummaryTag,int[] steps) {
		super(baseUrl, intputName, pageName, containerTag, listTag,itemATag,itemSummaryTag,steps);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getPageUrl(String rawUrl) {
		// TODO Auto-generated method stub
		return rawUrl;
	}
	

}
