package com.equation.cashierll.receipts.collection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.equation.cashierll.deco.BlinkingButton;
import com.equation.cashierll.deco.BlinkingLabel;
import com.equation.cashierll.fonts.AllFonts;
import com.equation.cashierll.printer.support.PrintSupport;
import com.equation.cashierll.receipts.Header;
import com.equation.cashierll.receipts.MyPrintable;

/**
 *
 * @author Wellington
 */

@SuppressWarnings("serial")
public class PrintPopup extends JPopupMenu {
	JTable table;
	Statement stm, stmt;
	ResultSet rs, rs1;
	Object[][] data;
	private JMenuItem viewreceipt, printmenu;
	private static final int MAX_CHAR = 18;
	JDialog dialog;
	JTextArea display;
	ViewReceipt viewReceipt;
	JPanel panel;

	public PrintPopup(JTable table, Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, JDialog dialog,
			JTextArea display, JPanel panel) {
		this.table = table;
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.dialog = dialog;
		this.display = display;
		this.panel = panel;
		printmenu = new JMenuItem("Print");
		viewreceipt = new JMenuItem("View Receipt");
		// viewreceipt.setEnabled(false);
		this.add(printmenu);
		this.add(viewreceipt);

		printmenu.addActionListener((e) -> {
			printReceipt();
		});
		viewreceipt.addActionListener((e) -> {
			viewReceipt();
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable source = (JTable) e.getSource();
				if (e.getClickCount() == 2) {
					int count = source.getRowCount();
					int row = source.getSelectedRow();
					if (row < count) {
						viewReceipt();
					}
				}
			}
		});
	}

	public void viewReceipt() {
		viewReceipt = new ViewReceipt(table, stm, stmt, rs, rs1);
		EventQueue.invokeLater(() -> {
			dialog.getContentPane().removeAll();
			dialog.setLayout(new BorderLayout());
			viewReceipt.viewIt(display);
			JPanel panel1 = new JPanel(new BorderLayout());
			panel1.setBackground(Color.WHITE);
			panel1.add(new JScrollPane(display), BorderLayout.CENTER);
			Box box = Box.createHorizontalBox();
			panel1.add(box, BorderLayout.SOUTH);
			JLabel label = new JLabel("Shopping Cart  ");
			label.setForeground(new Color(10, 70, 90));
			JLabel label1 = new BlinkingLabel(" \u2193");
			Box b = Box.createHorizontalBox();
			b.add(label);
			b.add(label1);
			label.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 50));
			label1.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 50));
			panel1.add(b, BorderLayout.NORTH);
			dialog.getContentPane().add(panel1, BorderLayout.CENTER, SwingConstants.CENTER);
			// JButton print = new BlinkingButton(Color.RED);
			JButton print = new JButton();
			print.setText("Print \u2191");
			print.setBackground(new Color(10, 70, 90));
			print.setForeground(Color.WHITE);
			print.setFont(new Font(AllFonts.getTimesNewRoman(), Font.PLAIN, 30));

			// JButton back = new JButton("|< Back");
			JButton back = new BlinkingButton(Color.RED);
			back.setText("\u2190 Back ");
			back.setFont(new Font(AllFonts.getTimesNewRoman(), Font.PLAIN, 30));
			box.add(print);
			box.add(Box.createHorizontalStrut(20));
			box.add(back);
			print.addActionListener((event) -> {
				Object[][] data = viewReceipt.data;
				double totalcost = viewReceipt.totalcost;
				double rendered = viewReceipt.rendered;
				double change = viewReceipt.change;
				int receiptno = viewReceipt.receiptno;

				DefaultTableModel model = new DefaultTableModel(data, Header.header);
				JTable jtable = new JTable(model);
				double rowcount = jtable.getRowCount();
				printingMethod(jtable, receiptno, rowcount, totalcost, rendered, change);
			});
			back.addActionListener((ev) -> {
				EventQueue.invokeLater(() -> {
					dialog.getContentPane().removeAll();
					dialog.setLayout(new BorderLayout());
					dialog.getContentPane().setBackground(Color.WHITE);
					dialog.getContentPane().add(panel, BorderLayout.CENTER);
					dialog.revalidate();
					dialog.repaint();
				});
			});
			dialog.revalidate();
			dialog.repaint();
		});
	}

	public void printReceipt() {
		String rcn = table.getValueAt(table.getSelectedRow(), 0).toString();
		int receiptno = Integer.parseInt(rcn);
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
			while (rs1.next()) {
				String item_type = rs1.getString(1);

				int maxLength = (item_type.length() < MAX_CHAR) ? item_type.length() : MAX_CHAR;
				item_type = item_type.substring(0, maxLength);
				data[i][0] = item_type;
				data[i][1] = rs1.getInt(2);
				data[i][2] = rs1.getDouble(3);
				i++;

			}
			String query2 = "SELECT amount,amount_given,amount_changed FROM receipt_amount WHERE receiptno = '"
					+ receiptno + "'";
			rs = stm.executeQuery(query2);
			rs.next();
			double totalcost = rs.getDouble(1);
			double rendered = rs.getDouble(2);
			double change = rs.getDouble(3);

			DefaultTableModel model = new DefaultTableModel(data, Header.header);
			JTable jtable = new JTable(model);
			double rowcount = jtable.getRowCount();
			printingMethod(jtable, receiptno, rowcount, totalcost, rendered, change);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}

	public void printingMethod(JTable table, int receiptno, double rowcount, double totalcost, double rendered,
			double change) {
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
	}
}