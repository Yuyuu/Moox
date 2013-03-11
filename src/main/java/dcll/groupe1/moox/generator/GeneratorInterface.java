/**
 * 
 */
package dcll.groupe1.moox.generator;

import dcll.groupe1.moox.domain.Tag;

/**
 * Interface for code generators.
 * 
 * @author SERIN Kévin
 * 
 */
public interface GeneratorInterface {

	/**
	 * Generates the code of the given tag.
	 * 
	 * @param root
	 *            The root tag.
	 * @return A string representing the code.
	 * @throws GeneratorException
	 *             If an error occurred during the generation.
	 */
	String generate(Tag root) throws GeneratorException;
}
