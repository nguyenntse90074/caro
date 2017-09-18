package com.tn.caro.bean;

public class Response {
	private Result result;
	private Step newStep;
	
	public Response() {
		result = new Result();
	}
	
	public void setResult(Result result) {
		this.result = result;
	}
	
	public void setNewStep(Step newStep) {
		this.newStep = newStep;
	}
	
	public Result getResult() {
		return result;
	}
	
	public Step getNewStep() {
		return newStep;
	}
}