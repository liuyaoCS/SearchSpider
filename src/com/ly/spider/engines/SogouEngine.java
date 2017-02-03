package com.ly.spider.engines;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ly.spider.app.Config;
import com.ly.spider.app.JsoupConn;
import com.ly.spider.bean.SearchItem;
import com.ly.spider.util.CommonUtil;

public class SogouEngine extends Engine {

	
	public SogouEngine(String baseUrl, String intputName, String pageName,
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
		String rawContent="";
		String pagecontent="";
		Response response=null;
		try {
			response = (Response) Jsoup.connect(rawUrl).timeout(Config.Timeout).execute();
			rawContent=response.body();
			pageurl=CommonUtil.getSougouUrl(rawContent);
			if(pageurl==null || pageurl.equals("")){
				return null;
			}
			System.out.println("#####################\n"+pageurl);
			//pagecontent
			response = (Response) Jsoup.connect(pageurl).timeout(Config.Timeout).execute();
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
		Elements summaryEles=searchItem.select("div.strBox div.str_info_div p.str_info"
				+ ",div.firstresult p.str_info"
				+ ",div.ft");
		if(summaryEles.size()==0){
			return null;
		}
		//过滤 a cite 标签
		Element summaryEle=summaryEles.get(0);
		if(summaryEle.select("a,cite").size()>0){
			return null;
		}
		String summary=summaryEle.text();
		System.out.println("summary->"+summary);
		//rank
		int rank_sogou=rank;
		System.out.println("rank-sogou->"+rank_sogou);
		
		SearchItem item=new SearchItem();
		item.setPageurl(pageurl);
		item.setContent(pagecontent);
		item.setQueryword(wd);
		item.setSummary(summary);
		item.setTitle(title);
		item.setRank_baidu(0);
		item.setRank_sogou(rank_sogou);
		item.setRank_360(0);
		item.setRank_bing(0);
	
		return item;
	}
	

}
