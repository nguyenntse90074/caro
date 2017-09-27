package com.tn.caro.bean;

public class Response {
	
	public static final int STATUS_OK = 0;
	public static final int STATUS_ERR = 1;
	
	private Result result;
	private Step step;
	private int status;
	private String message;
	
	public Response() {
		status = STATUS_OK;
		result = new Result();
	}
	
	public void setResult(Result result) {
		this.result = result;
	}
	
	public void setStep(Step step) {
		this.step = step;
	}
	
	public Result getResult() {
		return result;
	}
	
	public Step getStep() {
		return step;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}