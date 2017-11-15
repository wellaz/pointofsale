package com.equation.cashierll.temporarydata.getdata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextArea;

import com.equation.cashierll.helpers.DoubleForm;

/**
 *
 * @author Wellington
 */
public class GetTemporaryData {
	// instance variables
	private static final int MAX_CHAR = 25;
	ResultSet rs, rs1;
	Statement stm, stmt;
	public double sum = 0;
	DoubleForm df;

	// defining the constructor with parameters
	public GetTemporaryData(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs1;
		df = new DoubleForm();
	}

	// displaying all products in the shopping cart
	public void getTemporaryData(JTextArea display) {
		deleteZeroFields();
		String getQuery = "SELECT * FROM transaction_tmp";
		try {
			rs = stm.executeQuery(getQuery);
			if (!rs.next()) {
				return;
			} else {
				do {
					double cost = df.form(rs.getDouble(3));
					sum += cost;
					String item_type = rs.getString(1);
					int maxLength = (item_type.length() < MAX_CHAR) ? item_type.length() : MAX_CHAR;
					item_type = item_type.substring(0, maxLength);
					String text = item_type + "\t\t" + rs.getInt(2) + "\t" + cost
							+ "\n--------------------------------------------------------------------------------------------\n";
					// EventQueue.invokeLater(() -> {
					display.append(text);
					// });
				} while (rs.next());
			}
		} catch (SQLException ee) {
			ee.printStackTrace(System.err);
		}
	}

	// deleting from the transaction list,all products whose cost price is set
	// to zero
	public void deleteZeroFields() {
		String getQuery = "SELECT * FROM transaction_tmp";
		try {
			rs1 = stmt.executeQuery(getQuery);
			if (!rs1.next()) {
				return;
			} else {
				do {
					double cost = rs1.getDouble(3);
					if (cost <= 0) {
						String deleteQuery = "DELETE FROM transaction_tmp WHERE amount = '" + cost + "'";
						stm.execute(deleteQuery);
					}
				} while (rs1.next());
			}
		} catch (SQLException ee) {
			ee.printStackTrace(System.err);
		}
	}

	// return the total cost from the transaction list
	public double getTotalCost() {
		return df.form(sum);
	}
}