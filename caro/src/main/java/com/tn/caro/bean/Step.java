package com.tn.caro.bean;

public class Step {
	public static final byte CELL_VALUE_X = 1;
	public static final byte CELL_VALUE_O = -1;
	public static final byte CELL_VALUE_E = 0;
	public static final byte TYPE_REQUIRED = 1;
	public static final byte TYPE_NORMAL = -1;
	
	private Cell cell;
	private byte value;
	private byte type;
	
	public Step(Cell cell, byte value) {
		this.cell = cell;
		this.value = value;
		this.type = TYPE_NORMAL;
	}
	
	public Step(Cell cell, byte value, byte type) {
		this.cell = cell;
		this.value = value;
		this.type = type;
	}
	
	public Step(Cell cell) {
		this.cell = cell;
		this.value = 0;
	}
	
	public void setValue(byte value) {
		this.value = value;
	}
	
	public byte getValue() {
		return value;
	}
	
	public void setType(byte type) {
		this.type = type;
	}
	
	public byte getType() {
		return type;
	}
	
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	
	public Cell getCell() {
		return this.cell;
	}
	
}
