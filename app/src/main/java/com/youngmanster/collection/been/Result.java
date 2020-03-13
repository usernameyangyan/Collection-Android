package com.youngmanster.collection.been;



import java.io.Serializable;

/**
 * 根据接口规则写一个公用的接口数据类
 * Created by yangyan
 * on 2018/3/21.
 */

public class Result<T> implements Serializable {

	private int code;
	private String message;
	private T result;

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

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
