/**
 * 
 */
package br.srv.full.serviceLocator.annotations.processing;

import br.srv.full.ConfigManager;
import br.srv.full.serviceLocator.annotations.ServiceLocator;

/**
 * @author Carlos Delfino
 *
 */
public class TestServiceLocatorAnnotation {

	@ServiceLocator("ConfigManager")
	ConfigManager config;
}
