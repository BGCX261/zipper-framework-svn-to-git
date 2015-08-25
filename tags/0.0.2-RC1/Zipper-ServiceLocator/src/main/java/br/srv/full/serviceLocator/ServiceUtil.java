/**
 * 
 */
package br.srv.full.serviceLocator;

import java.lang.annotation.Annotation;

import javax.ejb.Remote;

/**
 * @author Delfino
 * 
 */
public final class ServiceUtil {

	@SuppressWarnings("unchecked")
	public static boolean isEJBRemoteAnnotated(Class p_class) {
		return isAnnotated(p_class, Remote.class);
	}

	@SuppressWarnings("unchecked")
	public static boolean isEJBLocalAnnotated(Class p_class) {
		return isAnnotated(p_class, Remote.class);
	}

	/**
	 * @param p_class
	 * @param p_annotClass
	 * @return
	 */
	private static boolean isAnnotated(Class<Remote> p_class, Class<Remote> p_annotClass) {
		Annotation l_annot = p_class.getAnnotation(p_annotClass);
		if (l_annot != null && l_annot instanceof Remote) {
			return true;
		}
		return false;
	}
}
