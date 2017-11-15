/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equation.cashierll.helpers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Wellington
 */
public class TextValidator extends KeyAdapter {
	public TextValidator() {
		return;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char character = e.getKeyChar();
		if (!(Character.isDigit(character) || (character == KeyEvent.VK_BACK_SPACE) || (character == KeyEvent.VK_DELETE)
				|| (character == KeyEvent.VK_ENTER) || (character == KeyEvent.VK_PERIOD))) {
			java.awt.Toolkit.getDefaultToolkit().beep();
			e.consume();
		}
	}
}