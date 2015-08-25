/**
 * 
 */
package br.srv.full.zipper.configmanager;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Carlos Delfino
 * 
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for br.srv.full.zipper.configmanager");
		// $JUnit-BEGIN$
		suite.addTest(new ConfigManagerTest());
		
		// $JUnit-END$
		return suite;
	}

}
