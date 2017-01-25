package com.ly.spider.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

public class CommonUtil {
	public static String getUrl(String rawUrl){
		String ret=rawUrl;
		String rawQuery=URI.create(rawUrl).getRawQuery();
		String[] querys=rawQuery.split("&");
		for(String query : querys){
			String[] kvs=query.split("=");
			if(kvs.length>1 && kvs[0].equals("url")){
				try {
					ret=URLDecoder.decode(kvs[1],"utf8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
				break;
			}
		}
		return ret;
	}
}
