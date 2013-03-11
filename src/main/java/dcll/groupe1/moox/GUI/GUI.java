package dcll.groupe1.moox.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileFilter;

import dcll.groupe1.moox.GUI.Enum.State;
import dcll.groupe1.moox.GUI.Parse;

/**
 * The graphical user interface of the application.
 * 
 * @author ?
 */
@SuppressWarnings("serial")
public class GUI extends JFrame {

	/**
	 * Open button.
	 */
	private JButton ouvrir;
	
	/**
	 * Convert button.
	 */
	private JButton convert;
	
	/**
	 * URL button.
	 */
	private JButton url;
	
	/**
	 * File menu.
	 */
	private JMenu fichier;
	
	/**
	 * About menu.
	 */
	private JMenu interr;
	
	/**
	 * Exit menu item
	 */
	private JMenuItem quitter;
	
	/**
	 * About menu item.
	 */
	private JMenuItem apropos;
	
	/**
	 * Menu bar.
	 */
	private JMenuBar barre;
	
	/**
	 * URL/Path field.
	 */
	private JTextField status;
	
	/**
	 * Content pane.
	 */
	private JPanel panel;

	/**
	 * State in which the application is.
	 */
	private State state;
	
	/**
	 * Last component clicked.
	 */
	private String lastClick;

	public GUI() {
		initComponents();
		init();
	}

	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		// Initialisation du lookAndFeel
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.put("nimbusBlueGrey", new Color(190, 191, 193));
					UIManager.setLookAndFeel(info.getClassName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ouvrir = new JButton();
		convert = new JButton();
		url = new JButton();
		status = new JTextField();
		panel = new JPanel();
		interr = new JMenu("?");
		fichier = new JMenu("File");
		apropos = new JMenuItem();
		quitter = new JMenuItem();
		barre = new JMenuBar();

		this.setTitle("JSON XML Converter");
		this.setSize(350, 150);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		quitter.setText("Exit");
		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				quitterActionPerformed(evt);
			}
		});

		apropos.setText("About");
		apropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				aproposActionPerformed(evt);
			}
		});

		ouvrir.setText("Open");
		ouvrir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ouvrirActionPerformed(evt);
			}
		});

		url.setText("Url");
		url.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				urlActionPerformed(evt);
			}
		});

		convert.setText("Convert ...");
		convert.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				convertActionPerformed(evt);
			}
		});

		// Barre de menus
		fichier.add(quitter);
		interr.add(apropos);
		barre.add(fichier);
		barre.add(interr);
		panel.add(barre);
		setJMenuBar(barre);

		status.setText("Choose a file ...");
		status.setPreferredSize(new Dimension(300, 30));
		status.setForeground(Color.GRAY);
		status.setEditable(true);

		panel.add(status);
		panel.add(url);
		panel.add(ouvrir);
		panel.add(convert);

		this.setContentPane(panel);
		this.setResizable(false);
		this.setVisible(true);
	}

	/**
	 * Initializes the state of the application.
	 */
	private void init() {
		this.state = Enum.State.E1;
		activateButton(state);
	}

	/**
	 * Activates the buttons based on the state of the application
	 * 
	 * @param s
	 * 			the current state
	 */
	private void activateButton(State s) {
		switch (s) {
		case E1:
			ouvrir.setEnabled(true);
			url.setEnabled(true);
			convert.setEnabled(false);
			status.setEnabled(false);
			break;
		case E2:
			ouvrir.setEnabled(true);
			url.setEnabled(true);
			convert.setEnabled(true);
			status.setEnabled(true);
			break;
		}

	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Plus bas se trouvent toutes les methodes d'appel de composants de la
	 * frame comme
	 * 
	 * la méthode d'ouverture d'un fichier, le "A propos" etc ...
	 */

	/**
	 * ActionPerformed of the Open button.
	 * 
	 * @param evt
	 * 			the event
	 */
	private void ouvrirActionPerformed(java.awt.event.ActionEvent evt) {
		selectionFichier();
		switch (this.state) {
		case E1:
			this.state = Enum.State.E2;
			activateButton(state);
			break;
		case E2:
			this.state = Enum.State.E2;
			activateButton(state);
			break;
		}

	}

	/**
	 * Handles the file selection.
	 */
	private void selectionFichier() {
		JFileChooser chooser = new JFileChooser();// création dun nouveau
													// filechosser
		chooser.setApproveButtonText("Choix du fichier..."); // intitulé du
																// bouton
		this.lastClick = "file";
		FileFilter ff = new FileFilter() {

			@Override
			public String getDescription() {
				return "*.json, *.xml";
			}

			@Override
			public boolean accept(File f) {
				return (f.getName().endsWith(".json")
						|| f.getName().endsWith(".xml") || f.isDirectory());
			}
		};
		chooser.setFileFilter(ff);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			status.setText(chooser.getSelectedFile().getPath()); // si un
																	// fichier
																	// est
																	// selectionné,
																	// récupérer
																	// le
																	// fichier
																	// puis sont
																	// path et
																	// l'afficher
																	// dans le
																	// champs de
																	// texte
			status.setForeground(Color.BLACK);
		}
	}

	/**
	 * ActionPerformed of the About MenuItem.
	 * 
	 * @param evt
	 * 			the event
	 */
	private void aproposActionPerformed(java.awt.event.ActionEvent evt) {
		switch (this.state) {
		case E1:
			this.state = Enum.State.E2;
			activateButton(state);
			showAbout();
			break;
		case E2:
			this.state = Enum.State.E2;
			activateButton(state);
			showAbout();
			break;
		}

	}

	/**
	 * Displays the About Dialog
	 */
	private void showAbout() {
		JOptionPane.showMessageDialog(this.panel,
				"Made by Yuyuu Team\n Moox project",
				"About " + this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * ActionPerformed of the Exit MenuItem
	 * 
	 * @param evt
	 * 			the event
	 */
	private void quitterActionPerformed(java.awt.event.ActionEvent evt) {

		switch (this.state) {
		case E1:
			this.state = Enum.State.E1;
			activateButton(state);
			System.exit(NORMAL);
			break;
		case E2:
			this.state = Enum.State.E2;
			activateButton(state);
			System.exit(NORMAL);
			break;
		}

	}

	/**
	 * ActionPerformed of the URL button.
	 * 
	 * @param evt
	 * 			the event
	 */
	private void urlActionPerformed(java.awt.event.ActionEvent evt) {
		selectionUrl();
		switch (this.state) {
		case E1:
			this.state = Enum.State.E2;
			activateButton(state);
			break;
		case E2:
			this.state = Enum.State.E2;
			activateButton(state);
			break;
		}

	}

	/**
	 * Edits the GUI when an event occurs on the URL button.
	 */
	private void selectionUrl() {
		this.lastClick = "URL";
		this.status.setText("Entrez l'URL");
		this.status.setEditable(true);
		this.status.setForeground(Color.BLACK);
		this.status.setEnabled(true);
	}

	/**
	 * ActionPerformed of the Convert button
	 * 
	 * @param evt
	 * 			the event
	 */
	private void convertActionPerformed(java.awt.event.ActionEvent evt) {

		switch (state) {
		case E1:
			// Interdit
			break;
		case E2:
			Parse.parse(status.getText(), this, lastClick);
			this.state = Enum.State.E1;
			this.status.setText("Choose a file ...");
			activateButton(state);
			break;
		}
	}
}
