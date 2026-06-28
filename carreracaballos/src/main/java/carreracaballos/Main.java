package carreracaballos;

import ui.CarreraFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		SwingUtilities.invokeLater(() -> {
			CarreraFrame frame = new CarreraFrame();
			frame.setVisible(true);
		});
	}
	
}