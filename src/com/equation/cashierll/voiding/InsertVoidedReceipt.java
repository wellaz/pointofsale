package com.equation.cashierll.voiding;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */

public class InsertVoidedReceipt {
	Statement stm;

	public InsertVoidedReceipt(Statement stm) {
		this.stm = stm;
	}

	public void insertVoidedReceipt(int receiptno, double amount, double amount_given, double amount_changed,
			double change_collected, String date, String time) {
		String query = "INSERT INTO receipt_amount_voided(receiptno,amount,amount_given,amount_changed,change_collected,date,time) VALUES('"
				+ receiptno + "','" + amount + "','" + amount_given + "','" + amount_changed + "','" + change_collected
				+ "','" + date + "','" + time + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace(System.err);
		}
	}
}