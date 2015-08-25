/**
 * 
 */
package br.srv.full.persistence.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
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
import br.srv.full.persistence.utils.BeansManagerSupport;
import br.srv.full.persistence.utils.PersistenceUtils;
import br.srv.full.virtual.bean.base.QueryID;

/**
 * @author Delfino
 * 
 * TODO esta classe está fortemente acoplada ao Hibernate, verificar forma de
 * reduzir este acoplamento
 * 
 */
public final class BeansManagerImpl extends BeansManagerAbstract {

	/**
	 * 
	 */
	private static final String FULL_SERVICE_VIRTUAL_U_W = "FullServiceVirtualUW";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final BeansManagerSupport bms = new BeansManagerSupport();
	/**
	 * 
	 */
	private String UW;

	/**
	 * @param p_uw
	 *            the uW to set
	 */
	public void setUnitWork(String p_uw) {
		if (UW == FULL_SERVICE_VIRTUAL_U_W || UW == null) {
			this.UW = p_uw;
		} else
			throw new RuntimeException("UnitWork não pode ser alterada depois de setada!");
	}

	private final static Log slog = LogFactory.getLog(BeansManagerImpl.class);

	static {
		slog.info("class referenciada no contexto");
	}

	protected final Log log = LogFactory.getLog(getClass());

	private int count;

	{
		log.info("Criando Instancia: " + ++count);
	}

	public BeansManagerImpl() {
		this(FULL_SERVICE_VIRTUAL_U_W);
	}

	/**
	 * @param p_uw
	 */
	public BeansManagerImpl(String p_uw) {
		UW = p_uw;
	}

	/**
	 * 
	 * @param <bean>
	 * @param <key>
	 * @param _beanClass
	 * @param _key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <bean, key> bean find(Class<bean> _beanClass, key _key) {

		EntityManager l_em = createEntityManager(false);

		bean l_result = bms.find(l_em, _beanClass, _key);

		closeEntityManager(l_em, false);

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
	@SuppressWarnings("unchecked")
	public <bean, params> Object find(Class<bean> _beanClass, QueryID _query, String[] p_fieldName, params[] p_params) {
		EntityManager l_em = createEntityManager(false);

		return bms.find(l_em, _beanClass, _query, p_fieldName, p_params);
	}

	public <bean, params> Object find(Class<bean> _beanClass, String _query, params... _params) {
		EntityManager l_em = createEntityManager(false);

		return bms.find(l_em, _beanClass, _query, _params);
	}

	public <bean, params> Object find(Class<bean> _beanClass, QueryID _query, params... _params) {
		EntityManager l_em = createEntityManager(false);

		log.info("Find usando a Query: " + _query);

		return bms.find(l_em, _beanClass, _params);
	}

	public <bean, params> Object find(Class<bean> _beanClass, QueryID _query) {
		EntityManager l_em = createEntityManager(false);

		return bms.find(l_em, _beanClass, _query);
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
	public <bean, params> Object find(Class<bean> p_class, QueryID p_query, Map<String, Object> p_params) {
		EntityManager l_em = createEntityManager(false);
		Query l_query = l_em.createNamedQuery(p_query.toString());

		return bms.find(l_em, p_class, p_query, p_params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.srv.full.persistence.BeansManager#find(java.lang .Class)
	 */
	public <bean> Object find(Class<bean> p_class) {
		EntityManager l_em = createEntityManager(false);

		return bms.find(l_em, p_class);
	}

	/**
	 * @param <bean>
	 * @param _bean
	 * @throws BeansManagerException
	 * @throws BeansManagerPropertyException
	 */

	public <bean> bean persist(bean _bean) throws PersistBeansException {
		EntityManager l_em = createEntityManager(true);

		_bean = bms.persist(l_em, _bean);

		closeEntityManager(l_em, true);

		return _bean;
	}

	public <bean> boolean contains(bean p_bean) throws BeansManagerException {
		EntityManager l_em = createEntityManager(false);
		boolean l_contain;
		try {
			log.info("Verificando se contexto contem objeto: " + p_bean);
			l_contain = l_em.contains(p_bean);
		} catch (Exception e) {
			throw new PersistBeansException(e);
		}
		closeEntityManager(l_em, false);

		return l_contain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.srv.full.virtual.engine.persistence.BeansManager
	 *      #refresh(java.lang .Object)
	 */
	public <bean> bean refresh(bean p_bean) throws RefreshBeansException {
		if (p_bean == null)
			throw new RefreshBeansException("O Bean informado náo pode estar null");

		EntityManager l_em = createEntityManager();
		bean l_bean;
		l_bean = bms.refresh(l_em, p_bean);
		closeEntityManager(l_em);

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
	public <bean> bean refresh(final bean p_bean, String... p_properties) throws RefreshBeansException {
		if (p_properties == null || p_properties.length == 0)
			throw new RefreshBeansException("Deve ser informado pelo menos um nome de propriedade!");
		try {
			PersistenceUtils.validatePersistenceBean(p_bean);
		} catch (BeansManagerException e) {
			throw new RefreshBeansException(e);
		}

		boolean l_tx = true;
		EntityManager l_em = createEntityManager(l_tx);

		bean l_bean;
		l_bean = bms.refresh(l_em, p_bean, p_properties);
		
		closeEntityManager(l_em, l_tx);

		return l_bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.srv.full.virtual.engine.persistence.BeansManager
	 *      #persist(java.lang .Object, java.lang.String[])
	 */
	public <bean> bean persist(bean p_bean, String... p_properties) throws BeansManagerException {
		boolean l_tx = true;

		EntityManager l_em = createEntityManager(l_tx);
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
							l_em.persist(l_object);
							l_collectionResult.add(l_object);
						} catch (PersistenceException e) {
							throw new BeansManagerException(e);
						}
					}
					l_propertyObject = l_collectionResult;
				} else {
					try {
						l_em.persist(l_propertyObject);
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
		l_em.persist(p_bean);

		closeEntityManager(l_em, l_tx);
		return p_bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.srv.full.persistence.impl.BeansManagerAbstract #getEmf()
	 */
	@Override
	protected EntityManagerFactory getEmf() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(getUnitWork());

		}
		return emf;
	}

	/**
	 * @return
	 */
	private String getUnitWork() {
		return UW;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.srv.full.persistence.BeansManager#update(java. lang.Object)
	 */
	public <bean> bean update(bean p_bean) throws UpdateBeansException {
		if (p_bean == null)
			throw new UpdateBeansException("O Bean informado náo pode estar null");

		EntityManager l_em = createEntityManager();
		bean l_bean;
		try {
			if (!l_em.contains(p_bean))
				l_bean = l_em.merge(p_bean);
			else
				l_bean = p_bean;
			l_em.persist(l_bean);
			log.info("Refresh sucess");
		} catch (Exception e) {
			log.warn("Problemas ao refrescar o objeto: " + p_bean);
			throw new UpdateBeansException(e);
		} finally {
			closeEntityManager(l_em);
		}
		return l_bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.srv.full.persistence.BeansManager#update(java. lang.Object,
	 *      java.lang.String[])
	 */
	public <bean> bean update(bean p_bean, String... p_properties) throws UpdateBeansException {
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
