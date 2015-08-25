/**
 * 
 */
package br.srv.full.base;

/**
 * @author Delfino
 * 
 */
public class NotImplementedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7517473276077204581L;

	/**
	 * @param _string
	 */
	public NotImplementedException(String _string) {
		super(_string);
	}

	public NotImplementedException() {
		super("Método Não implementado");
	}

}
