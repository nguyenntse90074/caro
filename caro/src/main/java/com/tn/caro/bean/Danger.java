package com.tn.caro.bean;

public class Danger implements Comparable<Danger> {

	public static final int LEVEL_RED = 4;
	public static final int LEVEL_YELLOW = 3;
	public static final int LEVEL_SILVER = 2;
	public static final int LEVEL_NORMAL = 1;
	private Cell cell;
	private int rate;
	private int level;
	
	public Danger(Cell cell, int level, int rate) {
		this.cell = cell;
		this.rate = rate;
		this.level = level;
	}

	public int compareTo(Danger o) {
		if(this.level == o.getLevel()) {
			return o.getRate() - this.rate;
		}
		return o.getLevel() - this.getLevel();
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
	
	@Override
	public boolean equals(Object object) {
		if(!(object instanceof Danger)) {
			return false;
		}
		
		return cell.equals(((Danger)object).getCell());
	}
	
	public void augmentRate(int rate) {
		this.rate += rate;
	}
}
