package com.tn.caro.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tn.caro.bean.CaroTable;
import com.tn.caro.bean.Cell;
import com.tn.caro.bean.Danger;
import com.tn.caro.bean.Result;
import com.tn.caro.dao.CaroDAO;

@Service("caroService")
public class CaroService {

	@Autowired
	CaroDAO caroDAO;
	
	public Cell findRobotStep(CaroTable caroTable) {
		Cell lastestUserCell = caroTable.getLastestCell();
		List<List<Cell>> allUserRows = caroTable.getAllRowByValue(lastestUserCell.getValue());
		List<Danger> allUserDanger = new ArrayList<Danger>();
		for(List<Cell> allCellsOfRow : allUserRows) {
			allUserDanger.addAll(findDangerCells(allCellsOfRow));
		}
		
		List<Danger> allRobotDanger = new ArrayList<Danger>();
		List<List<Cell>> allRobotRows = caroTable.getAllRowByValue(-lastestUserCell.getValue());
		for(List<Cell> allCellsOfRow : allRobotRows) {
			allRobotDanger.addAll(findDangerCells(allCellsOfRow));
		}
		
		return findBestCellForRobot(allUserDanger, allRobotDanger, caroTable);
	}
	
	private Cell findBestCellForRobot(List<Danger> allUserDanger, List<Danger> allRobotDanger, CaroTable caroTable) {	
		Collections.sort(allUserDanger);
		Collections.sort(allRobotDanger);
		
		List<Danger> allRobotRedDanger = findDangerByLevel(Danger.LEVEL_RED, allRobotDanger);
		List<Danger> allRobotYellowDanger = findDangerByLevel(Danger.LEVEL_YELLOW, allRobotDanger);
//		List<Danger> allRobotGreenDanger = findDangerByLevel(Danger.LEVEL_GREEN, allRobotDanger);
		List<Danger> allRobotWhiteDanger = findDangerByLevel(Danger.LEVEL_WHITE, allRobotDanger);
		
		List<Danger> allUserRedDanger = findDangerByLevel(Danger.LEVEL_RED, allUserDanger);
		List<Danger> allUserYellowDanger = findDangerByLevel(Danger.LEVEL_YELLOW, allUserDanger);
//		List<Danger> allUserGreenDanger = findDangerByLevel(Danger.LEVEL_GREEN, allUserDanger);
		List<Danger> allUserWhiteDanger = findDangerByLevel(Danger.LEVEL_WHITE, allUserDanger);
		
		if(!allRobotRedDanger.isEmpty()) {
			return allRobotRedDanger.get(0).getCell();
		}
		
		if(!allUserRedDanger.isEmpty()) {
			Danger dangerLv1;
			for(int i = 0; i<allUserRedDanger.size(); i++) {
				dangerLv1 = allUserRedDanger.get(i);
				for(int j=0; j<allUserDanger.size(); j++) {
					if(i != j && dangerLv1.equals(allUserDanger.get(j))) {
						dangerLv1.augmentRate(allUserDanger.get(j));
					}
				}
			}
			Collections.sort(allUserRedDanger);
			return allUserRedDanger.get(0).getCell();
		}

		if(allUserYellowDanger.size() >= 4 && !allRobotYellowDanger.isEmpty()) {
			for(Danger userDanger : allUserYellowDanger) {
				for(Danger robotDanger : allRobotYellowDanger) {
					if(userDanger.equals(robotDanger)) {
						userDanger.augmentRate(robotDanger);
					}
				}
			}
			Collections.sort(allUserYellowDanger);
			return allUserYellowDanger.get(0).getCell();
		}
		if(allUserYellowDanger.size() >= 3) {
			Danger dangerLv1;
			for(int i = 0; i<allUserYellowDanger.size(); i++) {
				dangerLv1 = allUserYellowDanger.get(i);
				for(int j=0; j<allUserYellowDanger.size(); j++) {
					if(i != j && dangerLv1.equals(allUserYellowDanger.get(j))) {
						dangerLv1.augmentRate(allUserYellowDanger.get(j));
					}
				}
			}
			
			Collections.sort(allUserYellowDanger);
			return allUserYellowDanger.get(0).getCell();
		}
		if(!allUserYellowDanger.isEmpty()) {
			Danger dangerLv1;
			for(int i = 0; i<allUserYellowDanger.size(); i++) {
				dangerLv1 = allUserYellowDanger.get(i);
				for(int j=0; j<allUserDanger.size(); j++) {
					if(i != j && dangerLv1.equals(allUserDanger.get(j))) {
						dangerLv1.augmentRate(allUserDanger.get(j));
					}
				}
			}
			
			Collections.sort(allUserYellowDanger);
			return allUserYellowDanger.get(0).getCell();
		}
		
		if(!allRobotYellowDanger.isEmpty()) {
			Danger dangerLv1;
			for(int i = 0; i<allRobotYellowDanger.size(); i++) {
				dangerLv1 = allRobotYellowDanger.get(i);
				for(int j=0; j<allRobotDanger.size(); j++) {
					if(i!=j && dangerLv1.equals(allRobotDanger.get(j))) {
						dangerLv1.augmentRate(allRobotDanger.get(j));
					}
				}
			}
			Collections.sort(allRobotYellowDanger);
			return allRobotYellowDanger.get(0).getCell();
		}
		
		List<Cell> experienceCell = caroDAO.findNextStepByByTableData(caroTable.getTableData());
		if(!experienceCell.isEmpty()) {
			Cell bestCell = experienceCell.get(0);
			bestCell.setX(bestCell.getX()+caroTable.getMinX());
			bestCell.setY(bestCell.getY()+caroTable.getMinY());
			return bestCell;
		}

		if(allRobotWhiteDanger.size()>=6) {
			List<Danger> allContinueDanger = getContinueDanger(allRobotWhiteDanger);
			if(!allContinueDanger.isEmpty()) {
				for(int i = 0; i < allContinueDanger.size(); i++) {
					for(int j=0; j<allContinueDanger.size(); j++) {
						if(j == i) continue;
						if(allContinueDanger.get(i).getCell().equals(allContinueDanger.get(j).getCell())) {
							allContinueDanger.get(i).augmentRate(allContinueDanger.get(j));
						}
					}
				}
				Collections.sort(allContinueDanger);
				return allContinueDanger.get(0).getCell();
			}
		}
		
		if(allUserWhiteDanger.size() >= 6) {
			List<Danger> allContinueDanger = getContinueDanger(allUserWhiteDanger);
			if(!allContinueDanger.isEmpty()) {
				for(int i = 0; i < allContinueDanger.size(); i ++) {
					for(int j = 0; j<allContinueDanger.size(); j++) {
						if(i == j) continue;
						if(allContinueDanger.get(i).equals(allContinueDanger.get(j))) {
							allContinueDanger.get(i).augmentRate(allContinueDanger.get(j));
						}
					}
				}
				Collections.sort(allContinueDanger);
				return allContinueDanger.get(0).getCell();
			}
		}
		if(!allRobotDanger.isEmpty()) {
			for(int i = 0; i < allRobotDanger.size(); i++) {
				for(int j = 0; j<allRobotDanger.size(); j++) {
					if(i==j) continue;
					if(allRobotDanger.get(i).equals(allRobotDanger.get(j))) {
						allRobotDanger.get(i).augmentRate(allRobotDanger.get(j));
					}
				}
			}
			Collections.sort(allRobotDanger);
			return allRobotDanger.get(0).getCell();
		}

		for(Danger userDanger : allUserDanger) {
			for(Danger robotDanger : allRobotDanger) {
				if(userDanger.equals(robotDanger)) {
					userDanger.augmentRate(robotDanger);
				}
			}
		}
		Collections.sort(allUserDanger);
		return allUserDanger.get(0).getCell();
	}
	
	private List<Danger> getContinueDanger(List<Danger> allDanger) {
		List<Danger> result = new ArrayList<Danger>();
		Map<String,Danger> rowDangerXX = new HashMap<String,Danger>();
		Map<String,Danger> rowDangerYY = new HashMap<String,Danger>();
		Map<String,Danger> rowDangerXXYY = new HashMap<String,Danger>();
		Map<String,Danger> rowDangerYXYX = new HashMap<String,Danger>();
		Danger checkingDanger = null;
		for(int i = 0; i < allDanger.size(); i++) {
			checkingDanger = allDanger.get(i);
			for(int j = 0; j<allDanger.size(); j++) {
				if(i==j) continue;
				if(checkingDanger.getCell().getX() == allDanger.get(j).getCell().getX()) {
					if(Math.abs(allDanger.get(j).getCell().getY() - checkingDanger.getCell().getY()) < 5) {
						if(!rowDangerXX.containsKey(checkingDanger.getCellAddress())) {
							rowDangerXX.put(checkingDanger.getCellAddress(), checkingDanger);
						}
						if(!rowDangerXX.containsKey(allDanger.get(j).getCellAddress())) {
							rowDangerXX.put(allDanger.get(j).getCellAddress(), allDanger.get(j));
						}
					}
				} else if(checkingDanger.getCell().getY() == allDanger.get(j).getCell().getY()) {
					if(Math.abs(allDanger.get(j).getCell().getX() - checkingDanger.getCell().getX()) < 5) {
						if(!rowDangerYY.containsKey(checkingDanger.getCellAddress())) {
							rowDangerYY.put(checkingDanger.getCellAddress(), checkingDanger);
						}
						if(!rowDangerYY.containsKey(allDanger.get(j).getCellAddress())) {
							rowDangerYY.put(allDanger.get(j).getCellAddress(), allDanger.get(j));
						}
					}				
				} else if(checkingDanger.getCell().getY() + allDanger.get(j).getCell().getX() == checkingDanger.getCell().getY() + allDanger.get(j).getCell().getX()) {
					if(Math.abs(allDanger.get(j).getCell().getY() - checkingDanger.getCell().getY()) < 5) {
						if(!rowDangerXXYY.containsKey(checkingDanger.getCellAddress())) {
							rowDangerXXYY.put(checkingDanger.getCellAddress(), checkingDanger);
						}
						if(!rowDangerXXYY.containsKey(allDanger.get(j).getCellAddress())) {
							rowDangerXXYY.put(allDanger.get(j).getCellAddress(), allDanger.get(j));
						}
					}
				} else if(checkingDanger.getCell().getY() - allDanger.get(j).getCell().getX() == checkingDanger.getCell().getY() - allDanger.get(j).getCell().getX()) {
					if(Math.abs(allDanger.get(j).getCell().getY()-checkingDanger.getCell().getY()) < 5) {
						if(!rowDangerYXYX.containsKey(checkingDanger.getCellAddress())) {
							rowDangerYXYX.put(checkingDanger.getCellAddress(), checkingDanger);
						}
						if(!rowDangerYXYX.containsKey(allDanger.get(j).getCellAddress())) {
							rowDangerYXYX.put(allDanger.get(j).getCellAddress(), allDanger.get(j));
						}
					}
				}
			}
		}
		List<Cell> cellDangerLv2 = new ArrayList<Cell>();
		for(Danger danger : rowDangerXX.values()) {
			cellDangerLv2.add(danger.getCell());
		}
		if(!findDangerByLevel(Danger.LEVEL_WHITE, findDangerCells(cellDangerLv2)).isEmpty()) {
			result.addAll(rowDangerXX.values());
		}
		cellDangerLv2.clear();
		for(Danger danger : rowDangerYY.values()) {
			cellDangerLv2.add(danger.getCell());
		}
		if(!findDangerByLevel(Danger.LEVEL_WHITE, findDangerCells(cellDangerLv2)).isEmpty()) {
			result.addAll(rowDangerYY.values());
		}
		cellDangerLv2.clear();
		for(Danger danger : rowDangerXXYY.values()) {
			cellDangerLv2.add(danger.getCell());
		}
		if(!findDangerByLevel(Danger.LEVEL_WHITE, findDangerCells(cellDangerLv2)).isEmpty()) {
			result.addAll(rowDangerXXYY.values());
		}
		cellDangerLv2.clear();
		for(Danger danger : rowDangerYXYX.values()) {
			cellDangerLv2.add(danger.getCell());
		}
		if(!findDangerByLevel(Danger.LEVEL_WHITE, findDangerCells(cellDangerLv2)).isEmpty()) {
			result.addAll(rowDangerYXYX.values());
		}
		
		return result;
	}
	
	private List<Danger> findDangerByLevel(int level ,List<Danger> allDanger) {
		List<Danger> result = new ArrayList<Danger>();
		for(Danger danger : allDanger) {
			if(danger.getLevel() >= level) {
				result.add(danger);
			}
		}
		return result;
	}
	public Result checkResult(Cell cell, CaroTable caroTable) {
		List<Cell> winRow = caroTable.findWinRowsByCell(cell);
		Result result = new Result();
		result.setIsWin(!winRow.isEmpty());
		result.setWinRow(winRow.toArray(new Cell[winRow.size()]));
		return result;
	}
	
	public void saveLostTable(CaroTable caroTable) {
		caroDAO.saveLostTable(caroTable);
	}
	
	private List<Danger> findDangerCells(List<Cell> allCellsOfRow) {
		List<Danger> allDangerCell = new ArrayList<Danger>();
//		if(allCellsOfRow.size() < 5) {
//			return allDangerCell;
//		}
		int leftPosition = 0;
		int rightPosition = 0;
		for(int i = 0; i < allCellsOfRow.size(); i++) {
			if(allCellsOfRow.get(i).getValue() != Cell.CELL_VALUE_E) {
				break;
			}
			++leftPosition;
		}
		for(int i = allCellsOfRow.size()-1; i >= 0; i--) {
			if(allCellsOfRow.get(i).getValue() != Cell.CELL_VALUE_E) {
				break;
			}
			++rightPosition;
		}
		int checkedNode = 0;
		for(int i = leftPosition; i< allCellsOfRow.size() - rightPosition; i++) {
			if(allCellsOfRow.get(i).getValue() != Cell.CELL_VALUE_E) {
				checkedNode++;
			}
		}
		int leftPiority = 2;
		int rightPiority = 2;
		int centerPiority = 2;
		int emptyHead = 0;
		if(leftPosition > 0) {
			emptyHead++;
		}
		if(rightPosition > 0) {
			emptyHead++;
		}
		int rowLength = allCellsOfRow.size() - leftPosition - rightPosition;
		if(rowLength - checkedNode == 1) {
			centerPiority += 10;
		}
		int dangerLevel = Danger.LEVEL_NORMAL;
		int dangerRate = checkedNode;
		if((checkedNode >= 4 && rowLength - checkedNode <= 1)
				|| (checkedNode == 4 && leftPosition + rightPosition > 0 && rowLength <= 5)) {
			dangerLevel = Danger.LEVEL_RED;
			if(rowLength - checkedNode == 1) {
				centerPiority = 999999;
				for(int i = leftPosition; i < leftPosition + rowLength; i++) {
					if(allCellsOfRow.get(i).getValue() == Cell.CELL_VALUE_E) {
						allDangerCell.add(new Danger(allCellsOfRow.get(i),dangerLevel, dangerRate, centerPiority, emptyHead));
					}
				}
				return allDangerCell;
			}
		} else if(checkedNode == 3 && rowLength <= 4 && leftPosition * rightPosition > 0) {
			dangerLevel = Danger.LEVEL_YELLOW;
			if(rowLength - checkedNode == 1) {
				centerPiority += 10;
			}
		} else if((checkedNode == 2 && rowLength - checkedNode < 2 
				&& leftPosition * rightPosition > 0 
				&& leftPosition + checkedNode + rightPosition >= 5)
				|| (checkedNode == 3 && rowLength - checkedNode<=4 && leftPosition + checkedNode + rightPosition >= 5)) {
			dangerLevel = Danger.LEVEL_WHITE;
			if(rowLength - checkedNode == 1) {
				centerPiority += 10;
			}
		}
		int limitted = 2;
		if(dangerLevel >= Danger.LEVEL_YELLOW) {
			limitted = 1;
			leftPiority = 2;
			rightPiority = 2;
		}else if(leftPosition > rightPosition) {
			leftPiority = 2;
			rightPiority = 1;
		} else if(leftPosition < rightPosition){
			leftPiority = 1;
			rightPiority = 2;
		} else if(leftPosition > 0 && rightPosition > 0) {
			leftPiority = 2;
			rightPiority = 2;
		}
		for(int i = 0; i < leftPosition && i<limitted; i++) {
			allDangerCell.add(new Danger(allCellsOfRow.get(leftPosition-i-1),dangerLevel, dangerRate, (leftPiority - i)*emptyHead, emptyHead));
		}
		for(int i = 0; i < rightPosition && i<limitted; i++) {
			allDangerCell.add(new Danger(allCellsOfRow.get(leftPosition+rowLength+i),dangerLevel, dangerRate, (rightPiority - i)*emptyHead, emptyHead));
		}
		for(int i = leftPosition; i < leftPosition + rowLength; i++) {
			if(allCellsOfRow.get(i).getValue() == Cell.CELL_VALUE_E) {
				allDangerCell.add(new Danger(allCellsOfRow.get(i),dangerLevel, dangerRate, centerPiority*emptyHead, emptyHead));
			}
		}
		return allDangerCell;
	}

}
