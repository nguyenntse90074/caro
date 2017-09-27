package com.tn.caro.bean;

public class Step {

	private static final String COMMAR = ",";
	public static final byte CELL_VALUE_X = 1;
	public static final byte CELL_VALUE_O = -1;
	public static final byte CELL_VALUE_E = 0;
	private short x;
	private short y;
	private short value;
	private byte owner;
	
	public Step(short x, short y, byte value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
	public Step(String cellAddress) {
		String[] address = cellAddress.split(COMMAR);
		x = Short.parseShort(address[0]);
		y = Short.parseShort(address[1]);
	}
	
	public Step(short x, short y) {
		this.x = x;
		this.y = y;
		this.value = 0;
	}
	
	public void setValue(short value) {
		this.value = value;
	}
	
	public short getValue() {
		return value;
	}
	
	public short getX() {
		return x;
	}
	
	public void setX(short x) {
		this.x = x;
	}
	
	public void addX(short x) {
		this.x += x;
	}
	
	public void addY(short y) {
		this.y += y;
	}
	
	public void setOwner(byte owner) {
		this.owner = owner;
	}
	
	public byte getOwner() {
		return owner;
	}
	
	public short getY() {
		return y;
	}
	
	public void setY(short y) {
		this.y = y;
	}
	
	public boolean equals(Object object) {
		if(!(object instanceof Step)) {
			return false;
		}
		Step otherStep = (Step) object;
		return this.x == otherStep.getX() && this.y == otherStep.getY() && this.value == otherStep.getValue();
	}
	
	public String toString() {
		return x + COMMAR + y;
	}
}
