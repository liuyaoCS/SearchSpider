package com.ly.spider.engines;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ly.spider.app.Config;
import com.ly.spider.bean.SearchItem;

public class BaiduEngine extends Engine {

	
	public BaiduEngine(String baseUrl, String intputName, String pageName,
			String itemTag,String listTag,int[] steps) {
		super(baseUrl, intputName, pageName, itemTag, listTag,steps);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SearchItem parseItem(Element searchItem,String wd,int rank) {
		// TODO Auto-generated method stub

		Elements urlEles=searchItem.select("h3 a");
		if(urlEles.size()==0){
			return null;
		}
		
		Element urlEle = urlEles.get(0);
		//pageurl 
		String rawUrl=urlEle.attr("href");
		String pageurl="";
		String pagecontent="";
		Response response=null;
		try {
			response = (Response) Jsoup.connect(rawUrl).timeout(Config.Timeout).execute();
			pageurl=response.url().toString();
			System.out.println("#####################\n"+pageurl);
			//pagecontent
			pagecontent=response.body();
			//System.out.println(pagecontent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		//title
		String title=urlEle.text();
		System.out.println(title);
		//summary
		Elements summaryEles=searchItem.select("div.c-span18 div.c-abstract"
				+ ",div.c-abstract"
				+ ",div.c-span18 p");
		if(summaryEles.size()==0){
			return null;
		}
		//过滤 a cite 标签
		Element summaryEle;
		if(summaryEles.size()==3){//div.c-span18 p
			summaryEle=summaryEles.get(1);
		}else{
			summaryEle=summaryEles.get(0);
		}
		
		if(summaryEle.select("a,cite").size()>0){
			return null;
		}
		String summary=summaryEle.text();
		System.out.println("summary->"+summary);
		//rank
		int rank_baidu=rank;
		System.out.println("rank-baidu->"+rank_baidu);
		
		SearchItem item=new SearchItem();
		item.setPageurl(pageurl);
		item.setContent(pagecontent);
		item.setQueryword(wd);
		item.setSummary(summary);
		item.setTitle(title);
		item.setRank_baidu(rank_baidu);
		item.setRank_sogou(0);
		item.setRank_360(0);
		item.setRank_bing(0);
	
		return item;
	}
	

}
