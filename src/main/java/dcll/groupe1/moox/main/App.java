package dcll.groupe1.moox.main;

import javax.swing.SwingUtilities;

import dcll.groupe1.moox.GUI.GUI;

public class App {
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new GUI();				
			}
			
		});
		
	}
}
