package com.equation.cashierll.dailysales;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */

public class DeleteActualReceipt {
	Statement stm;

	public DeleteActualReceipt(Statement stm) {
		this.stm = stm;
	}

	public void deleteActualReceipt(int receiptno) {
		String query = "DELETE FROM receipt_amount WHERE receiptno = '" + receiptno + "'";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}