package ui;

import javax.swing.*;
import java.awt.*;

class UIUtils {

	static final Color BG_DARK     = new Color(245, 241, 255);
	static final Color BG_PANEL    = new Color(250, 248, 255);
	static final Color COLOR_ORO   = new Color(80, 45, 120);
	static final Color COLOR_VERDE = new Color(40, 130, 70);
	static final Color[] COLORES_CABALLOS = {
		new Color(231,  76,  60),
		new Color( 52, 152, 219),
		new Color(155,  89, 182),
		new Color(243, 156,  18)
	};

	static JButton buildButton(String text, Color bg) {
		JButton btn = new JButton(text);
		btn.setBackground(bg);
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("SansSerif", Font.BOLD, 14));
		btn.setFocusPainted(false);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return btn;
	}

	static JLabel fieldLabel(String text) {
		JLabel lbl = new JLabel(text);
		lbl.setForeground(new Color(60, 60, 60));
		lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
		return lbl;
	}

	static JLabel resultLabel(String text, Color color, int size) {
		JLabel lbl = new JLabel(text, SwingConstants.CENTER);
		lbl.setForeground(color);
		lbl.setFont(new Font("SansSerif", Font.BOLD, size));
		lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		return lbl;
	}

	static void stylizeField(JTextField f) {
		f.setFont(new Font("SansSerif", Font.PLAIN, 14));
		f.setAlignmentX(Component.LEFT_ALIGNMENT);
	}
}
