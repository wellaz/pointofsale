package com.equation.cashierll.receipts.collection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.equation.cashierll.deco.AnimateDialog;
import com.equation.cashierll.deco.BlinkingLabel;
import com.equation.cashierll.deco.BlinkingPanel;
import com.equation.cashierll.deco.ContainerBorders;
import com.equation.cashierll.fonts.AllFonts;
import com.equation.cashierll.helpers.DoubleForm;
import com.equation.cashierll.helpers.IconImage;
import com.equation.cashierll.helpers.SetDateCreated;
import com.equation.cashierll.helpers.TableColumnResizer;
import com.equation.cashierll.helpers.TableRenderer;
import com.equation.cashierll.helpers.TableRowResizer;

/**
 *
 * @author Wellington
 */

@SuppressWarnings("serial")
public class ReceiptListDialog extends JDialog {

	// instance variables
	ResultSet rs, rs1;
	Statement stm, stmt;
	private double sum = 0;
	JTextArea display;
	JComponent itemscomponent;

	// constructor for the class
	public ReceiptListDialog(Statement stm, Statement stmt, ResultSet rs, ResultSet rs1, JComponent itemscomponent) {
		this.rs = rs;
		this.stm = stm;
		this.rs1 = rs1;
		this.stmt = stmt;
		this.itemscomponent = itemscomponent;
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent evvt) {
				setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 5, 5));
			}
		});
		this.setLayout(new BorderLayout());
		this.getContentPane().setBackground(Color.WHITE);
		this.setUndecorated(true);
		this.getRootPane().setBorder(ContainerBorders.createBevelBorder());
		this.getContentPane().add(receiptsPanel(), BorderLayout.CENTER);

		this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
		this.getRootPane().getActionMap().put("Cancel", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				fadeOut();
			}
		});

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(500, screen.height);
		Dimension d = this.getSize();
		int x = (screen.width - d.width), y = 0;
		this.setLocation(x, y);
		this.setAlwaysOnTop(true);
	}

	// create a panel for the table and its associated defaulttable model
	JPanel receiptsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setOpaque(false);
		String today = new SetDateCreated().getDate();
		String query = "SELECT * FROM receipt_amount WHERE date = '" + today + "'";
		try {
			rs = stm.executeQuery(query);
			rs.last();
			int rows = rs.getRow(), i = 0;
			if (rows > 0) {
				System.out.println(rows);
				Object[][] data = new Object[rows][ReceiptsHeader.header.length];
				String query1 = "SELECT * FROM receipt_amount WHERE date = '" + today + "'";
				rs1 = stmt.executeQuery(query1);
				while (rs1.next()) {
					int receiptno = rs1.getInt(1);
					double totalcost = rs1.getDouble(2);
					double given = rs1.getDouble(3);
					double change = rs1.getDouble(4);
					double collected = rs1.getDouble(5);
					String date = rs1.getString(6);
					String time = rs1.getString(7);

					data[i][0] = receiptno;
					data[i][1] = totalcost;
					data[i][2] = given;
					data[i][3] = change;
					data[i][4] = collected;
					data[i][5] = date;
					data[i][6] = time;

					sum += totalcost;
					i++;
				}
				// defiining the table data container
				DefaultTableModel model = new DefaultTableModel(data, ReceiptsHeader.header);
				JTable table = new JTable(model) {
					@Override
					public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
						Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
						// even index, selected or not selected
						comp.setFont(new java.awt.Font("", Font.PLAIN, 18));
						if (Index_row % 2 == 0 && !isCellSelected(Index_row, Index_col)) {
							comp.setBackground(new Color(235, 235, 235));
						} else {
							comp.setBackground(new Color(204, 204, 204));
						}
						if (isCellSelected(Index_row, Index_col)) {
							comp.setBackground(new Color(0.5f, 0.5f, 1f));
						}
						if (Index_row == 0 && Index_col == 0) {
							comp.setFont(new java.awt.Font("", Font.BOLD, 18));
							comp.setForeground(new Color(0.3f, 0.2f, 1f));
						}
						if (Index_row == 1 && Index_col == 0) {
							comp.setFont(new java.awt.Font("", Font.BOLD, 18));
							comp.setForeground(new Color(0.1f, 0.1f, 1f));
						}
						if (Index_row > 1 && Index_col == 0)
							comp.setFont(new java.awt.Font("", Font.BOLD, 15));

						return comp;
					}

					// setting the tooltip text for every table cell
					public String getToolTipText(MouseEvent e) {
						String tip = null;
						java.awt.Point p = e.getPoint();
						int rowIndex = rowAtPoint(p);
						int colIndex = columnAtPoint(p);

						try {
							tip = getValueAt(rowIndex, colIndex).toString();
						} catch (RuntimeException e1) {
							// catch null pointer exception if mouse is over an
							// empty line
						}

						return tip;
					}

					@Override
					public boolean isCellEditable(int rowIndex, int colIndex) {
						return false;
					}
				};
				// rendering table cells
				TableRenderer.setJTableColumnsWidth(table, 480, 20, 20, 20, 10, 10, 10, 10);
				table.setRowHeight(30);
				table.setAutoCreateRowSorter(true);
				table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				new TableColumnResizer(table);
				new TableRowResizer(table);
				table.setShowGrid(false);
				display = new JTextArea();
				display.setLineWrap(true);
				display.setWrapStyleWord(true);
				display.setFont(new Font(AllFonts.getTimesNewRoman(), Font.PLAIN, 30));
				display.setBackground(Color.BLACK);
				display.setForeground(Color.WHITE);
				display.setEditable(false);

				JScrollPane scroll = new JScrollPane();
				scroll.setOpaque(false);
				scroll.setViewportView(table);
				panel.add(scroll, BorderLayout.CENTER);
				JLabel toplabel = new BlinkingLabel("Total Sales $" + new DoubleForm().form(sum));
				toplabel.setFont(new Font(AllFonts.getTimesNewRoman(), Font.BOLD + Font.ITALIC, 30));
				JButton close = new JButton(new ImageIcon(new IconImage().createCloseImage()));
				close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				close.setBackground(Color.CYAN);
				close.addActionListener((e) -> {
					fadeOut();
				});
				Box box = Box.createHorizontalBox();
				box.add(toplabel);
				box.add(Box.createHorizontalGlue());
				box.add(close);
				panel.add(box, BorderLayout.NORTH);

				PrintPopup popup = new PrintPopup(table, stm, stmt, rs, rs1, this, display, panel);
				table.setComponentPopupMenu(popup);

			} else {
				// display a blinking panel message when the list is empty here
				panel.add(new BlinkingPanel("Empty List"), BorderLayout.CENTER);
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return panel;
	}

	// animating the dialog when closing and disposing its constructor
	public void fadeOut() {
		// all that should occur inside the event dispatch thread EDT
		EventQueue.invokeLater(() -> {
			new AnimateDialog().fadeOut(this, 50);
		});
		EventQueue.invokeLater(() -> {
			itemscomponent.requestFocusInWindow();
		});
	}
}