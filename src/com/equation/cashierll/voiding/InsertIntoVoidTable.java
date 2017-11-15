package com.equation.cashierll.voiding;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */

public class InsertIntoVoidTable {
	Statement stm, stmt;
	ResultSet rs;

	public InsertIntoVoidTable(Statement stm, Statement stmt, ResultSet rs) {
		this.stm = stm;
		this.stmt = stm;
		this.rs = rs;
	}

	public void insertData(int receiptno, int cashierid, String product_name, int quantity, double amount, String date,
			String time, String dated) {
		String query = "INSERT INTO voided_receipts(receiptno,cashier_id,product_name,quantity,amount,date,time,dated) VALUES('"
				+ receiptno + "','" + cashierid + "','" + product_name + "','" + quantity + "','" + amount + "','"
				+ date + "','" + time + "','" + dated + "')";
		try {
			stm.execute(query);
			String query1 = "SELECT product_name,items_sold,remaining FROM bulk_stock WHERE product_name='"
					+ product_name + "'";
			rs = stmt.executeQuery(query1);
			if (rs.next()) {
				int items_sold = rs.getInt(2);
				int remaining = rs.getInt(3);

				int new_items_sold = items_sold - quantity;
				int new_remaining = remaining + quantity;
				String query2 = "UPDATE bulk_stock SET items_sold = '" + new_items_sold + "',remaining = '"
						+ new_remaining + "' WHERE product_name= '" + product_name + "'";
				stm.executeUpdate(query2);
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}