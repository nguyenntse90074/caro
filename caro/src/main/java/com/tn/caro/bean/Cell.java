package com.tn.caro.bean;

public class Cell {

	private static final String COMMAR = ",";
	public static final int CELL_VALUE_X = 1;
	public static final int CELL_VALUE_O = -1;
	public static final int CELL_VALUE_E = 0;
	private int x;
	private int y;
	private int value;
	
	public Cell(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
	public Cell(String cellAddress) {
		String[] address = cellAddress.split(COMMAR);
		x = Integer.parseInt(address[0]);
		y = Integer.parseInt(address[1]);
	}
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		this.value = 0;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean equals(Object object) {
		if(!(object instanceof Cell)) {
			return false;
		}
		Cell otherCell = (Cell) object;
		return this.x == otherCell.getX() && this.y == otherCell.getY() && this.value == otherCell.getValue();
	}
	
	public String toString() {
		return x + COMMAR + y;
	}
}
