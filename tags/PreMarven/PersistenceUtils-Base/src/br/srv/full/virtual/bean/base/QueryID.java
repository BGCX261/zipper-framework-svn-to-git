package br.srv.full.virtual.bean.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QueryID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Log log = LogFactory.getLog(getClass());

	private final static Log logs = LogFactory.getLog(QueryID.class);

	/**
	 * Nome do Query que deve ser usado para queries que pesquisam todos os
	 * beans
	 */
	public static final String FIND_ALL = "FIND_ALL";

	public static final String FIND_BY_NAME = "FIND_BY_NAME";

	private static Set<QueryID> allBeansQuery = new LinkedHashSet<QueryID>();
	private static Map<Class, Set<QueryID>> beansQueryByOwner = new HashMap<Class, Set<QueryID>>();
	private static Map<String, Map<Class, Set<QueryID>>> beansQueryByName = new HashMap<String, Map<Class, Set<QueryID>>>();

	/**
	 * @param p_class
	 * @return
	 */
	public static Set<QueryID> getAllBeansQuery(Class p_class) {
		return allBeansQuery;
	}

	/**
	 * @param p_queryID
	 */
	private void addBeansQuery(QueryID p_queryID) {
		log.info("Adicionando nova Query: " + p_queryID);

		Set<QueryID> queryIDList = beansQueryByOwner.get(p_queryID.getOwner());

		if (queryIDList == null) {
			queryIDList = new LinkedHashSet<QueryID>();
			beansQueryByOwner.put(p_queryID.getOwner(), queryIDList);
		}

		queryIDList.add(p_queryID);

		allBeansQuery.add(p_queryID);

	}

	private String name;
	private Set<String> paramNames;
	private Class<? extends Serializable> owner;

	public QueryID(Class<? extends Serializable> p_owner, String p_name) {
		owner = p_owner;
		name = p_name;
		paramNames = Collections.EMPTY_SET;

		addBeansQuery(this);
	}

	/**
	 * @param p_string
	 * @param p_strings
	 */
	public QueryID(Class<? extends Serializable> p_owner, String p_queryName, String[] p_paramNames) {
		this(p_owner, p_queryName);
		Set<String> l_param = new HashSet<String>(Arrays.asList(p_paramNames));
		paramNames = Collections.unmodifiableSet(l_param);
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * @return the paramNames
	 */
	public Set<String> getParamNames() {
		return paramNames;
	}

	/**
	 * @return
	 */
	public Class<? extends Serializable> getOwner() {
		return owner;
	}

	/**
	 * @param p_class
	 * @return
	 */
	public static QueryID getQueryIdAllBeans(Class p_class) {
		QueryID l_queryId = null;
		try {
			Field l_field = p_class.getField(QueryID.FIND_ALL);

			if (l_field != null) {
				l_field.setAccessible(true);
				l_queryId = (QueryID) l_field.get(p_class);
			}
		} catch (SecurityException e) {
			logs.warn(e);
		} catch (NoSuchFieldException e) {
			logs.warn(e);
		} catch (IllegalArgumentException e) {
			logs.warn(e);
		} catch (IllegalAccessException e) {
			logs.warn(e);
		}
		return l_queryId;
	}

	/**
	 * @param p_findByName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static QueryID getQueryId(Class p_class, String p_findByName) {
		Map<Class, Set<QueryID>> l_mapQueries = beansQueryByName.get(p_findByName);
		Set<QueryID> l_setQueries;
		QueryID l_queryID = null;
		if (l_mapQueries != null) {
			l_setQueries = l_mapQueries.get(p_class);
			if (l_setQueries != null) {
				l_queryID = l_setQueries.iterator().next();
			} else {
				l_setQueries = new HashSet<QueryID>(1);
				l_mapQueries.put(p_class, l_setQueries);
				try {
					Field l_field = p_class.getField(p_findByName);
					l_field.setAccessible(true);
					l_queryID = (QueryID) l_field.get(null);
					l_setQueries.add(l_queryID);
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			l_mapQueries = new HashMap<Class, Set<QueryID>>();
			l_setQueries = new HashSet<QueryID>();
			l_mapQueries.put(p_class, l_setQueries);
			try {
				Field l_field = p_class.getField(p_findByName);
				l_field.setAccessible(true);
				l_queryID = (QueryID) l_field.get(null);
				l_setQueries.add(l_queryID);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return l_queryID;
	}

}
