package com.ly.spider.test;


import com.ly.spider.app.Config;
import com.ly.spider.engines.BaiduEngine;
import com.ly.spider.engines.BingEngine;
import com.ly.spider.engines.Engine;
import com.ly.spider.engines.SoEngine;
import com.ly.spider.engines.SogouEngine;
import com.ly.spider.util.SecureUtil;


public class JsoupTest {

	public static void main(String[] args) throws Exception {
		SecureUtil.trustAllHttpsCertificates();
		
		//baidu https://www.baidu.com/s?wd=11&pn=2 从第几条开始
		Engine baidu=new BaiduEngine("http://www.baidu.com/s?",
				"wd", "pn",
				"div#content_left","div.c-container",
				new int[]{0});
		//bing http://cn.bing.com/search?q=11&first=3 从第几条开始
		Engine bing=new BingEngine("http://cn.bing.com/search?",
				"q", "first",
				"ol#b_results","li.b_algo",
				new int[]{0,10});
		//sogou https://www.sogou.com/web?query=qq&page=1 0==1
		//没有选择"div#results"是因为搜狗结果页面多写了一个</div>,jsoup解析出错,只能用上一级的"div#main"
		Engine sogou=new SogouEngine("https://www.sogou.com/web?",
				"query", "page",
				"div#main","div.vrwrap,div.rb",
				new int[]{1,2});
		// 360 https://www.so.com/s?q=11&pn=0 0==1
		Engine so=new SoEngine("https://www.so.com/s?",
				"q", "pn",
				"ul#m-result","li.res-list",
				new int[]{1});
		
		for(String word:Config.keywords){
			baidu.request(word);
//			bing.request(word);
//			sogou.request(word);
//			so.request(word);
		}
	}
}
