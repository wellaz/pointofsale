package com.equation.cashierll.deco;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 *
 * @author Wellington
 */

public class RecordsLoader {

	public JPanel createLoader(JPanel panel) {
		CustomPanel customPanel = new CustomPanel();
		new Thread(() -> {
			try {
				for (int num = 0; num <= 100; num++) {
					customPanel.updateProgress(num);
					customPanel.repaint();
					Thread.sleep(50);
				}
			} catch (Exception ee) {
				ee.printStackTrace(System.err);
			}
			customPanel.removeAll();
			customPanel.setLayout(new BorderLayout());
			customPanel.add(panel, BorderLayout.CENTER);
			customPanel.revalidate();
		}).start();
		return customPanel;
	}
}