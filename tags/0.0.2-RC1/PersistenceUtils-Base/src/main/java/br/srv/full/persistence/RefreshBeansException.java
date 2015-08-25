/**
 * 
 */
package br.srv.full.persistence;

/**
 * @author Delfino
 * 
 */
public class RefreshBeansException extends BeansManagerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1429808032559727935L;

	/**
	 * @param p__t
	 */
	public RefreshBeansException(Throwable p__t) {
		super(p__t);
	}

	/**
	 * @param p_string
	 */
	public RefreshBeansException(String p_string) {
		super(p_string);
	}

	/**
	 * @param p_string
	 * @param p_e
	 */
	public RefreshBeansException(String p_string, Exception p_e) {
		super(p_string, p_e);

	}

}
