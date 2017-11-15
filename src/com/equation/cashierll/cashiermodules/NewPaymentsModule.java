package com.equation.cashierll.cashiermodules;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import com.equation.cashierll.deco.AnimateDialog;
import com.equation.cashierll.fonts.AllFonts;
import com.equation.cashierll.helpers.IconImage;
import com.equation.cashierll.receipts.collection.ReceiptListDialog;
import com.equation.cashierll.sign.in.Monitor;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class NewPaymentsModule extends JFrame {

	// instance variables
	JTabbedPane tabs;
	ResultSet rs;
	ResultSet rs1;
	Statement stm;
	Statement stmt;
	PreparedStatement pstmt;
	Connection conn;
	JFrame frame;
	DefaultListModel<String> listmodel;
	JLabel totallabel;
	JCheckBoxMenuItem tuner;
	NewPayment newpayment;
	public JButton close;
	private JMenu changemenu;
	public JComboBox<Object> combo;
	private JMenu timemenu;

	// constructor declaration here.
	public NewPaymentsModule(JTabbedPane tabs, ResultSet rs, ResultSet rs1, Statement stm, Statement stmt,
			PreparedStatement pstmt, Connection conn, JFrame frame, DefaultListModel<String> listmodel,
			JLabel totallabel, JCheckBoxMenuItem tuner) {
		this.tabs = tabs;
		this.stm = stm;
		this.stmt = stmt;
		this.rs = rs;
		this.rs1 = rs1;
		this.frame = frame;
		this.pstmt = pstmt;
		this.conn = conn;
		this.listmodel = listmodel;
		this.totallabel = totallabel;
		this.tuner = tuner;

		newpayment = new NewPayment(tabs, rs, rs1, stm, stmt, pstmt, conn, frame, listmodel, totallabel, tuner);
		begin();
		combo = newpayment.itemscombo;
	}

	// the final method for initializing and constructing objects of ui widgets
	public final void begin() {
		this.setUndecorated(true);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent evvt) {
				setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 5, 5));
			}
		});

		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);
		JMenu menu = new JMenu("CA$HIER - " + Monitor.fullname.get(Monitor.fullname.size() - 1).toUpperCase());
		menu.setFont(new Font(AllFonts.getTimesNewRoman(), Font.PLAIN, 30));

		bar.add(menu);
		timemenu = new JMenu();
		timemenu.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 25));

		// start the timer clock
		javax.swing.Timer timer = new javax.swing.Timer(1000, new Listener());
		timer.start();

		bar.add(timemenu);
		// bar.add(Box.createHorizontalStrut(80));
		changemenu = new JMenu();
		setChangeMenu(changemenu);
		bar.add(getChangeMenu());

		bar.add(Box.createHorizontalGlue());
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		close = new JButton(new ImageIcon(new IconImage().createCloseImage()));
		/*
		 * close.addActionListener((event) -> { new
		 * AnimateDialog().fadeOut(this, 100); });
		 */
		// changemenu = new JMenu();
		JButton receipts = new JButton("Sales");
		receipts.setToolTipText("Retrieve today's sales list");
		receipts.setFont(new Font(AllFonts.getTimesNewRoman(), Font.PLAIN, 20));
		receipts.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		receipts.addActionListener((e) -> {
			ReceiptListDialog listDialog = new ReceiptListDialog(stm, stmt, rs, rs1, combo);
			EventQueue.invokeLater(() -> {
				new AnimateDialog().fadeIn(listDialog, 50);
			});
			// listDialog.setVisible(true);
		});
		toolbar.add(receipts);
		toolbar.add(Box.createHorizontalGlue());
		toolbar.add(close);
		bar.add(toolbar);

		setContentPane(newpayment);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screen.width, screen.height);
		Dimension d = this.getSize();
		int x = (screen.width - d.width) / 2, y = (screen.height - d.height) / 2;
		this.setLocation(x, y);
	}

	// setting the change menu here
	public void setChangeMenu(JMenu changemenu) {
		this.changemenu = changemenu;
	}

	// getting the change menu
	public JMenu getChangeMenu() {
		return changemenu;
	}

	// timer class that keeps track of the system time and attempts to update
	// the label that displays the time string
	class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Calendar now = Calendar.getInstance();
			int hr = now.get(Calendar.HOUR_OF_DAY);
			int min = now.get(Calendar.MINUTE);
			int sec = now.get(Calendar.SECOND);
			int AM_PM = now.get(Calendar.AM_PM);

			String day_night;
			if (AM_PM == 1) {
				day_night = "PM";
			} else {
				day_night = "AM";
			}
			timemenu.setText(hr + ":" + min + ":" + sec + " " + day_night);
		}
	}
}