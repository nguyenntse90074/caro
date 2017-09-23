package com.tn.caro.cronjob;

import org.springframework.beans.factory.annotation.Autowired;

import com.tn.caro.manager.TableManager;

public class CaroTableDeleter implements Runnable {

	static {
		Runnable tableDeleter = new CaroTableDeleter();
		Thread tableDeleterThread = new Thread(tableDeleter);
		tableDeleterThread.start();
	}
	
	@Autowired
	TableManager tableManager;
	
	public void run() {
		while(true) {
			try {
				System.out.println("run job");
				Thread.sleep(60*1000);
				tableManager.deleteOutOfDateTable();
			} catch (InterruptedException e) {
			}
		}
	}
}
