package dcll.groupe1.moox.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import dcll.groupe1.moox.GUI.Enum.State;
import dcll.groupe1.moox.GUI.Parse;



@SuppressWarnings("serial")
public class GUI extends JFrame{
	
	private JButton ouvrir;
    private JButton convert;
    private JButton url;
    private JTextField status;
    private JPanel panel;
    
    private State state;
    private String lastClick;
    
    public GUI(){
    	initComponents();
    	init();
    }
    
    private void initComponents(){
    	
    	ouvrir = new JButton();
        convert = new JButton();
        url = new JButton();
        status = new JTextField();
        panel = new JPanel();
        
        this.setTitle("JSON XML Converter");
        this.setSize(350, 150);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        
        status.setText("Choose a file ...");
        status.setPreferredSize(new Dimension(300,30));
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
    
    private void init(){
    	this.state = Enum.State.E1;
    	activateButton(state);
    }
    
    private void activateButton(State s){
    	switch (s){
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
    
    private void selectionFichier() {
    	JFileChooser chooser = new JFileChooser();//création dun nouveau filechosser
    	chooser.setApproveButtonText("Choix du fichier..."); //intitulé du bouton
    	this.lastClick = "file";
    	FileFilter ff = new FileFilter() {
			
			@Override
			public String getDescription() {
				return "*.json, *.xml";
			}
			
			@Override
			public boolean accept(File f) {
				return (f.getName().endsWith(".json") || f.getName().endsWith(".xml") || f.isDirectory());
			}
		};
		chooser.setFileFilter(ff);
		
    	if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
    		status.setText(chooser.getSelectedFile().getPath()); //si un fichier est selectionné, récupérer le fichier puis sont path et l'afficher dans le champs de texte
    		status.setForeground(Color.BLACK);
    	}
	}
    
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

	private void selectionUrl() {
		this.lastClick = "URL";
		this.status.setText("Entrez l'URL");
		this.status.setEditable(true);
		this.status.setForeground(Color.BLACK);
		this.status.setEnabled(true);
	}

	private void convertActionPerformed(java.awt.event.ActionEvent evt) {                                         
        
		switch (state) {
            case E1:
               // Interdit
                break;
            case E2:
            	Parse.parse(status.getText(), this,lastClick);
                this.state = Enum.State.E1;
                this.status.setText("Choose a file ...");
                activateButton(state);
                break;
        }
    }
}
