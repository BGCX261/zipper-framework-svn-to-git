/**
 * 
 */
package br.srv.full.zipper.configmanager;

import java.io.FileNotFoundException;
import java.io.IOException;

import br.srv.full.ConfigManager;
import junit.framework.TestCase;

/**
 * @author Carlos Delfino
 * 
 */
public class ConfigManagerTest extends TestCase {

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#getInstance()}.
	 * 
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public final void testGetInstance() throws FileNotFoundException, IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {

		ConfigManager l_config1 = ConfigManager.getInstance();
		ConfigManager l_config2 = ConfigManager.getInstance();
		assertEquals(l_config1, l_config2);
		assertTrue(l_config1 == l_config2);

		l_config1.dispose();
		l_config2.dispose();
	}

	public final void testDispose() throws FileNotFoundException, IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		ConfigManager l_config1 = ConfigManager.getInstance();
		
		l_config1.getProperty("12345");
		
		l_config1.dispose();
		try {
			l_config1.getProperty("123");
		} catch (Exception e) {
			return;
		}
		fail("dispose não funciona!");
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#getInstance(java.lang.Class)}.
	 */
	public final void testGetInstanceClassOfT() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#getInstance(java.lang.Class, boolean)}.
	 */
	public final void testGetInstanceClassOfTBoolean() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#getClassImplentation(java.lang.Class)}.
	 */
	public final void testGetClassImplentationClass() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#getClassImplentation(java.lang.String, java.lang.Class)}.
	 */
	public final void testGetClassImplentationStringClassOfQ() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#getClassImplentation(java.lang.String, java.lang.String)}.
	 */
	public final void testGetClassImplentationStringString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#getProperty(java.lang.String)}.
	 */
	public final void testGetProperty() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#setProperty(java.lang.String, java.lang.String)}.
	 */
	public final void testSetPropertyStringString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#setProperty(java.lang.String, java.lang.Object)}.
	 */
	public final void testSetPropertyStringObject() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#setProperty(java.lang.String, java.lang.Integer, boolean)}.
	 */
	public final void testSetPropertyStringIntegerBoolean() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#setPropertyOnFile(java.lang.String, java.lang.Object)}.
	 */
	public final void testSetPropertyOnFileStringObject() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#setPropertyOnFile(java.lang.String, java.lang.String)}.
	 */
	public final void testSetPropertyOnFileStringString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#getSequencialProperty(java.lang.String, int[])}.
	 */
	public final void testGetSequencialPropertyStringIntArray() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#getNumberProperty(java.lang.String, java.lang.Class)}.
	 */
	public final void testGetNumberPropertyStringClassOfnum() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#getNumberProperty(java.lang.String, java.lang.Number, java.lang.Class)}.
	 */
	public final void testGetNumberPropertyStringNumClassOfnum() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#getBooleanProperty(java.lang.String)}.
	 */
	public final void testGetBooleanPropertyString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#getBooleanProperty(java.lang.String, java.lang.Boolean)}.
	 */
	public final void testGetBooleanPropertyStringBoolean() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#getArrayProperty(java.lang.String)}.
	 */
	public final void testGetArrayPropertyString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#getArrayProperty(java.lang.String, java.lang.String)}.
	 */
	public final void testGetArrayPropertyStringString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link br.srv.full.ConfigManager#getSequencialProperty(java.lang.String, java.lang.String[])}.
	 */
	public final void testGetSequencialPropertyStringStringArray() {
		fail("Not yet implemented"); // TODO
	}

}
