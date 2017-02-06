package com.ly.spider.test;


import java.io.File;
import java.util.Scanner;

import com.ly.spider.engines.BaiduEngine;
import com.ly.spider.engines.BingEngine;
import com.ly.spider.engines.Engine;
import com.ly.spider.engines.SoEngine;
import com.ly.spider.engines.SogouEngine;
import com.ly.spider.util.DBUtil;
import com.ly.spider.util.SecureUtil;


public class JsoupTest {

	public static void main(String[] args) throws Exception {
		DBUtil.emptyTable();
		SecureUtil.trustAllHttpsCertificates();
		//baidu https://www.baidu.com/s?wd=AI&pn=0 从第几条开始
		Engine baidu=new BaiduEngine("https://www.baidu.com/s?",
				"wd", "pn",
				"div#content_left","div.c-container","h3 a","div.c-span18 div.c-abstract,div.c-abstract,div.c-span18 p",
				new int[]{0,10,20});
		//sogou https://www.sogou.com/web?query=AI&page=1 0==1
		//没有选择"div#results"是因为搜狗结果页面多写了一个</div>,jsoup解析出错,只能用上一级的"div#main"
		Engine sogou=new SogouEngine("https://www.sogou.com/web?",
				"query", "page",
				"div#main","div.vrwrap,div.rb","h3 a","div.strBox div.str_info_div p.str_info,div.firstresult p.str_info,div.ft",
				new int[]{1,2,3});
		// 360 https://www.so.com/s?q=AI&pn=1 0==1
		Engine so=new SoEngine("https://www.so.com/s?",
				"q", "pn",
				"ul#m-result","li.res-list","h3 a","p.res-desc,div.res-comm-con p,div.res-comm-con div.res-desc,div.res-desc div.mh-summary",
				new int[]{1,2,3});
		//bing http://cn.bing.com/search?q=AI&first=0 从第几条开始
		Engine bing=new BingEngine("http://cn.bing.com/search?",
				"q", "first",
				"ol#b_results","li.b_algo","h2 a","div.b_caption p",
				new int[]{0,10,20});
				
		long btime=System.currentTimeMillis();
		System.out.println("begin time->"+btime);
		
		Scanner scanner=new Scanner(new File(args[0]));
		while(scanner.hasNextLine()){
			String word=scanner.nextLine();
			baidu.request(word);
			sogou.request(word);
			so.request(word);
			bing.request(word);
		}
		
		scanner.close();
		DBUtil.release();
		System.out.println("cost time->"+(System.currentTimeMillis()-btime)/1000+" seconds; "
				+"data count->"+DBUtil.count());
	}
}
