/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equation.cashierll.supervisor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import com.equation.cashierll.add.product.type.NewProductType;
import com.equation.cashierll.audio.Click;
import com.equation.cashierll.audio.HoverListener;
import com.equation.cashierll.bank.Chooser;
import com.equation.cashierll.billing.Authenticate;
import com.equation.cashierll.billing.CheckBill;
import com.equation.cashierll.billing.reports.ChooseMonth;
import com.equation.cashierll.browse.BrowseMenu;
import com.equation.cashierll.bulkstock.add.AddBulkProducts;
import com.equation.cashierll.cashiermodules.AddReceiptsToList;
import com.equation.cashierll.cashiermodules.NewPayment;
import com.equation.cashierll.change.search.Change;
import com.equation.cashierll.currentstock.InStock;
import com.equation.cashierll.dailysales.DailySales;
import com.equation.cashierll.deco.AnimateJFrame;
import com.equation.cashierll.deco.TranslucentJPanel;
import com.equation.cashierll.deco.TranslucentJPanel1;
import com.equation.cashierll.developer.Developer;
import com.equation.cashierll.expenses.post.ListExpenses;
import com.equation.cashierll.expenses.post.PostExpense;
import com.equation.cashierll.externals.apps.OpenPackages;
import com.equation.cashierll.fonts.AllFonts;
import com.equation.cashierll.helpers.AccessDbase;
import com.equation.cashierll.helpers.ComputerExplorer;
import com.equation.cashierll.helpers.CustomTabbedPaneUI;
import com.equation.cashierll.helpers.IconImage;
import com.equation.cashierll.helpers.ListCellRendering;
import com.equation.cashierll.helpers.LookAndFeelClass;
import com.equation.cashierll.helpers.RemoveTab;
import com.equation.cashierll.helpers.Ribbon1;
import com.equation.cashierll.helpers.SetDateCreated;
import com.equation.cashierll.helpers.TabMouseMotionListener;
import com.equation.cashierll.helpers.TextFinder;
import com.equation.cashierll.helpers.TextValidator;
import com.equation.cashierll.helpers.ToolbarPopup2;
import com.equation.cashierll.helpers.TreeMouseListener;
import com.equation.cashierll.ledger.SearchLedger;
import com.equation.cashierll.receipt.reprint.ReprintRe;
import com.equation.cashierll.reconciliation.Reconcile;
import com.equation.cashierll.salesreports.SalesReport;
import com.equation.cashierll.sign.out.SignOut;
import com.equation.cashierll.temporarydata.delete.DeleteTMPTableData;
import com.equation.cashierll.tooltips.ToolTips;
import com.equation.cashierll.updateprice.UpdatePrice;
import com.equation.cashierll.users.register.NewUser;
import com.equation.cashierll.validation.FormValidation;
import com.equation.cashierll.voiding.VoidReceipt;
import com.toedter.calendar.JDateChooser;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class MainUI extends JFrame implements ActionListener {

	public JToolBar toolbar;
	JPanel mainPanel;
	JPanel centerPanel;
	JPanel Panel;
	JPanel upperPanel;
	JLabel allLabels[], hintlabels[], error;
	JTextArea display;
	JScrollPane scroller;
	public JMenuBar mnb;
	JMenu fl, ed, wnd, hlp, con, sa;
	JMenuItem ns, of, ph, exi;
	JMenuItem und, red, cut, cop, past, del, repl, outstanding_change;
	JMenuItem nc, rec, tc, disc;
	JMenuItem hc, ks, search, report, about;
	JMenuItem ccl, aut, max, min, clw, rsw, reprint;
	public AccessDbase adbase;
	LookAndFeelClass looks;
	public JTextField cashierid, navi;
	JDialog dialog;
	JSplitPane split;
	public JTabbedPane tabs, lefttabs;
	JTree tree;
	public SetDateCreated setdate;

	JLabel tellername, numberoftrans, timelbl;
	// ToolTips tooltip;

	String labels[] = { "Transactions", "", "Debit", "Credit", "Balance" };
	String notes[] = { "Ecs:Exit, ", "PgUp:Scroll Up, ", "PgDn:Scroll Down, ", "End:Last Record, ", "F1:More Help",
			"[ENTER]:Next Page" };
	private JButton find_trans;
	private JButton re_print;
	private JButton outstand_channge;
	private JDateChooser datechooser, datechoosert;
	private String dateString, dateStringto;
	private JButton new_prod_categ;
	private JButton bulk_stock_entry;
	private JButton receipt_voiding;
	private JList<String> paymentslist;
	public DefaultListModel<String> paymentslistmodel;
	private JButton daily_sales_report;
	private JMenuItem new_user;
	private JMenuItem all_users;
	private JMenuItem ad_mins;
	private AbstractButton post_expense, expense_list;
	private JButton revenue_sus_trans, recon;
	public JLabel totallabel;
	private JMenuItem find;
	private JMenuItem daily_repor;
	private JMenuItem re_con;
	private JMenuItem exp_list;
	private JButton update_stock_price;
	private JButton cashieridbutton;
	private JButton sales_rep;
	private JButton in_stock;
	// DailySales dailysales;
	Reconcile reconcile;
	// ListExpenses listexpenses;
	// InStock instock;
	NewProductType newproducttype;
	Change change;
	AddBulkProducts addbulkproduct;
	UpdatePrice updateprice;
	NewPayment newpayment;
	SalesReport salesreport;
	PostExpense postexpense;
	ReprintRe reprintreceipt;
	public JCheckBoxMenuItem soundeffect;
	Chooser bankchooser;
	Click click;
	static String basicWindowTitle = "Cashier II -  AA: CMD";
	String changetitle = null;
	private JMenu billing;
	private JMenuItem b_settings;
	private JMenuItem b_report;

	// this is the constructor of the main class
	public MainUI() {
		// adding the basic window title
		super(basicWindowTitle);
		adbase = new AccessDbase();
		adbase.connectionDb();
		looks = new LookAndFeelClass();
		looks.setLookAndFeels();
		setdate = new SetDateCreated();
		begin();
		classInitialization();
		// adding a changelistener to the tabs
		tabs.addChangeListener((listen) -> {
			EventQueue.invokeLater(() -> {
				int count = tabs.getTabCount();
				for (int x = 0; x < count; x++) {
					changetitle = tabs.getTitleAt(x).trim();
					this.setTitle(basicWindowTitle + " - " + changetitle);
				}
			});
		});
		// adding a mouselistener to the tabs
		tabs.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent ev) {
				for (int i = 0; i < tabs.getTabCount(); i++) {
					changetitle = tabs.getTitleAt(i).trim();
					setTitle(basicWindowTitle + " - " + changetitle);
				}
			}
		});
	}

	// this is an attempt to initialize all classes and then put this method in
	// a constructor
	public void classInitialization() {
		reconcile = new Reconcile(tabs, adbase.stm, adbase.rs, adbase.rs1, adbase.stmt, this);
		newproducttype = new NewProductType(tabs, adbase.rs, adbase.stm, adbase.conn, adbase.pstmt, this);
		change = new Change(adbase.rs, adbase.rs1, adbase.stm, adbase.stmt, tabs);
		addbulkproduct = new AddBulkProducts(tabs, adbase.rs, adbase.rs1, adbase.stm, adbase.stmt, adbase.pstmt,
				adbase.conn, this);
		updateprice = new UpdatePrice(adbase.rs, adbase.rs1, adbase.stm, adbase.stmt, tabs);
		newpayment = new NewPayment(tabs, adbase.rs, adbase.rs1, adbase.stm, adbase.stmt, adbase.pstmt, adbase.conn,
				this, paymentslistmodel, totallabel, soundeffect);

		postexpense = new PostExpense(tabs, adbase.rs, adbase.stm, this);
		reprintreceipt = new ReprintRe(adbase.rs, adbase.rs1, adbase.stm, adbase.stmt, tabs, this);
		bankchooser = new Chooser(tabs, adbase.rs, adbase.stm, this);
		click = new Click();
	}

	// Creating objects of all widget set components.
	@SuppressWarnings({ "unchecked" })
	public final void begin() {
		toolbar = new JToolBar();
		toolbar.setFloatable(false);
		mainPanel = new JPanel();
		centerPanel = new JPanel();
		Panel = new JPanel();
		upperPanel = new JPanel();
		// an attempt to initialize a windows listener and control default
		// close, minimize and maximize buttons
		WindowListener listener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				deleteTemporaryData();
				click.closeappPlay();
				System.exit(0);
			}
		};
		addWindowListener(listener);
		mnb = new JMenuBar();
		/*
		 * mnb.setUI(new BasicMenuBarUI() { public void paint(Graphics g,
		 * JComponent comp) { g.setColor(Color.WHITE); g.fillRect(2, 2,
		 * comp.getWidth(), comp.getHeight()); } });
		 */
		// Setting the main window menu bar
		setJMenuBar(mnb);

		fl = new JMenu("File");
		mnb.add(fl);
		JMenu nnew = new JMenu("New");

		new_user = new JMenuItem("New User");
		new_user.addActionListener(this);
		nnew.add(new_user);
		nnew.addSeparator();
		fl.add(nnew);
		of = new JMenuItem("Open File");
		of.setEnabled(false);
		fl.add(of);
		fl.addSeparator();
		sa = new JMenu("Cashiers...");
		fl.add(sa);

		ph = new JMenuItem("Print to HTML...");
		ph.setEnabled(false);
		fl.add(ph);
		fl.addSeparator();
		exi = new JMenuItem("Exit");
		fl.add(exi);
		exi.addActionListener(this);

		ed = new JMenu("Edit");
		mnb.add(ed);
		und = new JMenuItem("Undo");
		und.setEnabled(false);
		ed.add(und);
		red = new JMenuItem("Redo");
		red.setEnabled(false);
		ed.add(red);
		ed.addSeparator();
		cut = new JMenuItem("Cut");
		ed.add(cut);
		cop = new JMenuItem("Copy");
		ed.add(cop);
		past = new JMenuItem("Paste");
		ed.add(past);
		ed.addSeparator();
		del = new JMenuItem("Delete");
		del.setEnabled(false);
		ed.add(del);
		find = new JMenuItem("Find");
		find.addActionListener(this);
		ed.add(find);
		repl = new JMenuItem("Replace...");
		repl.setEnabled(false);
		ns = new JMenuItem("Stock Pricing");
		ns.addActionListener(this);
		ed.add(repl);
		ed.addSeparator();
		ed.add(ns);

		JMenu repr = new JMenu("Search");

		JMenu users = new JMenu("Users");

		all_users = new JMenuItem("All Usres");
		all_users.addActionListener(this);
		users.add(all_users);
		JMenu groupby = new JMenu("Group By");
		users.add(groupby);
		ad_mins = new JMenuItem("Administrators");
		ad_mins.addActionListener(this);
		groupby.add(ad_mins);

		reprint = new JMenuItem("Reprint receipt");
		reprint.addActionListener(this);
		repr.add(users);
		repr.addSeparator();
		repr.add(reprint);
		repr.addSeparator();
		outstanding_change = new JMenuItem("Outstanding Change($)");
		outstanding_change.addActionListener(this);
		repr.add(outstanding_change);
		mnb.add(repr);

		JMenu pstngs = new JMenu("Generate");
		mnb.add(pstngs);
		daily_repor = new JMenuItem("Daily Sales Report");
		daily_repor.addActionListener(this);
		re_con = new JMenuItem("Reconciliation Statement");
		re_con.addActionListener(this);
		exp_list = new JMenuItem("Expenses List");
		exp_list.addActionListener(this);

		pstngs.add(daily_repor);
		pstngs.addSeparator();
		pstngs.add(re_con);

		con = new JMenu("Connections");
		mnb.add(con);
		nc = new JMenuItem("New Connection");
		nc.setEnabled(false);
		con.add(nc);
		rec = new JMenuItem("Reconnect");
		rec.setEnabled(false);
		con.add(rec);
		tc = new JMenuItem("Test Connection");
		tc.setEnabled(false);
		con.add(tc);
		disc = new JMenuItem("Disconnect");
		disc.addActionListener(this);
		con.add(disc);

		wnd = new JMenu("Window");
		mnb.add(wnd);
		ccl = new JMenuItem("Change Background");
		ccl.addActionListener(this);
		wnd.add(ccl);
		aut = new JMenuItem("Auto Scroll");
		wnd.add(aut);
		wnd.addSeparator();
		max = new JMenuItem("Maximise Window");
		max.setEnabled(false);
		wnd.add(max);
		min = new JMenuItem("Minimise Window");
		min.setEnabled(false);
		wnd.add(min);
		clw = new JMenuItem("Close Window");
		clw.setEnabled(false);
		wnd.add(clw);
		rsw = new JMenuItem("Reset Window");
		rsw.setEnabled(false);
		wnd.add(rsw);
		soundeffect = new JCheckBoxMenuItem("Turn On Sound Effects");
		soundeffect.setState(true);
		wnd.addSeparator();
		wnd.add(soundeffect);

		mnb.add(new BrowseMenu());

		hlp = new JMenu("Help");
		mnb.add(hlp);
		hc = new JMenuItem("Help Content");
		hlp.add(hc);
		search = new JMenuItem("Search...");
		hlp.add(search);
		hlp.addSeparator();
		ks = new JMenuItem("Keys Shortcuts");
		hlp.add(ks);
		report = new JMenuItem("Report An Issue");
		hlp.add(report);
		about = new JMenuItem("About...");
		hlp.add(about);
		billing = new JMenu("Billing");
		hlp.addSeparator();
		hlp.add(billing);
		b_settings = new JMenuItem("Settings");
		b_settings.addActionListener(this);
		b_report = new JMenuItem("Report");
		b_report.addActionListener(this);
		billing.add(b_settings);
		billing.add(b_report);

		JButton quitbutton = new JButton();
		quitbutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		quitbutton.setBackground(Color.WHITE);
		quitbutton.setBorder(BorderFactory.createEmptyBorder());
		quitbutton.setIcon(new ImageIcon(new IconImage().createCloseImage()));
		quitbutton.addActionListener((event) -> {
			closeAndQuit();
		});
		JButton minimise = new JButton();
		minimise.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		minimise.setBackground(Color.WHITE);
		minimise.setBorder(BorderFactory.createEmptyBorder());
		minimise.setIcon(new ImageIcon(new IconImage().createMinImage()));
		minimise.addActionListener((event) -> {
			if (this.getExtendedState() == JFrame.NORMAL)
				this.setExtendedState(JFrame.ICONIFIED);
		});

		mnb.add(Box.createHorizontalGlue());
		mnb.add(minimise);
		mnb.add(Box.createHorizontalStrut(20));
		mnb.add(quitbutton);

		allLabels = new JLabel[6];
		hintlabels = new JLabel[notes.length];
		display = new JTextArea("");
		display.setEditable(false);
		display.setLineWrap(true);
		display.setMargin(new Insets(30, 30, 30, 30));
		display.setBackground(Color.BLACK);
		display.setForeground(Color.white);
		display.setFont(new Font("", Font.ROMAN_BASELINE, 19));
		display.getCaret().setSelectionVisible(true);
		display.getCaret().setVisible(true);
		display.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				int key = arg0.getKeyCode();
				if (key == KeyEvent.VK_C || key == KeyEvent.VK_DELETE) {
					clearData();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});

		scroller = new JScrollPane(display);
		scroller.setBorder(null);
		mainPanel = new JPanel(new BorderLayout());
		Panel = new JPanel(new FlowLayout());
		upperPanel = new JPanel(new GridLayout(1, 6, 5, 5));
		// for loops for the painting of labels here
		for (int x = 0; x < labels.length; x++) {
			allLabels[x] = new JLabel(labels[x]);
			allLabels[x].setSize(3, 3);
			allLabels[x].setForeground(Color.RED);
			allLabels[x].setFont(new Font("", Font.BOLD, 20));
			upperPanel.add(allLabels[x]);
		}

		for (int y = 0; y < notes.length; y++) {
			hintlabels[y] = new JLabel(notes[y]);
			hintlabels[y].setSize(2, 3);
			hintlabels[y].setForeground(Color.BLUE);
			hintlabels[y].setFont(new Font("", Font.ITALIC + Font.BOLD, 13));
			Panel.add(hintlabels[y]);
		}
		navi = new JTextField(20);
		navi.addActionListener(this);
		Panel.add(new JLabel(" Search:"));
		Panel.add(navi);

		mainPanel.add(upperPanel, BorderLayout.NORTH);
		mainPanel.add(scroller, BorderLayout.CENTER);
		mainPanel.add(Panel, BorderLayout.SOUTH);

		tellername = new JLabel();
		tellername.setFont(new Font("Arial", Font.PLAIN, 20));
		tellername.setForeground(Color.WHITE);
		numberoftrans = new JLabel();
		numberoftrans.setFont(new Font("Arial", Font.PLAIN, 20));
		numberoftrans.setForeground(Color.WHITE);
		timelbl = new JLabel();
		timelbl.setFont(new Font("Arial", Font.PLAIN, 20));
		timelbl.setForeground(Color.WHITE);

		tree = ComputerExplorer.createExplorer();
		tree.addMouseListener(new TreeMouseListener(tree, this));

		JPanel mailpanel = new TranslucentJPanel1(Color.BLACK);
		mailpanel.setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(tree);
		mailpanel.add(scroll, BorderLayout.CENTER);

		lefttabs = new JTabbedPane();
		lefttabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		lefttabs.addMouseMotionListener(new TabMouseMotionListener());
		// lefttabs.addTab("My Computer", null, mailpanel, "My Computer");
		lefttabs.addTab("MS Office", null, new OpenPackages(), "Write Reports, Documents And other Files");

		paymentslist = new JList<>();
		paymentslist.setFont(new Font("Dialog", Font.PLAIN, 19));
		paymentslist.setModel(paymentslistmodel = new DefaultListModel<>());
		paymentslistmodel.addElement("Transactions List");
		paymentslist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		paymentslist.setCellRenderer(new ListCellRendering());

		JPanel paymentsPanel = new TranslucentJPanel(Color.BLUE);
		paymentsPanel.setLayout(new BorderLayout());
		paymentsPanel.add(new JScrollPane(paymentslist), BorderLayout.CENTER);
		totallabel = new JLabel("SUM $ 0.00");
		totallabel.setFont(new Font("", Font.BOLD + Font.ITALIC, 19));
		// totallabel.setForeground(Color.WHITE);

		paymentsPanel.add(totallabel, BorderLayout.SOUTH);

		// lefttabs.addTab("Payments".toUpperCase(), null, paymentsPanel,
		// "today's payments");
		lefttabs.setFont(new Font("Dialog", Font.PLAIN, 20));

		tabs = new JTabbedPane();

		tabs.setFont(new Font("Dialog", Font.PLAIN, 20));
		tabs.setForeground(Color.WHITE);
		tabs.setBackground(Color.BLUE);
		tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabs.setUI(new CustomTabbedPaneUI());
		JPanel deco = new TranslucentJPanel1(Color.BLACK);
		deco.setLayout(new GridLayout(3, 1));
		JLabel eq4 = new JLabel("<html><u>C A $ H I E R II</u></html>");
		eq4.setFont(new Font(AllFonts.getMagnetor(), Font.BOLD, 100));
		eq4.setForeground(Color.WHITE);
		JLabel lin = new JLabel(
				"_________________________________________________________________________________________________");
		lin.setForeground(Color.WHITE);
		JLabel md = new JLabel("Sales And Inventory Management Service");
		md.setFont(new Font(AllFonts.getTimesNewRoman(), Font.ITALIC, 30));
		md.setForeground(Color.WHITE);
		deco.add(eq4);
		deco.add(lin);
		deco.add(md);

		JPanel innert = new JPanel();
		innert.setOpaque(false);
		JPanel innerb = new JPanel();
		innerb.setOpaque(false);
		JPanel innerl = new JPanel();
		innerl.setOpaque(false);
		JPanel innerr = new JPanel(new BorderLayout());
		Box versionBox = Box.createHorizontalBox();
		JLabel versionLabel = new JLabel("Product version 1.0.3 - Product ID 017");
		versionLabel.setFont(new Font(AllFonts.getTimesNewRoman(), Font.ITALIC, 17));
		JLabel serviceproviderLabel = new JLabel("\u0020 \u0020 \u00A9 " + new SetDateCreated().getYear() + "\u002C "
				+ Developer.getCompanyName() + "\u2122" + "  Developer : " + Developer.getDeveloperName());
		serviceproviderLabel.setForeground(Color.WHITE);
		serviceproviderLabel.setFont(new Font(AllFonts.getTimesNewRoman(), Font.ITALIC, 18));
		versionBox.add(serviceproviderLabel);
		versionBox.add(Box.createHorizontalGlue());
		versionBox.add(versionLabel);
		innerr.add(versionBox);
		innerr.setOpaque(false);

		JPanel tabp = new JPanel(new BorderLayout(100, 100));
		tabp.setBackground(new Color(10, 75, 100));
		// tabp.setBackground(new Color(150, 75, 190));

		tabp.add(deco, BorderLayout.CENTER);
		tabp.add(innert, BorderLayout.WEST);
		tabp.add(innerb, BorderLayout.EAST);
		tabp.add(innerl, BorderLayout.NORTH);
		tabp.add(innerr, BorderLayout.SOUTH);

		// starting of the tab, configuhring how the tab is going to display the
		// first window.
		tabs.addTab("Start   ", null, tabp, "Start");
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		split.setDividerSize(10);
		split.setOneTouchExpandable(true);
		split.setDividerLocation(200);
		// split.add(lefttabs);
		// split.add(tabs);
		Color cv = new Color(0.5f, 0.3f, 1f);
		JPanel centerpanel = new JPanel(new BorderLayout());
		centerpanel.setBackground(cv);
		centerpanel.setBorder(null);
		centerpanel.add(tabs, BorderLayout.CENTER);
		split.add(centerpanel);

		JTabbedPane toolbarpane = createToolbar();

		toolbar.add(toolbarpane);
		toolbar.add(Box.createHorizontalGlue());

		this.setLayout(new BorderLayout());
		JPanel toppanell = new JPanel();
		toppanell.setLayout(new BorderLayout());
		Color c = new Color(0.5f, 0.5f, 1f);
		toppanell.setBackground(c);
		toppanell.add(toolbar, BorderLayout.CENTER);
		/*
		 * JLayeredPane layeredPane = new JLayeredPane();
		 * 
		 * FadePanel fadePanel = new FadePanel(this); fadePanel.setLocation(0,
		 * 0); fadePanel.setSize(fadePanel.getPreferredSize());
		 * 
		 * JPanel bluePanel = new JPanel(new BorderLayout());
		 * bluePanel.setBackground(Color.blue);
		 * bluePanel.setSize(fadePanel.getPreferredSize());
		 * bluePanel.add(toppanell, BorderLayout.NORTH); bluePanel.add(split,
		 * BorderLayout.CENTER);
		 * 
		 * layeredPane.setPreferredSize(fadePanel.getPreferredSize());
		 * layeredPane.add(bluePanel, JLayeredPane.DEFAULT_LAYER);
		 * layeredPane.add(fadePanel, JLayeredPane.PALETTE_LAYER);
		 */
		toolbar.setComponentPopupMenu(new ToolbarPopup2(this, toolbar, mnb, toolbarpane));
		this.getContentPane().add(toppanell, BorderLayout.NORTH);
		this.getContentPane().add(split, BorderLayout.CENTER);
		this.setIconImage(new IconImage().createIconImage());
		// this.getContentPane().add(layeredPane,BorderLayout.CENTER);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screen.width, screen.height);

		this.setUndecorated(true);
		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent evvt) {
				setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 5, 5));
			}
		});

		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"Cancel");
		getRootPane().getActionMap().put("Cancel", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				closeAndQuit();
			}
		});

		Dimension d = this.getSize();
		int x = (screen.width - d.width) / 2, y = (screen.height - d.height) / 2;
		this.setLocation(x, y);
	}

	// This is for inserting the tab for our session manager
	public void insertTab() {
		EventQueue.invokeLater(() -> {
			int numberoftabs = tabs.getTabCount();
			boolean exist = false;
			for (int a = 0; a < numberoftabs; a++) {
				if (tabs.getTitleAt(a).trim().equals("Session MGR AA_FND")) {
					exist = true;
					tabs.setSelectedIndex(a);
					break;
				}
			}
			if (!exist) {
				tabs.addTab("Session MGR AA_FND   ", null, detPanel(), "Run queries, find transactions,e.t.c");
				tabs.setSelectedIndex(numberoftabs);
			}
		});
	}

	// Overriding the actionlistener method for hadling clicks
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ccl) {
			EventQueue.invokeLater(() -> {
				Color cl = new Color(153, 153, 204);
				cl = JColorChooser.showDialog(this, "Choose Background Color", cl);
				display.setBackground(cl);
				display.repaint();
			});
		}
		if (e.getSource() == exi || e.getSource() == disc) {
			closeAndQuit();
		}
		if (e.getSource() == in_stock) {
			new RemoveTab(tabs).removeTab("In Stock");
			InStock instock = new InStock(adbase.rs, adbase.rs1, adbase.stm, adbase.stmt, tabs);
			instock.insertTab();
		}

		if (e.getSource() == ns || e.getSource() == new_prod_categ) {
			stockPricing();
		}
		if (e.getSource() == find || e.getSource() == find_trans) {
			EventQueue.invokeLater(() -> {
				clearData();
			});
		}
		if (e.getSource() == cashierid || e.getSource() == cashieridbutton) {
			EventQueue.invokeLater(() -> {
				display.setText("");
				tellername.setText("");
				numberoftrans.setText("");
				validateAccount();
				dialog.dispose();
			});
		}
		if (e.getSource() == reprint || e.getSource() == re_print) {
			reprintreceipt.insertTab();
		}
		if (e.getSource() == navi) {
			EventQueue.invokeLater(() -> {
				new TextFinder().find(navi.getText(), display);
			});
		}
		if (e.getSource() == outstanding_change || e.getSource() == outstand_channge) {
			new RemoveTab(tabs).removeTab("Change");
			change.showDialog();
		}
		if (e.getSource() == bulk_stock_entry) {
			new RemoveTab(tabs).removeTab("New Bulk Stock");
			addbulkproduct.insertTab();
		}
		if (e.getSource() == receipt_voiding) {
			EventQueue.invokeLater(() -> {
				new RemoveTab(tabs).removeTab("Today's Sales");
			});
			VoidReceipt voidReceipt = new VoidReceipt(adbase.rs, adbase.rs1, adbase.stm, adbase.stmt, tabs, this);
			voidReceipt.insertTab();
		}
		if (e.getSource() == daily_sales_report || e.getSource() == daily_repor) {
			DailySales dailysales = new DailySales(adbase.rs, adbase.rs1, adbase.stm, adbase.stmt, tabs);
			dailysales.insertTab();
		}
		if (e.getSource() == new_user) {
			NewUser newUser = new NewUser(tabs, adbase.rs, adbase.stm, adbase.stmt, this);
			newUser.insertTab();

		}
		if (e.getSource() == all_users) {

		}
		if (e.getSource() == ad_mins) {

		}
		if (e.getSource() == post_expense) {
			new RemoveTab(tabs).removeTab("Expenses");
			postexpense.insertTab();
		}
		if (e.getSource() == expense_list || e.getSource() == exp_list) {
			new RemoveTab(tabs).removeTab("Expenses List");
			ListExpenses listexpenses = new ListExpenses(tabs, adbase.stm, adbase.rs, adbase.rs1, adbase.stmt, this);
			EventQueue.invokeLater(() -> {
				listexpenses.showDialog();
			});
		}
		if (e.getSource() == revenue_sus_trans) {
			bankchooser.showDialog();
		}
		if (e.getSource() == recon || e.getSource() == re_con) {
			// reconcilliation statement right here
			new RemoveTab(tabs).removeTab("Reconciliation");
			EventQueue.invokeLater(() -> {
				reconcile.showDialog();
			});
		}
		if (e.getSource() == sales_rep) {
			new RemoveTab(tabs).removeTab("Sales Report");
			salesreport = new SalesReport(tabs, adbase.stm, adbase.rs, adbase.rs1, adbase.stmt, this);
			EventQueue.invokeLater(() -> {
				salesreport.showDialog();
			});
		}

		if (e.getSource() == update_stock_price) {
			new RemoveTab(tabs).removeTab("Update Prices");
			EventQueue.invokeLater(() -> {
				updateprice.showDialog();
			});
		}
		if (e.getSource() == b_settings) {
			authenticateBilling();
		}
		if (e.getSource() == b_report) {
			processBillingreport();
		}
	}

	// mnthly billing report for any given expiry date......licence purchasing
	// and service fee
	public void processBillingreport() {
		ChooseMonth chooseMonth = new ChooseMonth(adbase.rs, adbase.rs1, adbase.stm, adbase.stmt, tabs);
		EventQueue.invokeLater(() -> {
			chooseMonth.showDialog();
		});
	}

	// This is for alloing or locking the billing method
	public void authenticateBilling() {
		Authenticate a = new Authenticate(adbase.stm, adbase.rs);
		EventQueue.invokeLater(() -> {
			a.createDialog();
		});
	}

	// This is for sending the billing data
	public void sendBillindData() {
		CheckBill c = new CheckBill(adbase.rs, adbase.stm);
		c.check();
	}

	// searching for transaction history
	public void showDialog() {
		dialog = new JDialog((JFrame) null, "Search", true);
		dialog.setLayout(new BorderLayout());
		dialog.setIconImage(new IconImage().createIconImage());
		JLabel datelbl = new JLabel("From (Date):");
		JLabel datelblto = new JLabel("To (Date):");
		datechooser = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
		datechooser.setDate(new Date());
		datechoosert = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
		datechoosert.setDate(new Date());
		JPanel datepanel = new JPanel(new GridLayout(2, 2));
		datepanel.setOpaque(false);
		datepanel.add(datelbl);
		datepanel.add(datechooser);
		datepanel.add(datelblto);
		datepanel.add(datechoosert);
		dialog.getContentPane().add(datepanel, BorderLayout.NORTH);
		JLabel top = new JLabel("<html><h3>Type Account Number, <i>(eg 4100)</i><h3>");
		// top.setForeground(Color.WHITE);

		cashierid = new JTextField();
		cashierid.setFont(new Font("", Font.ROMAN_BASELINE, 35));
		cashierid.addActionListener(this);
		cashierid.addKeyListener(new TextValidator());
		JPanel midpanel = new JPanel(new GridLayout(2, 1));
		midpanel.setOpaque(false);
		midpanel.add(top);
		midpanel.add(cashierid);

		error = new JLabel();
		error.setForeground(Color.red);
		// midpanel.add(error);
		cashieridbutton = new JButton("OK");
		cashieridbutton.addActionListener(this);
		// midpanel.add(cashieridbutton);
		// dialog.getContentPane().setBackground(new Color(0.5f, 0.5f, 1f));
		dialog.getContentPane().setBackground(Color.WHITE);
		dialog.getContentPane().add(midpanel, BorderLayout.CENTER);
		Box defaultBox = Box.createHorizontalBox();
		defaultBox.add(error);
		defaultBox.add(Box.createHorizontalGlue());
		defaultBox.add(cashieridbutton);
		dialog.getRootPane().setDefaultButton(cashieridbutton);
		dialog.getContentPane().add(defaultBox, BorderLayout.SOUTH);

		dialog.setSize(300, 200);
		Dimension d = dialog.getSize(), screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - d.width) / 2, y = (screen.height - d.height) / 2;
		dialog.setLocation(x, y);
		dialog.setVisible(true);
		dialog.setAlwaysOnTop(true);
	}

	// creating a panel for htransactions
	private JPanel detPanel() {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(null);
		p.setBackground(Color.BLACK);
		// start the timer clock
		javax.swing.Timer timer = new javax.swing.Timer(1000, new Listener());
		timer.start();

		JLabel lbl = new JLabel("*** Transaction History Enquiry ***");
		lbl.setFont(new Font("Arial", Font.PLAIN, 20));
		lbl.setForeground(Color.WHITE);

		Box bc = Box.createVerticalBox();
		bc.add(lbl);
		bc.add(tellername);

		p.add(bc, BorderLayout.WEST);
		p.add(timelbl, BorderLayout.CENTER);
		p.add(numberoftrans, BorderLayout.EAST);

		JPanel mp = new TranslucentJPanel(Color.BLUE);
		mp.setLayout(new BorderLayout());
		mp.add(p, BorderLayout.NORTH);
		mainPanel.setOpaque(false);
		mp.add(mainPanel, BorderLayout.CENTER);

		return mp;
	}

	public void validateAccount() {
		if (!cashierid.getText().trim().equals("")) {
			String id = cashierid.getText();
			Date fdate = datechooser.getDate();
			Date todate = datechoosert.getDate();
			dateString = String.format("%1$tY-%1$tm-%1$td", fdate);
			dateStringto = String.format("%1$tY-%1$tm-%1$td", todate);
			if (id.equals("4131")) {
				tellername.setText("General Ledger 4131");
				SearchLedger searchledger = new SearchLedger(adbase.rs, adbase.stm, this);
				searchledger.search(dateString, dateStringto, display);
			} else {
				searchData(id);
				dialog.dispose();
			}
		} else {
			error.setText("Account DO NOT HONOR?");
		}
	}

	// searching the revenue suspense account data
	public void searchData(String id) {
		Date fdate = datechooser.getDate();
		Date todate = datechoosert.getDate();
		dateString = String.format("%1$tY-%1$tm-%1$td", fdate);
		dateStringto = String.format("%1$tY-%1$tm-%1$td", todate);
		try {
			int cid = Integer.parseInt(id);
			String query11 = "SELECT * FROM cashpay WHERE cashierid = '" + cid + "'AND date BETWEEN '" + dateString
					+ "' AND '" + dateStringto + "'";
			adbase.rs1 = adbase.stmt.executeQuery(query11);
			adbase.rs1.last();
			numberoftrans.setText("" + adbase.rs1.getRow() + " Transactions found");

			String query111 = "SELECT * FROM cashiers WHERE cashierid = '" + cid + "'";
			adbase.rs1 = adbase.stmt.executeQuery(query111);
			adbase.rs1.next();
			tellername.setText("Name :" + adbase.rs1.getString(3) + " " + adbase.rs1.getString(5));

			String query = "SELECT * FROM cashpay WHERE cashierid = '" + cid + "'AND date BETWEEN '" + dateString
					+ "' AND '" + dateStringto + "'";
			adbase.rs = adbase.stm.executeQuery(query);
			while (adbase.rs.next()) {
				int rid = adbase.rs.getInt(1);
				int sid = adbase.rs.getInt(2);
				String date = adbase.rs.getString(5) + " " + adbase.rs.getString(6);
				long amount = adbase.rs.getLong(4);

				String am = "" + amount;
				String trans = "ReceiptNo " + rid + "\nStudentNo " + sid + "\nDate " + date + "\t\t\t\t$" + am + "\n\n";
				display.append(trans);
			}
		} catch (SQLException ee) {
			dialog.setVisible(true);
			error.setText("Cashier ID " + cashierid.getText() + " DO NOT HONOUR!");
		}
	}

	// timer class
	class Listener implements ActionListener {
		// Overriding the ActionListener method for handling actions and changes
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
			timelbl.setText("TIME " + hr + ":" + min + ":" + sec + " " + day_night);
		}
	}

	// Creating the toolbar for the main window here
	public JTabbedPane createToolbar() {
		JTabbedPane pane = new JTabbedPane();
		// Overriding the default UI and look and feel of the JTabbedPane
		pane.setUI(new Ribbon1());
		// defining all toolbar buttons instances
		JToolBar inventory = new JToolBar();
		inventory.setFloatable(false);
		inventory.setRollover(true);
		new_prod_categ = new JButton("<html><p>Stock<br>Pricing</p></html>");
		new_prod_categ.setToolTipText(new ToolTips().stock_pr);
		new_prod_categ.addActionListener(this);
		new_prod_categ.addMouseListener(new HoverListener(soundeffect));
		bulk_stock_entry = new JButton("<html><p>Bulk Stock<br>Entry</p></html>");
		bulk_stock_entry.setToolTipText(new ToolTips().bulk_stock_ent);
		bulk_stock_entry.addActionListener(this);
		bulk_stock_entry.addMouseListener(new HoverListener(soundeffect));
		post_expense = new JButton("<html><p>Post An<br>Expense</p></html>");
		post_expense.setToolTipText(new ToolTips().post_ex);
		post_expense.addActionListener(this);
		post_expense.addMouseListener(new HoverListener(soundeffect));
		revenue_sus_trans = new JButton("<html><p>General Revenue<br>Suspense Transaction</p></html>");
		revenue_sus_trans.setToolTipText(new ToolTips().banking);
		revenue_sus_trans.addActionListener(this);
		revenue_sus_trans.addMouseListener(new HoverListener(soundeffect));
		update_stock_price = new JButton("<html><p>Update Stock<br>Price</p></html>");
		update_stock_price.setToolTipText(new ToolTips().update_price);
		update_stock_price.addActionListener(this);
		update_stock_price.addMouseListener(new HoverListener(soundeffect));

		inventory.add(new_prod_categ);
		inventory.addSeparator();
		inventory.add(bulk_stock_entry);
		inventory.addSeparator();
		inventory.addSeparator();
		inventory.add(post_expense);
		inventory.addSeparator();
		inventory.addSeparator();
		inventory.add(revenue_sus_trans);
		inventory.addSeparator();
		inventory.add(update_stock_price);
		inventory.addSeparator();

		JToolBar seach = new JToolBar();
		seach.setFloatable(false);
		seach.setRollover(true);
		receipt_voiding = new JButton("<html><p>Receipt<br>Voiding</p></html> ");
		receipt_voiding.setToolTipText(new ToolTips().pro_trans);
		receipt_voiding.addActionListener(this);
		receipt_voiding.addMouseListener(new HoverListener(soundeffect));
		re_print = new JButton("<html><p>Reprint<br>Receipt</p></html>");
		re_print.setToolTipText(new ToolTips().reprint);
		re_print.addActionListener(this);
		re_print.addMouseListener(new HoverListener(soundeffect));
		outstand_channge = new JButton("<html><p>Outstanding<br>Change($)</p></html>");
		outstand_channge.setToolTipText(new ToolTips().changes);
		outstand_channge.addActionListener(this);
		outstand_channge.addMouseListener(new HoverListener(soundeffect));

		find_trans = new JButton("<html><p>Session<br>Manager</p></html>");
		find_trans.setToolTipText(new ToolTips().sessions);
		find_trans.addActionListener(this);
		find_trans.addMouseListener(new HoverListener(soundeffect));

		seach.add(receipt_voiding);
		seach.addSeparator();
		seach.add(outstand_channge);
		seach.addSeparator();
		seach.add(re_print);
		seach.addSeparator();
		seach.addSeparator();
		seach.addSeparator();
		seach.addSeparator();
		seach.add(find_trans);
		seach.addSeparator();

		JToolBar run = new JToolBar();
		run.setFloatable(false);
		run.setRollover(true);
		recon = new JButton("<html><p>Reconciliation<br>Statement</p></html>");
		recon.setToolTipText(new ToolTips().recon);
		recon.addActionListener(this);
		recon.addMouseListener(new HoverListener(soundeffect));
		daily_sales_report = new JButton("<html><p>Daily Sales<br>Report</p></html>");
		daily_sales_report.setToolTipText(new ToolTips().daily_sales_report);
		daily_sales_report.addActionListener(this);
		daily_sales_report.addMouseListener(new HoverListener(soundeffect));
		expense_list = new JButton("<html><p>Generate<br>Expenses List</p></html>");
		expense_list.setToolTipText(new ToolTips().expenses_report);
		expense_list.addActionListener(this);
		expense_list.addMouseListener(new HoverListener(soundeffect));
		sales_rep = new JButton("<html><p>Previous<br>Sales Report</p></html>");
		sales_rep.setToolTipText(new ToolTips().pre_sales);
		sales_rep.addActionListener(this);
		sales_rep.addMouseListener(new HoverListener(soundeffect));
		in_stock = new JButton("<html><p>In Stock<br> Report</p></html>");
		in_stock.setToolTipText(new ToolTips().in_stock);
		in_stock.addActionListener(this);
		in_stock.addMouseListener(new HoverListener(soundeffect));
		run.add(daily_sales_report);
		run.addSeparator();
		run.add(recon);
		run.addSeparator();
		run.add(expense_list);
		run.addSeparator();
		run.add(sales_rep);
		run.addSeparator();
		run.add(in_stock);

		pane.addTab("<html><h4 style='padding:5px;background-color:#59599C;color:#FFFFFF;'>  Inventory  </h4></html>",
				null, inventory, "Admin work");
		pane.addTab(
				"<html><h4 style='padding:5px;background-color:#628275;color:#FFFFFF;'>  Transactions  </h4></html>",
				null, seach, "Payments processing");
		pane.addTab("<html><h4 style='padding:5px;background-color:#243806;color:#FFFFFF;'>  Generate  </h4></html>",
				null, run, "Run");

		pane.addMouseMotionListener(new TabMouseMotionListener());

		return pane;
	}

	public void deleteTemporaryData() {
		DeleteTMPTableData delete = new DeleteTMPTableData(adbase.rs, adbase.stm);
		delete.deleteTemporaryData();
	}

	public void addReceipts() {
		AddReceiptsToList addreceipts = new AddReceiptsToList(adbase.rs, adbase.stm, paymentslistmodel, totallabel);
		addreceipts.returnData();
	}

	public void stockPricing() {
		new RemoveTab(tabs).removeTab("Stock Pricing");
		newproducttype.insertTab();
	}

	// clearing the display
	public void clearData() {
		display.setText("");
		insertTab();
		tellername.setText("");
		numberoftrans.setText("");
		showDialog();
	}

	/*
	 * public static void main(String[] args) { MainUI ui = new MainUI();
	 * EventQueue.invokeLater(() -> { new AnimateJFrame().fadeIn(ui, 100); }); }
	 */
	// this method is for closing the application. It asks the use to confirm
	// their action
	public void closeAndQuit() {
		int x = JOptionPane.showConfirmDialog(this,
				"You are force quitting this session!\nYou will need to disconnect.\nAre you sure to proceed?",
				"Force Disconnect", JOptionPane.YES_NO_OPTION);
		if (x == JOptionPane.YES_OPTION) {
			new AnimateJFrame().fadeOut(this, 100);
			SignOut out = new SignOut(adbase.stm, adbase.rs);
			out.signOff();
			FormValidation fm = new FormValidation();
			fm.launcher();
		} else {
			return;
		}
	}
}