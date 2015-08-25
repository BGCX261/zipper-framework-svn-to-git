/**
 * 
 */
package br.srv.full.zipper.jsf.event;

import java.util.EventObject;

/**
 * @author Carlos Delfino
 *
 */
public abstract class ZipperEvent extends EventObject{

	/**
	 * @param p_source
	 */
	public ZipperEvent(Object p_source) {
		super(p_source); 
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7083472360816372992L;

	/**
	 * @param p_userLogin
	 * @return
	 */
	public abstract boolean isEvent(String p_userLogin);

}
