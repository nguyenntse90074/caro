package com.tn.caro.bean;

public class Step {

	public static int VALUE_X = 1;
	public static int VALUE_O = -1;
	public static int VALUE_EMPTY = 0;
	
	private int x;
	private int y;
	private int value;
	
	public Step (int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}
