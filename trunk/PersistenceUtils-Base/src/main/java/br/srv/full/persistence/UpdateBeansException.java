/**
 * 
 */
package br.srv.full.persistence;

/**
 * @author Delfino
 * 
 */
public class UpdateBeansException extends BeansManagerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1583207380155029280L;

	/**
	 * @param _t
	 */
	public UpdateBeansException(Throwable _t) {
		super(_t);
	}

	/**
	 * @param p_string
	 */
	public UpdateBeansException(String p_string) {
		super(p_string);
	}

}
