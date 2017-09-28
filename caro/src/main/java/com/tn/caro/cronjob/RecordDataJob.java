package com.tn.caro.cronjob;

import org.hibernate.SQLQuery;
import org.hibernate.StatelessSession;

import com.tn.caro.bean.CaroTable;
import com.tn.caro.bean.Step;

public class RecordDataJob implements Runnable {

	private StatelessSession session;
	private CaroTable caroTable;
	
	public RecordDataJob(StatelessSession session, CaroTable caroTable) {
		this.session = session;
		this.caroTable = caroTable;
	}
	public void run() {
		String insertQuery = "CALL insert_caro_table(?, ?, ?, ?, ?);";
		SQLQuery query = session.createSQLQuery(insertQuery);
		for(int i = 2; i < caroTable.getHistoryLength() - 2; i += 2) {
			if(caroTable.getStep((short)i).getType() == Step.TYPE_REQUIRED) continue;
			query.setLong(0, caroTable.getTableId());
			query.setString(1, caroTable.getTableDataBefore(i));
			query.setString(2, caroTable.getStep((short)i).toString());
			query.setInteger(3, i);
			query.setString(4, caroTable.getStep((short)(i - 1)).toString());
			query.executeUpdate();
			break;
		}
		session.close();
	}

}
