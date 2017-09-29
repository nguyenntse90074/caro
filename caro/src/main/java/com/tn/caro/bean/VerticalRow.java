package com.tn.caro.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VerticalRow extends Row {

	public VerticalRow(Cell cellBegin, Cell cellEnd) {
		super(Row.ROW_TYPE_VERTICAL,(byte)(cellEnd.getY() - cellBegin.getY() + 1), cellBegin, cellEnd);
		allCheckedRow = new HashMap<Byte, List<CheckedRow>>();
	}

	@Override
	void setStepToRow(Step newStep) {
		allEmptyCell[newStep.getX()] = newStep;
	}

	@Override
	void remapRowByValue(byte value) {
		List<CheckedRow> allRows = new ArrayList<CheckedRow>();
		CheckedRow checkedRow = new CheckedRow(type);
		List<Step> tailList = new ArrayList<Step>();
		boolean hasValue = false;
		Step checkingStep = null;
		for(byte x = 0; x < length; x++) {
			checkingStep = allEmptyCell[x];
			if(checkingStep.getValue() == Step.CELL_VALUE_E) {
				if(hasValue) tailList.add(checkingStep);
				checkedRow.addStep(checkingStep);
			} else if(checkingStep.getValue() == value) {
				if(hasValue && tailList.size() > 1) {
					allRows.add(checkedRow);
					checkedRow = new CheckedRow(type);
					checkedRow.addAllStep(tailList);
					tailList.clear();
					hasValue = false;
				} else {
					checkedRow.addStep(checkingStep);
					hasValue = true;
					tailList.clear();
				}
			}
			if(checkingStep.getValue() == -value || x ==  length - 1) {
				if(hasValue) {
					allRows.add(checkedRow);
				}
				hasValue = false;
				tailList.clear();
				checkedRow = new CheckedRow(type);
			}
		}
		if(allRows.isEmpty()) return;
		if(allCheckedRow.containsKey(value)) {
			allCheckedRow.replace(value, allRows);
		} else {
			allCheckedRow.put(value, allRows);
		}
	}
	

}
