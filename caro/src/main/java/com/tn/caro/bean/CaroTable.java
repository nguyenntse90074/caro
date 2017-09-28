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
	
	public static final short ROW = 30;
	public static final short COLLUMN = 30;
	public static final String COMMA = ",";
	private long tableId;
	private Step[][] allStep;
	private List<Step> history;
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
		allStep = new Step[COLLUMN][ROW];
		Step[] rowCell = null;
		for(short y = 0; y < 30; y++) {
			rowCell = new Step[30];
			for(short x = 0; x< 30; x++) {
				rowCell [x] = new Step(x, y);
			}
			allStep[y] = rowCell;
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
	
	public List<List<Step>> getAllRowByValue(int value) {
		List<List<Step>>allRows = new ArrayList<List<Step>>();
		List<Step> stepsOfRow = null;
		List<Step> tailList = null;
		boolean hasValue = false;
		Step checkingStep;
		for(int y = 0; y<allStep.length; y++) {
			stepsOfRow = new ArrayList<Step>();
			tailList = new ArrayList<Step>();
			hasValue = false;
			for(int x = 0; x<allStep[y].length; x++) {
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
						|| x == allStep[y].length - 1) {
					if(hasValue) {
						allRows.add(stepsOfRow);
					}
					hasValue = false;
					tailList.clear();
					stepsOfRow = new ArrayList<Step>();
				}
				
			}
		}
		for(int x = 0; x<allStep[0].length; x++) {
			stepsOfRow = new ArrayList<Step>();
			tailList = new ArrayList<Step>();
			hasValue = false;
			for(int y = 0; y<allStep.length; y++) {
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
						|| y == allStep.length - 1) {
					if(hasValue) {
						allRows.add(stepsOfRow);
					}
					hasValue = false;
					tailList.clear();
					stepsOfRow = new ArrayList<Step>();
				}
			}
		}
		for(int i = 0; i < allStep.length; i++) {
			stepsOfRow = new ArrayList<Step>();
			tailList = new ArrayList<Step>();
			hasValue = false;
			for(int x = 0; x < allStep.length - i; x++) {
				int y = x + i;
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
						|| x == allStep[y].length - i - 1) {
					if(hasValue) {
						allRows.add(stepsOfRow);
					}
					hasValue = false;
					tailList.clear();
					stepsOfRow = new ArrayList<Step>();
				}
			}
		}
		for(int i = 1; i < allStep.length; i++) {
			stepsOfRow = new ArrayList<Step>();
			tailList = new ArrayList<Step>();
			hasValue = false;
			for(int x = i; x < allStep.length; x++) {
				int y = x - i;
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
		}
		for(int i = 0; i < allStep.length; i++) {
			stepsOfRow = new ArrayList<Step>();
			tailList = new ArrayList<Step>();
			hasValue = false;
			for(int x = 0; x <= i; x++) {
				int y = -x + i;
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
						|| x == i) {
					if(hasValue) {
						allRows.add(stepsOfRow);
					}
					hasValue = false;
					tailList.clear();
					stepsOfRow = new ArrayList<Step>();
				}
			}
		}
		
		for(int i = allStep.length; i < allStep.length * 2; i++) {
			stepsOfRow = new ArrayList<Step>();
			tailList = new ArrayList<Step>();
			hasValue = false;
			for(int x = i - allStep.length + 1; x < allStep.length; x++) {
				int y = -x + i;
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
		}
		
		return allRows; 
	}
	
	public Step getLastestStep() {
		if(history.isEmpty()) {
			return null;
		}
		return history.get(history.size()-1);
	}
	
	public List<Step> findWinRowsByStep(Step step) {
		List<Step> winRow = new ArrayList<Step>();
		winRow.add(step);
		for(int index = step.getX()+1; index < allStep[step.getY()].length; index++) {
			if(allStep[step.getY()][index].getValue() == step.getValue()) {
				winRow.add(allStep[step.getY()][index]);
				if(winRow.size() >= 5) {
					isFinished = true;
					return winRow;
				}
			} else {
				break;
			}
		}
		for(int index = step.getX()-1; index >=0; index--) {
			if(allStep[step.getY()][index].getValue() == step.getValue()) {
				winRow.add(allStep[step.getY()][index]);
				if(winRow.size() >= 5) {
					isFinished = true;
					return winRow;
				}
			} else {
				break;
			}
		}
		winRow.clear();
		winRow.add(step);
		for(int index = step.getY()+1; index < allStep.length; index++) {
			if(allStep[index][step.getX()].getValue() == step.getValue()) {
				winRow.add(allStep[index][step.getX()]);
				if(winRow.size() >= 5) {
					isFinished = true;
					return winRow;
				}
			} else {
				break;
			}
		}
		for(int index = step.getY()-1; index >=0; index--) {
			if(allStep[index][step.getX()].getValue() == step.getValue()) {
				winRow.add(allStep[index][step.getX()]);
				if(winRow.size() >= 5) {
					isFinished = true;
					return winRow;
				}
			} else {
				break;
			}
		}
		winRow.clear();
		winRow.add(step);
		for(int indexX = step.getX()+1, indexY = step.getY()+1; indexX < allStep.length && indexY < allStep.length; indexX++, indexY++) {
			if(allStep[indexY][indexX].getValue() == step.getValue()) {
				winRow.add(allStep[indexY][indexX]);
				if(winRow.size() >= 5) {
					isFinished = true;
					return winRow;
				}
			} else {
				break;
			}
		}
		for(int indexX = step.getX()-1, indexY = step.getY()-1; indexX >= 0 && indexY >= 0; indexX--, indexY--) {
			if(allStep[indexY][indexX].getValue() == step.getValue()) {
				winRow.add(allStep[indexY][indexX]);
				if(winRow.size() >= 5) {
					isFinished = true;
					return winRow;
				}
			} else {
				break;
			}
		}
		winRow.clear();
		winRow.add(step);
		for(int indexX = step.getX()-1, indexY = step.getY()+1; indexX >= 0 && indexY < allStep.length; indexX--, indexY++) {
			if(allStep[indexY][indexX].getValue() == step.getValue()) {
				winRow.add(allStep[indexY][indexX]);
				if(winRow.size() >= 5) {
					isFinished = true;
					return winRow;
				}
			} else {
				break;
			}
		}
		for(int indexX = step.getX()+1, indexY = step.getY()-1; indexX < allStep.length && indexY >= 0; indexX++, indexY--) {
			if(allStep[indexY][indexX].getValue() == step.getValue()) {
				winRow.add(allStep[indexY][indexX]);
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
	
	public void checkToStep(Step step) {
//		if(allStep[step.getY()][step.getX()].getValue() != 0) {
//			return;
//		}
		allStep[step.getY()][step.getX()].setValue(step.getValue());
		history.add(step);
		if(history.size() == 1) {
			minX = step.getX();
			maxX = step.getX();
			minY = step.getY();
			maxY = step.getY();
			return;
		}
		if(step.getX() < minX) {
			minX = step.getX();
		} else if(step.getX() > maxX) {
			maxX = step.getX();
		}
		if(step.getY() < minY) {
			minY = step.getY();
		} else if(step.getY() > maxY) {
			maxY = step.getY();
		}
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
	public Step[][] getAllStep() {
		return allStep;
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