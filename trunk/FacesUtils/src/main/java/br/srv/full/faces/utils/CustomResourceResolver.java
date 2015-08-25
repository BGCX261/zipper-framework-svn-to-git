/**
 * 
 */
package br.srv.full.faces.utils;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.facelets.impl.DefaultResourceResolver;
import com.sun.facelets.impl.ResourceResolver;

/**
 * @author Carlos Delfino
 * 
 */
public class CustomResourceResolver extends DefaultResourceResolver implements ResourceResolver {

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public URL resolveUrl(String p_resource) {
		log.debug("Tentando encontrar: " + p_resource);

		URL l_resourceUrl = super.resolveUrl(p_resource);
		if (l_resourceUrl == null) {
			if (p_resource.startsWith("/")) {
				p_resource = p_resource.substring(1);
			}
			l_resourceUrl = getResource(p_resource);
			if (l_resourceUrl == null) { 
				log.warn("Resource não encontrad: " + p_resource);
			}
		}
		log.debug("Encontrado em: " + l_resourceUrl);
		return l_resourceUrl;
	}

	/**
	 * @param p_resource
	 * @return
	 */
	private URL getResource(String p_resource) {
		URL l_resourceUrl;
		Thread l_currentThread = Thread.currentThread();
		ClassLoader l_contextClassLoader = l_currentThread.getContextClassLoader();
		l_resourceUrl = l_contextClassLoader.getResource(p_resource);
		return l_resourceUrl;
	}

	public String toString() {
		return getClass().getName();
	}
}
