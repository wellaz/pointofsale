package com.equation.cashierll.voiding;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.equation.cashierll.dailysales.DeleteActualReceipt;
import com.equation.cashierll.deco.BlinkingButton;
import com.equation.cashierll.deco.TranslucentJPanel1;
import com.equation.cashierll.helpers.RemoveTab;
import com.equation.cashierll.helpers.SetDateCreated;
import com.equation.cashierll.helpers.TextValidator;

/**
 *
 * @author Wellington
 */

@SuppressWarnings("serial")
public class VoidReceipt extends JPanel implements ActionListener {

	JTextField sid;
	String idstring;
	JButton search;
	ResultSet rs, rs1;
	Statement stm, stmt;
	JTabbedPane tabs;
	JFrame frame;
	JTextArea area;
	private JButton void_receipt;
	int receiptno, cashierid, quantity;
	double amount;
	String date, time, dated, product_name;
	ArrayList<String> productnamearr = new ArrayList<>();
	ArrayList<Integer> quantityarr = new ArrayList<>();
	ArrayList<Double> amountarr = new ArrayList<>();
	ArrayList<String> datesarr = new ArrayList<>();
	ArrayList<String> timearr = new ArrayList<>();
	private double sum = 0;
	private double amount_given = 0;
	private double amount_changed = 0;
	private String ddate = null;
	private String ttime = null;
	private double change_collectedd = 0;

	public VoidReceipt(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt, JTabbedPane tabs, JFrame frame) {
		this.rs = rs;
		this.rs1 = rs1;
		this.stm = stm;
		this.stmt = stmt;
		this.tabs = tabs;
		this.frame = frame;
		this.setLayout(new BorderLayout());
		init();
	}

	// the init method
	public final void init() {
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setOpaque(false);
		JLabel idlbl = new JLabel("Receipt Number :");
		idlbl.setFont(new Font("", Font.BOLD, 30));
		sid = new JTextField();

		sid.addKeyListener(new TextValidator());
		sid.setBackground(Color.BLACK);
		sid.setForeground(Color.WHITE);
		sid.setFont(new Font("", Font.ROMAN_BASELINE, 30));
		sid.addActionListener(this);

		p1.add(idlbl, BorderLayout.WEST);
		p1.add(sid, BorderLayout.CENTER);

		search = new JButton("Search");
		search.addActionListener(this);
		search.setForeground(Color.WHITE);
		search.setBackground(Color.BLUE);
		p1.add(search, BorderLayout.EAST);
		// JPanel p3 = new TranslucentJPanel(Color.BLUE);
		JPanel p3 = new JPanel();
		p3.setBackground(Color.WHITE);
		p3.setLayout(new BorderLayout());
		p3.add(p1, BorderLayout.NORTH);

		JPanel topp = new TranslucentJPanel1(Color.BLUE);
		topp.setLayout(new FlowLayout());
		JLabel toplbl = new JLabel("Find Any Transaction Details For Receipt Reprinting".toUpperCase());
		toplbl.setFont(new java.awt.Font("", Font.BOLD, 20));
		toplbl.setForeground(Color.WHITE);
		topp.add(toplbl, SwingConstants.CENTER);

		this.add(topp);
		this.add(p3);

		area = new JTextArea();
		area.setEditable(false);
		area.setLineWrap(true);
		area.setMargin(new Insets(30, 30, 30, 30));
		area.setBackground(Color.BLACK);
		area.setForeground(Color.white);
		area.setFont(new java.awt.Font("", Font.ROMAN_BASELINE, 19));
		area.getCaret().setSelectionVisible(true);
		area.getCaret().setVisible(true);

		JScrollPane scroller = new JScrollPane(area);
		scroller.setBorder(null);

		JPanel mainpanel = new JPanel(new GridLayout(1, 2));
		mainpanel.add(p3);
		mainpanel.add(scroller);
		void_receipt = new BlinkingButton(Color.RED);
		void_receipt.setText("<html><h1>Void Receipt</h1></html>");
		// void_receipt = new JButton("<html><h1>Void Receipt</h1></html>");
		// void_receipt.setBackground(new Color(10, 70, 90));
		// void_receipt.setForeground(Color.WHITE);
		void_receipt.addActionListener(this);
		this.add(topp, BorderLayout.NORTH);
		this.add(mainpanel, BorderLayout.CENTER);
		this.add(void_receipt, BorderLayout.SOUTH);
	}

	public void insertTab() {
		EventQueue.invokeLater(() -> {
			int numberoftabs = tabs.getTabCount();
			boolean exist = false;
			for (int a = 0; a < numberoftabs; a++) {
				if (tabs.getTitleAt(a).trim().equals("Voiding")) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				tabs.addTab("Voiding   ", null, this, "Run queries, find transactions and Voiding,e.t.c");
				tabs.setSelectedIndex(numberoftabs);
				sid.requestFocusInWindow();
			}
		});
	}

	// the actual receipt
	public void actualReceipt() {
		String query = "SELECT * FROM cashpay WHERE receiptno = '" + idstring + "'";
		area.append("Items\t\tQ\t$");
		area.append("\n-----------------------------------------------------------------------\n");
		try {

			rs1 = stmt.executeQuery(query);
			while (rs1.next()) {
				receiptno = rs1.getInt(1);
				cashierid = rs1.getInt(2);
				product_name = rs1.getString(3);
				quantity = rs1.getInt(4);
				amount = rs1.getDouble(5);
				date = rs1.getString(6);
				time = rs1.getString(7);

				productnamearr.add(product_name);
				quantityarr.add(quantity);
				amountarr.add(amount);
				datesarr.add(date);
				timearr.add(time);

				String text = product_name + "\t\t" + quantity + "\t" + amount + "\n";
				area.append(text);
			}
			area.append("\n-----------------------------------------------------------------------\n");
			String query1 = "SELECT * FROM receipt_amount WHERE receiptno = '" + idstring + "'";
			rs = stm.executeQuery(query1);
			rs.next();
			String rc = "ReceiptNo :RC" + rs.getInt(1) + "\n";
			sum = rs.getDouble(2);
			String tot = "Total Cost \t\t\t$" + sum + "\n";
			amount_given = rs.getDouble(3);
			String ren = "Cash Tendered \t\t$" + amount_given + "\n";
			amount_changed = rs.getDouble(4);
			String ch = "Change \t\t\t$" + amount_changed + "\n";
			change_collectedd = rs.getDouble(5);
			String change_collected = "Once Off Change Collected \t\t$" + change_collectedd;
			ddate = rs.getString(6);
			ttime = rs.getString(7);
			String timestamp = "Dated " + ddate + " " + ttime + "\n";
			area.append(timestamp);
			area.append(rc);
			area.append(tot);
			area.append(ren);
			area.append(ch);
			area.append(change_collected);
			area.append("\n\n\t------WITH THANKS------\n");
			area.append("\t----Wellington------");

		} catch (Exception e) {
			// e.printStackTrace(System.err);
			JOptionPane.showMessageDialog(frame, "No Data for Receipt " + idstring, "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == search || e.getSource() == sid) {
			EventQueue.invokeLater(() -> {
				new RemoveTab(tabs).removeTab("Today's Sales");
			});
			idstring = sid.getText();
			if (!idstring.equals("")) {
				area.setText("");
				actualReceipt();
			} else {
				JOptionPane.showMessageDialog(frame, "Receipt Number is empty", "Empty Field",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		if (e.getSource() == void_receipt) {
			int x = JOptionPane.showConfirmDialog(frame, "Receipt number " + receiptno + " will be voided?\nContinue?",
					"Confirm Voiding", JOptionPane.YES_NO_OPTION);
			if (x == JOptionPane.YES_OPTION) {
				dated = new SetDateCreated().timeStamp();
				InsertIntoVoidTable insertIntoVoidTable = new InsertIntoVoidTable(stm, stmt, rs);
				int size = productnamearr.size();
				for (int i = 0; i < size; i++) {
					int receiptnoo = receiptno;
					int cashieridd = cashierid;
					String product_name = productnamearr.get(i);
					int quantity = quantityarr.get(i);
					double amount = amountarr.get(i);
					String date = datesarr.get(i);
					String time = timearr.get(i);
					insertIntoVoidTable.insertData(receiptnoo, cashieridd, product_name, quantity, amount, date, time,
							dated);
				}
				InsertVoidedReceipt insertVoidedReceipt = new InsertVoidedReceipt(stm);
				insertVoidedReceipt.insertVoidedReceipt(receiptno, sum, amount_given, amount_changed, change_collectedd,
						ddate, ttime);
				// this class is for deleting the items
				DeleteVoidedReceipt deleteVoidedReceipt = new DeleteVoidedReceipt(stm);
				deleteVoidedReceipt.deleteVoided(receiptno);
				// this class deletes receipts and their amounts
				DeleteActualReceipt actualReceipt = new DeleteActualReceipt(stm);
				actualReceipt.deleteActualReceipt(receiptno);
				// clearing the input box
				sid.setText("");
				sid.requestFocusInWindow();
				// clearing the console window
				area.setText("");
			} else {
				// do nothing
				return;
			}
		}
	}
}