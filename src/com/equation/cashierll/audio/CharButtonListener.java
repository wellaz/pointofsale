package com.equation.cashierll.audio;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBoxMenuItem;

/**
 *
 * @author Wellington
 */
public class CharButtonListener extends MouseAdapter {

	Click click;
	JCheckBoxMenuItem tuner;

	public CharButtonListener(JCheckBoxMenuItem tuner) {
		this.tuner = tuner;
		click = new Click();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (tuner.getState()) {
			click.charButtonPlay();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (tuner.getState()) {
			// click.charButtonStop();
		}
	}
}
