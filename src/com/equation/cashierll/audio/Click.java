package com.equation.cashierll.audio;

import java.io.File;
import java.io.IOException;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.JOptionPane;

/**
 *
 * @author Wellington
 */

public class Click {
	Player bplayer, mathplayer, charplayer, dialogplayer, closeappplayer, hoverplayer, tabplayer, okplayer;
	File bfile, mathfile, charfile, dialogfile, closeappfile, hoverfile, tabfile, okfile;

	public Click() {
		bfile = new File(
				System.getProperty("user.home") + File.separatorChar + "resources" + File.separatorChar + "0.wav");
		mathfile = new File(System.getProperty("user.home") + File.separatorChar + "resources" + File.separatorChar
				+ "mathbutton.wav");
		charfile = new File(System.getProperty("user.home") + File.separatorChar + "resources" + File.separatorChar
				+ "charbutton.wav");
		dialogfile = new File(
				System.getProperty("user.home") + File.separatorChar + "resources" + File.separatorChar + "dialog.wav");
		closeappfile = new File(System.getProperty("user.home") + File.separatorChar + "resources" + File.separatorChar
				+ "closeapp.wav");
		hoverfile = new File(
				System.getProperty("user.home") + File.separatorChar + "resources" + File.separatorChar + "hover.wav");
		tabfile = new File(
				System.getProperty("user.home") + File.separatorChar + "resources" + File.separatorChar + "hittab.wav");
		okfile = new File(System.getProperty("user.home") + File.separatorChar + "resources" + File.separatorChar
				+ "okbutton.wav");
	}

	public void buttonPlay() {
		try {
			if (bfile.exists()) {
				bplayer = Manager.createRealizedPlayer(bfile.toURI().toURL());
				bplayer.start();
			} else {
				JOptionPane.showMessageDialog(null, "This environment is not favourable for CA$HIER to operate on!",
						"Do Not Honor", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} catch (IOException | NoPlayerException | CannotRealizeException e) {
			e.printStackTrace();
		}
	}

	public void buttonStop() {
		bplayer.stop();
	}

	public void mathOppPlay() {
		try {
			if (mathfile.exists()) {
				mathplayer = Manager.createRealizedPlayer(mathfile.toURI().toURL());
				mathplayer.start();
			} else {
				JOptionPane.showMessageDialog(null, "This environment is not favourable for CA$HIER to operate on!",
						"Do Not Honor", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} catch (IOException | NoPlayerException | CannotRealizeException e) {
			e.printStackTrace();
		}
	}

	public void mathOppStop() {
		mathplayer.stop();
	}

	public void charButtonPlay() {
		try {
			if (charfile.exists()) {
				charplayer = Manager.createRealizedPlayer(charfile.toURI().toURL());
				charplayer.start();
			} else {
				JOptionPane.showMessageDialog(null, "This environment is not favourable for CA$HIER to operate on!",
						"Do Not Honor", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} catch (IOException | NoPlayerException | CannotRealizeException e) {
			e.printStackTrace();
		}
	}

	public void charButtonStop() {
		charplayer.stop();
	}

	public void dialogPlayer() {
		try {
			if (dialogfile.exists()) {
				dialogplayer = Manager.createRealizedPlayer(dialogfile.toURI().toURL());
				dialogplayer.start();
			} else {
				JOptionPane.showMessageDialog(null, "This environment is not favourable for CA$HIER to operate on!",
						"Do Not Honor", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} catch (IOException | NoPlayerException | CannotRealizeException e) {
			e.printStackTrace();
		}
	}

	public void closeappPlay() {
		try {
			if (closeappfile.exists()) {
				closeappplayer = Manager.createRealizedPlayer(closeappfile.toURI().toURL());
				closeappplayer.start();
			} else {
				JOptionPane.showMessageDialog(null, "This environment is not favourable for CA$HIER to operate on!",
						"Do Not Honor", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} catch (IOException | NoPlayerException | CannotRealizeException e) {
			e.printStackTrace();
		}

	}

	public void hoverPlay() {
		try {
			if (hoverfile.exists()) {
				hoverplayer = Manager.createRealizedPlayer(hoverfile.toURI().toURL());
				hoverplayer.start();
			} else {
				JOptionPane.showMessageDialog(null, "This environment is not favourable for CA$HIER to operate on!",
						"Do Not Honor", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} catch (IOException | CannotRealizeException | NoPlayerException e) {
			e.printStackTrace();
		}
	}

	public void hoverStop() {
		hoverplayer.stop();
	}

	public void tabPlay() {
		try {
			if (tabfile.exists()) {
				tabplayer = Manager.createRealizedPlayer(tabfile.toURI().toURL());
				tabplayer.start();
			} else {
				JOptionPane.showMessageDialog(null, "This environment is not favourable for CA$HIER to operate on!",
						"Do Not Honor", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} catch (IOException | NoPlayerException | CannotRealizeException e) {
			e.printStackTrace();
		}
	}

	public void tabStop() {
		// tabplayer.stop();
	}

	public void okPlay() {
		try {
			if (okfile.exists()) {
				okplayer = Manager.createRealizedPlayer(okfile.toURI().toURL());
				okplayer.start();
			} else {
				JOptionPane.showMessageDialog(null, "This environment is not favourable for CA$HIER to operate on!",
						"Do Not Honor", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} catch (IOException | NoPlayerException | CannotRealizeException e) {
			e.printStackTrace();
		}
	}

	public void okStop() {
		okplayer.stop();
	}
}