package com.equation.cashierll.dailysales.anyperiod.print;

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

public class MakePeriodSalesReceipt {

	ResultSet rs, rs1;
	Statement stm, stmt;
	double sum = 0;
	GetPeriodSales getPeriodSales;

	// constructor that constructs a all neccessary components with their
	// respective layout.
	public MakePeriodSalesReceipt(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs1;
		getPeriodSales = new GetPeriodSales(rs, rs1, stm, stmt);
	}

	// method that is used to make receipt, and that is used to format a
	// receipt.
	public void makeReceipt(String receiptno, double totalcost, String from, String to) {
		// Object[][] set = getTableData(from, to);

		double rowcount = getTable(from, to).getRowCount();
		// new PrintSupport(getTable(), getModel(), set, rowcount);

		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setPrintable(new MyPeriodSalesPrintable(getTable(from, to), receiptno, totalcost, from, to),
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

	private JTable getTable(String from, String to) {
		return getPeriodSales.getTable(from, to);
	}
}