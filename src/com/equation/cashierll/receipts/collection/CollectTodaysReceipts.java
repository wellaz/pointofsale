package com.equation.cashierll.receipts.collection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.cashierll.helpers.SetDateCreated;

/**
 *
 * @author Wellington
 */

public class CollectTodaysReceipts {
	ResultSet rs, rs1;
	Statement stm, stmt;

	public CollectTodaysReceipts(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
	}

	public void getTodaysReceiptList() {
		String today = new SetDateCreated().getDate();
		String query = "SELECT * FROM receipt_amount WHERE date = '" + today + "'";
		try {
			rs = stm.executeQuery(query);
			rs.next();
			int rows = rs.getRow();
			if (rows > 0) {
				System.out.println(rows);
				AllArrays allArrays = new AllArrays();
				String query1 = "SELECT * FROM receipt_amount WHERE date = '" + today + "'";
				rs1 = stm.executeQuery(query1);

				while (rs1.next()) {
					int receiptno = rs1.getInt(1);
					double totalcost = rs1.getDouble(2);
					double given = rs1.getDouble(3);
					double change = rs1.getDouble(4);
					double collected = rs1.getDouble(5);
					String date = rs1.getString(6);
					String time = rs1.getString(7);

					allArrays.receipts.add(receiptno);
					allArrays.totalcostarray.add(totalcost);
					allArrays.tenderedarray.add(given);
					allArrays.changearray.add(change);
					allArrays.collectedarray.add(collected);
					allArrays.datearray.add(date);
					allArrays.timearray.add(time);
				}
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}