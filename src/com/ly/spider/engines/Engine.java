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
import com.ly.spider.util.DBUtil;

public abstract class Engine {
	protected String baseUrl;
	protected String intputName;
	protected String pageName;
	protected String containerTag;
	protected String listTag;
	protected String itemATag;
	protected String itemSummaryTag;
	protected int[] steps;
	
	public Engine(String baseUrl,String intputName,String pageName,String containTag,String listTag,String itemATag,String itemSummaryTag,int[] steps) {
		// TODO Auto-generated constructor stub
		this.baseUrl=baseUrl;
		this.intputName=intputName;
		this.pageName=pageName;
		this.containerTag=containTag;
		this.listTag=listTag;
		this.itemATag=itemATag;
		this.itemSummaryTag=itemSummaryTag;
		this.steps=steps;
	}
	protected abstract String getPageUrl(String rawUrl);
	protected  SearchItem parseItem(Element searchItem,String wd,int rank){
		Elements urlEles=searchItem.select(itemATag);
		if(urlEles.size()==0){
			return null;
		}
		Element urlEle = urlEles.get(0);
		System.out.println("############"+getClass().getSimpleName()+" parse begin############");
		//pageurl 
		String rawUrl=urlEle.attr("href");
		String pageurl=getPageUrl(rawUrl);
		if(pageurl==null || pageurl.equals("")){
			return null;
		}
		System.out.println("pageurl->"+pageurl);
		//pagecontent
		String pagecontent="";
		try {
			Response response = (Response) Jsoup.connect(pageurl).timeout(Config.Timeout).execute();
			pagecontent=response.body();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
		//title
		String title=urlEle.text();
		System.out.println("title->"+title);
		//summary
		Elements summaryEles=searchItem.select(itemSummaryTag);
		if(summaryEles.size()==0){
			return null;
		}
		Element summaryEle;
		if(summaryEles.size()==3){//just for baidu->div.c-span18 p
			summaryEle=summaryEles.get(1);
		}else{
			summaryEle=summaryEles.get(0);
		}
		//过滤 a cite 标签
		if(summaryEle.select("a,cite").size()>0){
			return null;
		}
		String summary=summaryEle.text();
		System.out.println("summary->"+summary);
		//rank
		int rank_baidu=0,rank_sogou=0,rank_360=0,rank_bing=0;
		switch (getClass().getSimpleName()) {
			case "BaiduEngine":
				rank_baidu=rank;
				break;
			case "SogouEngine":
				rank_sogou=rank;			
				break;
			case "SoEngine":
				rank_360=rank;
				break;
			case "BingEngine":
				rank_bing=rank;
				break;
			default:
				break;
		}
		System.out.println("rank->"+rank);
		
		SearchItem item=new SearchItem();
		item.setPageurl(pageurl);
		item.setContent(pagecontent);
		item.setQueryword(wd);
		item.setSummary(summary);
		item.setTitle(title);
		item.setRank_baidu(rank_baidu);
		item.setRank_sogou(rank_sogou);
		item.setRank_360(rank_360);
		item.setRank_bing(rank_bing);
	
		return item;
	}
	public void request(String word){		  
	    
		for(int i:steps){
			
			String url=baseUrl+intputName+"="+word+"&"+pageName+"="+i;
			
			Document doc = null;
			try {
				doc = JsoupConn.getInstance(url).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(doc==null){
				break;
			}
			
			Elements containers = doc.select(containerTag);
			if(containers==null){
				break;
			}
			Element container=containers.get(0);
			Elements list=container.select(listTag);
			for (Element searchItemEle : list){
				int seq=(i>0 && i<10)?(i*10-10):i;
				seq+=list.indexOf(searchItemEle)+1;
				SearchItem item=parseItem(searchItemEle, word, seq);
				if(item!=null){
					DBUtil.insertItem(item);
				}
			}
		}
	}
}
