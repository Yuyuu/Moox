package dcll.groupe1.moox;

import java.io.File;
import java.net.URI;

import dcll.groupe1.moox.parser.ParserException;
import dcll.groupe1.moox.parserImpl.Parser;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Parser pars = new Parser();
        File f = new File("MoodleXML.xml");
        URI uri = f.toURI();
        
        
        try {
			pars.parse(uri);
		} catch (ParserException e) {
			e.printStackTrace();
		}
        
        pars.affiche();
    }
}
