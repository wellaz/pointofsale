package com.equation.cashierll.cashiermodules;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Wellington
 */

@SuppressWarnings("serial")
public class FilterItems extends JComboBox<Object> {

	private List<String> array;
	private int currentCaretPosition = 0;
	JTextField numberfield, tendered;

	public FilterItems(List<String> array, JTextField numberfield, JTextField tendered) {
		super();
		this.numberfield = numberfield;
		this.tendered = tendered;
		this.array = array;
		this.setEditable(true);
		final JTextField textfield = (JTextField) this.getEditor().getEditorComponent();
		textfield.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				EventQueue.invokeLater(() -> {
					currentCaretPosition = textfield.getCaretPosition();
					if (textfield.getSelectedText() == null) {
						textfield.setCaretPosition(0);
						comboFilter(textfield.getText());
						textfield.setCaretPosition(currentCaretPosition);
					}
				});
				char character = ke.getKeyChar();
				if (character == KeyEvent.VK_ENTER) {
					EventQueue.invokeLater(() -> {
						numberfield.requestFocusInWindow();
						numberfield.setText("1");
					});
				} else if (character == KeyEvent.VK_EQUALS) {
					ke.consume();
					EventQueue.invokeLater(() -> {
						tendered.setEditable(true);
						tendered.requestFocusInWindow();
						tendered.setBackground(Color.WHITE);
						tendered.setForeground(Color.BLACK);
					});
				}
			}
		});
	}

	public void comboFilter(String enteredText) {
		List<String> filterArray = new ArrayList<String>();
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).toLowerCase().contains(enteredText.toLowerCase())) {
				filterArray.add(array.get(i));
			}
		}
		if (filterArray.size() > 0) {
			this.setModel(new DefaultComboBoxModel<Object>(filterArray.toArray()));
			this.setSelectedItem(enteredText);
			this.showPopup();
		} else {
			this.hidePopup();
		}
	}
}