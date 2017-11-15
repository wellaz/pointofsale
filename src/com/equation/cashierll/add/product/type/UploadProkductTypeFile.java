package com.equation.cashierll.add.product.type;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.equation.cashierll.deco.AnimateDialog;
import com.equation.cashierll.deco.TranslucentJPanel;
import com.equation.cashierll.helpers.MyNativeFileView;
import com.equation.cashierll.helpers.SetDateCreated;

/**
 *
 * @author Wellington
 */

@SuppressWarnings("serial")
public class UploadProkductTypeFile extends JPanel {
	String backUpPath;
	private JLabel path;
	Connection conn;
	PreparedStatement pstmt;
	JFrame frame;

	public UploadProkductTypeFile(Connection conn, PreparedStatement pstmt, JFrame frame) {
		this.frame = frame;
		this.conn = conn;
		this.pstmt = pstmt;
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		path = new JLabel();
		JButton upload = new JButton("Upload File");
		upload.addActionListener((e) -> {
			getBackUpPath();
			String file = getFile();
			if (!file.equals("")) {
				try {
					if (isValidFile(file)) {
						Worker w = new Worker();
						w.execute();
					} else {
						JOptionPane.showMessageDialog(frame, "The selected file is not of MIMETYPE CSV", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception ee) {
					ee.printStackTrace(System.err);
				}
			} else {
				return;
			}
		});
		JPanel pan = new JPanel(new BorderLayout());
		pan.setOpaque(false);
		pan.add(path, BorderLayout.CENTER);
		pan.add(upload, BorderLayout.EAST);
		this.add(pan, BorderLayout.NORTH);

	}

	public String getBackUpPath() {
		JFileChooser fc = null;
		if (fc == null) {
			fc = new JFileChooser();
			fc.setDialogTitle("Choose CSV File");
			fc.setFileView(new MyNativeFileView());
			fc.setAcceptAllFileFilterUsed(false);
			fc.setPreferredSize(new Dimension(500, 350));
		}
		int returnVal = fc.showDialog(frame, "Upload");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (file.exists())
				backUpPath = file.getAbsolutePath();
			else
				backUpPath = "";
		} else {
			backUpPath = "";
		}
		return backUpPath;
	}

	private boolean isValidFile(String file) {
		List<String> fileTypes = Arrays.asList(".csv");
		return fileTypes.stream().anyMatch(t -> file.endsWith(t));
	}

	public void upload() {
		String file = getFile();
		path.setText(file);
		SetDateCreated setdate = new SetDateCreated();
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String line;
			while ((line = bf.readLine()) != null) {
				String[] value = line.split(",");
				String date = setdate.getDate();
				String time = setdate.getTime();
				String year = setdate.getYear();
				String query = "INSERT INTO commonproducts(barcode_number,product_name,unit_price,date,time,year)VALUES('"
						+ value[0] + "','" + value[1] + "','" + value[2] + "','" + date + "','" + time + "','" + year
						+ "')";
				pstmt = conn.prepareStatement(query);
				pstmt.executeUpdate();
			}
			bf.close();
			JOptionPane.showMessageDialog(frame, "Upload completed ", "Upload Wizard", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ee) {
			ee.printStackTrace(System.err);
			JOptionPane.showMessageDialog(frame, "Error\n" + ee.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public class Worker extends SwingWorker<Void, Void> {
		JDialog dialog;
		JProgressBar prog;
		JButton hider;
		JLabel waitlbl;

		public Worker() {
			dialog = new JDialog((JFrame) null, true);
			dialog.setLayout(new BorderLayout());
			prog = new JProgressBar();
			dialog.setUndecorated(true);
			hider = new JButton("Run in Background");
			hider.addActionListener((ActionEvent event) -> {
				dialog.setVisible(false);
			});
			waitlbl = new JLabel("Backup in Progress....");
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
			dialog.getContentPane().setBackground(new Color(0.5f, 0.5f, 1f));
			JPanel pann = new TranslucentJPanel(Color.BLUE);
			pann.setLayout(new BorderLayout());
			pann.add(box, BorderLayout.CENTER);
			dialog.getContentPane().add(pann, BorderLayout.CENTER);
			dialog.setSize(300, 100);
			Dimension d = dialog.getSize(), screen = Toolkit.getDefaultToolkit().getScreenSize();
			int a = (screen.width - d.width) / 2, b = (screen.height - d.height) / 2;
			dialog.setLocation(a, b);
			prog.setIndeterminate(true);
			// dialog.setVisible(true);
			new AnimateDialog().fadeIn(dialog, 100);
		}

		@Override
		protected Void doInBackground() throws Exception {
			upload();
			return null;
		}

		@Override
		public void done() {
			prog.setIndeterminate(false);
			new AnimateDialog().fadeOut(dialog, 100);
		}
	}

	private String getFile() {
		return backUpPath;
	}
}