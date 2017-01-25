package com.ly.spider.engines;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ly.spider.app.Config;
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
		String pageurl=CommonUtil.getUrl(rawUrl);
		System.out.println("#####################\n"+pageurl);
		//pagecontent
		String pagecontent="";
		Response response=null;
		try {
			response = (Response) Jsoup.connect(rawUrl).timeout(Config.Timeout).execute();
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
		Elements summaryEles=searchItem.select("p.str.info,div.str-text-info,div.ft");
		if(summaryEles.size()==0){
			return null;
		}
		Element summaryEle=summaryEles.get(0);
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
