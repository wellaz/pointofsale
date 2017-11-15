package com.equation.cashierll.currentstock.print;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.equation.cashierll.deco.TranslucentJPanel;
import com.equation.cashierll.helpers.DoubleForm;

/**
 *
 * @author Wellington
 */

public class GetCurrentStockData {

	ResultSet rs, rs1;
	Statement stm, stmt;
	DefaultTableModel model;
	private static final int MAX_CHAR = 15;

	public GetCurrentStockData(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs1;
	}

	public Object[][] getData() {
		JPanel panel = new TranslucentJPanel(Color.BLUE);
		panel.setLayout(new BorderLayout());
		String rowsQuery = "SELECT product_name,items_sold,remaining FROM bulk_stock";
		String extractQuery = "SELECT product_name,items_sold,remaining FROM bulk_stock";
		Object[][] data = null;
		try {
			rs1 = stmt.executeQuery(rowsQuery);
			rs1.last();
			int rows = rs1.getRow(), i = 0;
			if (rows > 0) {
				rs = stm.executeQuery(extractQuery);
				data = new Object[rows][Header.header.length];
				while (rs.next()) {
					String item_type = rs.getString(1);
					int quantity = rs.getInt(2);
					int remaining = rs.getInt(3);
					int maxLength = (item_type.length() < MAX_CHAR) ? item_type.length() : MAX_CHAR;
					item_type = item_type.substring(0, maxLength);
					data[i][0] = item_type;
					data[i][1] = quantity;
					data[i][2] = remaining;
					i++;
				}
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return data;
	}

	public JTable getTable() {
		DefaultTableModel model = new DefaultTableModel(getData(), Header.header);
		JTable table = new JTable(model);
		return table;
	}

	// return cash to two significant figures
	public double returnTwoSF(double number) {
		return new DoubleForm().form(number);
	}
}