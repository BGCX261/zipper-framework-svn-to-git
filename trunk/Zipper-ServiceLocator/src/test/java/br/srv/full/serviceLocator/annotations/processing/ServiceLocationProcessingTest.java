/**
 * 
 */
package br.srv.full.serviceLocator.annotations.processing;

import junit.framework.Assert;

import org.junit.Test;


/**
 * @author Carlos Delfino
 *
 */ 
public class ServiceLocationProcessingTest {

	@Test
	public void test1(){
		Assert.assertNotNull(new TestServiceLocatorAnnotation());
	}
}
