package com.tn.caro.bean;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
	private boolean isFinished;
	private LocalDateTime lastUpdated;
	
	public CaroTable() {
		isFinished = false;
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
		lastUpdated = LocalDateTime.now();
	}
	
	public List<List<Cell>> getAllRowByValue(int value) {
		List<List<Cell>>allRows = new ArrayList<List<Cell>>();
		List<Cell> cellsOfRow = null;
		List<Cell> tailList = null;
		boolean hasValue = false;
		Cell checkingCell;
		for(int y = 0; y<allCell.length; y++) {
			cellsOfRow = new ArrayList<Cell>();
			tailList = new ArrayList<Cell>();
			hasValue = false;
			for(int x = 0; x<allCell[y].length; x++) {
				checkingCell = allCell[y][x];
				if(checkingCell.getValue() == 0) {
					if(hasValue) {
						tailList.add(checkingCell);
						if(tailList.size() < 2) {
							cellsOfRow.add(checkingCell);
						}
					} else {
						cellsOfRow.add(checkingCell);
					}
				} else if(checkingCell.getValue() == value) {
					cellsOfRow.add(checkingCell);
					hasValue = true;
					tailList.clear();
				} else if(checkingCell.getValue() == -value 
						|| x == allCell[y].length - 1) {
					if(hasValue) {
						if(!tailList.isEmpty()) {
							cellsOfRow.addAll(tailList);
						}
						allRows.add(cellsOfRow);
					}
					hasValue = false;
					tailList.clear();
					cellsOfRow = new ArrayList<Cell>();
				}
				if(tailList.size() == 2) {
					if(hasValue) {
						allRows.add(cellsOfRow);
					}
					hasValue = false;
					cellsOfRow = new ArrayList<Cell>();
					cellsOfRow.addAll(tailList);
					tailList.clear();
				}
			}
		}
		for(int x = 0; x<allCell[0].length; x++) {
			cellsOfRow = new ArrayList<Cell>();
			tailList = new ArrayList<Cell>();
			hasValue = false;
			for(int y = 0; y<allCell.length; y++) {
				checkingCell = allCell[y][x];
				if(checkingCell.getValue() == 0) {
					if(hasValue) {
						tailList.add(checkingCell);
						if(tailList.size() < 2) {
							cellsOfRow.add(checkingCell);
						}
					} else {
						cellsOfRow.add(checkingCell);
					}
				} else if(checkingCell.getValue() == value) {
					cellsOfRow.add(checkingCell);
					hasValue = true;
					tailList.clear();
				} else if(checkingCell.getValue() == -value 
						|| y == allCell.length - 1) {
					if(hasValue) {
						if(!tailList.isEmpty()) {
							cellsOfRow.addAll(tailList);
						}
						allRows.add(cellsOfRow);
					}
					hasValue = false;
					tailList.clear();
					cellsOfRow = new ArrayList<Cell>();
				}
				if(tailList.size() == 2) {
					if(hasValue) {
						cellsOfRow.addAll(tailList);
						allRows.add(cellsOfRow);
					}
					hasValue = false;
					cellsOfRow = new ArrayList<Cell>();
					cellsOfRow.addAll(tailList);
					tailList.clear();
				}
			}
		}
		for(int i = 0; i < allCell.length; i++) {
			cellsOfRow = new ArrayList<Cell>();
			tailList = new ArrayList<Cell>();
			hasValue = false;
			for(int x = 0; x < allCell.length - i; x++) {
				int y = x + i;
				checkingCell = allCell[y][x];
				if(checkingCell.getValue() == 0) {
					if(hasValue) {
						tailList.add(checkingCell);
						if(tailList.size() < 2) {
							cellsOfRow.add(checkingCell);
						}
					} else {
						cellsOfRow.add(checkingCell);
					}
				} else if(checkingCell.getValue() == value) {
					cellsOfRow.add(checkingCell);
					hasValue = true;
					tailList.clear();
				} else if(checkingCell.getValue() == -value 
						|| x == allCell[y].length - i - 1) {
					if(hasValue) {
						if(!tailList.isEmpty()) {
							cellsOfRow.addAll(tailList);
						}
						allRows.add(cellsOfRow);
					}
					hasValue = false;
					tailList.clear();
					cellsOfRow = new ArrayList<Cell>();
				}
				if(tailList.size() == 2) {
					if(hasValue) {
						cellsOfRow.addAll(tailList);
						allRows.add(cellsOfRow);
					}
					hasValue = false;
					cellsOfRow = new ArrayList<Cell>();
					cellsOfRow.addAll(tailList);
					tailList.clear();
				}
			}
		}
		for(int i = 1; i < allCell.length; i++) {
			cellsOfRow = new ArrayList<Cell>();
			tailList = new ArrayList<Cell>();
			hasValue = false;
			for(int x = i; x < allCell.length; x++) {
				int y = x - i;
				checkingCell = allCell[y][x];
				if(checkingCell.getValue() == 0) {
					if(hasValue) {
						tailList.add(checkingCell);
						if(tailList.size() < 2) {
							cellsOfRow.add(checkingCell);
						}
					} else {
						cellsOfRow.add(checkingCell);
					}
				} else if(checkingCell.getValue() == value) {
					cellsOfRow.add(checkingCell);
					hasValue = true;
					tailList.clear();
				} else if(checkingCell.getValue() == -value 
						|| x == allCell.length - 1) {
					if(hasValue) {
						if(!tailList.isEmpty()) {
							cellsOfRow.addAll(tailList);
						}
						allRows.add(cellsOfRow);
					}
					hasValue = false;
					tailList.clear();
					cellsOfRow = new ArrayList<Cell>();
				}
				if(tailList.size() == 2) {
					if(hasValue) {
						cellsOfRow.addAll(tailList);
						allRows.add(cellsOfRow);
					}
					hasValue = false;
					cellsOfRow = new ArrayList<Cell>();
					cellsOfRow.addAll(tailList);
					tailList.clear();
				}
			}
		}
		for(int i = 0; i < allCell.length; i++) {
			cellsOfRow = new ArrayList<Cell>();
			tailList = new ArrayList<Cell>();
			hasValue = false;
			for(int x = 0; x <= i; x++) {
				int y = -x + i;
				checkingCell = allCell[y][x];
				if(checkingCell.getValue() == 0) {
					if(hasValue) {
						tailList.add(checkingCell);
						if(tailList.size() < 2) {
							cellsOfRow.add(checkingCell);
						}
					} else {
						cellsOfRow.add(checkingCell);
					}
				} else if(checkingCell.getValue() == value) {
					cellsOfRow.add(checkingCell);
					hasValue = true;
					tailList.clear();
				} else if(checkingCell.getValue() == -value 
						|| x == i) {
					if(hasValue) {
						if(!tailList.isEmpty()) {
							cellsOfRow.addAll(tailList);
						}
						allRows.add(cellsOfRow);
					}
					hasValue = false;
					tailList.clear();
					cellsOfRow = new ArrayList<Cell>();
				}
				if(tailList.size() == 2) {
					if(hasValue) {
						cellsOfRow.addAll(tailList);
						allRows.add(cellsOfRow);
					}
					hasValue = false;
					cellsOfRow = new ArrayList<Cell>();
					cellsOfRow.addAll(tailList);
					tailList.clear();
				}
			}
		}
		
		for(int i = allCell.length; i < allCell.length * 2; i++) {
			cellsOfRow = new ArrayList<Cell>();
			tailList = new ArrayList<Cell>();
			hasValue = false;
			for(int x = i - allCell.length + 1; x < allCell.length; x++) {
				int y = -x + i;
				checkingCell = allCell[y][x];
				if(checkingCell.getValue() == 0) {
					if(hasValue) {
						tailList.add(checkingCell);
						if(tailList.size() < 2) {
							cellsOfRow.add(checkingCell);
						}
					} else {
						cellsOfRow.add(checkingCell);
					}
				} else if(checkingCell.getValue() == value) {
					cellsOfRow.add(checkingCell);
					hasValue = true;
					tailList.clear();
				} else if(checkingCell.getValue() == -value 
						|| x == allCell.length - 1) {
					if(hasValue) {
						if(!tailList.isEmpty()) {
							cellsOfRow.addAll(tailList);
						}
						allRows.add(cellsOfRow);
					}
					hasValue = false;
					tailList.clear();
					cellsOfRow = new ArrayList<Cell>();
				}
				if(tailList.size() == 2) {
					if(hasValue) {
						cellsOfRow.addAll(tailList);
						allRows.add(cellsOfRow);
					}
					hasValue = false;
					cellsOfRow = new ArrayList<Cell>();
					cellsOfRow.addAll(tailList);
					tailList.clear();
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
		winRow.add(cell);
		for(int index = cell.getX()+1; index < allCell[cell.getY()].length; index++) {
			if(allCell[cell.getY()][index].getValue() == cell.getValue()) {
				winRow.add(allCell[cell.getY()][index]);
				if(winRow.size() >= 5) {
					isFinished = true;
					return winRow;
				}
			} else {
				break;
			}
		}
		for(int index = cell.getX()-1; index >=0; index--) {
			if(allCell[cell.getY()][index].getValue() == cell.getValue()) {
				winRow.add(allCell[cell.getY()][index]);
				if(winRow.size() >= 5) {
					isFinished = true;
					return winRow;
				}
			} else {
				break;
			}
		}
		winRow.clear();
		winRow.add(cell);
		for(int index = cell.getY()+1; index < allCell.length; index++) {
			if(allCell[index][cell.getX()].getValue() == cell.getValue()) {
				winRow.add(allCell[index][cell.getX()]);
				if(winRow.size() >= 5) {
					isFinished = true;
					return winRow;
				}
			} else {
				break;
			}
		}
		for(int index = cell.getY()-1; index >=0; index--) {
			if(allCell[index][cell.getX()].getValue() == cell.getValue()) {
				winRow.add(allCell[index][cell.getX()]);
				if(winRow.size() >= 5) {
					isFinished = true;
					return winRow;
				}
			} else {
				break;
			}
		}
		winRow.clear();
		winRow.add(cell);
		for(int indexX = cell.getX()+1, indexY = cell.getY()+1; indexX < allCell.length && indexY < allCell.length; indexX++, indexY++) {
			if(allCell[indexY][indexX].getValue() == cell.getValue()) {
				winRow.add(allCell[indexY][indexX]);
				if(winRow.size() >= 5) {
					isFinished = true;
					return winRow;
				}
			} else {
				break;
			}
		}
		for(int indexX = cell.getX()-1, indexY = cell.getY()-1; indexX >= 0 && indexY >= 0; indexX--, indexY--) {
			if(allCell[indexY][indexX].getValue() == cell.getValue()) {
				winRow.add(allCell[indexY][indexX]);
				if(winRow.size() >= 5) {
					isFinished = true;
					return winRow;
				}
			} else {
				break;
			}
		}
		winRow.clear();
		winRow.add(cell);
		for(int indexX = cell.getX()-1, indexY = cell.getY()+1; indexX >= 0 && indexY < allCell.length; indexX--, indexY++) {
			if(allCell[indexY][indexX].getValue() == cell.getValue()) {
				winRow.add(allCell[indexY][indexX]);
				if(winRow.size() >= 5) {
					isFinished = true;
					return winRow;
				}
			} else {
				break;
			}
		}
		for(int indexX = cell.getX()+1, indexY = cell.getY()-1; indexX < allCell.length && indexY >= 0; indexX++, indexY--) {
			if(allCell[indexY][indexX].getValue() == cell.getValue()) {
				winRow.add(allCell[indexY][indexX]);
				if(winRow.size() >= 5) {
					isFinished = true;
					return winRow;
				}
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
		lastUpdated = LocalDateTime.now();
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
	
	public boolean isOutOfDate() {
		return isFinished || LocalDateTime.now().until(lastUpdated, ChronoUnit.MINUTES) > 1;
	}
}
