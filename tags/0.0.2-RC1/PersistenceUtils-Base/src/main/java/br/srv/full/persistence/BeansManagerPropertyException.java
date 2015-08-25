/**
 * 
 */
package br.srv.full.persistence;

/**
 * @author Delfino
 * 
 */
public class BeansManagerPropertyException extends PersistBeansException {

	private String propertyName;

	/**
	 * @param p_e
	 */
	public BeansManagerPropertyException(Exception p_e) {
		super(p_e);
	}

	/**
	 * @param p_e
	 * @param p_propertyName
	 */
	public BeansManagerPropertyException(Exception p_e, String p_propertyName) {
		super(p_e);
		propertyName = p_propertyName;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

}
