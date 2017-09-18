package com.tn.caro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tn.caro.bean.CaroTable;
import com.tn.caro.bean.Result;
import com.tn.caro.bean.Step;
import com.tn.caro.dao.CaroDAO;

@Service("caroService")
public class CaroService {

	@Autowired
	CaroDAO caroDAO;
	
	public Step findRobotStep(CaroTable caroTable) {
		return null;
	}
	
	public Step findNextStep(CaroTable caroTable) {
		caroTable.getMinimizeTableData();
		//caroDAO.findNextStep();
		return null;
	}
	
	public Result checkResult(Step newStep, CaroTable caroTable) {
		Result result = new Result();
		int[][] winArr = new int[5][2];
		int counter = 0;
		int x = newStep.getX();
		int y = newStep.getY();
		int[][] tableData = caroTable.getTableData();
		for(int i = x - 5; i < x+ 5; i++) {
			if(counter == 5) {
				break;
			}
			if(i<0) {
				continue;
			} else if(i > 29) {
				break;
			}
			if(tableData[i][y] == newStep.getValue()) {
				winArr[counter][0] = i;
				winArr[counter][1] = y;
				++counter;
			} else {
				counter = 0;
			}
		}
		if(counter == 5) {
			result.setIsWin(true);
			return result;
		}
		counter = 0;
		for(int i = y - 5; i < y + 5; i++) {
			if(counter == 5) {
				break;
			}
			if(i<0) {
				continue;
			} else if(i > 29) {
				break;
			}
			if(tableData[x][i] == newStep.getValue()) {
				winArr[counter][0] = x;
				winArr[counter][1] = i;
				++counter;
			} else {
				counter = 0;
			}
		}
		if(counter == 5) {
			result.setIsWin(true);
			return result;
		}
		counter = 0;
		for(int i = x - 5, j = y - 5; i < x + 5 && j < y + 5; i++, j++) {
			if(counter == 5) {
				break;
			}
			if(i<0 || j < 0) {
				continue;
			} else if(i > 29 || j > 29) {
				break;
			}
			if(tableData[i][j] == newStep.getValue()) {
				winArr[counter][0] = i;
				winArr[counter][1] = j;
				++counter;
			} else {
				counter = 0;
			}
		}
		if(counter == 5) {
			result.setIsWin(true);
			return result;
		}
		counter = 0;
		for(int i = x + 5, j = y - 5; i > x - 5 && j < y + 5; i--, j++) {
			if(counter == 5) {
				break;
			}
			if(i<0 || j < 0) {
				continue;
			} else if(i > 29 || j > 29) {
				break;
			}
			if(tableData[i][j] == newStep.getValue()) {
				winArr[counter][0] = i;
				winArr[counter][1] = y;
				++counter;
			} else {
				counter = 0;
			}
		}
		if(counter == 5) {
			result.setIsWin(true);
			return result;
		}
		return result;
	}
}
