/**
 * 
 */
package br.srv.full.serviceLocator;

/**
 * @author Delfino
 * 
 */
public enum ServiceType {
	REMOTE("remote"), LOCAL("local");

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
