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

	@Override
	protected String handleStyle(String content) {
		// TODO Auto-generated method stub
		String text=super.handleStyle(content);
		return text.replaceAll("<(span|/span).*?>", "")
				.replaceAll("<strong>", "<em>").replaceAll("</strong>", "</em>");
	}
	

}
