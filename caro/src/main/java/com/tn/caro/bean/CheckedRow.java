package com.tn.caro.bean;

import java.util.ArrayList;
import java.util.List;

public class CheckedRow {
	
	private List<Step> allStep;
	private List<Danger> allDanger;
	private byte dangerLevel;
	private byte rowType;
	private byte emptyTail;
	private byte emptyHead;
	private byte checkedNode;
	private byte emptyMiddle;
	
	public CheckedRow(byte rowType) {
		this.rowType = rowType;
		allStep = new ArrayList<Step>(30);
	}
	
	public void addAllStep(List<Step> allNewStep) {
		if(allNewStep == null || allNewStep.isEmpty()) return;
		for(Step newStep : allNewStep) {
			if(newStep == null) continue;
			addStep(newStep);
		}
	}
	
	public void addStep(Step step) {
		addStepAt(allStep.size(), step);
	}
	
	public void addStepAt(int index, Step step) {
		allStep.add(index, step);
		refreshCheckedRow();
	}
	
	private void refreshCheckedRow() {
		for(int i = 0; i<allStep.size(); i++) {
			if(allStep.get(i).getValue() == Step.CELL_VALUE_E) {
				emptyHead++;
			} else {
				break;
			}
		}
		for(int i = allStep.size() -1; i>=0; i--) {
			if(allStep.get(i).getValue() == Step.CELL_VALUE_E) {
				emptyTail++;
			} else {
				break;
			}
		}
		for(Step step : allStep) {
			if(step.getValue() != Step.CELL_VALUE_E) {
				checkedNode++;
			}
		}
		emptyMiddle = (byte)(allStep.size() - checkedNode - emptyHead - emptyTail);
	}

	public void clearRow() {
		allStep.clear();
		if(allDanger != null) {
			allDanger.clear();
		}
		dangerLevel = 0;
		rowType = 0;
	}
	
	public boolean isWin() {
		return checkedNode == 5 && emptyMiddle == 0 && (emptyHead + emptyTail > 0);
	}
	
	public List<Step> findWinStep() {
		List<Step> result = new ArrayList<Step>();
		int counter = 0;
		for(Step step : allStep) {
			if(step.getValue() == Step.CELL_VALUE_E) {
				if(counter == 5) {
					return result;
				} else {
					counter = 0;
					result.clear();
				}
			} else {
				result.add(step);
				counter++;
			}
		}
		return null;
	}
}
