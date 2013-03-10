package dcll.groupe1.moox;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AppTest.class, XmlParserTestCase.class,
		XmlGeneratorTestCase.class, 
		JsonGeneratorTestCase.class })
public class AllTests {

}
