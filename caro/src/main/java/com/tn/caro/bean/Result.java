package com.tn.caro.bean;

public class Result {

	private int[][] winArr;
	private boolean isWin;
	
	public Result() {
		winArr = new int[5][2];
		isWin = false;
	}
	
	public int[][] getWinArr() {
		return winArr;
	}
	
	public boolean getIsWin() {
		return isWin;
	}
	
	public void setIsWin(boolean isWin) {
		this.isWin = isWin;
	}
	
	public void setWinArr(int[][] winArr) {
		this.winArr = winArr;
	}
}
