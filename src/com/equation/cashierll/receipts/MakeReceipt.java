package com.equation.cashierll.receipts;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.equation.cashierll.helpers.DoubleForm;
import com.equation.cashierll.printer.support.PrintSupport;
import com.equation.cashierll.temporarydata.delete.DeleteTMPTableData;

/**
 *
 * @author Wellington
 */
public class MakeReceipt {
	ResultSet rs, rs1;
	Statement stm, stmt;
	double sum = 0;
	Object[][] data;
	JTable table;
	DefaultTableModel model;
	private static final int MAX_CHAR = 18;

	// constructor that constructs a all neccessary components with their
	// respective layout.
	public MakeReceipt(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs1;
	}

	// method that is used to make receipt, and that is used to format a
	// receipt.
	public void makeReceipt(int receiptno, double totalcost, double rendered, double change) {
		Object[][] set = getTableData();
		model = new DefaultTableModel(set, Header.header);
		table = new JTable(model);

		double rowcount = table.getRowCount();
		// new PrintSupport(table, model, set, rowcount);

		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setPrintable(new MyPrintable(table, receiptno, totalcost, rendered, change),
				PrintSupport.getPageFormat(pj, rowcount));
		// boolean ok = pj.printDialog();
		// if (ok) {
		try {
			pj.print();
		} catch (PrinterException ex) {
			ex.printStackTrace();
		}
		// }
		new DeleteTMPTableData(rs, stm).deleteTemporaryData();
	}

	public int getRows() {
		String getQuery = "SELECT * FROM transaction_tmp";
		int i = 0;
		try {
			rs = stm.executeQuery(getQuery);
			if (rs.last())
				i = rs.getRow();
			else
				i = 0;
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return i;
	}

	public Object[][] getTableData() {

		String getQuery = "SELECT * FROM transaction_tmp";
		int x = getRows(), i = 0;
		if (x > 0) {
			data = new Object[getRows()][Header.header.length];
			try {
				rs = stm.executeQuery(getQuery);
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
			} catch (SQLException ee) {
				ee.printStackTrace();
			}
		}
		return data; // return object array with data.
	}

	// return cash to two significant figures
	public double returnTwoSF(double number) {
		return new DoubleForm().form(number);
	}
}