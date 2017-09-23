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
		
		return findBestCellForRobot(allUserDanger, allRobotDanger);
	}
	
	private Cell findBestCellForRobot(List<Danger> allUserDanger, List<Danger> allRobotDanger) {	
		List<Danger> allRobotRedDanger = findDangerByLevel(Danger.LEVEL_RED, allRobotDanger);
		if(!allRobotRedDanger.isEmpty()) {
			Collections.sort(allRobotDanger);
			return allRobotDanger.get(0).getCell();
		}
		List<Danger> allUserRedDanger = findDangerByLevel(Danger.LEVEL_RED, allUserDanger);
		if(!allUserRedDanger.isEmpty()) {
			Danger dangerLv1;
			for(int i = 0; i<allUserRedDanger.size(); i++) {
				dangerLv1 = allUserRedDanger.get(i);
				for(int j=i+1; j<allUserDanger.size()-1; j++) {
					if(dangerLv1.equals(allUserDanger.get(j))) {
						dangerLv1.augmentRate(allUserDanger.get(j).getRate());
					}
				}
			}
//			for(Danger userRedDanger : allUserRedDanger) {
//				for(Danger robotDanger : allRobotDanger) {
//					if(userRedDanger.equals(robotDanger)){
//						userRedDanger.augmentRate(robotDanger.getRate());
//					}
//				}
//			}
			Collections.sort(allUserRedDanger);
			return allUserRedDanger.get(0).getCell();
		}
		List<Danger> allUserYellowDanger = findDangerByLevel(Danger.LEVEL_YELLOW, allUserDanger);
		if(!allUserYellowDanger.isEmpty()) {
			Danger dangerLv1;
			for(int i = 0; i<allUserYellowDanger.size(); i++) {
				dangerLv1 = allUserYellowDanger.get(i);
				for(int j=i+1; j<allUserDanger.size()-1; j++) {
					if(dangerLv1.equals(allUserDanger.get(j))) {
						dangerLv1.augmentRate(allUserDanger.get(j).getRate());
					}
				}
			}
//			for(Danger userYellowDanger : allUserYellowDanger) {
//				for(Danger robotDanger : allRobotDanger) {
//					if(userYellowDanger.equals(robotDanger)) {
//						userYellowDanger.augmentRate(robotDanger.getRate());
//					}
//				}
//			}
			Collections.sort(allUserYellowDanger);
			//return allUserYellowDanger.get(0).getCell();
		}
		
		List<Danger> allRobotYellowDanger = findDangerByLevel(Danger.LEVEL_YELLOW, allRobotDanger);
		if(!allRobotYellowDanger.isEmpty()) {
			Danger dangerLv1;
			for(int i = 0; i<allRobotYellowDanger.size(); i++) {
				dangerLv1 = allRobotYellowDanger.get(i);
				for(int j=i+1; j<allRobotDanger.size()-1; j++) {
					if(dangerLv1.equals(allRobotDanger.get(j))) {
						dangerLv1.augmentRate(allRobotDanger.get(j).getRate());
					}
				}
			}
			for(Danger robotYellowDanger : allRobotYellowDanger) {
				for(Danger userDanger : allUserDanger) {
					if(robotYellowDanger.equals(userDanger)) {
						robotYellowDanger.augmentRate(userDanger.getRate());
					}
				}
			}
			Collections.sort(allRobotYellowDanger);
			//return allRobotYellowDanger.get(0).getCell();
		}
		if(allUserYellowDanger.isEmpty() && !allRobotYellowDanger.isEmpty()) {
			return allRobotYellowDanger.get(0).getCell();
		} else if(allRobotYellowDanger.isEmpty() && !allUserYellowDanger.isEmpty()) {
			return allUserYellowDanger.get(0).getCell();
		} else if(!allRobotYellowDanger.isEmpty() && !allUserYellowDanger.isEmpty()){
			if(allUserYellowDanger.get(0).getRate() >= allRobotYellowDanger.get(0).getRate()) {
				return allUserYellowDanger.get(0).getCell();
			} else {
				return allRobotYellowDanger.get(0).getCell();
			}
		}
		
		List<Danger> allUserSilverDanger = findDangerByLevel(Danger.LEVEL_SILVER, allUserDanger);
		if(allUserSilverDanger.size()>=2) {
			Danger dangerLv1;
			for(int i = 0; i<allUserSilverDanger.size(); i++) {
				dangerLv1 = allUserSilverDanger.get(i);
				for(int j=i+1; j<allUserDanger.size()-1; j++) {
					if(dangerLv1.equals(allUserDanger.get(j))) {
						dangerLv1.augmentRate(allUserDanger.get(j).getRate());
					}
				}
			}
			Collections.sort(allUserSilverDanger);
			return allUserSilverDanger.get(0).getCell();
		}
		
		Danger dangerLv1 = null;
		for(int i = 0; i<allUserDanger.size(); i++) {
			dangerLv1 = allUserDanger.get(i);
			for(int j=i+1; j<allUserDanger.size()-1; j++) {
				if(dangerLv1.equals(allUserDanger.get(j))) {
					dangerLv1.augmentRate(allUserDanger.get(j).getRate());
				}
			}
		}
		Collections.sort(allUserDanger);
		return allUserDanger.get(0).getCell();
//		if(allRobotDanger.isEmpty()) {
//			return allUserDanger.get(0).getCell();
//		}
//		
//		for(int i = 0; i<allRobotDanger.size(); i++) {
//			dangerLv1 = allRobotDanger.get(i);
//			for(int j=i+1; j<allRobotDanger.size()-1; j++) {
//				if(dangerLv1.equals(allRobotDanger.get(j))) {
//					dangerLv1.augmentRate(allRobotDanger.get(j).getRate());
//				}
//			}
//		}
//		Collections.sort(allRobotDanger);
//		
//		if(allRobotDanger.get(0).getRate() > allUserDanger.get(0).getRate()) {
//			return allRobotDanger.get(0).getCell();
//		}
//		return allUserDanger.get(0).getCell();
	}
	
	private List<Danger> findDangerByLevel(int level ,List<Danger> allDanger) {
		List<Danger> result = new ArrayList<Danger>();
		for(Danger danger : allDanger) {
			if(danger.getLevel() == level) {
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
		if(allCellsOfRow.size() < 5) {
			return allDangerCell;
		}
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
		int leftPiority = leftPosition;
		int rightPiority = rightPosition;
		int centerPiority = 0;
		int rowLength = allCellsOfRow.size() - leftPosition - rightPosition;
		if(rowLength - checkedNode == 1) {
			centerPiority += 10;
		}
		int dangerLevel = Danger.LEVEL_NORMAL;
		if(leftPosition + rightPosition > 0 && checkedNode >= 4 && rowLength - checkedNode <= 1) {
			dangerLevel = Danger.LEVEL_RED;
			if(rowLength - checkedNode == 1) {
				centerPiority = 999999;
			}
		} else if(leftPosition > 0 && rightPosition > 0 && checkedNode >= 3 && rowLength - checkedNode <= 1) {
			dangerLevel = Danger.LEVEL_YELLOW;
			if(rowLength - checkedNode == 1) {
				centerPiority = 100;
			}
		} else if(leftPosition + rightPosition + rowLength - checkedNode >= 5 && checkedNode >= 3 && rowLength - checkedNode <= 1) {
			dangerLevel = Danger.LEVEL_SILVER;
		} else if(leftPosition>0 && rightPosition>0 && leftPosition + rightPosition >=3 && checkedNode == 2 && rowLength == 2) {
			dangerLevel = Danger.LEVEL_SILVER;
		}
		int limitted = 3;
		if(dangerLevel >= Danger.LEVEL_YELLOW) {
			limitted = 1;
		} else if(dangerLevel >= Danger.LEVEL_SILVER) {
			limitted = 2;
		}
		int dangerRate = checkedNode*10/(rowLength-checkedNode+1);
		if(leftPosition > 0) {
			dangerRate++;
		}
		if(rightPosition > 0) {
			dangerRate++;
		}
		if(checkedNode == 1) {
			leftPiority = 0;
			rightPiority = 0;
			dangerRate = 2;
		}
		for(int i = 0; i < leftPosition && i<limitted; i++) {
			allDangerCell.add(new Danger(allCellsOfRow.get(leftPosition-i-1),dangerLevel, dangerRate - i + leftPiority));
		}
		for(int i = 0; i < rightPosition && i<limitted; i++) {
			allDangerCell.add(new Danger(allCellsOfRow.get(leftPosition+rowLength+i),dangerLevel, dangerRate - i + rightPiority));
		}
		for(int i = leftPosition; i < leftPosition + checkedNode; i++) {
			if(allCellsOfRow.get(i).getValue() == Cell.CELL_VALUE_E) {
				allDangerCell.add(new Danger(allCellsOfRow.get(i),dangerLevel, dangerRate + centerPiority));
			}
		}
		return allDangerCell;
	}
}
