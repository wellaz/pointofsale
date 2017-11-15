package com.equation.cashierll.receipts;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JTable;

import com.equation.cashierll.developer.Developer;
import com.equation.cashierll.helpers.SetDateCreated;
import com.equation.cashierll.sign.in.Monitor;

/**
 *
 * @author Wellington
 */

public class MyPrintable implements Printable {
	JTable itemsTable;
	int receiptnumber;
	double totalcost, rendered, change;

	public MyPrintable(JTable table, int receiptnumber, double totalcost, double rendered, double change) {
		this.itemsTable = table;
		this.receiptnumber = receiptnumber;
		this.totalcost = totalcost;
		this.rendered = rendered;
		this.change = change;
	}

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss a";

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		int result = NO_SUCH_PAGE;
		if (pageIndex == 0) {
			Graphics2D g2d = (Graphics2D) graphics;

			// double width = pageFormat.getImageableWidth();
			// double height = pageFormat.getImageableHeight();
			g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
			Font font = new Font("Monospaced", Font.BOLD, 7);
			g2d.setFont(font);

			try {
				/*
				 * Draw Image* assume that printing reciept has logo on top that
				 * logo image is in .gif format .png also support image
				 * resolution is width 100px and height 50px image located in
				 * root--->image folder
				 */
				int x = 10; // print start at 100 on x axies
				int y = 10; // print start at 10 on y axies
				int imagewidth = 100;
				int imageheight = 30;
				BufferedImage read = ImageIO.read(this.getClass().getResource("images/ssuperstore.png"));
				// draw image
				g2d.drawImage(read, x, y, imagewidth, imageheight, null);
				g2d.drawLine(0, y + 60, 180, y + 60); // draw line
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				/* Draw Header */
				int y = 80;
				g2d.drawString(Monitor.shop_name.get(Monitor.shop_name.size() - 1), 10, y);
				g2d.drawString("Contact " + Monitor.shop_contact.get(Monitor.shop_contact.size() - 1), 30, y + 10);
				g2d.drawString("Receipt Number " + receiptnumber, 30, y + 20);
				// shift a line by adding 10 to y value

				g2d.drawString(now(), 10, y + 30); // print date
				g2d.drawString("Cashier :" + Monitor.fullname.get(Monitor.fullname.size() - 1).toUpperCase(), 10,
						y + 40);

				// g2d.drawString("TOTAL $" + totalcost, 10, y + 50);
				// g2d.drawString("Tendered $" + rendered, 10, y + 60);
				// g2d.drawString("Change $" + change, 10, y + 70);
				/* Draw Colums */
				g2d.drawLine(0, y + 50, 180, y + 50);
				g2d.drawString(Header.header[0], 0, y + 60);
				g2d.drawString(Header.header[1], 80, y + 60);
				g2d.drawString(Header.header[2], 100, y + 60);
				// g2d.drawLine(10, y + 100, 180, y + 100);

				int cH = 0;

				for (int i = 0; i < itemsTable.getRowCount(); i++) {
					String itemname = (String) itemsTable.getModel().getValueAt(i, 0);
					String quantity = itemsTable.getModel().getValueAt(i, 1).toString();
					String price = itemsTable.getModel().getValueAt(i, 2).toString();

					cH = (y + 70) + (10 * i); // shifting drawing line

					g2d.drawString(itemname, 0, cH);
					g2d.drawString(quantity, 80, cH);
					g2d.drawString(price, 100, cH);
				}
				g2d.drawLine(0, cH + 10, 180, cH + 10);
				g2d.drawString("TOTAL $" + totalcost, 70, cH + 20);
				g2d.drawString("Tendered $" + rendered, 70, cH + 30);
				g2d.drawString("Change $" + change, 70, cH + 40);

				/* Footer */
				font = new Font("Arial", Font.ITALIC + Font.PLAIN, 5); // changed
																		// font
				// size
				g2d.setFont(font);
				g2d.drawString("\u00A9 " + new SetDateCreated().getYear() + Developer.getDeveloperFirstName()
						+ " \u2192 CA$HIER II\u2122", 30, cH + 50);
				// end of the reciept
			} catch (Exception r) {
				r.printStackTrace();
			}
			result = PAGE_EXISTS;
		}
		return result;
	}

	public static String now() {
		// get current date and time as a String output
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
}