package com.tn.caro.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tn.caro.bean.CaroTable;
import com.tn.caro.bean.Cell;

@Repository
public class CaroDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void saveLostTable(CaroTable caroTable) {
		String insertQuery = "CALL insert_caro_table(?, ?, ?, ?);";
		StatelessSession session = sessionFactory.openStatelessSession(); 
		SQLQuery query = session.createSQLQuery(insertQuery);
		query.setString(0, caroTable.getTableDataBefore(6));
		query.setString(1, caroTable.getStepBefore(6));
		query.setInteger(2, 6);
		query.setString(3, caroTable.getTableData());
		query.executeUpdate();
		session.close();
	}
	
	public List<Cell> findNextStepByByTableData(String tableData) {
		String selectQuery = "select next_step_1 from caro_table where table_data = ?;";
		StatelessSession session = sessionFactory.openStatelessSession(); 
		SQLQuery query = session.createSQLQuery(selectQuery);
		query.setString(0, tableData);
		List<String> allResult = query.list();
		List<Cell> allCell = new ArrayList<Cell>();
		for(String result : allResult) {
			if(result == null || result.isEmpty()) continue;
			allCell.add(new Cell(result));
		}
		session.close();
		return allCell;
	}
}
