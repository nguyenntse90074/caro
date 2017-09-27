package com.tn.caro.bean;

public class Danger implements Comparable<Danger> {

	public static final int LEVEL_RED = 4;
	public static final int LEVEL_YELLOW = 3;
	public static final int LEVEL_GREEN = 2;
	public static final int LEVEL_WHITE = 1;
	public static final int LEVEL_NORMAL = 0;
	
	private Cell cell;
	private int rate;
	private int positionPriority;
	private int level;
	private int emptyHead;
	
	public Danger(Cell cell, int level, int rate, int positionPiority, int emptyHead) {
		this.cell = cell;
		this.rate = rate;
		this.level = level;
		this.positionPriority = positionPiority;
		this.emptyHead = emptyHead;
	}

	public int compareTo(Danger o) {
		if(this.level != o.getLevel()) {
			return o.getLevel() - this.getLevel();
		}
		if(this.rate != o.getRate()) {
			return o.getRate() - this.getRate();
		}
		return o.getEmptyHead() * o.getPositionPriority() - this.getEmptyHead() * this.getPositionPriority();
	}
	
	public int getRate() {
		return rate;
	}
	
	public Cell getCell() {
		return cell;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getEmptyHead() {
		return emptyHead;
	}
	
	public int getPositionPriority() {
		return positionPriority;
	}
	
	@Override
	public boolean equals(Object object) {
		if(!(object instanceof Danger)) {
			return false;
		}
		
		return cell.equals(((Danger)object).getCell());
	}
	
	public void augmentRate(Danger oDanger) {
		this.level += oDanger.getLevel();
		this.rate += oDanger.getRate();
		this.emptyHead += oDanger.getEmptyHead();
		this.positionPriority += oDanger.getPositionPriority();
	}
	
	public String getCellAddress() {
		return cell.toString();
	}
}
