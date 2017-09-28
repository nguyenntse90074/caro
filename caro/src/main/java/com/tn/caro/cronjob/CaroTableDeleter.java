package com.tn.caro.cronjob;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tn.caro.bean.CaroTable;

public class CaroTableDeleter implements Runnable {
	
	private Map<Long, CaroTable> allTables;
	
	public CaroTableDeleter(Map<Long, CaroTable> allTables) {
		this.allTables = allTables;
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(60*1000);
				deleteOutOfDateTable();
			} catch (InterruptedException e) {
			}
		}
	}
	public void deleteOutOfDateTable() {
		if(allTables.values().isEmpty()) return;
		int index = 0;
		List<Long> allOutOfDateIndex = new ArrayList<Long>();
		for(CaroTable table : allTables.values()) {
			if(table.isOutOfDate()) {
				allOutOfDateIndex.add(table.getTableId());
			}
		}
		for(Long id : allOutOfDateIndex) {
			allTables.remove(id);
		}
		System.out.println("deleted " + allOutOfDateIndex.size());
	}
}
