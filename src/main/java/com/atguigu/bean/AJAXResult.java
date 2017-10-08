package com.atguigu.bean;

public class AJAXResult {
	private String msg;
	private boolean success;
	private Object data;
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	@Override
	public String toString() {
		return "AJAXResult [msg=" + msg + ", success=" + success + ", data=" + data + "]";
	}
	
	
}
