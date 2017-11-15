package com.equation.cashierll.receipts.collection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.JTextArea;

import com.equation.cashierll.receipts.Header;

/**
 *
 * @author Wellington
 */

public class ViewReceipt {
	JTable table;
	Statement stm, stmt;
	ResultSet rs, rs1;
	String item_type;
	int quantity, receiptno;
	double amount, change, rendered, totalcost;

	Object[][] data;
	private static final int MAX_CHAR = 18;

	public ViewReceipt(JTable table, Statement stm, Statement stmt, ResultSet rs, ResultSet rs1) {
		this.table = table;
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
	}

	public void viewIt(JTextArea display) {
		String rcn = table.getValueAt(table.getSelectedRow(), 0).toString();
		receiptno = Integer.parseInt(rcn);
		String query = "SELECT product_name,SUM(quantity),SUM(amount) FROM cashpay WHERE receiptno = '" + receiptno
				+ "' GROUP BY product_name";
		try {
			rs = stm.executeQuery(query);
			rs.last();
			int rows = rs.getRow(), i = 0;
			data = new Object[rows][Header.header.length];
			String query1 = "SELECT product_name,SUM(quantity),SUM(amount) FROM cashpay WHERE receiptno = '" + receiptno
					+ "' GROUP BY product_name";
			rs1 = stmt.executeQuery(query1);
			display.setText("");
			while (rs1.next()) {
				item_type = rs1.getString(1);

				int maxLength = (item_type.length() < MAX_CHAR) ? item_type.length() : MAX_CHAR;
				String item_type1 = item_type.substring(0, maxLength);
				quantity = rs1.getInt(2);
				amount = rs1.getDouble(3);

				data[i][0] = "" + item_type1;
				data[i][1] = "" + quantity;
				data[i][2] = "" + amount;

				display.append("------------------------------------------\n");
				String qn = (quantity > 1) ? "items" : "item";
				display.append(item_type + "\t" + quantity + " " + qn + "\t$" + amount + "\n");

				i++;

			}
			String query2 = "SELECT amount,amount_given,amount_changed FROM receipt_amount WHERE receiptno = '"
					+ receiptno + "'";
			rs = stm.executeQuery(query2);
			rs.next();
			totalcost = rs.getDouble(1);
			rendered = rs.getDouble(2);
			change = rs.getDouble(3);

			display.append("------------------------------------------\n");
			display.append("Total Cost $" + totalcost + "\nTendered $" + rendered + "\nChange $" + change);
			display.append("\nReceipt Number " + receiptno);
			display.append("\n------------------------------------------\n");

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}