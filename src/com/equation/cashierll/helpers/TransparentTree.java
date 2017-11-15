/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equation.cashierll.helpers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Wellington
 */
@SuppressWarnings("serial")
public class TransparentTree extends DefaultTreeCellRenderer {
	private final Color ALPHA_OF_ZERO = new Color(0, true);

	@Override
	public Color getBackgroundNonSelectionColor() {
		return ALPHA_OF_ZERO;
	}

	@Override
	public Color getBackgroundSelectionColor() {
		return Color.GREEN;
	}

	@Override
	public Color getBackground() {
		return ALPHA_OF_ZERO;
	}

	@Override
	public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel,
			final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
		final Component ret = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		
		//final DefaultMutableTreeNode node = ((DefaultMutableTreeNode) (value));
		
		this.setText(value.toString());
		return ret;
	}
}
