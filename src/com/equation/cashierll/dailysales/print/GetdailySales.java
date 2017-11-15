package com.equation.cashierll.dailysales.print;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.equation.cashierll.helpers.DoubleForm;

/**
 *
 * @author Wellington
 */

public class GetdailySales {
	ResultSet rs, rs1;
	Statement stm, stmt;
	double sum = 0;
	DoubleForm df;
	private static final int MAX_CHAR = 18;

	public GetdailySales(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs1;
		df = new DoubleForm();
	}

	public Object[][] getDailySalesData() {
		String rowsQuery = "SELECT product_name,SUM(quantity),SUM(amount) FROM cashpay WHERE date = CURDATE() GROUP BY product_name";
		String extractQuery = "SELECT product_name,SUM(quantity),SUM(amount) FROM cashpay WHERE date = CURDATE() GROUP BY product_name";
		Object[][] data = null;
		try {
			rs1 = stmt.executeQuery(rowsQuery);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {
				rs = stm.executeQuery(extractQuery);
				data = new Object[rows][Header.header.length];
				while (rs.next()) {
					double cost = returnTwoSF(rs.getDouble(3));
					sum += cost;
					String item_type = rs.getString(1);
					int quantity = rs.getInt(2);
					int maxLength = (item_type.length() < MAX_CHAR) ? item_type.length() : MAX_CHAR;
					item_type = item_type.substring(0, maxLength);
					data[i][0] = item_type;
					data[i][1] = quantity;
					data[i][2] = cost;
					i++;

				}
			}
		} catch (SQLException ee) {
			ee.printStackTrace(System.err);
		}
		return data;
	}

	public JTable getTable() {

		DefaultTableModel model = new DefaultTableModel(getDailySalesData(), Header.header);
		JTable table = new JTable(model);
		return table;
	}

	// return cash to two significant figures
	public double returnTwoSF(double number) {
		return new DoubleForm().form(number);
	}

	public double getTotalSum() {
		return sum;
	}

}