package com.tn.caro.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "caro_table", schema="caro_data")
public class CaroTable {
	
	public static final int ROW = 30;
	public static final int COLLUMN = 30;
	
	private long tableId;
	private Cell[][] allCell;
	private List<Cell> history;
	private boolean userTurns;
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	
	public CaroTable() {
		history = new ArrayList<Cell>();
		allCell = new Cell[COLLUMN][ROW];
		Cell[] rowCell = null;
		for(int y = 0; y < 30; y++) {
			rowCell = new Cell[30];
			for(int x = 0; x< 30; x++) {
				rowCell [x] = new Cell(x, y);
			}
			allCell[y] = rowCell;
		}
	}
	
	public List<List<Cell>> getAllRowByValue(int value) {
		List<List<Cell>>allRows = new ArrayList<List<Cell>>();
		List<Cell> cellsOfRow = null;
		boolean isValidRow = false;
		for(int y = 0; y<allCell.length; y++) {
			cellsOfRow = new ArrayList<Cell>();
			isValidRow = false;
			for(int x = 0; x<allCell[y].length; x++) {
				if(allCell[y][x].getValue() == 0) {
					cellsOfRow.add(allCell[y][x]);
				} else if(allCell[y][x].getValue() == value) {
					isValidRow = true;
					cellsOfRow.add(allCell[y][x]);
				} 
				if(allCell[y][x].getValue() == -value || x == allCell[y].length - 1) {
					if(isValidRow) {
						allRows.add(cellsOfRow);
						isValidRow = false;
					}
					cellsOfRow = new ArrayList<Cell>();
				}
			}
		}
		for(int x = 0; x<allCell[0].length; x++) {
			cellsOfRow = new ArrayList<Cell>();
			isValidRow = false;
			for(int y = 0; y<allCell.length; y++) {
				if(allCell[y][x].getValue() == 0) {
					cellsOfRow.add(allCell[y][x]);
				}else if(allCell[y][x].getValue() == value) {
					isValidRow = true;
					cellsOfRow.add(allCell[y][x]);
				}
				if(allCell[y][x].getValue() == -value || y == allCell.length - 1) {
					if(isValidRow) {
						allRows.add(cellsOfRow);
						isValidRow = false;
					}
					cellsOfRow = new ArrayList<Cell>();
				}
			}
		}
		for(int i = 0; i < allCell.length; i++) {
			cellsOfRow = new ArrayList<Cell>();
			isValidRow = false;
			for(int x = 0; x < allCell.length - i; x++) {
				int y = x + i;
				if(allCell[y][x].getValue() == 0) {
					cellsOfRow.add(allCell[y][x]);
				}else if(allCell[y][x].getValue() == value) {
					isValidRow = true;
					cellsOfRow.add(allCell[y][x]);
				}
				if(allCell[y][x].getValue() == -value || x == allCell.length - i - 1) {
					if(isValidRow) {
						allRows.add(cellsOfRow);
						isValidRow = false;
					}
					cellsOfRow = new ArrayList<Cell>();
				}
			}
		}
		for(int i = 1; i < allCell.length; i++) {
			cellsOfRow = new ArrayList<Cell>();
			isValidRow = false;
			for(int x = i; x < allCell.length; x++) {
				int y = x - i;
				if(allCell[y][x].getValue() == 0) {
					cellsOfRow.add(allCell[y][x]);
				}else if(allCell[y][x].getValue() == value) {
					isValidRow = true;
					cellsOfRow.add(allCell[y][x]);
				}
				if(allCell[y][x].getValue() == -value || x == allCell.length - 1) {
					if(isValidRow) {
						allRows.add(cellsOfRow);
						isValidRow = false;
					}
					cellsOfRow = new ArrayList<Cell>();
				}
			}
		}
		for(int i = 0; i < allCell.length; i++) {
			cellsOfRow = new ArrayList<Cell>();
			isValidRow = false;
			for(int x = 0; x <= i; x++) {
				int y = -x + i;
				if(allCell[y][x].getValue() == 0) {
					cellsOfRow.add(allCell[y][x]);
				}else if(allCell[y][x].getValue() == value) {
					isValidRow = true;
					cellsOfRow.add(allCell[y][x]);
				}
				if(allCell[y][x].getValue() == -value || x == i) {
					if(isValidRow) {
						allRows.add(cellsOfRow);
						isValidRow = false;
					}
					cellsOfRow = new ArrayList<Cell>();
				}
			}
		}
		
		for(int i = allCell.length; i < allCell.length * 2; i++) {
			cellsOfRow = new ArrayList<Cell>();
			isValidRow = false;
			for(int x = i - allCell.length + 1; x < allCell.length; x++) {
				int y = -x + i;
				if(allCell[y][x].getValue() == 0) {
					cellsOfRow.add(allCell[y][x]);
				}else if(allCell[y][x].getValue() == value) {
					isValidRow = true;
					cellsOfRow.add(allCell[y][x]);
				}
				if(allCell[y][x].getValue() == -value || x == allCell.length - 1) {
					if(isValidRow) {
						allRows.add(cellsOfRow);
						isValidRow = false;
					}
					cellsOfRow = new ArrayList<Cell>();
				}
			}
		}
		
		return allRows; 
	}
	
	public Cell getLastestCell() {
		if(history.isEmpty()) {
			return null;
		}
		return history.get(history.size()-1);
	}
	
	public List<Cell> findWinRowsByCell(Cell cell) {
		List<Cell> winRow = new ArrayList<Cell>();
		for(int index = cell.getX(); index < allCell[cell.getY()].length; index++) {
			if(allCell[cell.getY()][index].getValue() == cell.getValue()) {
				winRow.add(cell);
				if(winRow.size() >= 5) return winRow;
			} else {
				break;
			}
		}
		for(int index = cell.getX()-1; index >=0; index--) {
			if(allCell[cell.getY()][index].getValue() == cell.getValue()) {
				winRow.add(cell);
				if(winRow.size() >= 5) return winRow;
			} else {
				break;
			}
		}
		winRow.clear();
		for(int index = cell.getY(); index < allCell.length; index++) {
			if(allCell[index][cell.getX()].getValue() == cell.getValue()) {
				winRow.add(cell);
				if(winRow.size() >= 5) return winRow;
			} else {
				break;
			}
		}
		for(int index = cell.getY()-1; index >=0; index--) {
			if(allCell[index][cell.getX()].getValue() == cell.getValue()) {
				winRow.add(cell);
				if(winRow.size() >= 5) return winRow;
			} else {
				break;
			}
		}
		winRow.clear();
		for(int indexX = cell.getX(), indexY = cell.getY(); indexX < allCell.length && indexY < allCell.length; indexX++, indexY++) {
			if(allCell[indexY][indexX].getValue() == cell.getValue()) {
				winRow.add(cell);
				if(winRow.size() >= 5) return winRow;
			} else {
				break;
			}
		}
		for(int indexX = cell.getX()-1, indexY = cell.getY()-1; indexX >= 0 && indexY >= 0; indexX--, indexY--) {
			if(allCell[indexY][indexX].getValue() == cell.getValue()) {
				winRow.add(cell);
				if(winRow.size() >= 5) return winRow;
			} else {
				break;
			}
		}
		winRow.clear();
		for(int indexX = cell.getX(), indexY = cell.getY(); indexX >= 0 && indexY < allCell.length; indexX--, indexY++) {
			if(allCell[indexY][indexX].getValue() == cell.getValue()) {
				winRow.add(cell);
				if(winRow.size() >= 5) return winRow;
			} else {
				break;
			}
		}
		for(int indexX = cell.getX()+1, indexY = cell.getY()-1; indexX < allCell.length && indexY >= 0; indexX++, indexY--) {
			if(allCell[indexY][indexX].getValue() == cell.getValue()) {
				winRow.add(cell);
				if(winRow.size() >= 5) return winRow;
			} else {
				break;
			}
		}
		winRow.clear();
		return winRow;
	}
	
	public void checkToCell(Cell cell) {
		if(allCell[cell.getY()][cell.getX()].getValue() != 0) {
			return;
		}
		allCell[cell.getY()][cell.getX()].setValue(cell.getValue());
		history.add(cell);
		if(history.size() == 1) {
			minX = cell.getX();
			maxX = cell.getX();
			minY = cell.getY();
			maxY = cell.getY();
			return;
		}
		if(cell.getX() < minX) {
			minX = cell.getX();
		} else if(cell.getX() > maxX) {
			maxX = cell.getX();
		}
		if(cell.getY() < minY) {
			minY = cell.getY();
		} else if(cell.getY() > maxY) {
			maxY = cell.getY();
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
	public Cell[][] getAllCell() {
		return allCell;
	}
}
