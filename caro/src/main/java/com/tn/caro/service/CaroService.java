package com.tn.caro.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		
		return findBestCellForRobot(allUserDanger, allRobotDanger, caroTable.getAllCell());
	}
	
	private Cell findBestCellForRobot(List<Danger> allUserDanger, List<Danger> allRobotDanger, Cell[][] allCell) {	
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
						dangerLv1.augmentRate(allUserDanger.get(j).getRate());
					}
				}
			}
			Collections.sort(allUserRedDanger);
			return allUserRedDanger.get(0).getCell();
		}

		if(!allUserYellowDanger.isEmpty()) {
			Danger dangerLv1;
			for(int i = 0; i<allUserYellowDanger.size(); i++) {
				dangerLv1 = allUserYellowDanger.get(i);
				for(int j=0; j<allUserDanger.size(); j++) {
					if(i != j && dangerLv1.equals(allUserDanger.get(j))) {
						dangerLv1.augmentRate(allUserDanger.get(j).getRate());
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
						dangerLv1.augmentRate(allRobotDanger.get(j).getRate());
					}
				}
				for(Danger userDanger : allUserDanger) {
					if(dangerLv1.equals(userDanger)) {
						dangerLv1.augmentRate(userDanger.getRate());
					}
				}
			}
			Collections.sort(allRobotYellowDanger);
			return allRobotYellowDanger.get(0).getCell();
		}
		
//		if(!allUserGreenDanger.isEmpty()) {
//			Danger dangerLv1;
//			for(int i = 0; i<allUserGreenDanger.size(); i++) {
//				dangerLv1 = allUserGreenDanger.get(i);
//				for(int j=0; j<allUserDanger.size(); j++) {
//					if(i!=j && dangerLv1.equals(allUserDanger.get(j))) {
//						dangerLv1.augmentRate(allUserDanger.get(j).getRate());
//					}
//				}
//				for(Danger robotDanger : allRobotDanger) {
//					if(dangerLv1.equals(robotDanger)) {
//						dangerLv1.augmentRate(robotDanger.getRate());
//					}
//				}
//			}
//			Collections.sort(allUserGreenDanger);
//			return allUserGreenDanger.get(0).getCell();
//		}
		
		if(allUserWhiteDanger.size() >= 6) {
			reRateDangerContinue(allUserWhiteDanger);
			if(allUserWhiteDanger.size() >= 6) {
				for(int i = 0; i < allUserDanger.size(); i ++) {
					for(int j = 0; j<allUserDanger.size(); j++) {
						if(i == j) continue;
						if(allUserDanger.get(i).equals(allUserDanger.get(j))) {
							allUserDanger.get(i).augmentRate(allUserDanger.get(j).getRate());
						}
					}
				}
				Collections.sort(allUserWhiteDanger);
				return allUserWhiteDanger.get(0).getCell();
			}
		}
		if(allRobotWhiteDanger.size()>=6) {
			reRateDangerContinue(allRobotWhiteDanger);
			for(int i = 0; i < allRobotWhiteDanger.size(); i++) {
				for(int j=0; j<allRobotWhiteDanger.size(); j++) {
					if(j == i) continue;
					if(allRobotWhiteDanger.get(i).getCell().equals(allRobotWhiteDanger.get(j).getCell())) {
						allRobotWhiteDanger.get(i).augmentRate(allRobotWhiteDanger.get(j).getRate());
					}
				}
			}
			Collections.sort(allRobotWhiteDanger);
			return allRobotWhiteDanger.get(0).getCell();
		}
		if(!allRobotDanger.isEmpty()) {
			for(Danger robotDanger : allRobotDanger) {
				for(Danger userDanger : allRobotDanger) {
					if(robotDanger.equals(userDanger)) {
						robotDanger.augmentRate(userDanger.getRate());
					}
				}
			}
			Collections.sort(allRobotDanger);
			return allRobotDanger.get(0).getCell();
		}
		return allUserDanger.get(0).getCell();
		
	}
	
	private void reRateDangerContinue(List<Danger> allDanger) {
		Danger checkingDanger = null;
		for(int i = 0; i < allDanger.size(); i++) {
			checkingDanger = allDanger.get(i);
			for(int j = 0; j<allDanger.size(); j++) {
				if(i==j) continue;
				if(checkingDanger.getCell().getX() == allDanger.get(j).getCell().getX()) {
					if(Math.abs(allDanger.get(j).getCell().getY() - checkingDanger.getCell().getY()) < 5) {
						checkingDanger.augmentRate(1);
						allDanger.get(j).augmentRate(1);
					}
				} else if(checkingDanger.getCell().getY() == allDanger.get(j).getCell().getY()) {
					if(Math.abs(allDanger.get(j).getCell().getX() - checkingDanger.getCell().getX()) < 5) {
						checkingDanger.augmentRate(1);
						allDanger.get(j).augmentRate(1);
					}				
				} else if(checkingDanger.getCell().getY() + allDanger.get(j).getCell().getX() == checkingDanger.getCell().getY() + allDanger.get(j).getCell().getX()) {
					if(Math.abs(allDanger.get(j).getCell().getY() - checkingDanger.getCell().getY()) < 5) {
						checkingDanger.augmentRate(1);
						allDanger.get(j).augmentRate(1);
					}
				} else if(checkingDanger.getCell().getY() - allDanger.get(j).getCell().getX() == checkingDanger.getCell().getY() - allDanger.get(j).getCell().getX()) {
					if(Math.abs(allDanger.get(j).getCell().getY()-checkingDanger.getCell().getY()) < 5) {
						checkingDanger.augmentRate(1);
						allDanger.get(j).augmentRate(1);
					}
				}
			}
		}
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
		int leftPiority = 0;
		int rightPiority = 0;
		int centerPiority = 0;
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
		if(dangerLevel >= Danger.LEVEL_GREEN) {
			limitted = 1;
		}
		if(leftPosition > rightPosition) {
			leftPiority = 1;
			rightPiority = 0;
		} else {
			leftPiority = 0;
			rightPiority = 1;
		}
		for(int i = 0; i < leftPosition && i<limitted; i++) {
			allDangerCell.add(new Danger(allCellsOfRow.get(leftPosition-i-1),dangerLevel, dangerRate, leftPiority - i, emptyHead));
		}
		for(int i = 0; i < rightPosition && i<limitted; i++) {
			allDangerCell.add(new Danger(allCellsOfRow.get(leftPosition+rowLength+i),dangerLevel, dangerRate, rightPiority - i, emptyHead));
		}
		for(int i = leftPosition; i < leftPosition + rowLength; i++) {
			if(allCellsOfRow.get(i).getValue() == Cell.CELL_VALUE_E) {
				allDangerCell.add(new Danger(allCellsOfRow.get(i),dangerLevel, dangerRate, centerPiority, emptyHead));
			}
		}
		return allDangerCell;
	}
}
