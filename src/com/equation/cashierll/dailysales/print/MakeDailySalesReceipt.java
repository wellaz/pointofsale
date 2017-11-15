package com.equation.cashierll.dailysales.print;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JTable;

import com.equation.cashierll.printer.support.PrintSupport;

/**
 *
 * @author Wellington
 */

public class MakeDailySalesReceipt {
	ResultSet rs, rs1;
	Statement stm, stmt;
	double sum = 0;
	GetdailySales getdailySales;

	// constructor that constructs a all neccessary components with their
	// respective layout.
	public MakeDailySalesReceipt(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs1;
		getdailySales = new GetdailySales(rs, rs1, stm, stmt);
	}

	// method that is used to make receipt, and that is used to format a
	// receipt.
	public void makeReceipt(String receiptno, double totalcost, String dated) {
		// Object[][] set = getTableData();

		double rowcount = getTable().getRowCount();
		// new PrintSupport(getTable(), getModel(), set, rowcount);

		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setPrintable(new MyDailySalesPrintable(getTable(), receiptno, totalcost, dated),
				PrintSupport.getPageFormat(pj, rowcount));
		// boolean ok = pj.printDialog();
		// if (ok) {
		try {
			pj.print();
		} catch (PrinterException ex) {
			ex.printStackTrace();
		}
		// }
	}

	private JTable getTable() {
		return getdailySales.getTable();
	}
}