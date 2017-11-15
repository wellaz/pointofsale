package com.equation.cashierll.voiding;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */

public class DeleteVoidedReceipt {
	Statement stm;

	public DeleteVoidedReceipt(Statement stm) {
		this.stm = stm;
	}

	public void deleteVoided(int recceiptno) {
		String query = "DELETE FROM cashpay WHERE receiptno = '" + recceiptno + "'";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}