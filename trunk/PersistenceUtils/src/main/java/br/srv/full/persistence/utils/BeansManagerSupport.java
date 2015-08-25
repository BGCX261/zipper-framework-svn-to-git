package br.srv.full.persistence.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.srv.full.base.NotImplementedException;
import br.srv.full.persistence.BeansManagerException;
import br.srv.full.persistence.BeansManagerPropertyException;
import br.srv.full.persistence.PersistBeansException;
import br.srv.full.persistence.RefreshBeansException;
import br.srv.full.persistence.UpdateBeansException; 
import br.srv.full.virtual.bean.base.QueryID;

/**
 * Acho que esta classe generica está ficando obsoleta ou deve ser revista em um
 * formato que permita mudar o UnitWork de forma mais generica. por exemplo
 * estarei em breve desenvolvimento o sistema de cobrança e este estará usando
 * um UnitWork diferente entáo náo será possivel usar esta classe, já que esta
 * somente aceita o virtualUW
 * 
 * @author Delfino
 * 
 */
public class BeansManagerSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7256862544184157977L;

	private final static Log slog = LogFactory.getLog(BeansManagerSupport.class);

	static {
		slog.info("class referenciada no contexto");
	}

	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * 
	 * @param <bean>
	 * @param <key>
	 * @param _beanClass
	 * @param _key
	 * @return
	 */
	public <bean, key> bean find(EntityManager em, Class<bean> _beanClass, key _key) {

		bean l_result = (bean) em.find(_beanClass, _key);

		return l_result;
	}

	/**
	 * @param <bean>
	 * @param <params>
	 * @param _beanClass
	 * @param _query
	 * @param _params
	 * @return
	 */
	public <bean, params> Object find(EntityManager em, Class<bean> _beanClass, QueryID _query, String[] p_fieldName,
			params[] p_params) {

		Query l_query = em.createNamedQuery(_query.toString());
		int i = 1;
		for (Object l_param : p_params) {
			l_query.setParameter(p_fieldName[i++], l_param);
		}

		return getResult(_beanClass, em, l_query);
	}

	public <bean, params> Object find(EntityManager em, Class<bean> _beanClass, String _query, params... _params) {

		log.info("Usando Query: " + _query);
		log.info("Usando Parametros: " + _params);

		Query l_query = em.createQuery(_query);
		int i = 0;
		if (_params != null)
			for (Object l_param : _params) {
				l_query.setParameter(++i, l_param);
			}

		return getResult(_beanClass, em, l_query);
	}

	public <bean, params> Object find(EntityManager em, Class<bean> _beanClass, QueryID _query, params... _params) {

		log.info("Find usando a Query: " + _query);

		Query l_query = em.createNamedQuery(_query.toString());
		int i = 0;
		for (Object l_param : _params) {
			l_query.setParameter(++i, l_param);
		}

		return getResult(_beanClass, em, l_query);
	}

	public <bean, params> Object find(EntityManager em, Class<bean> _beanClass, QueryID _query) {

		Query l_query = em.createNamedQuery(_query.toString());

		return getResult(_beanClass, em, l_query);
	}

	/**
	 * 
	 * @param <bean>
	 * @param <params>
	 * @param _beanClass
	 * @param _query
	 * @param _params
	 * @return
	 */
	public <bean, params> Object find(EntityManager em, Class<bean> p_class, QueryID p_query,
			Map<String, Object> p_params) {

		Query l_query = em.createNamedQuery(p_query.toString());

		for (String l_paramName : p_params.keySet()) {
			l_query.setParameter(l_paramName, p_params.get(l_paramName));
		}

		return getResult(p_class, em, l_query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.srv.full.persistence.BeansManager#find(java.lang .Class)
	 */
	public <bean> Object find(EntityManager em, Class<bean> p_class) {
		Query l_query = getQueryIdAllBeans(em, p_class);

		return getResult(p_class, em, l_query);
	}

	@SuppressWarnings("unchecked")
	public Query getQueryIdAllBeans(EntityManager l_em, Class p_class) {
		QueryID l_queryId;

		l_queryId = QueryID.getQueryIdAllBeans(p_class);

		Query l_query;
		if (l_queryId == null) {
			l_query = createQuerySelectAll(l_em, p_class);
		} else {
			l_query = l_em.createNamedQuery(l_queryId.toString());
		}
		return l_query;
	}

	/**
	 * @param p_l_em
	 * @param p_class
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Query createQuerySelectAll(EntityManager p_l_em, Class p_class) {
		Query l_query = p_l_em.createQuery("Select obj from " + p_class.getSimpleName() + " obj ");
		return l_query;
	}

	/**
	 * @param <bean>
	 * @param _beanClass
	 * @param l_em
	 * @param l_query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <bean> Object getResult(Class<bean> _beanClass, EntityManager l_em, Query l_query) {
		Object l_result = null;
		try {

			Object l_single = l_query.getSingleResult();
			l_result = (bean) l_single;
		} catch (NonUniqueResultException e) {
			log.warn("Tentando uma lista!");
			try {
				List l_list = l_query.getResultList();
				l_result = l_list;
			} catch (Exception e2) {
				log.warn("Ouve Outro Problema:", e2);
			}
		} catch (NoResultException e) {
			log.info("Esta query não retorna nada!");
		} catch (PersistenceException e) {
			log.warn("Problemas na Camada de persistência!", e);
		} catch (Exception e) {
			log.warn("Outro Problema: ", e);
		}
		return l_result;
	}

	/**
	 * @param <bean>
	 * @param _bean
	 * @throws BeansManagerException
	 * @throws BeansManagerPropertyException
	 */
	public <bean> bean persist(EntityManager em, bean _bean) throws PersistBeansException {

		try {
			log.info("Persistindo objeto: " + _bean);
			em.persist(_bean);
		} catch (PersistenceException e) {
			Throwable l_cause = e.getCause();
			Class<? extends Throwable> l_class = l_cause.getClass();
			if (l_class.getName().indexOf("PropertyValueException") > -1) {
				log.warn("problema ao persistir o objeto: " + _bean.getClass() + " com o conteúdo: " + _bean);

				Method l_method;
				String l_invoke;
				try {
					l_method = l_class.getMethod("getPropertyName");
					l_invoke = (String) l_method.invoke(l_cause);
				}catch (Exception e2) {
					throw new PersistBeansException(e2);
				}
				
				log.warn("Propriedade com problema: " + l_invoke);
				throw new BeansManagerPropertyException(e, l_invoke);
			} else
				throw new PersistBeansException(e);
		} catch (Exception e) {
			throw new PersistBeansException(e);
		}

		return _bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.srv.full.virtual.engine.persistence.BeansManager
	 *      #refresh(java.lang .Object)
	 */
	public <bean> bean refresh(EntityManager em, bean p_bean) throws RefreshBeansException {
		if (p_bean == null)
			throw new RefreshBeansException("O Bean informado náo pode estar null");

		bean l_bean;
		try {
			if (!em.contains(p_bean))
				l_bean = em.merge(p_bean);
			else
				l_bean = p_bean;
			em.refresh(l_bean);
			log.info("Refresh sucess");
		} catch (Exception e) {
			log.warn("Problemas ao refrescar o objeto: " + p_bean);
			throw new RefreshBeansException(e);
		}
		return l_bean;
	}

	/**
	 * Atualiza uma ou mais propriedades da entidade.
	 * 
	 * Como no processo de atualização é feito um merge com o contexto, é
	 * solicitado uma nova entidade, diante disto é impotante retorna-la para
	 * que seja usada a entidade atualizada, de qualquer forma a antiga é também
	 * atualizada, mas esta pode estar desconectada do contexto.
	 * 
	 * @see br.srv.full.virtual.engine.persistence.BeansManager
	 *      #refresh(java.lang .Object)
	 */
	public <bean> bean refresh(EntityManager em, final bean p_bean, String... p_properties)
			throws RefreshBeansException {
		if (p_properties == null || p_properties.length == 0)
			throw new RefreshBeansException("Deve ser informado pelo menos um nome de propriedade!");
		try {
			PersistenceUtils.validatePersistenceBean(p_bean);
		} catch (BeansManagerException e) {
			throw new RefreshBeansException(e);
		}

		bean l_bean;
		try {
			if (!em.contains(p_bean))
				l_bean = em.merge(p_bean);
			else
				l_bean = p_bean;

			em.refresh(l_bean);

			for (String l_string : p_properties) {
				Object l_tmp = PropertyUtils.getProperty(l_bean, l_string);
				log.info("Atualizando a propriedade: " + l_string);
				log.info("Resultado: " + l_tmp);

				if (l_bean != p_bean) {
					try {
						PropertyUtils.setProperty(p_bean, l_string, l_tmp);
					} finally {
						log.warn("Bean original não ter a propridade " + l_string + " atualizada!");
					}
				}
			}

			log.info("Refresh sucess");
		} catch (Exception e) {
			log.warn("Problemas ao refrescar o objeto: " + p_bean, e);
			throw new RefreshBeansException(e);
		}
		return l_bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.srv.full.virtual.engine.persistence.BeansManager
	 *      #persist(java.lang .Object, java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	public <bean> bean persist(EntityManager em, bean p_bean, String... p_properties) throws BeansManagerException {

		for (String l_property : p_properties) {
			if (PersistenceUtils.isCascate(p_bean, l_property, CascadeType.ALL, CascadeType.PERSIST, CascadeType.MERGE)) {
				Object l_propertyObject = null;
				try {
					l_propertyObject = PropertyUtils.getProperty(p_bean, l_property);
				} catch (Exception e1) {
					log.warn("Problemas ao obter a propriedade informada!", e1);
					throw new BeansManagerException(e1);
				}
				if (l_propertyObject instanceof Collection) {
					Collection l_collection = (Collection) l_propertyObject;

					Collection l_collectionResult = new ArrayList(l_collection.size());

					for (Object l_object : l_collection) {
						try {
							em.persist(l_object);
							l_collectionResult.add(l_object);
						} catch (PersistenceException e) {
							throw new BeansManagerException(e);
						}
					}
					l_propertyObject = l_collectionResult;
				} else {
					try {
						em.persist(l_propertyObject);
					} catch (PersistenceException e) {
						throw new BeansManagerException(e);
					}
				}
				try {
					PropertyUtils.setProperty(p_bean, l_property, l_propertyObject);
				} catch (Exception e) {
					log.warn("Problemas ao obter a propriedade informada!", e);
					throw new BeansManagerException(e);
				}
			}
		}
		em.persist(p_bean);

		return p_bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.srv.full.persistence.BeansManager#update(java. lang.Object)
	 */
	public <bean> bean update(EntityManager em, bean p_bean) throws UpdateBeansException {
		if (p_bean == null)
			throw new UpdateBeansException("O Bean informado náo pode estar null");

		bean l_bean;
		try {
			if (!em.contains(p_bean))
				l_bean = em.merge(p_bean);
			else
				l_bean = p_bean;
			em.persist(l_bean);
			log.info("Refresh sucess");
		} catch (Exception e) {
			log.warn("Problemas ao refrescar o objeto: " + p_bean);
			throw new UpdateBeansException(e);
		}
		return l_bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.srv.full.persistence.BeansManager#update(java. lang.Object,
	 *      java.lang.String[])
	 */
	public <bean> bean update(EntityManager em, bean p_bean, String... p_properties) throws UpdateBeansException {
		// if (p_bean == null)
		// throw new
		// UpdateBeansException("O Bean informado náo pode estar null");
		// if (p_properties == null || p_properties.length
		// == 0)
		// throw new
		// UpdateBeansException("Deve ser informado pelo menos um nome de
		// propriedade!");
		//
		// boolean l_tx = true;
		// EntityManager l_em = createEntityManager(l_tx);
		//
		// bean l_bean;
		// try {
		// if (!l_em.contains(p_bean))
		// l_bean = l_em.merge(p_bean);
		// else
		// l_bean = p_bean;
		// l_em.refresh(l_bean);
		// for (String l_string : p_properties) {
		//
		// }
		// } catch (Exception e) {
		// log.warn("Problemas ao atualizar o objeto no banco: "
		// + p_bean, e);
		// rollBackTransaction(l_em);
		// throw new UpdateBeansException(e);
		// } finally {
		// closeEntityManager(l_em, l_tx);
		//
		// }
		throw new NotImplementedException("Metodo update de propriedades de um beam não estão ainda implementado");
	}

}
