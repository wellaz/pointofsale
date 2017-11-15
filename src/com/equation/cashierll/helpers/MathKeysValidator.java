package com.equation.cashierll.helpers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.equation.cashierll.audio.Click;

/**
 *
 * @author Wellington
 */
public class MathKeysValidator extends KeyAdapter {
	public MathKeysValidator() {
		return;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char character = e.getKeyChar();
		if (!(Character.isDigit(character) || (character == KeyEvent.VK_BACK_SPACE) || (character == KeyEvent.VK_DELETE)
				|| (character == KeyEvent.VK_ENTER) || (character == KeyEvent.VK_PERIOD)
				|| (character == KeyEvent.VK_ASTERISK) || (character == KeyEvent.VK_BRACELEFT)
				|| (character == KeyEvent.VK_BRACERIGHT) || (character == KeyEvent.VK_BRACELEFT)
				|| (character == KeyEvent.VK_BRACERIGHT) || (character == KeyEvent.VK_PLUS)
				|| (character == KeyEvent.VK_MINUS) || (character == KeyEvent.VK_SHIFT)
				|| (character == KeyEvent.VK_SHIFT))) {
			e.consume();
			java.awt.Toolkit.getDefaultToolkit().beep();
		} else {
			new Click().tabPlay();
		}
	}
}