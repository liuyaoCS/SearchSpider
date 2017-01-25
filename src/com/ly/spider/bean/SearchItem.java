package com.ly.spider.bean;

public class SearchItem {
	//int id;
	String pageurl;
	String title;
	String summary;
	String content;
	String queryword;
	//String updatetime;
	int rank_baidu;
	int rank_sogou;
	int rank_360;
	int rank_bing;
	public String getPageurl() {
		return pageurl;
	}
	public void setPageurl(String pageurl) {
		this.pageurl = pageurl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String pagecontent) {
		this.content = pagecontent;
	}
	public String getQueryword() {
		return queryword;
	}
	public void setQueryword(String queryword) {
		this.queryword = queryword;
	}
	public int getRank_baidu() {
		return rank_baidu;
	}
	public void setRank_baidu(int rank_baidu) {
		this.rank_baidu = rank_baidu;
	}
	public int getRank_sogou() {
		return rank_sogou;
	}
	public void setRank_sogou(int rank_sogou) {
		this.rank_sogou = rank_sogou;
	}
	public int getRank_360() {
		return rank_360;
	}
	public void setRank_360(int rank_360) {
		this.rank_360 = rank_360;
	}
	public int getRank_bing() {
		return rank_bing;
	}
	public void setRank_bing(int rank_bing) {
		this.rank_bing = rank_bing;
	}
	
}
