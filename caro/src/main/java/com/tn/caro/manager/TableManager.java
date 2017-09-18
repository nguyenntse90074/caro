package com.tn.caro.manager;

import java.util.HashMap;
import java.util.Map;

import com.tn.caro.bean.CaroTable;

public class TableManager {

	private static Map<Long, CaroTable> allTables = new HashMap<Long, CaroTable>();
	
	public long createdNewTable() {
		long newId = allTables.size() + 1;
		CaroTable newTable = new CaroTable();
		newTable.setTableId(newId);
		allTables.put(newId, newTable);
		return newId;
	}
	
	public void addNewTable(CaroTable newTable) {
		allTables.put(newTable.getTableId(), newTable);
	}
	
	public CaroTable getTableById(long id) {
		return allTables.get(id);
	}
}

