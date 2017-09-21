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
		Collections.sort(allUserDanger);
		if(allRobotDanger.isEmpty()) {
			return allUserDanger.get(0).getCell();
		}
		
		List<Danger> allRobotRedDanger = findDangerByLevel(Danger.LEVEL_RED, allRobotDanger);
		if(!allRobotRedDanger.isEmpty()) {
			Collections.sort(allRobotDanger);
			return allRobotDanger.get(0).getCell();
		}
		List<Danger> allUserRedDanger = findDangerByLevel(Danger.LEVEL_RED, allUserDanger);
		if(!allUserRedDanger.isEmpty()) {
			for(Danger userRedDanger : allUserRedDanger) {
				for(Danger robotDanger : allRobotDanger) {
					if(userRedDanger.equals(robotDanger)){
						userRedDanger.augmentRate(robotDanger.getRate());
					}
				}
			}
			Collections.sort(allUserRedDanger);
			return allUserRedDanger.get(0).getCell();
		}
		List<Danger> allRobotYellowDanger = findDangerByLevel(Danger.LEVEL_YELLOW, allRobotDanger);
		if(!allRobotYellowDanger.isEmpty()) {
			for(Danger robotYellowDanger : allRobotYellowDanger) {
				for(Danger userDanger : allUserDanger) {
					if(robotYellowDanger.equals(userDanger)) {
						robotYellowDanger.augmentRate(userDanger.getRate());
					}
				}
			}
			Collections.sort(allRobotYellowDanger);
			return allRobotYellowDanger.get(0).getCell();
		}
		List<Danger> allUserYellowDanger = findDangerByLevel(Danger.LEVEL_YELLOW, allUserDanger);
		if(!allUserYellowDanger.isEmpty()) {
			for(Danger userYellowDanger : allUserYellowDanger) {
				for(Danger robotDanger : allRobotDanger) {
					if(userYellowDanger.equals(robotDanger)) {
						userYellowDanger.augmentRate(robotDanger.getRate());
					}
				}
			}
			Collections.sort(allUserYellowDanger);
			return allUserYellowDanger.get(0).getCell();
		}
		
		List<Danger> allUserSilverDanger = findDangerByLevel(Danger.LEVEL_SILVER, allUserDanger);
		if(!allUserSilverDanger.isEmpty()) {
			for(Danger userSilverDanger : allUserSilverDanger) {
				for(Danger robotDanger : allRobotDanger) {
					if(userSilverDanger.equals(robotDanger)) {
						userSilverDanger.augmentRate(robotDanger.getRate());
					}
				}
			}
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
		for(int i = 0; i<allRobotDanger.size(); i++) {
			dangerLv1 = allRobotDanger.get(i);
			for(int j=i+1; j<allRobotDanger.size()-1; j++) {
				if(dangerLv1.equals(allRobotDanger.get(j))) {
					dangerLv1.augmentRate(allRobotDanger.get(j).getRate());
				}
			}
		}
		Collections.sort(allRobotDanger);
		
		if(allRobotDanger.get(0).getRate() > allUserDanger.get(0).getRate()) {
			return allRobotDanger.get(0).getCell();
		}
		return allUserDanger.get(0).getCell();
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
		Cell[] rowCells = caroTable.getAllCellAtRow(cell.getY());
		Result rs = checkResultInRow(cell.getValue(), rowCells);
		if(rs.getIsWin()) {
			return rs;
		}
		rowCells = caroTable.getAllCellAtCollumn(cell.getX());
		rs = checkResultInRow(cell.getValue(), rowCells);
		if(rs.getIsWin()) {
			return rs;
		}
		return rs;
	}
	
	private Result checkResultInRow(int value, Cell[] rowCells) {
		Result result = new Result();
		Cell[] winRow = new Cell[5];
		int counter = 0;
		for(Cell cell : rowCells) {
			if(counter == 5) {
				break;
			}
			if(cell.getValue() == value) {
				winRow[counter++] = cell;
			} else {
				counter = 0;
			}
		}
		result.setIsWin(counter == 5);
		result.setWinRow(winRow);
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
		int rowLength = allCellsOfRow.size() - leftPosition - rightPosition;
		int dangerRate = checkedNode*(checkedNode*leftPosition + checkedNode*rightPosition);
		int dangerLevel = Danger.LEVEL_NORMAL;
		if(dangerRate > 0 && checkedNode >= 4 && rowLength - checkedNode <= 2) {
			dangerLevel = Danger.LEVEL_RED;
		} else if(leftPosition > 0 && rightPosition > 0 && checkedNode >= 3 && rowLength - checkedNode <= 1) {
			dangerLevel = Danger.LEVEL_YELLOW;
		} else if(leftPosition + rightPosition > 2 && checkedNode >= 3 && rowLength - checkedNode <= 1) {
			dangerLevel = Danger.LEVEL_SILVER;
		}
		int limitted = 2;
		if(dangerLevel >= Danger.LEVEL_YELLOW) {
			limitted = 1;
		}
		for(int i = 0; i < leftPosition && i<limitted; i++) {
			allDangerCell.add(new Danger(allCellsOfRow.get(leftPosition-i-1),dangerLevel, dangerRate - i + leftPosition));
		}
		for(int i = 0; i < rightPosition && i<limitted; i++) {
			allDangerCell.add(new Danger(allCellsOfRow.get(leftPosition+checkedNode+i),dangerLevel, dangerRate - i + rightPosition));
		}
		for(int i = leftPosition; i < leftPosition + checkedNode; i++) {
			if(allCellsOfRow.get(i).getValue() == Cell.CELL_VALUE_E) {
				allDangerCell.add(new Danger(allCellsOfRow.get(i),dangerLevel, dangerRate + leftPosition + rightPosition + i));
			}
		}
		return allDangerCell;
	}
}
