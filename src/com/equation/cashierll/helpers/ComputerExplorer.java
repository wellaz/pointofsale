package com.equation.cashierll.helpers;

import java.io.File;

import javax.swing.JTree;

public class ComputerExplorer {
	
	public ComputerExplorer() {
		
	}
	
	public static JTree createExplorer() {
		
		
		JTree tree = new JTree();
		tree.setModel(new FileSystem(new File(System.getProperty("user.home"))));
		tree.setCellRenderer(new TransparentTree());

		return tree;
	}

}
