package com.youngmanster.collection.been;

import com.youngmanster.collection.been.wechat.WeChatNews;

import java.util.List;

/**
 * Created by yangyan
 * on 2018/5/13.
 */

public class WeChatNewsResult {
	private int code;
	private String message;
	private List<WeChatNews> result;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<WeChatNews> getResult() {
		return result;
	}

	public void setResult(List<WeChatNews> result) {
		this.result = result;
	}
}
