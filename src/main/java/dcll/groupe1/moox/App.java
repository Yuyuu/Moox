package dcll.groupe1.moox;

import java.io.File;
import java.net.URI;

import dcll.groupe1.moox.GUI.GUI;
import dcll.groupe1.moox.parser.ParserException;
import dcll.groupe1.moox.parserImpl.XmlParser;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        XmlParser pars = new XmlParser();
        File f = new File("MoodleXML.xml");
        URI uri = f.toURI();
        new GUI();
        
        try {
			pars.parse(uri);
		} catch (ParserException e) {
			e.printStackTrace();
		}
        
        pars.affiche();
    }
}
