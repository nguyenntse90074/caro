package com.tn.caro.bean;

public class Result {

	private Step[] winRow;
	private boolean isWin;
	
	public Result() {
		isWin = false;
	}
	
	public Step[] getWinRow() {
		return winRow;
	}
	
	public boolean getIsWin() {
		return isWin;
	}
	
	public void setIsWin(boolean isWin) {
		this.isWin = isWin;
	}
	
	public void setWinRow(Step[] winRow) {
		this.winRow = winRow;
	}
}
