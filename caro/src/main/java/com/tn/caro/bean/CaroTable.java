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
	
	public static final byte ROW = 30;
	public static final byte COLLUMN = 30;
	public static final String COMMA = ",";
	private long tableId;
	private List<Step> history;
	private Row[] horizontal;
	private Row[] vertical;
	private Row[] diagonalDown;
	private Row[] diagonalUp;
	private boolean userTurns;
	private short minX;
	private short minY;
	private short maxX;
	private short maxY;
	private boolean isFinished;
	private LocalDateTime lastUpdated;
	
	public CaroTable() {
		isFinished = false;
		history = new ArrayList<Step>();
		horizontal = new Row[ROW];
		vertical = new Row[ROW];
		diagonalDown = new Row[ROW*2-1];
		diagonalUp = new Row[ROW*2-1];
		for(byte x = 0; x < ROW; x++) {
			horizontal[x] = new HorizontalRow(new Cell((byte)0, x), new Cell((byte)(ROW-1), x));
			vertical[x] = new VerticalRow(new Cell(x, (byte)0), new Cell(x, (byte)(ROW-1)));
			
			diagonalUp[x] = new DiagonalUpRow(new Cell((byte)0, x), new Cell((byte)(ROW-1), (byte)(ROW-x-1)));
			diagonalDown[x] = new DiagonalDownRow(new Cell(x, (byte)0), new Cell((byte)0, x));
			
			if(x == ROW - 1) break;
			diagonalUp[ROW+x] = new DiagonalUpRow(new Cell((byte)0, (byte)(x+1)), new Cell((byte)(ROW-x-1), (byte)(ROW-1)));
			diagonalDown[ROW+x] = new DiagonalDownRow(new Cell((byte)(ROW-1), (byte)(x + 1)), new Cell((byte)(x+1), (byte)(ROW-1)));
		}
		lastUpdated = LocalDateTime.now();
	}
	
	public List<List<Step>> getAllRowHasValueAndCell(int value, Step step) {
		List<List<Step>>allRows = new ArrayList<List<Step>>();
		List<Step> stepsOfRow = null;
		List<Step> tailList = null;
		boolean hasValue = false;
		Step checkingStep;
		//for(int y = 0; y<allStep.length; y++) {
			stepsOfRow = new ArrayList<Step>();
			tailList = new ArrayList<Step>();
			hasValue = false;
			for(int x = 0; x<allStep[step.getY()].length; x++) {
				checkingStep = allStep[step.getY()][x];
				if(checkingStep.getValue() == 0) {
					if(hasValue) {
						tailList.add(checkingStep);
					}
					stepsOfRow.add(checkingStep);
				} else if(checkingStep.getValue() == value) {
					if(hasValue && tailList.size() > 1) {
						allRows.add(stepsOfRow);
						stepsOfRow = new ArrayList<Step>();
						stepsOfRow.addAll(tailList);
						tailList.clear();
						hasValue = false;
					} else {
						stepsOfRow.add(checkingStep);
						hasValue = true;
						tailList.clear();
					}
				} 
				if(checkingStep.getValue() == -value 
						|| x == allStep[step.getY()].length - 1) {
					if(hasValue) {
						allRows.add(stepsOfRow);
					}
					hasValue = false;
					tailList.clear();
					stepsOfRow = new ArrayList<Step>();
				}
				
			}
		//}
		//for(int x = 0; x<allStep[0].length; x++) {
			stepsOfRow = new ArrayList<Step>();
			tailList = new ArrayList<Step>();
			hasValue = false;
			for(int y = 0; y<allStep.length; y++) {
				checkingStep = allStep[y][step.getX()];
				if(checkingStep.getValue() == 0) {
					if(hasValue) {
						tailList.add(checkingStep);
					}
					stepsOfRow.add(checkingStep);
				} else if(checkingStep.getValue() == value) {
					if(hasValue && tailList.size() > 1) {
						allRows.add(stepsOfRow);
						stepsOfRow = new ArrayList<Step>();
						stepsOfRow.addAll(tailList);
						tailList.clear();
						hasValue = false;
					} else {
						stepsOfRow.add(checkingStep);
						hasValue = true;
						tailList.clear();
					}
				} 
				if(checkingStep.getValue() == -value 
						|| y == allStep.length - 1) {
					if(hasValue) {
						allRows.add(stepsOfRow);
					}
					hasValue = false;
					tailList.clear();
					stepsOfRow = new ArrayList<Step>();
				}
			}
		//}
		//for(int i = 0; i < allStep.length; i++) {
			
			stepsOfRow = new ArrayList<Step>();
			tailList = new ArrayList<Step>();
			hasValue = false;
			for(int x = 0; x < allStep.length - (step.getX() - step.getY()); x++) {
				int y = x + (step.getX() - step.getY());
				if(x < 0 || y < 0 || x >= allStep.length || y >= allStep.length) continue;
				checkingStep = allStep[y][x];
				if(checkingStep.getValue() == 0) {
					if(hasValue) {
						tailList.add(checkingStep);
					}
					stepsOfRow.add(checkingStep);
				} else if(checkingStep.getValue() == value) {
					if(hasValue && tailList.size() > 1) {
						allRows.add(stepsOfRow);
						stepsOfRow = new ArrayList<Step>();
						stepsOfRow.addAll(tailList);
						tailList.clear();
						hasValue = false;
					} else {
						stepsOfRow.add(checkingStep);
						hasValue = true;
						tailList.clear();
					}
				} 
				if(checkingStep.getValue() == -value 
						|| x == allStep[y].length - (step.getX() - step.getY()) - 1) {
					if(hasValue) {
						allRows.add(stepsOfRow);
					}
					hasValue = false;
					tailList.clear();
					stepsOfRow = new ArrayList<Step>();
				}
			}
		//}
		//for(int i = 1; i < allStep.length; i++) {
			stepsOfRow = new ArrayList<Step>();
			tailList = new ArrayList<Step>();
			hasValue = false;
			for(int x = (step.getX() - step.getY()); x < allStep.length; x++) {
				int y = x - (step.getX() - step.getY());
				if(x < 0 || y < 0 || x >= allStep.length || y >= allStep.length) continue;
				checkingStep = allStep[y][x];
				if(checkingStep.getValue() == 0) {
					if(hasValue) {
						tailList.add(checkingStep);
					}
					stepsOfRow.add(checkingStep);
				} else if(checkingStep.getValue() == value) {
					if(hasValue && tailList.size() > 1) {
						allRows.add(stepsOfRow);
						stepsOfRow = new ArrayList<Step>();
						stepsOfRow.addAll(tailList);
						tailList.clear();
						hasValue = false;
					} else {
						stepsOfRow.add(checkingStep);
						hasValue = true;
						tailList.clear();
					}
				} 
				if(checkingStep.getValue() == -value 
						|| x == allStep.length - 1) {
					if(hasValue) {
						allRows.add(stepsOfRow);
					}
					hasValue = false;
					tailList.clear();
					stepsOfRow = new ArrayList<Step>();
				}
			}
		//}
		//for(int i = 0; i < allStep.length; i++) {
			stepsOfRow = new ArrayList<Step>();
			tailList = new ArrayList<Step>();
			hasValue = false;
			for(int x = 0; x <= (step.getX()+step.getY()); x++) {
				int y = -x + (step.getX()+step.getY());
				if(x < 0 || y < 0 || x >= allStep.length || y >= allStep.length) continue;
				checkingStep = allStep[y][x];
				if(checkingStep.getValue() == 0) {
					if(hasValue) {
						tailList.add(checkingStep);
					}
					stepsOfRow.add(checkingStep);
				} else if(checkingStep.getValue() == value) {
					if(hasValue && tailList.size() > 1) {
						allRows.add(stepsOfRow);
						stepsOfRow = new ArrayList<Step>();
						stepsOfRow.addAll(tailList);
						tailList.clear();
						hasValue = false;
					} else {
						stepsOfRow.add(checkingStep);
						hasValue = true;
						tailList.clear();
					}
				} 
				if(checkingStep.getValue() == -value 
						|| x == (step.getX()+step.getY())) {
					if(hasValue) {
						allRows.add(stepsOfRow);
					}
					hasValue = false;
					tailList.clear();
					stepsOfRow = new ArrayList<Step>();
				}
			}
		//}
		
		//for(int i = allStep.length; i < allStep.length * 2; i++) {
			stepsOfRow = new ArrayList<Step>();
			tailList = new ArrayList<Step>();
			hasValue = false;
			for(int x = (step.getX()+step.getY()) - allStep.length + 1; x < allStep.length; x++) {
				int y = -x + (step.getX()+step.getY());
				if(x < 0 || y < 0 || x >= allStep.length || y >= allStep.length) continue;
				checkingStep = allStep[y][x];
				if(checkingStep.getValue() == 0) {
					if(hasValue) {
						tailList.add(checkingStep);
					}
					stepsOfRow.add(checkingStep);
				} else if(checkingStep.getValue() == value) {
					if(hasValue && tailList.size() > 1) {
						allRows.add(stepsOfRow);
						stepsOfRow = new ArrayList<Step>();
						stepsOfRow.addAll(tailList);
						tailList.clear();
						hasValue = false;
					} else {
						stepsOfRow.add(checkingStep);
						hasValue = true;
						tailList.clear();
					}
				} 
				if(checkingStep.getValue() == -value 
						|| x == allStep.length - 1) {
					if(hasValue) {
						allRows.add(stepsOfRow);
					}
					hasValue = false;
					tailList.clear();
					stepsOfRow = new ArrayList<Step>();
				}
			}
		//}
		
		return allRows; 
	}
	
	public void remapTable(Step newStep) {
		
	}
	
	public Step getLastestStep() {
		if(history.isEmpty()) {
			return null;
		}
		return history.get(history.size()-1);
	}
	
	public List<Step> findWinRowsByStep(Step step) {
		Row horizontalRow = getHorizontalByCell(step.getCell());
		List<Step> result = horizontalRow.findWinStep(step.getValue());
		if(result != null) return result;
	}
	
	public Row getHorizontalByCell(Cell cell) {
		return horizontal[cell.getY()];
	}
	
	public Row getVerticalByCell(Cell cell) {
		return vertical[cell.getX()];
	}
	
	public Row getDiagonalUpByCell(Cell cell) {
		if(cell.getX() >= cell.getY()) {
			return diagonalUp[cell.getX()];
		} else {
			return diagonalUp[ROW + cell.getX() - 1];
		}
	}
	
	public Row getDiagonalDownByCell(Cell cell) {
		if(cell.getX() + cell.getY() >= ROW - 1) {
			return diagonalDown[cell.getX()];
		} else {
			return diagonalDown[ROW + cell.getX() - 1];
		}
	}
	public void checkToStep(Step step) {
		history.add(step);
		if(history.size() == 1) {
			return;
		}
		getHorizontalByCell(step.getCell()).addNewStep(step);
		getVerticalByCell(step.getCell()).addNewStep(step);
		getDiagonalDownByCell(step.getCell()).addNewStep(step);
		getDiagonalUpByCell(step.getCell()).addNewStep(step);
		lastUpdated = LocalDateTime.now();
	}
	
	public long getTableId() {
		return tableId;
	}
	
	public int getHistoryLength() {
		return history.size();
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

	public boolean isOutOfDate() {
		System.out.println(lastUpdated.until(LocalDateTime.now(), ChronoUnit.MINUTES));
		return isFinished || lastUpdated.until(LocalDateTime.now(), ChronoUnit.MINUTES) >= 1;
	}

	public String getTableData() {
		StringBuilder dataBuilder = new StringBuilder();
		for(Step step : history) {
			dataBuilder.append(step.toString());
			dataBuilder.append("|");
		}
		return dataBuilder.deleteCharAt(dataBuilder.length()-1).toString();
	}
	public String getTableDataBefore(int step) {
		StringBuilder dataBuilder = new StringBuilder();
		for(int i = 0; i < history.size() - step; i++){
			dataBuilder.append(history.get(i).toString());
			dataBuilder.append("|");
		}
		return dataBuilder.deleteCharAt(dataBuilder.length()-1).toString();
	}

	public Step getStep(short stepIndex) {
		if(stepIndex < history.size()) {
			return history.get(history.size() - stepIndex);
		}
		return null;
	}
}