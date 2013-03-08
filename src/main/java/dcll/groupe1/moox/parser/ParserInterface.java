/**
 * 
 */
package dcll.groupe1.moox.parser;

import java.net.URI;

import dcll.groupe1.moox.domain.Tag;

/**
 * Interface for implementations of parsers.
 * 
 * @author SERIN Kévin
 * 
 */
public interface ParserInterface {

	/**
	 * Parse a document.
	 * 
	 * @param uri
	 *            The URI of the document.
	 * @return The root tag of the document.
	 * @throws ParserException
	 *             If an error occurred during the parsing.
	 */
	public Tag parse(URI uri) throws ParserException;
}
