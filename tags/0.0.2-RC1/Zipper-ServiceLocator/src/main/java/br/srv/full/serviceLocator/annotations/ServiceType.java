/**
 * 
 */
package br.srv.full.serviceLocator.annotations;

/**
 * @author Delfino
 * 
 */
public enum ServiceType {
	/**
	 * Baseado no EJB, deve usar um ejb Remoto
	 */
	REMOTE("remote"), 
	/**
	 * Baseado no EJB, deve ser usado ejb Local
	 */
	LOCAL("local"), 
	/**
	 * Baseado no arquivo de Propriedades.
	 * normalmente usa o arquivo implementations.properties
	 * 
	 */
	Properties("properties");

	private String name;

	private ServiceType(String p_name) {
		name = p_name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

}
