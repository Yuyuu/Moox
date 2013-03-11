package dcll.groupe1.moox.GUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import dcll.groupe1.moox.domain.Tag;
import dcll.groupe1.moox.generator.GeneratorException;
import dcll.groupe1.moox.generator.impl.JsonGenerator;
import dcll.groupe1.moox.generator.impl.XmlGenerator;
import dcll.groupe1.moox.parser.ParserException;
import dcll.groupe1.moox.parser.impl.JsonParser;
import dcll.groupe1.moox.parser.impl.XmlParser;

public class Parse {

	public static void parse(String s, JFrame j, String type) {

		File f;
		URI uri = null;
		URL url = null;
		String test = null;

		if (type.equals("file")) {
			f = new File(s);
			test = f.toString();
			uri = f.toURI();
		} else if (type.equals("URL")) {
			try {
				url = new URL(s);
			} catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(j, "This URL is not valid", "URL invalid", 1);
			}
			test = url.toString();

			try {
				uri = url.toURI();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

		}

		if (!((test.endsWith(".json")) || (test.endsWith(".xml")))) {
			JOptionPane.showMessageDialog(j, "File or URL invalid");
		}
		else {
			Tag t = new Tag();
			if (test.endsWith(".json")) {
				JsonParser parser = new JsonParser();
				try {
					t = parser.parse(uri);
				} catch (ParserException e) { e.printStackTrace(); }

				XmlGenerator xml = new XmlGenerator();
				try {
					ecritureFichier(xml.generate(t), ".xml");
				} catch (GeneratorException e) { e.printStackTrace(); }
				JOptionPane.showMessageDialog(j, "Conversion to XML ended.", "Finish", 1);
			}
			else {

				XmlParser parser = new XmlParser();
				try {
					t = parser.parse(uri);
				} catch (ParserException e) {
					e.printStackTrace();
				}

				JsonGenerator json = new JsonGenerator(true);
				try {
					ecritureFichier(json.generate(t), ".json");
				} catch (GeneratorException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(j, "Conversion to JSON ended.", "Finish", 1);
			}

		}
		
		
	}
	 
	private static void ecritureFichier(String texte, String format) {
		 
		 JFileChooser choix = new JFileChooser(); 
		 choix.setCurrentDirectory(new File("."));
		 if (choix.showDialog(choix, new String("ENREGISTRER")) == JFileChooser.APPROVE_OPTION) {
		 
			 String adressedufichier = (choix.getSelectedFile().toString() + format);
			 try {
					FileWriter fw = new FileWriter(adressedufichier, false);
					BufferedWriter output = new BufferedWriter(fw);
					output.write(texte);
					output.flush();
					output.close();
				} catch (IOException ioe) { 
					System.out.println("erreur : " + ioe);
				}
			 }
	 }
}
