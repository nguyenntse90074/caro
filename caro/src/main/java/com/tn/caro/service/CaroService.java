package com.tn.caro.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tn.caro.bean.CaroTable;
import com.tn.caro.bean.Step;
import com.tn.caro.bean.Danger;
import com.tn.caro.bean.Result;
import com.tn.caro.dao.CaroDAO;

@Service("caroService")
public class CaroService {

	@Autowired
	CaroDAO caroDAO;
	
	public Step findRobotStep(CaroTable caroTable) {
		Step lastestUserStep = caroTable.getLastestStep();
		List<List<Step>> allUserRows = caroTable.getAllRowByValue(lastestUserStep.getValue());
		List<Danger> allUserDanger = new ArrayList<Danger>();
		for(List<Step> allStepsOfRow : allUserRows) {
			allUserDanger.addAll(findDangerSteps(allStepsOfRow));
		}
		
		List<Danger> allRobotDanger = new ArrayList<Danger>();
		List<List<Step>> allRobotRows = caroTable.getAllRowByValue(-lastestUserStep.getValue());
		for(List<Step> allStepsOfRow : allRobotRows) {
			allRobotDanger.addAll(findDangerSteps(allStepsOfRow));
		}
		
		return findBestStepForRobot(allUserDanger, allRobotDanger, caroTable);
	}
	
	private Step findBestStepForRobot(List<Danger> allUserDanger, List<Danger> allRobotDanger, CaroTable caroTable) {	
		Collections.sort(allUserDanger);
		Collections.sort(allRobotDanger);
		
		List<Danger> allRobotRedDanger = findDangerByLevel(Danger.LEVEL_RED, allRobotDanger);
		List<Danger> allRobotYellowDanger = findDangerByLevel(Danger.LEVEL_YELLOW, allRobotDanger);
		List<Danger> allRobotWhiteDanger = findDangerByLevel(Danger.LEVEL_WHITE, allRobotDanger);
		
		List<Danger> allUserRedDanger = findDangerByLevel(Danger.LEVEL_RED, allUserDanger);
		List<Danger> allUserYellowDanger = findDangerByLevel(Danger.LEVEL_YELLOW, allUserDanger);
		List<Danger> allUserWhiteDanger = findDangerByLevel(Danger.LEVEL_WHITE, allUserDanger);
		
		if(!allRobotRedDanger.isEmpty()) {
			return allRobotRedDanger.get(0).getStep();
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
			return allUserRedDanger.get(0).getStep();
		}
		if(!allRobotYellowDanger.isEmpty()) {
			Danger dangerLv1;
			for(int i = 0; i<allRobotYellowDanger.size(); i++) {
				dangerLv1 = allRobotYellowDanger.get(i);
				for(int j=0; j<allRobotYellowDanger.size(); j++) {
					if(i != j && dangerLv1.equals(allRobotYellowDanger.get(j))) {
						dangerLv1.augmentRate(allRobotYellowDanger.get(j));
					}
				}
			}
			Collections.sort(allRobotYellowDanger);
			return allRobotYellowDanger.get(0).getStep();
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
			return allUserYellowDanger.get(0).getStep();
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
			return allUserYellowDanger.get(0).getStep();
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
			return allRobotYellowDanger.get(0).getStep();
		}
		
		List<Step> experienceCell = caroDAO.findNextStepByByTableData(caroTable.getTableData());
		if(!experienceCell.isEmpty()) {
			Step bestStep = experienceCell.get(0);
			return bestStep;
		}

//		if(allRobotWhiteDanger.size()>=6) {
//			List<Danger> allContinueDanger = getMultiDimentionDanger(allRobotWhiteDanger);
//			if(!allContinueDanger.isEmpty()) {
//				for(int i = 0; i < allContinueDanger.size(); i++) {
//					for(int j=0; j<allContinueDanger.size(); j++) {
//						if(j == i) continue;
//						if(allContinueDanger.get(i).getStep().equals(allContinueDanger.get(j).getStep())) {
//							allContinueDanger.get(i).augmentRate(allContinueDanger.get(j));
//						}
//					}
//				}
//				Collections.sort(allContinueDanger);
//				return allContinueDanger.get(0).getStep();
//			}
//		}
		
		if(allUserWhiteDanger.size() >= 6) {
			List<Danger> allMultiDimentionDanger = getMultiDimentionDanger(allUserWhiteDanger);
			if(!allMultiDimentionDanger.isEmpty()) {
				for(int i = 0; i < allMultiDimentionDanger.size(); i ++) {
					for(int j = 0; j<allMultiDimentionDanger.size(); j++) {
						if(i == j) continue;
						if(allMultiDimentionDanger.get(i).equals(allMultiDimentionDanger.get(j))) {
							allMultiDimentionDanger.get(i).augmentRate(allMultiDimentionDanger.get(j));
						}
					}
				}
				Collections.sort(allMultiDimentionDanger);
				return allMultiDimentionDanger.get(0).getStep();
			}
		} else if(allUserWhiteDanger.size() > 4 && allUserDanger.size() > 12) {
			if(checkIfHasContinuousDanger(Step.CELL_VALUE_X, allUserWhiteDanger, caroTable)){
				Collections.sort(allUserWhiteDanger);
				return allUserWhiteDanger.get(0).getStep();
			}
		}
//		if(!allRobotDanger.isEmpty()) {
//			for(int i = 0; i < allRobotDanger.size(); i++) {
//				for(int j = 0; j<allRobotDanger.size(); j++) {
//					if(i==j) continue;
//					if(allRobotDanger.get(i).equals(allRobotDanger.get(j))) {
//						allRobotDanger.get(i).augmentRate(allRobotDanger.get(j));
//					}
//				}
//			}
//			Collections.sort(allRobotDanger);
//			return allRobotDanger.get(0).getStep();
//		}

		for(Danger userDanger : allUserDanger) {
			for(Danger robotDanger : allRobotDanger) {
				if(userDanger.equals(robotDanger)) {
					userDanger.augmentRate(robotDanger);
				}
			}
		}
		Collections.sort(allUserDanger);
		return allUserDanger.get(0).getStep();
	}
	
	private boolean checkIfHasContinuousDanger(int value, List<Danger> allDanger, CaroTable caroTable) {
		int count = 0;
		for(Danger danger : allDanger) {
			List<List<Step>> allRowHasValueAndStep = caroTable.getAllRowHasValueAndCell(value, danger.getStep());
			for(List<Step> row : allRowHasValueAndStep) {
				List<Danger> allGreenDanger =  findDangerByLevel(Danger.LEVEL_GREEN, findDangerSteps(row));
				if(allGreenDanger.isEmpty()) continue;
				danger.addRate(1);
				count++;
			}
		}
		return count > 3;
	}
	
	private List<Danger> getMultiDimentionDanger(List<Danger> allDanger) {
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
				if(checkingDanger.getStep().getX() == allDanger.get(j).getStep().getX()) {
					if(Math.abs(allDanger.get(j).getStep().getY() - checkingDanger.getStep().getY()) < 5) {
						if(!rowDangerXX.containsKey(checkingDanger.getCellAddress())) {
							rowDangerXX.put(checkingDanger.getCellAddress(), checkingDanger);
						}
						if(!rowDangerXX.containsKey(allDanger.get(j).getCellAddress())) {
							rowDangerXX.put(allDanger.get(j).getCellAddress(), allDanger.get(j));
						}
					}
				} else if(checkingDanger.getStep().getY() == allDanger.get(j).getStep().getY()) {
					if(Math.abs(allDanger.get(j).getStep().getX() - checkingDanger.getStep().getX()) < 5) {
						if(!rowDangerYY.containsKey(checkingDanger.getCellAddress())) {
							rowDangerYY.put(checkingDanger.getCellAddress(), checkingDanger);
						}
						if(!rowDangerYY.containsKey(allDanger.get(j).getCellAddress())) {
							rowDangerYY.put(allDanger.get(j).getCellAddress(), allDanger.get(j));
						}
					}				
				} else if(checkingDanger.getStep().getY() + allDanger.get(j).getStep().getX() == checkingDanger.getStep().getY() + allDanger.get(j).getStep().getX()) {
					if(Math.abs(allDanger.get(j).getStep().getY() - checkingDanger.getStep().getY()) < 5) {
						if(!rowDangerXXYY.containsKey(checkingDanger.getCellAddress())) {
							rowDangerXXYY.put(checkingDanger.getCellAddress(), checkingDanger);
						}
						if(!rowDangerXXYY.containsKey(allDanger.get(j).getCellAddress())) {
							rowDangerXXYY.put(allDanger.get(j).getCellAddress(), allDanger.get(j));
						}
					}
				} else if(checkingDanger.getStep().getY() - allDanger.get(j).getStep().getX() == checkingDanger.getStep().getY() - allDanger.get(j).getStep().getX()) {
					if(Math.abs(allDanger.get(j).getStep().getY()-checkingDanger.getStep().getY()) < 5) {
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
		List<Step> stepDangerLv2 = new ArrayList<Step>();
		for(Danger danger : rowDangerXX.values()) {
			stepDangerLv2.add(danger.getStep());
		}
		if(!findDangerByLevel(Danger.LEVEL_WHITE, findDangerSteps(stepDangerLv2)).isEmpty()) {
			result.addAll(rowDangerXX.values());
		}
		stepDangerLv2.clear();
		for(Danger danger : rowDangerYY.values()) {
			stepDangerLv2.add(danger.getStep());
		}
		if(!findDangerByLevel(Danger.LEVEL_WHITE, findDangerSteps(stepDangerLv2)).isEmpty()) {
			result.addAll(rowDangerYY.values());
		}
		stepDangerLv2.clear();
		for(Danger danger : rowDangerXXYY.values()) {
			stepDangerLv2.add(danger.getStep());
		}
		if(!findDangerByLevel(Danger.LEVEL_WHITE, findDangerSteps(stepDangerLv2)).isEmpty()) {
			result.addAll(rowDangerXXYY.values());
		}
		stepDangerLv2.clear();
		for(Danger danger : rowDangerYXYX.values()) {
			stepDangerLv2.add(danger.getStep());
		}
		if(!findDangerByLevel(Danger.LEVEL_WHITE, findDangerSteps(stepDangerLv2)).isEmpty()) {
			result.addAll(rowDangerYXYX.values());
		}
		
		return result;
	}
	
	private List<Danger> findDangerByLevel(int level ,List<Danger> allDanger) {
		List<Danger> result = new ArrayList<Danger>();
		for(Danger danger : allDanger) {
			if(danger.getLevel() >= Danger.LEVEL_YELLOW) {
				danger.getStep().setType(Step.TYPE_REQUIRED);
			}
			if(danger.getLevel() >= level) {
				result.add(danger);
			}
		}
		return result;
	}
	public Result checkResult(Step step, CaroTable caroTable) {
		List<Step> winRow = caroTable.findWinRowsByStep(step);
		Result result = new Result();
		result.setIsWin(!winRow.isEmpty());
		result.setWinRow(winRow.toArray(new Step[winRow.size()]));
		return result;
	}
	
	public void saveLostTable(CaroTable caroTable) {
		caroDAO.saveLostTable(caroTable);
	}
	
	private List<Danger> findDangerSteps(List<Step> allStepsOfRow) {
		List<Danger> allDangerCell = new ArrayList<Danger>();
//		if(allCellsOfRow.size() < 5) {
//			return allDangerCell;
//		}
		int leftPosition = 0;
		int rightPosition = 0;
		for(int i = 0; i < allStepsOfRow.size(); i++) {
			if(allStepsOfRow.get(i).getValue() != Step.CELL_VALUE_E) {
				break;
			}
			++leftPosition;
		}
		for(int i = allStepsOfRow.size()-1; i >= 0; i--) {
			if(allStepsOfRow.get(i).getValue() != Step.CELL_VALUE_E) {
				break;
			}
			++rightPosition;
		}
		int checkedNode = 0;
		for(int i = leftPosition; i< allStepsOfRow.size() - rightPosition; i++) {
			if(allStepsOfRow.get(i).getValue() != Step.CELL_VALUE_E) {
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
		int rowLength = allStepsOfRow.size() - leftPosition - rightPosition;
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
					if(allStepsOfRow.get(i).getValue() == Step.CELL_VALUE_E) {
						allDangerCell.add(new Danger(allStepsOfRow.get(i),dangerLevel, dangerRate, centerPiority, emptyHead));
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
			allDangerCell.add(new Danger(allStepsOfRow.get(leftPosition-i-1),dangerLevel, dangerRate, (leftPiority - i)*emptyHead, emptyHead));
		}
		for(int i = 0; i < rightPosition && i<limitted; i++) {
			allDangerCell.add(new Danger(allStepsOfRow.get(leftPosition+rowLength+i),dangerLevel, dangerRate, (rightPiority - i)*emptyHead, emptyHead));
		}
		for(int i = leftPosition; i < leftPosition + rowLength; i++) {
			if(allStepsOfRow.get(i).getValue() == Step.CELL_VALUE_E) {
				allDangerCell.add(new Danger(allStepsOfRow.get(i),dangerLevel, dangerRate, centerPiority*emptyHead, emptyHead));
			}
		}
		return allDangerCell;
	}

}
