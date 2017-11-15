package com.equation.cashierll.helpers;

import java.io.File;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;

/**
 *
 * @author Wellington
 */
public class MyNativeFileView extends FileView {
	public MyNativeFileView() {
		super();
		return;
	}

	public Icon getIcon(File f) {
		FileSystemView view = FileSystemView.getFileSystemView();
		return view.getSystemIcon(f);
	}
}