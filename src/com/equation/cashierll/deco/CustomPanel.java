package com.equation.cashierll.deco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 *
 * @author Wellington
 */

@SuppressWarnings("serial")
public class CustomPanel extends JPanel {
	int progress = 0;

	public CustomPanel() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
	}

	public void updateProgress(int progress_value) {
		progress = progress_value;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.translate(this.getWidth() / 2, this.getHeight() / 2);
		g2d.rotate(Math.toRadians(270));
		Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
		Ellipse2D circle = new Ellipse2D.Float(0, 0, 110, 110);
		arc.setFrameFromCenter(new Point(0, 0), new Point(120, 120));
		circle.setFrameFromCenter(new Point(0, 0), new Point(110, 110));
		arc.setAngleStart(1);
		arc.setAngleExtent(-progress * 3.6);
		g2d.setColor(Color.RED);
		g2d.draw(arc);
		g2d.fill(arc);
		g2d.setColor(Color.WHITE);
		g2d.draw(circle);
		g2d.fill(circle);
		g2d.setColor(Color.RED);
		g2d.rotate(Math.toRadians(90));
		g.setFont(new Font("Vardena", Font.PLAIN, 50));
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D r = fm.getStringBounds(progress + "%", g);
		int x = (0 - (int) r.getWidth()) / 2;
		int y = (0 - (int) r.getHeight()) / 2 + fm.getAscent();
		g2d.drawString(progress + "%", x, y);

	}

	/*
	 * public static void main(String[] args) { JFrame frame = new JFrame();
	 * frame.setLayout(new BorderLayout()); frame.setSize(600, 600); JButton b =
	 * new JButton("Start");
	 * 
	 * frame.getContentPane().add(b, BorderLayout.NORTH); CustomPanel
	 * customPanel = new CustomPanel(); frame.getContentPane().add(customPanel,
	 * BorderLayout.CENTER);
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * frame.setLocationByPlatform(true); frame.setVisible(true);
	 * b.addActionListener((e) -> { new Thread(() -> { try { for (int num = 0;
	 * num <= 100; num++) { customPanel.updateProgress(num);
	 * customPanel.repaint(); Thread.sleep(50); } } catch (Exception ee) {
	 * ee.printStackTrace(System.err); } try {
	 * Thread.sleep(UNDEFINED_CONDITION); } catch (Exception e1) { // TODO
	 * Auto-generated catch block e1.printStackTrace(); } System.out.println(
	 * "The thread has just stopped"); // System.exit(0); }).start(); }); }
	 */
}
