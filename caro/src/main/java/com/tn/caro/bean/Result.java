package com.tn.caro.bean;

public class Result {

	private Cell[] winRow;
	private boolean isWin;
	
	public Result() {
		isWin = false;
	}
	
	public Cell[] getWinRow() {
		return winRow;
	}
	
	public boolean getIsWin() {
		return isWin;
	}
	
	public void setIsWin(boolean isWin) {
		this.isWin = isWin;
	}
	
	public void setWinRow(Cell[] winRow) {
		this.winRow = winRow;
	}
}
