package com.tn.caro.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "caro_table", schema="caro_data")
public class CaroTable {
	
	private long tableId;
	private int[][] tableData;
	private List<Step> history;
	private boolean userTurns;
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	
	public CaroTable() {
		history = new ArrayList<Step>();
		tableData = new int[30][30];
	}
	
	public void addNewStep(Step newStep) {
		history.add(newStep);
		if(history.size() == 1) {
			minX = newStep.getX();
			maxX = newStep.getX();
			minY = newStep.getY();
			maxY = newStep.getY();
			return;
		}
		if(newStep.getX() < minX) {
			minX = newStep.getX();
		} else if(newStep.getX() > maxX) {
			maxX = newStep.getX();
		}
		if(newStep.getY() < minY) {
			minY = newStep.getY();
		} else if(newStep.getY() > maxY) {
			maxY = newStep.getY();
		}
	}
	
	public long getTableId() {
		return tableId;
	}
	
	public void setTableId(long tableId) {
		this.tableId = tableId;
	}
	
	public boolean getUserTurns() {
		return userTurns;
	}
	
	public int getMaxX() {
		return maxX;
	}
	
	public int getMinX() {
		return minX;
	}
	
	public int getMaxY() {
		return maxY;
	}
	
	public int getMinY() {
		return minY;
	}
	public int[][] getTableData() {
		return tableData;
	}

	public int[][] getMinimizeTableData() {
		int[][] minimizeTable = new int[maxX - minX + 1][maxY - minY + 1];
		int x = 0;
		for(int i = minY; i <= maxY; i++) {
			int y = 0;
			for(int j = minX; j <= maxX; j++) {
				minimizeTable[x][y++] = tableData[i][j];
			}
			x++;
		}
		return minimizeTable;
	}
}
