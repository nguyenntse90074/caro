package com.tn.caro.bean;

import java.util.List;
import java.util.Map;

public abstract class Row {
	public static final byte ROW_TYPE_HORIZONTAL = 0;
	public static final byte ROW_TYPE_VERTICAL = 1;
	public static final byte ROW_TYPE_DIAGONAL_DOWN = 2;
	public static final byte ROW_TYPE_DIAGONAL_UP = 3;
	
	protected byte type;
	protected byte length;
	protected Step[] allEmptyCell;
	protected Cell cellBegin;
	protected Cell cellEnd;
	protected Map<Byte, List<CheckedRow>> allCheckedRow;
	
	public Row(byte type, byte length, Cell cellBegin, Cell cellEnd) {
		this.type = 0;
		this.length = length;
		allEmptyCell = new Step[length];
	}
	public void addNewStep(Step newStep) {
		setStepToRow(newStep);
		remapRowByValue(Step.CELL_VALUE_X);
		remapRowByValue(Step.CELL_VALUE_O);
	}
	
	protected List<Step> findWinStep(byte value) {
		List<CheckedRow> allCheckedRowByValue = allCheckedRow.get(value);
		if(allCheckedRowByValue == null) return null;
		for(CheckedRow checkedRow : allCheckedRowByValue) {
			if(checkedRow.isWin()) {
				return checkedRow.findWinStep();
			}
		}
		return null;
	}
	abstract void setStepToRow(Step newStep);
	abstract void remapRowByValue(byte value);
}
