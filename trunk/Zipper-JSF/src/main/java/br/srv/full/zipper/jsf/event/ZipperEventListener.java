/**
 * 
 */
package br.srv.full.zipper.jsf.event;

import java.util.EventListener;

/**
 * @author Carlos Delfino
 * 
 */
public interface ZipperEventListener extends EventListener {

	/**
	 * @param p_loginEvent
	 */
	void processEvent(ZipperEvent p_loginEvent);

}
