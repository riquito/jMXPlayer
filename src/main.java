package src;

import src.View.MainWindow;
import src.View.Window;

public class main {
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				Window window = new MainWindow();
				window.render();
			}
		});
	}
}
