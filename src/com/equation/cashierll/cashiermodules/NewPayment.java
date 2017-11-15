/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equation.cashierll.cashiermodules;

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
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.equation.cashierll.audio.ButtonListener;
import com.equation.cashierll.audio.CharButtonListener;
import com.equation.cashierll.audio.ClearCartListener;
import com.equation.cashierll.audio.Click;
import com.equation.cashierll.audio.MathOppListener;
import com.equation.cashierll.audio.OkMouseListener;
import com.equation.cashierll.buttons.RoundButton;
import com.equation.cashierll.deco.BlinkingLabel;
import com.equation.cashierll.deco.TranslucentJPanel;
import com.equation.cashierll.fonts.AllFonts;
import com.equation.cashierll.helpers.DoubleForm;
import com.equation.cashierll.helpers.GetProductNames;
import com.equation.cashierll.helpers.IconImage;
import com.equation.cashierll.helpers.Increment;
import com.equation.cashierll.helpers.MathKeysValidator;
import com.equation.cashierll.insertintocashpay.InsertTransaction;
import com.equation.cashierll.instockproducts.LoadInStockProducts;
import com.equation.cashierll.receipts.MakeReceipt;
import com.equation.cashierll.script.Script;
import com.equation.cashierll.temporarydata.delete.DeleteTMPTableData;
import com.equation.cashierll.temporarydata.getdata.GetTemporaryData;
import com.equation.cashierll.temporarydata.manage.ManageTemporaryData;
import com.equation.cashierll.temporarydata.unitprice.GetUnitPrice;
import com.toedter.calendar.JDateChooser;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class NewPayment extends JPanel implements ActionListener {
	// instance varibles
	JTextField amounttxt;
	JComboBox<Object> memberids;
	JComboBox<Object> months;
	JDateChooser birth;
	JButton submit, cancel, uploadimage;
	JLabel imagelbl;
	JTabbedPane tabs;
	ResultSet rs, rs1;
	Statement stm, stmt;
	PreparedStatement pstmt;
	Connection conn;
	JFrame frame;
	public JComboBox<Object> itemscombo;
	DoubleForm df;
	String pay;
	DefaultListModel<String> listmodel;
	PaymentsList list = new PaymentsList();
	JLabel totallabel;
	String cashierid;
	String[] buttonText = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "\u00D7", "+", ".", "-", "\u00F7", "(",
			")" };
	JButton[] calculatorButtons;
	JButton plus, minus, receipt;
	JTextArea display;
	private JButton enterbutton;
	JLabel[] allLabels;
	private JLabel totallbl, changelbl;
	private JLabel topmostlbl;
	private JTextField rendered;
	private JDialog dialog;
	private JRadioButton yes;
	private JRadioButton no;
	private JButton okButton;
	private JButton ocancelButton;
	private double changefee;
	private JRadioButton usedialpad;
	private JRadioButton normalmode;
	Click soundclick;
	JCheckBoxMenuItem tuner;
	private JDialog dialog1;
	private JLabel changelbl1;
	private JRadioButton yes1;
	private JRadioButton no1;
	private JButton okButton1;
	private JButton ocancelButton1;
	private JButton reset;
	private JTextField spinner;
	LoadInStockProducts loadInStockProducts;
	ArrayList<String> instockitems;
	private JPanel calculatorpanel;
	private JRadioButton automode;
	private JPanel centerleftPanel;
	private JTextField barcodeField;

	// the constructor of the class
	public NewPayment(JTabbedPane tabs, ResultSet rs, ResultSet rs1, Statement stm, Statement stmt,
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
		df = new DoubleForm();
		cashierid = "1";// LogData.details.get(LogData.details.size() - 1);
		soundclick = new Click();
		init();
	}

	// this method is for initializing all components that make up the parent
	// container
	public void init() {
		JPanel topp = new TranslucentJPanel(Color.BLUE);
		topp.setLayout(new FlowLayout());
		topmostlbl = new JLabel("", SwingConstants.CENTER);
		topmostlbl.setForeground(Color.WHITE);
		topmostlbl.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 25));
		topp.add(topmostlbl, SwingConstants.CENTER);

		JPanel leftpanel = new JPanel(new BorderLayout());
		JPanel topleft = new JPanel(new FlowLayout());

		int itemsarraysize = new GetProductNames(rs, stm).getProductName().size();
		Object[] seme = new String[itemsarraysize];
		for (int i = 0; i < itemsarraysize; i++) {
			seme[i] = new GetProductNames(rs, stm).getProductName().get(i);
		}

		JLabel itemlbl = new JLabel("Choose item", SwingConstants.CENTER);
		itemlbl.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 25));
		// itemlbl.setForeground(Color.WHITE);
		spinner = new JTextField();
		spinner.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 30));
		spinner.setToolTipText("Quantity");
		rendered = new JTextField();
		plus = new JButton("<html><h1>+<h1></html>");
		minus = new JButton("<html><h1>-<h1></html>");
		rendered.setEditable(false);
		rendered.setBackground(Color.BLACK);
		rendered.setForeground(Color.WHITE);
		rendered.setToolTipText("Enter amount received from the customer");
		itemscombo = new FilterItems(populateArray(), spinner, rendered);
		// itemscombo = new JComboBox<>(seme);
		// itemscombo.setEditable(true);
		// itemscombo.setMaximumRowCount(15);
		itemscombo.setFont(new Font(AllFonts.getTimesNewRoman(), Font.PLAIN, 30));
		itemscombo.setPreferredSize(new Dimension(400, 40));

		EventQueue.invokeLater(() -> {
			spinner.setText("1");
		});
		// adding the itemListener to the comboBox here
		itemscombo.addItemListener((e) -> {
			Object item = e.getItem();
			if (e.getStateChange() == ItemEvent.SELECTED) {
				String selecteditem = item.toString();
				itemscombo.setToolTipText(selecteditem);
				double unitprice = new GetUnitPrice(rs, stm).getunitPrice(selecteditem);
				String labeltext = selecteditem.toUpperCase() + " = $" + unitprice;

				EventQueue.invokeLater(() -> {
					topmostlbl.setText(labeltext);
					topmostlbl.setToolTipText(labeltext);
					// spinner.requestFocusInWindow();
					new Click().tabPlay();
				});
			} else if (e.getStateChange() == ItemEvent.DESELECTED) {
			}
		});
		// adding the keyListener to the comboBox and all its items
		itemscombo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char character = e.getKeyChar();
				new Click().tabPlay();
				if (character == KeyEvent.VK_PLUS || character == KeyEvent.VK_INSERT || character == KeyEvent.VK_ADD
						|| character == KeyEvent.VK_ENTER) {
					e.consume();
					String item = itemscombo.getModel().getSelectedItem().toString();
					if (!item.equals("")) {
						addProducatsToCart();
						EventQueue.invokeLater(() -> {
							spinner.requestFocusInWindow();
							spinner.setText("1");
						});
					}
				} else if (character == KeyEvent.VK_MINUS || character == KeyEvent.VK_DELETE
						|| character == KeyEvent.VK_BACK_SPACE) {
					removeProductFromCart();
					EventQueue.invokeLater(() -> {
						spinner.requestFocusInWindow();
					});
				} else if (character == KeyEvent.VK_EQUALS) {
					e.consume();
					EventQueue.invokeLater(() -> {
						focusTenderred();
						new Click().okPlay();
					});

				} else {

				}
			}
		});
		Box box1 = Box.createVerticalBox();
		box1.add(itemlbl);
		box1.add(Box.createVerticalStrut(4));
		box1.add(itemscombo);

		JLabel qn = new JLabel("Quantity", SwingConstants.CENTER);
		qn.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 25));
		// qn.setForeground(Color.WHITE);
		Box box2 = Box.createVerticalBox();
		box2.add(qn);
		box2.add(Box.createVerticalStrut(4));
		box2.add(spinner);

		topleft.add(box1);
		topleft.add(Box.createHorizontalStrut(30));
		topleft.add(box2);

		centerleftPanel = new JPanel(new BorderLayout());
		centerleftPanel.setBackground(Color.WHITE);
		calculatorpanel = new JPanel(new GridLayout(6, 3, 5, 5));
		calculatorpanel.setBorder(new TitledBorder(""));
		calculatorpanel.setBackground(Color.WHITE);

		// array for all buttons that are for calculator buttons
		calculatorButtons = new JButton[buttonText.length];
		for (int i = 0; i < buttonText.length; i++) {
			calculatorButtons[i] = new RoundButton(buttonText[i]);
			// calculatorButtons[i].setBackground(new Color(10, 75, 90));
			calculatorButtons[i].setBackground(Color.GRAY);
			calculatorButtons[i].setForeground(Color.WHITE);
			calculatorButtons[i].setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 25));
			calculatorButtons[i].setToolTipText(buttonText[i]);
			// calculatorButtons[i].setEnabled(false);
			calculatorpanel.add(calculatorButtons[i]);
		}
		// setting the whole system on normal mode by default
		setOnNormalMode();
		// setting the background of different buttons
		calculatorButtons[10].setBackground(new Color(100, 80, 90));
		calculatorButtons[11].setBackground(new Color(100, 80, 90));
		calculatorButtons[12].setBackground(new Color(100, 45, 101));
		calculatorButtons[13].setBackground(new Color(130, 75, 100));

		enterbutton = new JButton("<html><h1>ENTER</h1></html>");
		enterbutton.addActionListener(this);

		calculatorpanel.add(enterbutton);
		leftpanel.add(topleft, BorderLayout.NORTH);

		plus.addActionListener(this);
		plus.addMouseListener(new MathOppListener(tuner));
		plus.setBackground(Color.BLUE);
		plus.setForeground(Color.WHITE);
		plus.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		plus.setToolTipText("Add selected product to shopping cart \u2192 \u2302");

		minus.addActionListener(this);
		minus.addMouseListener(new CharButtonListener(tuner));
		minus.setBackground(Color.RED);
		minus.setForeground(Color.WHITE);
		minus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		minus.setToolTipText("Remove selected product from shopping cart \u2190 \u2302");

		receipt = new JButton("<html><h1>OK<h1></html>");
		receipt.setBackground(new Color(10, 70, 90));
		receipt.setForeground(Color.WHITE);
		receipt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		receipt.setToolTipText("Hit this button to enter amount tendered");

		reset = new JButton("<html><h3>Reset \u2192<h3></html>");
		reset.setBackground(new Color(10, 40, 100));
		reset.setForeground(Color.WHITE);
		reset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		reset.setToolTipText("Cancel this transaction");

		Box buttonBox = Box.createVerticalBox();
		buttonBox.add(plus);
		buttonBox.add(Box.createVerticalStrut(5));
		buttonBox.add(minus);
		buttonBox.add(Box.createVerticalStrut(15));
		buttonBox.add(receipt);
		buttonBox.add(Box.createVerticalStrut(20));
		buttonBox.add(reset);
		centerleftPanel.add(calculatorpanel, BorderLayout.CENTER);
		centerleftPanel.add(buttonBox, BorderLayout.EAST);

		leftpanel.add(centerleftPanel, BorderLayout.CENTER);

		display = new JTextArea("");
		display.setEditable(false);
		display.setLineWrap(true);
		display.setMargin(new Insets(30, 30, 30, 30));
		display.setBackground(Color.BLACK);
		display.setForeground(Color.white);
		display.setFont(new Font("", Font.ROMAN_BASELINE, 18));
		display.getCaret().setSelectionVisible(true);
		display.getCaret().setVisible(true);
		// adding the key listener to the shopping cart
		display.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// scannig the key that is typed
				char key = arg0.getKeyChar();
				if (key == KeyEvent.VK_C || key == KeyEvent.VK_DELETE) {
					// clearing the shopping cart
					clearData();
					// deleting the temporary transaction data
					deleteTemporaryData();
					EventQueue.invokeLater(() -> {
						itemscombo.requestFocusInWindow();
					});
				}
			}
		});

		JScrollPane scroller = new JScrollPane(display);
		scroller.setBorder(null);

		JPanel rightpanel = new JPanel(new BorderLayout());
		rightpanel.setBackground(Color.BLACK);
		JLabel cartlbl = new JLabel("<html><i><u>Shopping Cart</u></i></html>", SwingConstants.CENTER);
		JLabel flicking = new BlinkingLabel("<html><i> \u2193</i></html>");
		cartlbl.setForeground(Color.WHITE);
		cartlbl.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 18));
		flicking.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 18));
		flicking.setForeground(Color.WHITE);

		JLabel label1 = new JLabel("TOTAL $");
		label1.setForeground(Color.WHITE);
		label1.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 30));

		totallbl = new JLabel("0.00");
		totallbl.setForeground(Color.WHITE);
		totallbl.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 30));

		Box topprightbox = Box.createHorizontalBox();
		topprightbox.add(label1);
		topprightbox.add(totallbl);
		topprightbox.add(Box.createHorizontalStrut(15));
		topprightbox.add(cartlbl);
		topprightbox.add(flicking);

		String[] labels = { "Item Names", "", "Quantity", "Total Cost($)" };
		allLabels = new JLabel[labels.length];
		JPanel rightupperPanel = new JPanel(new GridLayout(1, 4, 5, 5));
		// for loops for the painting of labels here
		for (int x = 0; x < labels.length; x++) {
			allLabels[x] = new JLabel(labels[x]);
			allLabels[x].setSize(3, 3);
			allLabels[x].setForeground(Color.RED);
			allLabels[x].setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 25));
			rightupperPanel.add(allLabels[x]);
		}
		Box rightbox = Box.createVerticalBox();
		rightbox.add(topprightbox);
		rightbox.add(Box.createVerticalStrut(20));
		rightbox.add(rightupperPanel);

		rightpanel.add(rightbox, BorderLayout.NORTH);
		rightpanel.add(scroller, BorderLayout.CENTER);

		this.setLayout(new BorderLayout());
		JPanel mainpanbel = new JPanel(new GridLayout(1, 2, 15, 0));

		rendered.addKeyListener(new MathKeysValidator());

		rendered.addActionListener(this);
		rendered.setFont(new Font(AllFonts.getTimesNewRoman(), Font.ROMAN_BASELINE + Font.BOLD, 30));

		// rightpanel.add(rendered, BorderLayout.SOUTH);

		mainpanbel.add(leftpanel);
		mainpanbel.add(rightpanel);
		JPanel topmostpanel = new JPanel(new BorderLayout());
		// topmostpanel.setLayout(new GridLayout(1, 3, 3, 0));
		usedialpad = new JRadioButton("Use Dial Pad", false);
		usedialpad.setToolTipText("Useful only when your screen is touch enabled");
		usedialpad.setForeground(Color.WHITE);
		usedialpad.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		normalmode = new JRadioButton("Use Normal Mode", true);
		normalmode.setToolTipText("Use the default normal mode when operating CA$HIER");
		normalmode.setForeground(Color.WHITE);
		normalmode.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		automode = new JRadioButton("Auto Mode", false);
		automode.setToolTipText("Use the Reader when operating CA$HIER");
		automode.setForeground(Color.WHITE);
		automode.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		ButtonGroup swichmodegroup = new ButtonGroup();
		swichmodegroup.add(normalmode);
		swichmodegroup.add(usedialpad);
		swichmodegroup.add(automode);
		usedialpad.addActionListener(this);
		normalmode.addActionListener(this);
		automode.addActionListener(this);
		Box checkboxes = Box.createVerticalBox();
		checkboxes.setBorder(new TitledBorder(""));
		checkboxes.add(normalmode);
		checkboxes.add(Box.createVerticalStrut(8));
		checkboxes.add(usedialpad);
		checkboxes.add(Box.createVerticalStrut(8));
		checkboxes.add(automode);

		JPanel anotherpanel = new JPanel(new GridLayout(1, 2));
		anotherpanel.setBackground(new Color(10, 90, 90));
		anotherpanel.add(topmostlbl);
		anotherpanel.add(rendered);

		topmostpanel.add(checkboxes, BorderLayout.WEST);
		topmostpanel.setBackground(new Color(10, 90, 90));
		topmostpanel.add(anotherpanel, BorderLayout.CENTER);

		this.add(topmostpanel, BorderLayout.NORTH);
		this.add(mainpanbel, BorderLayout.CENTER);
		// this.add(checkboxes, BorderLayout.WEST);

		receipt.addActionListener(this);
		receipt.addMouseListener(new OkMouseListener(tuner));
		reset.addActionListener(this);
		reset.addMouseListener(new ClearCartListener(tuner));

		changelbl = new JLabel("", JLabel.CENTER);
		// changelbl.setForeground(Color.WHITE);
		changelbl.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 25));
		// showDialog();
		// showDialog1();
		changelbl1 = new JLabel("", JLabel.CENTER);
		// changelbl1.setForeground(Color.WHITE);
		changelbl1.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 25));

		// loading all prodducts that are in stock
		loadInStockProducts = new LoadInStockProducts(rs, rs1, stmt, stmt);
		instockitems = loadInStockProducts.inStockItems();

		// creating a lower panel for the conntrol of the input type

		JPanel lowrpanel = new JPanel();
		lowrpanel.setBackground(Color.WHITE);

		this.add(lowrpanel, BorderLayout.SOUTH);

		barcodeField = new JTextField();
		barcodeField.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 30));
		barcodeField.setPreferredSize(new Dimension(400, 50));
		barcodeField.addCaretListener((e) -> {
			String value = barcodeField.getText();
			if (!value.equals("")) {
				double unitprice = new GetUnitPrice(rs, stm).getunitPrice(value, 0);
				String labeltext = new GetProductNames(rs, stm).getProductName(value) + " = $" + unitprice;
				EventQueue.invokeLater(() -> {
					topmostlbl.setText(labeltext);
					topmostlbl.setToolTipText(labeltext);
					// spinner.requestFocusInWindow();
					// new Click().tabPlay();
				});
			}
		});

		// adding the keyListener to the barcodeField
		barcodeField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char character = e.getKeyChar();
				// new Click().tabPlay();
				String barcode = barcodeField.getText();
				if (character == KeyEvent.VK_PLUS || character == KeyEvent.VK_INSERT || character == KeyEvent.VK_ADD
						|| character == KeyEvent.VK_ENTER) {
					e.consume();
					if (!barcode.equals("")) {
						addProducatsToCart(barcode, barcodeField);
						EventQueue.invokeLater(() -> {
							// spinner.requestFocusInWindow();
							spinner.setText("1");
						});
					}
				} else if (character == KeyEvent.VK_MINUS || character == KeyEvent.VK_DELETE
						|| character == KeyEvent.VK_BACK_SPACE) {
					removeProductFromCart(barcode);
				} else if (character == KeyEvent.VK_EQUALS) {
					e.consume();
					EventQueue.invokeLater(() -> {
						focusTenderred();
						new Click().okPlay();
					});

				} else {

				}
			}
		});

		centerleftPanel.add(barcodeField, BorderLayout.NORTH);
		barcodeField.setVisible(false);

		// int minValue = 1;
		// int maxValue = 100;
		// int currentValue = 1;
		// int steps = 1;
		// spinnerModel = new SpinnerNumberModel(currentValue, minValue,
		// maxValue, steps);

		// JSpinner.NumberEditor nEditor = new JSpinner.NumberEditor(spinner,
		// "0");
		// spinner.setEditor(nEditor);

		// adding a keylistener to the spinner to the spinner that is used to
		// select the quantity of a given item
		spinner.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// scanning the key that is typed here
				char character = e.getKeyChar();
				if (character == KeyEvent.VK_PLUS || character == KeyEvent.VK_INSERT
						|| character == KeyEvent.VK_ENTER) {
					String q = spinner.getText();
					if (!q.equals("")) {
						if (normalmode.isSelected()) {
							addProducatsToCart();
							EventQueue.invokeLater(() -> {
								itemscombo.requestFocusInWindow();
							});
							EventQueue.invokeLater(() -> {
								itemscombo.setSelectedItem("");
							});
							EventQueue.invokeLater(() -> {
								itemscombo.showPopup();
							});
							EventQueue.invokeLater(() -> {
								spinner.setText("");
							});
						} else if (automode.isSelected()) {
							String barcode_number = getBarcodeText();
							addProducatsToCart(barcode_number, barcodeField);
							EventQueue.invokeLater(() -> {
								barcodeField.requestFocusInWindow();
							});
							EventQueue.invokeLater(() -> {
								spinner.setText("");
							});
						} else {

						}
					}
				} else if (character == KeyEvent.VK_MINUS) {
					// consume the key and not allow any input
					e.consume();
					if (normalmode.isSelected()) {
						removeProductFromCart();
						EventQueue.invokeLater(() -> {
							itemscombo.requestFocusInWindow();
							itemscombo.setSelectedItem("");
							itemscombo.showPopup();
							spinner.setText("");
						});
					} else if (automode.isSelected()) {
						String barcode_number = getBarcodeText();
						removeProductFromCart(barcode_number);
						EventQueue.invokeLater(() -> {
							barcodeField.requestFocusInWindow();
							spinner.setText("");
						});
					} else {

					}
				} else if (character == KeyEvent.VK_EQUALS) {
					e.consume();
					focusTenderred();
				} else if (!(Character.isDigit(character) || (character == KeyEvent.VK_BACK_SPACE)
						|| (character == KeyEvent.VK_DELETE) || (character == KeyEvent.VK_ENTER)
						|| (character == KeyEvent.VK_PERIOD))) {
					java.awt.Toolkit.getDefaultToolkit().beep();
					e.consume();
				} else {
					// spinner.setText("");
				}
			}
		});
		/*
		 * spinner.addChangeListener((e) -> {
		 * 
		 * });
		 */

		// adding actionlisteners and mouselisteners for for different touchpad
		// buttons
		calculatorButtons[0].addActionListener((ev) -> {
			String newtext = "1";
			touchPadOptions(newtext);
		});
		calculatorButtons[0].addMouseListener(new ButtonListener(tuner));
		calculatorButtons[1].addActionListener((ev) -> {
			String newtext = "2";
			touchPadOptions(newtext);
		});
		calculatorButtons[1].addMouseListener(new ButtonListener(tuner));
		calculatorButtons[2].addActionListener((ev) -> {
			String newtext = "3";
			touchPadOptions(newtext);
		});
		calculatorButtons[2].addMouseListener(new ButtonListener(tuner));
		calculatorButtons[3].addActionListener((ev) -> {
			String newtext = "4";
			touchPadOptions(newtext);
		});
		calculatorButtons[3].addMouseListener(new ButtonListener(tuner));
		calculatorButtons[4].addActionListener((ev) -> {
			String newtext = "5";
			touchPadOptions(newtext);
		});
		calculatorButtons[4].addMouseListener(new ButtonListener(tuner));
		calculatorButtons[5].addActionListener((ev) -> {
			String newtext = "6";
			touchPadOptions(newtext);
		});
		calculatorButtons[5].addMouseListener(new ButtonListener(tuner));
		calculatorButtons[6].addActionListener((ev) -> {
			String newtext = "7";
			touchPadOptions(newtext);
		});
		calculatorButtons[6].addMouseListener(new ButtonListener(tuner));
		calculatorButtons[7].addActionListener((ev) -> {
			String newtext = "8";
			touchPadOptions(newtext);
		});
		calculatorButtons[7].addMouseListener(new ButtonListener(tuner));
		calculatorButtons[8].addActionListener((ev) -> {
			String newtext = "9";
			touchPadOptions(newtext);
		});
		calculatorButtons[8].addMouseListener(new ButtonListener(tuner));
		calculatorButtons[9].addActionListener((ev) -> {
			String newtext = "0";
			touchPadOptions(newtext);
		});
		calculatorButtons[9].addMouseListener(new ButtonListener(tuner));
		calculatorButtons[10].addActionListener((ev) -> {
			String newtext = "*";
			touchPadOptions(newtext);
		});
		calculatorButtons[10].addMouseListener(new MathOppListener(tuner));
		calculatorButtons[11].addActionListener((ev) -> {
			String newtext = "+";
			touchPadOptions(newtext);
		});
		calculatorButtons[11].addMouseListener(new MathOppListener(tuner));
		calculatorButtons[12].addActionListener((ev) -> {
			String newtext = ".";
			touchPadOptions(newtext);
		});
		calculatorButtons[12].addMouseListener(new CharButtonListener(tuner));
		calculatorButtons[13].addActionListener((ev) -> {
			String newtext = "-";
			touchPadOptions(newtext);
		});
		calculatorButtons[13].addMouseListener(new MathOppListener(tuner));
		calculatorButtons[14].addActionListener((ev) -> {
			String newtext = "/";
			touchPadOptions(newtext);
		});
		calculatorButtons[14].addMouseListener(new MathOppListener(tuner));
		calculatorButtons[15].addActionListener((ev) -> {
			String newtext = "(";
			touchPadOptions(newtext);
		});
		calculatorButtons[15].addMouseListener(new CharButtonListener(tuner));
		calculatorButtons[16].addActionListener((ev) -> {
			String newtext = ")";
			touchPadOptions(newtext);
		});
		calculatorButtons[16].addMouseListener(new CharButtonListener(tuner));

	}

	/*
	 * public void insertTab() { EventQueue.invokeLater(() -> { int numberoftabs
	 * = tabs.getTabCount(); boolean exist = false; for (int a = 0; a <
	 * numberoftabs; a++) { if (tabs.getTitleAt(a).trim().equals(
	 * "New Transaction")) { exist = true; tabs.setSelectedIndex(numberoftabs);
	 * break; } } if (!exist) { tabs.addTab("New Transaction   ", null, this,
	 * "Make New Transaction"); tabs.setSelectedIndex(numberoftabs); } }); }
	 */

	public List<String> populateArray() {
		List<String> test = new GetProductNames(rs, stm).getProductName();
		return test;
	}

	// managing temporary transactions
	public void processTemporaryTransaction(String product_name, int quantity, double amount) {
		ManageTemporaryData manage = new ManageTemporaryData(rs, stm);
		manage.manageTemporaryData(product_name, quantity, amount);

		GetTemporaryData getdata = new GetTemporaryData(rs, rs1, stm, stmt);

		EventQueue.invokeLater(() -> {
			getdata.getTemporaryData(display);
		});
		EventQueue.invokeLater(() -> {
			totallbl.setText("" + getdata.getTotalCost());
		});
	}

	// clearing the cart
	public void clearCart() {
		EventQueue.invokeLater(() -> {
			display.setText("");
		});
	}

	// the dialog box is for calculating the change and printing the receipt
	public void showDialog() {
		dialog = new JDialog((JFrame) null, "Change", true);
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.setIconImage(new IconImage().createIconImage());

		dialog.getContentPane().add(changelbl, BorderLayout.NORTH);

		JLabel top = new JLabel("<html><h1>Keep Change?<h1>", SwingConstants.CENTER);
		// top.setForeground(Color.WHITE);

		Box b = Box.createVerticalBox();
		JLabel yeslbl = new JLabel("YES", SwingConstants.CENTER);
		yeslbl.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 19));
		yes = new JRadioButton();
		b.add(yeslbl);
		b.add(yes);

		Box b1 = Box.createVerticalBox();
		JLabel nolbl = new JLabel("NO", SwingConstants.CENTER);
		nolbl.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 19));
		no = new JRadioButton("", true);
		no.setBackground(Color.GREEN);
		// no.setSelected(true);
		b1.add(nolbl);
		b1.add(no);

		ButtonGroup bgr = new ButtonGroup();
		bgr.add(yes);
		bgr.add(no);

		Box b3 = Box.createHorizontalBox();
		b3.add(Box.createHorizontalStrut(30));
		b3.add(b1);
		b3.add(Box.createHorizontalStrut(80));
		b3.add(b);
		JPanel midpanel = new JPanel(new GridLayout(3, 1));
		midpanel.setOpaque(false);
		midpanel.add(top);
		midpanel.add(b3);

		Box buttonbox = Box.createHorizontalBox();

		midpanel.add(buttonbox);
		// dialog.getContentPane().setBackground(new Color(0.5f, 0.5f, 1f));
		dialog.getContentPane().setBackground(Color.WHITE);
		dialog.getContentPane().add(midpanel, BorderLayout.CENTER);
		// this button is for printing the receipt and settimg for a new
		// transaction
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		ocancelButton = new JButton("Cancel");
		ocancelButton.setBackground(Color.MAGENTA);
		ocancelButton.setForeground(Color.WHITE);
		ocancelButton.addActionListener(this);
		buttonbox.add(Box.createHorizontalGlue());
		buttonbox.add(okButton);
		buttonbox.add(Box.createHorizontalStrut(30));
		buttonbox.add(ocancelButton);
		dialog.getRootPane().setDefaultButton(okButton);

		dialog.setSize(300, 185);
		dialog.setResizable(false);
		Dimension d = dialog.getSize(), screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - d.width) / 2, y = (screen.height - d.height) / 2;
		dialog.setLocation(x, y);
		dialog.setVisible(true);

		dialog.setAlwaysOnTop(true);
		okButton.addMouseListener(new OkMouseListener(tuner));
	}

	// calculate the change and give the operator the change
	public void showDialog1() {
		dialog1 = new JDialog((JFrame) null, "Change Suggestion", true);
		dialog1.setLayout(new BorderLayout());
		dialog1.setIconImage(new IconImage().createIconImage());

		dialog1.getContentPane().add(changelbl1, BorderLayout.NORTH);

		JLabel top = new JLabel("<html><h1>Keep Change?<h1>", SwingConstants.CENTER);
		top.setForeground(Color.WHITE);

		Box b = Box.createVerticalBox();
		JLabel yeslbl = new JLabel("YES", SwingConstants.CENTER);
		yeslbl.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 19));
		yes1 = new JRadioButton();
		b.add(yeslbl);
		b.add(yes1);

		Box b1 = Box.createVerticalBox();
		JLabel nolbl = new JLabel("NO", SwingConstants.CENTER);
		nolbl.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD, 19));
		no1 = new JRadioButton("", true);
		no1.setBackground(Color.GREEN);
		// no.setSelected(true);
		b1.add(nolbl);
		b1.add(no1);

		ButtonGroup bgr = new ButtonGroup();
		bgr.add(yes1);
		bgr.add(no1);

		Box b3 = Box.createHorizontalBox();
		b3.add(Box.createHorizontalStrut(30));
		b3.add(b1);
		b3.add(Box.createHorizontalStrut(80));
		b3.add(b);
		JPanel midpanel = new JPanel(new GridLayout(3, 1));
		midpanel.setOpaque(false);
		midpanel.add(top);
		midpanel.add(b3);

		Box buttonbox = Box.createHorizontalBox();

		midpanel.add(buttonbox);
		dialog1.getContentPane().setBackground(new Color(0.5f, 0.5f, 1f));
		dialog1.getContentPane().add(midpanel, BorderLayout.CENTER);

		okButton1 = new JButton("OK");
		okButton1.addActionListener(this);
		ocancelButton1 = new JButton("Cancel");
		ocancelButton1.setBackground(Color.MAGENTA);
		ocancelButton1.setForeground(Color.WHITE);
		ocancelButton1.addActionListener(this);
		buttonbox.add(Box.createHorizontalGlue());
		buttonbox.add(okButton1);
		buttonbox.add(Box.createHorizontalStrut(30));
		buttonbox.add(ocancelButton1);
		dialog1.getRootPane().setDefaultButton(okButton1);

		dialog1.setSize(300, 185);
		dialog1.setResizable(false);
		Dimension d = dialog1.getSize(), screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - d.width) / 2, y = (screen.height - d.height) / 2;
		dialog1.setLocation(x, y);
		dialog1.setVisible(true);
		dialog1.setAlwaysOnTop(true);
		okButton1.addMouseListener(new OkMouseListener(tuner));
	}

	// Action listener for this class
	@Override
	public void actionPerformed(ActionEvent e) {
		DoubleForm df = new DoubleForm();
		if (e.getSource() == plus) {
			if (normalmode.isSelected()) {
				addProducatsToCart();
			} else if (automode.isSelected()) {
				String barcode_number = getBarcodeText();
				addProducatsToCart(barcode_number, barcodeField);
			} else {

			}
		}
		if (e.getSource() == minus) {
			if (normalmode.isSelected())
				removeProductFromCart();
			else if (automode.isSelected()) {
				String barcode_number = getBarcodeText();
				removeProductFromCart(barcode_number);
			} else {

			}
		}
		if (e.getSource() == receipt) {
			focusTenderred();
		}
		if (e.getSource() == rendered || e.getSource() == enterbutton) {
			String the_totallbl = totallbl.getText().trim();
			if (!the_totallbl.equals("0.00")) {
				double totalcost = Double.parseDouble(the_totallbl);
				if (!rendered.getText().equals("")) {
					double renderedamount = getRenderedAmount();
					if (renderedamount < totalcost) {
						JOptionPane.showMessageDialog(frame,
								"Tendered $" + renderedamount + " is less than  $" + totalcost + " total cost!",
								"Invalid Payment", JOptionPane.ERROR_MESSAGE);
					} else {
						if (normalmode.isSelected()) {
							changefee = df.form(renderedamount - totalcost);
							changelbl.setText("CHANGE $" + changefee);
							// EventQueue.invokeLater(() -> {
							// new NewPaymentsModule(tabs, rs, rs1, stm, stmt,
							// pstmt, conn, frame, listmodel, totallabel,
							// tuner).getChangeMenu().setText("CHANGE $" +
							// changefee);
							// });

							soundclick.dialogPlayer();
							showDialog();
							// rendered.setEditable(false);
							for (int i = 0; i < buttonText.length; i++) {
								calculatorButtons[i].setEnabled(false);
							}
						} else if (usedialpad.isSelected()) {
							changefee = df.form(renderedamount - totalcost);
							String text = getRenderedText();
							String script = new Script().evaluateString(text);
							totallbl.setText(script);
							soundclick.dialogPlayer();
							changelbl1.setText("CHANGE $" + changefee);
							/*
							 * EventQueue.invokeLater(() -> { new
							 * NewPaymentsModule(tabs, rs, rs1, stm, stmt,
							 * pstmt, conn, frame, listmodel, totallabel,
							 * tuner).getChangeMenu().setText("CHANGE $" +
							 * changefee); });
							 */
							showDialog();
						} else {
							changefee = df.form(renderedamount - totalcost);
							changelbl.setText("CHANGE $" + changefee);
							// EventQueue.invokeLater(() -> {
							// new NewPaymentsModule(tabs, rs, rs1, stm, stmt,
							// pstmt, conn, frame, listmodel, totallabel,
							// tuner).getChangeMenu().setText("CHANGE $" +
							// changefee);
							// });

							soundclick.dialogPlayer();
							showDialog();
							// rendered.setEditable(false);
							for (int i = 0; i < buttonText.length; i++) {
								// calculatorButtons[i].setEnabled(false);
							}
						}
					}
				} else {
					JOptionPane.showMessageDialog(frame, "No Cash Tendered!", "Invalid Payment",
							JOptionPane.ERROR_MESSAGE);
					EventQueue.invokeLater(() -> {
						rendered.requestFocusInWindow();
					});
				}
			} else {
				JOptionPane.showMessageDialog(frame, "No goods are in the shopping cart!", "No items",
						JOptionPane.ERROR_MESSAGE);
				rendered.setText("");
				EventQueue.invokeLater(() -> {
					itemscombo.requestFocusInWindow();
				});
			}
			/*
			 * EventQueue.invokeLater(() -> { try { String item =
			 * itemscombo.getSelectedItem().toString(); String quantity =
			 * spinner.getText(); if (!item.equals("")) { if
			 * (!quantity.equals("")) { int qu = Integer.parseInt(quantity);
			 * plus.setToolTipText("Add " + qu + " \u00D7 " + item +
			 * " to shopping cart"); minus.setToolTipText("Less " + qu +
			 * " \u00D7 " + item + " from shopping cart"); } } } catch
			 * (NullPointerException ee) { ee.printStackTrace(); } });
			 */
		}

		if (e.getSource() == okButton) {
			double given = getRenderedAmount();
			double cost = Double.parseDouble(totallbl.getText());// getTotalCostOfGoods();
			double change = df.form(given - cost);
			double collected = 0;
			int receiptno = new Increment(rs1, stmt).incrementReceiptno();
			InsertTransaction insert = new InsertTransaction(rs, rs1, stm, stmt);
			if (no.isSelected()) {
				dialog.dispose();
				collected = change;
				insert.affectTransaction(given, change, collected, receiptno, cost);
				new MakeReceipt(rs, rs1, stm, stmt).makeReceipt(receiptno, cost, given, change);
				list.addtoArray(Integer.toString(receiptno), Double.toString(cost));
				list.addToListModel(listmodel);
				EventQueue.invokeLater(() -> {
					totallabel.setText(list.getTotal());
				});
				clearData();
				EventQueue.invokeLater(() -> {
					rendered.setEditable(false);
					if (normalmode.isSelected())
						itemscombo.requestFocusInWindow();
					else if (automode.isSelected())
						barcodeField.requestFocusInWindow();
					else {

					}
				});
			} else {
				dialog.dispose();
				collected = 0;
				insert.affectTransaction(given, change, collected, receiptno, cost);
				new MakeReceipt(rs, rs1, stm, stmt).makeReceipt(receiptno, cost, given, change);
				list.addtoArray(Integer.toString(receiptno), Double.toString(cost));
				list.addToListModel(listmodel);
				EventQueue.invokeLater(() -> {
					totallabel.setText(list.getTotal());
				});
				clearData();
			}
		}
		if (e.getSource() == usedialpad) {
			display.setText("");
			totallbl.setText("0.00");
			rendered.setEditable(true);
			rendered.requestFocusInWindow();
			rendered.setBackground(Color.WHITE);
			rendered.setForeground(Color.BLACK);
			// plus.setEnabled(false);
			// minus.setEnabled(false);
			// receipt.setEnabled(false);
			for (int i = 0; i < buttonText.length; i++) {
				// calculatorButtons[i].setEnabled(true);
				calculatorButtons[i].setVisible(true);
			}
			EventQueue.invokeLater(() -> {
				barcodeField.setVisible(false);
				calculatorpanel.setBackground(Color.DARK_GRAY);
			});
		}
		if (e.getSource() == normalmode) {
			EventQueue.invokeLater(() -> {
				itemscombo.requestFocusInWindow();
			});
			for (int i = 0; i < buttonText.length; i++) {
				// calculatorButtons[i].setEnabled(false);
				calculatorButtons[i].setVisible(false);
			}
			rendered.setText("");
			rendered.setEditable(false);
			plus.setEnabled(true);
			minus.setEnabled(true);
			receipt.setEnabled(true);
			calculatorpanel.setBackground(Color.WHITE);
			rendered.setBackground(Color.BLACK);
		}
		if (e.getSource() == automode) {
			for (int i = 0; i < buttonText.length; i++) {
				calculatorButtons[i].setVisible(true);
			}
			rendered.setText("");
			rendered.setEditable(false);
			plus.setEnabled(true);
			minus.setEnabled(true);
			receipt.setEnabled(true);
			calculatorpanel.setBackground(Color.WHITE);
			rendered.setBackground(Color.BLACK);
			EventQueue.invokeLater(() -> {
				barcodeField.setVisible(true);
				barcodeField.requestFocusInWindow();
				calculatorpanel.setBackground(Color.DARK_GRAY);
			});
		}
		if (e.getSource() == ocancelButton) {
			dialog.dispose();
			rendered.setEditable(true);
			rendered.requestFocusInWindow();
			/*
			 * for (int i = 0; i < buttonText.length; i++) {
			 * calculatorButtons[i].setEnabled(true); }
			 */
		}
		if (e.getSource() == okButton1) {
			double given = getRenderedAmount();
			double cost = Double.parseDouble(totallbl.getText());// getTotalCostOfGoods();
			double change = df.form(given - cost);
			double collected = 0;
			int receiptno = new Increment(rs1, stmt).incrementReceiptno();
			InsertTransaction insert = new InsertTransaction(rs, rs1, stm, stmt);
			if (no1.isSelected()) {
				dialog1.dispose();
				collected = change;
				insert.affectTransaction(given, change, collected, receiptno, cost);
				new MakeReceipt(rs, rs1, stm, stmt).makeReceipt(receiptno, cost, given, change);
				list.addtoArray(Integer.toString(receiptno), Double.toString(cost));
				list.addToListModel(listmodel);
				totallabel.setText(list.getTotal());
				clearData();
			} else {
				dialog1.dispose();
				collected = 0;
				insert.affectTransaction(given, change, collected, receiptno, cost);
				new MakeReceipt(rs, rs1, stm, stmt).makeReceipt(receiptno, cost, given, change);
				list.addtoArray(Integer.toString(receiptno), Double.toString(cost));
				list.addToListModel(listmodel);
				totallabel.setText(list.getTotal());
				clearData();
			}
		}
		if (e.getSource() == ocancelButton1) {
			dialog1.dispose();
			rendered.setEditable(true);
			rendered.requestFocusInWindow();
			for (int i = 0; i < buttonText.length; i++) {
				calculatorButtons[i].setEnabled(true);
			}
		}
		if (e.getSource() == reset) {
			clearData();
			deleteTemporaryData();
			EventQueue.invokeLater(() -> {
				itemscombo.requestFocusInWindow();
			});
		}
	}

	private void focusTenderred() {
		String the_totallbl = totallbl.getText().trim();
		if (!the_totallbl.equals("0.00")) {
			rendered.setEditable(true);
			rendered.getCaret().setVisible(true);
			rendered.requestFocusInWindow();
		} else {
			JOptionPane.showMessageDialog(this, "There's nothing in the cart", "No goods", JOptionPane.ERROR_MESSAGE);
			EventQueue.invokeLater(() -> {
				itemscombo.requestFocusInWindow();
			});
		}

		/*
		 * for (int i = 0; i < buttonText.length; i++) {
		 * calculatorButtons[i].setEnabled(true); }
		 */
	}

	// this method removes any selected item from the cart.
	private void removeProductFromCart() {
		String product_name = itemscombo.getModel().getSelectedItem().toString();
		String qn = spinner.getText();
		if (!product_name.equals("")) {
			clearCart();
			if (!qn.equals("")) {
				// Object qn = spinnerModel.getNumber();
				// int quantity = (int) qn;
				int quantity = Integer.parseInt(qn);
				double unitprice = new GetUnitPrice(rs, stm).getunitPrice(product_name);
				double cost_price = df.form(unitprice * quantity);
				processTemporaryTransaction(product_name, (-1) * quantity, (-1) * cost_price);
			}
		} else {

		}
	}

	// this method removes any selected item from the cart.
	private void removeProductFromCart(String barcode) {
		String product_name = null;
		if (!barcodeField.getText().equals(""))
			product_name = new GetProductNames(rs, stm).getProductName(barcode);
		else
			product_name = (topmostlbl.getText().split("=")[0]).trim();
		String qn = spinner.getText();
		if (!product_name.equals("")) {
			clearCart();
			if (!qn.equals("")) {
				int quantity = Integer.parseInt(qn);
				double unitprice = new GetUnitPrice(rs, stm).getunitPrice(product_name);
				double cost_price = df.form(unitprice * quantity);
				processTemporaryTransaction(product_name, (-1) * quantity, (-1) * cost_price);
			}
		} else {

		}
	}

	// this method adds any selected items to the cart.
	private void addProducatsToCart() {
		String product_name = itemscombo.getModel().getSelectedItem().toString();
		String qn = spinner.getText();
		if (!product_name.equals("")) {
			if (loadInStockProducts.isItemInStock(product_name, instockitems)) {
				clearCart();
				if (!qn.equals("")) {
					// Object qn = spinnerModel.getNumber();
					// int quantity = (int) qn;
					int quantity = Integer.parseInt(qn);
					double unitprice = new GetUnitPrice(rs, stm).getunitPrice(product_name);
					double cost_price = df.form(unitprice * quantity);
					processTemporaryTransaction(product_name, quantity, cost_price);
				}
			} else {
				JOptionPane.showMessageDialog(frame, product_name, "Out of stock", JOptionPane.WARNING_MESSAGE);

			}
		} else {

		}
	}

	private void addProducatsToCart(String barcode, JTextField barcodeField) {
		String product_name = null;
		if (!barcodeField.getText().equals(""))
			product_name = new GetProductNames(rs, stm).getProductName(barcode);
		else
			product_name = (topmostlbl.getText().split("=")[0]).trim();
		String qn = spinner.getText();
		if (!product_name.equals("")) {
			if (loadInStockProducts.isItemInStock(product_name, instockitems)) {
				clearCart();
				if (!qn.equals("")) {
					int quantity = Integer.parseInt(qn);
					double unitprice = new GetUnitPrice(rs, stm).getunitPrice(product_name);
					double cost_price = df.form(unitprice * quantity);
					processTemporaryTransaction(product_name, quantity, cost_price);
					barcodeField.setText("");
				}
			} else {
				JOptionPane.showMessageDialog(frame, product_name, "Out of stock", JOptionPane.WARNING_MESSAGE);
				barcodeField.setText("");
				barcodeField.requestFocusInWindow();
			}
		} else {

		}
	}

	public void touchPadOptions(String newtext) {
		String oldtext = getRenderedText();
		String code = getBarcodeText();
		if (usedialpad.isSelected()) {
			rendered.setText(oldtext + newtext);
			rendered.requestFocusInWindow();
		} else if (automode.isSelected()) {
			barcodeField.setText(code + newtext);
			barcodeField.requestFocusInWindow();
		} else {

		}
	}

	public double getTotalCostOfGoods() {
		return new GetTemporaryData(rs, rs1, stm, pstmt).getTotalCost();
	}

	public void clearData() {
		EventQueue.invokeLater(() -> {
			rendered.setText("");
			rendered.setBackground(Color.BLACK);
			rendered.setForeground(Color.WHITE);
		});
		EventQueue.invokeLater(() -> {
			display.setText("");
		});
		EventQueue.invokeLater(() -> {
			totallbl.setText("0.00");
		});
	}

	public String getRenderedText() {
		return rendered.getText();
	}

	public String getBarcodeText() {
		return barcodeField.getText();
	}

	public double getRenderedAmount() {
		String script = new Script().evaluateString(rendered.getText());
		return Double.parseDouble(script);
	}

	public void closeDialog(JDialog dialog) {
		ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();
		s.schedule(() -> {
			dialog.dispose();
			clearData();
			synchronized (s) {
				deleteTemporaryData();
			}
			JOptionPane.showMessageDialog(frame, "Transaction session has expired", "Session Expired",
					JOptionPane.ERROR_MESSAGE);
		}, 20, TimeUnit.SECONDS);
	}

	// method to delete temporary data, this should be able to manage
	// transactions that InnoDB offers as a functionality.
	public void deleteTemporaryData() {
		DeleteTMPTableData delete = new DeleteTMPTableData(rs, stm);
		delete.deleteTemporaryData();
	}

	public void setOnNormalMode() {
		for (int i = 0; i < buttonText.length; i++) {
			// calculatorButtons[i].setEnabled(false);
			calculatorButtons[i].setVisible(false);
		}
	}
}