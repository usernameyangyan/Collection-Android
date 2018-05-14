package com.youngmanster.collection.been;

import com.youngmanster.collection.been.wechat.WeChatNews;

import java.util.List;

/**
 * Created by yangyan
 * on 2018/5/13.
 */

public class WeChatNewsResult {
	private int code;
	private String msg;
	private List<WeChatNews> newslist;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<WeChatNews> getNewslist() {
		return newslist;
	}

	public void setNewslist(List<WeChatNews> newslist) {
		this.newslist = newslist;
	}
}
