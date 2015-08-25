/**
 * 
 */
package br.srv.full.persistence;

/**
 * @author Delfino
 * 
 */
public class BeansManagerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4093837874638479703L;

	/**
	 * @param _t
	 */
	public BeansManagerException(Throwable _t) {
		super(_t);
	}

	/**
	 * @param p_string
	 */
	public BeansManagerException(String p_string) {
		super(p_string);
	}

	/**
	 * @param p_string
	 * @param p_e
	 */
	public BeansManagerException(String p_string, Exception p_e) {
		super(p_string, p_e);
	}
}
