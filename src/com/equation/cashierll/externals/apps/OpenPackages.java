/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equation.cashierll.externals.apps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.equation.cashierll.deco.TranslucentJPanel1;

/**
 *
 * @author Wellington
 */

@SuppressWarnings("serial")
public class OpenPackages extends JPanel {

	String[] text = { "<html><p>Start<br><b>Microsoft Word</b></p></html>",
			"<html><p>Start<br><b>Microsoft Excel</b></p></html>",
			"<html><p>Start<br><b>Microsoft Access</b></p></html>",
			"<html><p>Start<br><b>Microsoft Power Point</b></p></html>",
			"<html><p>Start<br><b>Microsoft Publisher</b></p></html>",
			"<html><p>Start<br><b>Microsoft OneNote</b></p></html>",
			"<html><p>Start<br><b>Microsoft Outlook</b></p></html>" };
	JButton[] buttons;

	public OpenPackages() {
		JPanel pann = new TranslucentJPanel1(Color.BLACK);
		pann.setLayout(new GridLayout(7, 1, 0, 7));
		this.setLayout(new BorderLayout());

		buttons = new JButton[text.length];
		for (int i = 0; i < text.length; i++) {
			buttons[i] = new JButton(text[i]);
			buttons[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			buttons[i].setBackground(new Color(10, 70, 90));
			buttons[i].setForeground(Color.WHITE);
			pann.add(buttons[i]);
		}
		this.add(pann, BorderLayout.CENTER);

		buttons[0].addActionListener((ActionEvent event) -> {
			Worker w = new Worker();
			w.execute();
			MsOffice.msWord();
		});
		buttons[1].addActionListener((ActionEvent event) -> {
			Worker w = new Worker();
			w.execute();
			MsOffice.msExcel();

		});
		buttons[2].addActionListener((ActionEvent event) -> {
			Worker w = new Worker();
			w.execute();
			MsOffice.msAccess();

		});
		buttons[3].addActionListener((ActionEvent event) -> {
			Worker w = new Worker();
			w.execute();
			MsOffice.mspowerPoint();
		});
		buttons[4].addActionListener((ActionEvent event) -> {
			Worker w = new Worker();
			w.execute();
			MsOffice.msLync();
		});
		buttons[5].addActionListener((ActionEvent event) -> {
			Worker w = new Worker();
			w.execute();
			MsOffice.msOnenote();
		});
		buttons[6].addActionListener((ActionEvent event) -> {
			Worker w = new Worker();
			w.execute();
			MsOffice.msOutLook();
		});

	}

	public class Worker extends SwingWorker<Void, Void> {
		JDialog dialog;
		JProgressBar prog;
		JButton hider;
		JLabel waitlbl;

		public Worker() {

			dialog = new JDialog();
			dialog.setLayout(new BorderLayout());
			prog = new JProgressBar();
			dialog.setUndecorated(true);
			hider = new JButton("Run in Background");
			hider.addActionListener((ActionEvent event) -> {
				dialog.setVisible(false);
			});
			waitlbl = new JLabel("Processing....");
			dialog.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent evvt) {
					dialog.setShape(new RoundRectangle2D.Double(0, 0, dialog.getWidth(), dialog.getHeight(), 5, 5));
				}
			});
			Box box = Box.createVerticalBox();
			box.add(waitlbl);
			box.add(prog);
			box.add(hider);
			dialog.getContentPane().setBackground(new Color(0.5f, 0.5f, 1f));
			dialog.getContentPane().add(box, BorderLayout.CENTER);
			dialog.setSize(300, 100);
			Dimension d = dialog.getSize(), screen = Toolkit.getDefaultToolkit().getScreenSize();
			int a = (screen.width - d.width) / 2, c = (screen.height - d.height) / 2;
			dialog.setLocation(a, c);
		}

		@Override
		protected Void doInBackground() throws Exception {
			prog.setIndeterminate(true);
			dialog.setVisible(true);
			Thread.sleep(3000);
			return null;
		}

		@Override
		public void done() {
			prog.setIndeterminate(false);
			dialog.dispose();
		}
	}
}
