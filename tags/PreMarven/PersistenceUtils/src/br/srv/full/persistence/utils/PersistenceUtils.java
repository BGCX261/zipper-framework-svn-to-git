/**
 * 
 */
package br.srv.full.persistence.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.srv.full.persistence.BeansManagerException;
import br.srv.full.persistence.RefreshBeansException;
import br.srv.full.virtual.bean.base.EntityBean;

/**
 * @author Delfino
 * 
 */
public class PersistenceUtils {
	private final static Log log = LogFactory.getLog(PersistenceUtils.class);

	static synchronized public <bean> void validatePersistenceBean(bean p_bean) throws BeansManagerException {

		if (p_bean == null)
			throw new BeansManagerException("O Bean informado náo pode estar null");

		if (p_bean instanceof EntityBean) {
			EntityBean p_entity = (EntityBean) p_bean;
			if (p_entity.getId() == null)
				throw new BeansManagerException("O bean de entidade informado não tem o ID definido!");
		} else {
			try {
				Method l_method = p_bean.getClass().getMethod("getId");
				if (l_method.invoke(p_bean) == null)
					throw new BeansManagerException("O bean informado náo tem o ID definido!");
			} catch (Exception e) {
				log.warn("Náo é possivel identificar se este bean pode ser refrescado", e);
				// TODO aqui deve pesquisar por anotações.
			}

		}
	}

	static synchronized public <bean> boolean isCascate(bean p_bean, String l_property, CascadeType... p_cascadeTypes) {
		CascadeType[] l_cascate = null;
		Class<? extends Object> l_class = p_bean.getClass();
		try {
			Field l_field = l_class.getField(l_property);
			l_cascate = getCascate(l_field);
			if (l_cascate == null || l_cascate.length == 0) {
				Method l_method = l_class.getMethod(l_property, null);
				l_cascate = getCascate(l_method);
			}
		} catch (Exception e) {//
		} finally {

			if (l_cascate != null || l_cascate.length > 0) {
				for (CascadeType l_cascadeType : l_cascate) {
					if (p_cascadeTypes != null || p_cascadeTypes.length > 0)
						for (CascadeType l_cascadeTypeUser : p_cascadeTypes) {
							if (l_cascadeType.equals(l_cascadeTypeUser))
								return true;
						}
					else
						// basta existir um cascate para indicar que está ativo!
						return true;

				}
			}
		}
		return false;
	}

	/**
	 * @param p_field
	 * @return
	 */
	private static CascadeType[] getCascate(Field p_field) {
		ManyToMany l_manyToMany = p_field.getAnnotation(ManyToMany.class);
		CascadeType[] l_cascate = null;
		if (l_manyToMany != null) {
			l_cascate = l_manyToMany.cascade();
		} else {
			ManyToOne l_manyToOne = p_field.getAnnotation(ManyToOne.class);
			if (l_manyToOne != null) {
				l_cascate = l_manyToOne.cascade();
			} else {
				OneToMany l_oneToMany = p_field.getAnnotation(OneToMany.class);
				if (l_oneToMany != null) {
					l_cascate = l_oneToMany.cascade();
				} else {
					OneToOne l_oneToOne = p_field.getAnnotation(OneToOne.class);
					if (l_oneToOne != null) {
						l_cascate = l_oneToOne.cascade();
					}
				}
			}
		}
		return l_cascate;
	}

	/**
	 * Busca o tipo de cascateamento configurado para o metodo informado.
	 * 
	 * @param p_method
	 * @return
	 */
	private static CascadeType[] getCascate(Method p_method) {
		ManyToMany l_manyToMany = p_method.getAnnotation(ManyToMany.class);
		CascadeType[] l_cascate = null;
		if (l_manyToMany != null) {
			l_cascate = l_manyToMany.cascade();
		} else {
			ManyToOne l_manyToOne = p_method.getAnnotation(ManyToOne.class);
			if (l_manyToOne != null) {
				l_cascate = l_manyToOne.cascade();
			} else {
				OneToMany l_oneToMany = p_method.getAnnotation(OneToMany.class);
				if (l_oneToMany != null) {
					l_cascate = l_oneToMany.cascade();
				} else {
					OneToOne l_oneToOne = p_method.getAnnotation(OneToOne.class);
					if (l_oneToOne != null) {
						l_cascate = l_oneToOne.cascade();
					}
				}
			}
		}
		return l_cascate;
	}
}
