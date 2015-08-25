/**
 * 
 */
package br.srv.full.zipper.jsf.navigationHandler;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Carlos Delfino
 * 
 */
public class NavigationHandlerFullServiceVirtual extends NavigationHandler {

	static private final Log slog = LogFactory
			.getLog(NavigationHandlerFullServiceVirtual.class);
	static {
		slog.info("referenciado");
	}
	private final Log log = LogFactory.getLog(getClass());
	private NavigationHandler delegate;
	{
		log.info("Criado");
	}

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private NavigationHandlerFullServiceVirtual() {
		log.info("Created NavigationHandler instance ");

		ApplicationFactory l_applicationFactory = (ApplicationFactory) FactoryFinder
				.getFactory("javax.faces.application.ApplicationFactory");
		Application l_aplication = l_applicationFactory.getApplication();
		delegate = l_aplication.getNavigationHandler();

	}
	
	public NavigationHandlerFullServiceVirtual(NavigationHandler p_delegate){
		delegate = p_delegate;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.application.NavigationHandler#handleNavigation(javax.faces.context.FacesContext,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void handleNavigation(FacesContext p_facescontext,
			String p_fromAction, String p_outcome) {
		log.info("Tratando Navegação!");
		log.info(p_facescontext);
		log.info("From Action: " + p_fromAction);
		log.info("OutCome: " + p_outcome);
		delegate.handleNavigation(p_facescontext, p_fromAction, p_outcome);
	}

	
}
