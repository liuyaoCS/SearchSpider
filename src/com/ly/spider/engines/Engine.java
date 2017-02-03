package com.ly.spider.engines;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Request;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ly.spider.app.Config;
import com.ly.spider.app.JsoupConn;
import com.ly.spider.bean.SearchItem;
import com.ly.spider.util.DBUtil;
import com.ly.spider.util.SecureUtil;

public abstract class Engine {
	protected String baseUrl;
	protected String intputName;
	protected String pageName;
	protected String containerTag;
	protected String listTag;
	protected int[] steps;
	public String getBaseUrl() {
		return baseUrl;
	}
	public String getIntputName() {
		return intputName;
	}
	public String getPageName() {
		return pageName;
	}
	public String getContainerTag() {
		return containerTag;
	}
	public String getListTag() {
		return listTag;
	}
	public int[] getSteps() {
		return steps;
	}
	public Engine(String baseUrl,String intputName,String pageName,String itemTag,String listTag,int[] steps) {
		// TODO Auto-generated constructor stub
		this.baseUrl=baseUrl;
		this.intputName=intputName;
		this.pageName=pageName;
		this.containerTag=itemTag;
		this.listTag=listTag;
		this.steps=steps;
	}
	protected abstract SearchItem parseItem(Element searchItem,String wd,int pn);
	public void request(String word){		  
	    
		for(int i:getSteps()){
			
			String url=baseUrl+intputName+"="+word+"&"+pageName+"="+i;
			
			Document doc = null;
			try {
				doc = JsoupConn.getInstance(url).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(doc==null)break;
			
			Element container = doc.select(getContainerTag()).get(0);
			Elements list=container.select(getListTag());
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
