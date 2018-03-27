package com.youngmanster.collection.been;



import java.io.Serializable;

/**
 * 根据接口规则写一个公用的接口数据类
 * Created by yangyan
 * on 2018/3/21.
 */

public class Result<T> implements Serializable {

	private int code;
	private String msg;
	private T newslist;

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

	public T getNewslist() {
		return newslist;
	}

	public void setNewslist(T newslist) {
		this.newslist = newslist;
	}
}
