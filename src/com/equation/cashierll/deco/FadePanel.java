package com.equation.cashierll.deco;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class FadePanel extends JPanel {
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private final int PREF_W = screen.width;
	private final int PREF_H = screen.height;
	private static final int COMP_SIZE = 200;
	private static final int RULE = AlphaComposite.SRC_OVER;
	private static final int TIMER_DELAY = 40;
	private Composite[] comps = new Composite[COMP_SIZE];
	private int compsIndex = 0;
	private Composite comp;
	public JFrame frame;

	public FadePanel(final JFrame frame) {
		setOpaque(false);
		for (int i = 0; i < comps.length; i++) {
			float alpha = (float) Math.cos(2 * Math.PI * i / COMP_SIZE);
			alpha += 1;
			alpha /= 2.0;
			comps[i] = AlphaComposite.getInstance(RULE, alpha);
		}
		comp = comps[compsIndex];
		setBackground(Color.LIGHT_GRAY);
		new Timer(TIMER_DELAY, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				compsIndex++;
				compsIndex %= COMP_SIZE;
				comp = comps[compsIndex];
				frame.repaint();
			}
		}).start();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setComposite(comp);
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight());
		super.paint(g2);
		g2.dispose();
	}

	public void createAndShowGui() {
		frame = new JFrame("Start");
		JLayeredPane layeredPane = new JLayeredPane();

		FadePanel fadePanel = new FadePanel(frame);
		fadePanel.setLocation(0, 0);

		fadePanel.setSize(fadePanel.getPreferredSize());

		JPanel bluePanel = new JPanel(new BorderLayout());
		bluePanel.setBackground(Color.blue);
		bluePanel.setSize(fadePanel.getPreferredSize());
		JLabel eq4 = new JLabel("<html><u>C A S H I E R II</u></html>", SwingConstants.CENTER);
		eq4.setFont(new Font("", Font.BOLD, 100));

		JLabel label = new JLabel("<html><i><u>P O S & Inventory Management Service</u></i></html>",
				SwingConstants.CENTER);
		label.setFont(label.getFont().deriveFont(Font.BOLD, 50f));
		bluePanel.add(eq4, BorderLayout.CENTER);
		bluePanel.add(label, BorderLayout.SOUTH);

		layeredPane.setPreferredSize(fadePanel.getPreferredSize());
		layeredPane.add(bluePanel, JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(fadePanel, JLayeredPane.PALETTE_LAYER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(layeredPane);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screen.width, screen.height);

		frame.setUndecorated(true);
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent evvt) {
				frame.setShape(new RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), 5, 5));
			}
		});

		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
		frame.getRootPane().getActionMap().put("Cancel", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				new AnimateJFrame().fadeOut(frame, 100);
				// closeAndQuit();
			}
		});

		Dimension d = frame.getSize();
		int x = (screen.width - d.width) / 2, y = (screen.height - d.height) / 2;
		frame.setLocation(x, y);

		// frame.setLocationByPlatform(true);
		new AnimateJFrame().fadeIn(frame, 100);
	}

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(() -> {
	 * //createAndShowGui(); }); }
	 */
}
