package com.tn.caro.manager;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.tn.caro.bean.CaroTable;

public class TableManager {

	private static Map<Long, CaroTable> allTables = new HashMap<Long, CaroTable>();
	
	public long createdNewTable() {
		long newId = gennerateTableId();
		CaroTable newTable = new CaroTable();
		newTable.setTableId(newId);
		allTables.put(newId, newTable);
		return newId;
	}
	
	private long gennerateTableId() {
		String currentTime = LocalDateTime.now().toString();
		long hash = 7;
		for (int i = 0; i < currentTime.length(); i++) {
		    hash = hash*15 + currentTime.charAt(i);
		}
		return hash;
	}
	
	public void addNewTable(CaroTable newTable) {
		allTables.put(newTable.getTableId(), newTable);
	}
	
	public CaroTable getTableById(long id) {
		return allTables.get(id);
	}
	
	public void deleteOutOfDateTable() {
		int i = 0;
		for(CaroTable table : allTables.values()) {
			if(table.isOutOfDate()) {
				allTables.remove(table).getTableId();
				i++;
			}
		}
		System.out.println("Deteled " + i + "tables");
	}
}

