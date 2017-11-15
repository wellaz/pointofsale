package com.equation.cashierll.troubleshoot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.equation.cashierll.validation.FormValidation;

public class Worker1 extends SwingWorker<Void, Void> {
	JDialog dialog, dialog1;
	JProgressBar prog;
	JButton hider;
	JLabel waitlbl;

	public Worker1(JDialog dialog1) {
		this.dialog1 = dialog1;
		dialog = new JDialog();
		dialog.setLayout(new BorderLayout());
		prog = new JProgressBar();
		dialog.setUndecorated(true);
		hider = new JButton("Run in Background");
		hider.addActionListener((ActionEvent event) -> {
			dialog.setVisible(false);
		});
		waitlbl = new JLabel("Processing...");
		waitlbl.setFont(new java.awt.Font("", Font.ITALIC, 11));
		waitlbl.setForeground(Color.WHITE);
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
		dialog.getContentPane().setBackground(Color.GREEN);
		dialog.getContentPane().add(box, BorderLayout.CENTER);
		dialog.setSize(300, 100);
		Dimension d = dialog.getSize(), screen = Toolkit.getDefaultToolkit().getScreenSize();
		int a = (screen.width - d.width) / 2, b = (screen.height - d.height) / 2;
		dialog.setLocation(a, b);
	}

	@Override
	protected Void doInBackground() throws Exception {
		prog.setIndeterminate(true);
		dialog.setVisible(true);
		startWampMysqlserver();
		return null;
	}

	@Override
	public void done() {
		prog.setIndeterminate(false);
		dialog.dispose();
		dialog1.dispose();
		new FormValidation().setVisible(true);
	}

	@SuppressWarnings("unused")
	public void startWampMysqlserver() {
		try {
			Process p = Runtime.getRuntime().exec("cmd.exe /C start wampmysqld");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}