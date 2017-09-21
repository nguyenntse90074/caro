package com.tn.caro.bean;

public class Response {
	
	public static final int STATUS_OK = 0;
	public static final int STATUS_ERR = 1;
	
	private Result result;
	private Cell cell;
	private int status;
	private String message;
	
	public Response() {
		status = STATUS_OK;
		result = new Result();
	}
	
	public void setResult(Result result) {
		this.result = result;
	}
	
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	
	public Result getResult() {
		return result;
	}
	
	public Cell getCell() {
		return cell;
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