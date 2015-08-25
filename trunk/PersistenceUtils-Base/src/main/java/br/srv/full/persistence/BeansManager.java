package br.srv.full.persistence;

import java.io.Serializable;
import java.util.Map;

import br.srv.full.virtual.bean.base.EntityBean;
import br.srv.full.virtual.bean.base.QueryID;

public interface BeansManager extends Serializable {

	/**
	 * @param <bean
	 *            extends EntityBean>
	 * @param <key>
	 * @param _beanClass
	 * @param _key
	 * @return
	 */
	<bean, key> bean find(Class<bean> _beanClass, key _key);

	/**
	 * @param <bean
	 *            extends EntityBean>
	 * @param _beanClass
	 * @return
	 */
	<bean> Object find(Class<bean> _beanClass);

	public <bean, params> Object find(Class<bean> _beanClass, QueryID _query, String[] p_fieldName, params[] p_params);

	/**
	 * @param <bean
	 *            extends EntityBean>
	 * @param <params>
	 * @param _beanClass
	 * @param _query
	 * @param _params
	 * @return
	 */
	<bean, params> Object find(Class<bean> _beanClass, QueryID _query, params... _params);

	<bean, params> Object find(Class<bean> _beanClass, QueryID _query);

	<bean, params> Object find(Class<bean> _beanClass, String _query, params... _params);

	/**
	 * @param <bean
	 *            extends EntityBean>
	 * @param <params>
	 * @param _beanClass
	 * @param _query
	 * @param _params
	 * @return
	 */
	<bean, params> Object find(Class<bean> _beanClass, QueryID _query, Map<String, Object> _params);

	/**
	 * @param <bean
	 *            extends EntityBean>
	 * @param _bean
	 * @throws PersistBeansException
	 * @throws BeansManagerException
	 */
	<bean> bean persist(bean _bean) throws PersistBeansException, BeansManagerException;

	/**
	 * Persist o objeto informado priorizando as propriedates participantes em
	 * sua composiçao.
	 * 
	 * @param <bean
	 *            extends EntityBean>
	 * @param p_bean
	 * @param p_properties
	 * @return
	 * @throws PersistBeansException
	 * @throws BeansManagerException
	 */
	<bean> bean persist(bean p_bean, String... p_properties) throws PersistBeansException, BeansManagerException;

	/**
	 * @param p_login
	 * @return
	 * @throws RefreshBeansException
	 */
	public <bean> bean refresh(bean p_bean) throws RefreshBeansException;

	/**
	 * @param p_bean
	 * @param p_properties
	 */
	public <bean> bean refresh(bean p_bean, String... p_properties) throws RefreshBeansException;

	/**
	 * @param p_loginDB
	 */
	<bean> bean update(bean p_bean) throws UpdateBeansException;

	/**
	 * @param p_loginDB
	 */
	<bean> bean update(bean p_bean, String... p_properties) throws UpdateBeansException;
}
