package com.business.api;


import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

/**
 * 网络请求服务器处理错误
 * 
 * @author mian
 *
 */
public class CommonServerError extends VolleyError {

	/*
	 * code=100:接口成功;
	 */
	private int code;
	private int errorCode;
	private String desc;

	public CommonServerError() {
		super();
	}

	public CommonServerError(NetworkResponse response) {
		super(response);
	}

	public CommonServerError(String exceptionMessage, Throwable reason) {
		super(exceptionMessage, reason);
	}

	public CommonServerError(String exceptionMessage) {
		super(exceptionMessage);
	}

	public CommonServerError(Throwable cause) {
		super(cause);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
