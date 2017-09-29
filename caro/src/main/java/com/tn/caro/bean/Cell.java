package com.tn.caro.bean;

public class Cell {
	private static final String COMMAR = ",";
	private byte x;
	private byte y;
	
	public Cell(byte x, byte y) {
		this.x = x;
		this.y = y;
	}
	
	public byte getX(){
		return x;
	}
	
	public byte getY() {
		return y;
	}
	
	public Cell (String address) {
		String[] xy = address.split(COMMAR);
		x = Byte.parseByte(xy[0]);
		y = Byte.parseByte(xy[1]);
	}
	
	public boolean equals(Object object) {
		if(!(object instanceof Cell)) {
			return false;
		}
		Cell otherCell = (Cell) object;
		return this.x == otherCell.getX() && this.y == otherCell.getY();
	}
	
	public String toString() {
		return x + COMMAR + y;
	}
	
}
