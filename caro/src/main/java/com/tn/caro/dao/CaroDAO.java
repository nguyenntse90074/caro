package com.tn.caro.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tn.caro.bean.CaroTable;
import com.tn.caro.bean.Step;
import com.tn.caro.cronjob.RecordDataJob;

@Repository
public class CaroDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void saveLostTable(CaroTable caroTable) {
		StatelessSession session = sessionFactory.openStatelessSession(); 
		Runnable dataRecorder = new RecordDataJob(session, caroTable);
		Thread dataRecordThreader = new Thread(dataRecorder);
		dataRecordThreader.start();
	}
	
	public List<Step> findNextStepByByTableData(String tableData) {
		String selectQuery = "select next_user_step from caro_table where table_data = ? order by rate_robot_step;";
		StatelessSession session = sessionFactory.openStatelessSession(); 
		SQLQuery query = session.createSQLQuery(selectQuery);
		query.setString(0, tableData);
		List<String> allResult = query.list();
		List<Step> allStep = new ArrayList<Step>();
		for(String result : allResult) {
			if(result == null || result.isEmpty()) continue;
			allStep.add(new Step(result));
		}
		session.close();
		return allStep;
	}
}
