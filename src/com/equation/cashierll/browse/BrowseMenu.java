package com.equation.cashierll.browse;

import java.awt.event.ActionEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class BrowseMenu extends JMenu {

	public BrowseMenu() {
		this.setText("Browser");
		JMenuItem browse = new JMenuItem("Browse The Internet");
		this.add(browse);
		browse.addActionListener((ActionEvent event) -> {
			OpenBrowser.browse();
		});
	}
}
