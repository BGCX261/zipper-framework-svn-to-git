package br.srv.full.configmanager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author CarlosDelfino
 * 
 */
public abstract class ConfigManager {

	private static ConfigManager instance;

	protected final Log log = LogFactory.getLog(getClass());
	protected final static Log slog = LogFactory.getLog(ConfigManager.class);

	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	protected Properties prop;
	protected Properties implProp;

	@SuppressWarnings("unchecked")
	private final Map<Class, Object> instances = new HashMap<Class, Object>();

	/**
	 * Obtem a instancia do ConfigManager. Caso a propriedade
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public static ConfigManager getInstance() throws FileNotFoundException, IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		if (instance == null) {
			String l_conf = System.getProperty("ConfigManager.impl");

			ConfigManager l_config;
			if (l_conf != null) {
				@SuppressWarnings("unchecked")
				Class<? extends ConfigManager> l_class = (Class<? extends ConfigManager>) Class.forName(l_conf);
				l_config = l_class.newInstance();

				instance = l_config;
			} else {

				l_config = ConfigManagerProperties.class.newInstance();
				try {
					instance = l_config.getInstance(ConfigManager.class);
				} catch (Exception _e) {
					slog.info("Classe para o ConfigManager não encontrada usando a padrão.");
				}
				if (instance == null)
					instance = l_config;
			}
		}
		return instance;
	}

	/**
	 * @param <T>
	 * @param p_class
	 * @return
	 */
	public <T> T getInstance(Class<T> p_class) {
		log.info("Obtendo Instancia Concreta para a  " + p_class);
		return getInstance(p_class, false);
	}

	/**
	 * Tenta obter uma instancia da classe informada.
	 * 
	 * a instância é identificada conforme as configura��es fornecidas ao
	 * ConfigManager.
	 * 
	 * Se a propriedade p-setConfig for definida como true ele n�o tenta achar
	 * um m�todo Singleton.getInstance(ConfigManager) e busca um m�todo
	 * Singleton.setConfig(ConfigManager) para injetar o {@link ConfigManager}
	 * 
	 * @param <T>
	 * @param p_class
	 *            Classe ou Interface que identifica a instancia.
	 * @param p_setConfig
	 *            se deve ou n�o usar o setConfig para a configura��o.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getInstance(Class<T> p_class, boolean p_setConfig) {

		@SuppressWarnings("unchecked")
		T l_implent = (T) instances.get(p_class);
		boolean l_useSetConfig = true;
		if (l_implent == null) {
			try {
				Class<? extends T> l_class = getClassImplentation(p_class);

				Method l_method = null;
				try {
					boolean l_getInstanceWithConfig = false;
					try {
						if (!p_setConfig) {
							l_method = l_class.getMethod("getInstance", ConfigManager.class);
							l_useSetConfig = false;
							l_getInstanceWithConfig = true;
						}
					} catch (NoSuchMethodException e) {
						log.info("N�o existe metodo singleton para carga da configura��o, Usando singleton padr�o!");
						try {
							l_method = l_class.getDeclaredMethod("getInstance", (Class[]) null);
						} catch (NoSuchMethodException e1) {
							log.warn("N�o existe m�todo getInstance em " + p_class + ", Criando uma nova instancia!");

						}
					}
					try {
						if (p_setConfig)
							l_method = l_class.getDeclaredMethod("getInstance", (Class[]) null);
					} catch (NoSuchMethodException e1) {
						log.warn("N�o existe m�todo getInstance em " + p_class + ", Criando uma nova instancia!");

					}
					if (l_method != null && l_getInstanceWithConfig) {
						l_implent = (T) l_method.invoke(null, this);
					} else if (l_method != null && Modifier.isStatic(l_method.getModifiers())) {
						l_implent = (T) l_method.invoke(null, (Object[]) null);
					} else {
						l_implent = l_class.newInstance();
					}
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				instances.put(p_class, l_implent);
			} catch (ClassNotFoundException e) {
				log.warn("Problemas ao encontrar a class: " + p_class, e);
			}
			
		}
		if (l_useSetConfig && l_implent != null && p_setConfig)
			setConfig(l_implent);
		return l_implent;

	}

	private void setConfig(Object l_implent) {
		try {
			Method l_setConfig = l_implent.getClass().getMethod("setConfig", ConfigManager.class);
			l_setConfig.invoke(l_implent, this);
		} catch (Exception e) {
			log.warn(e);
			throw new RuntimeException("Objeto " + l_implent + " n�o Aceita servi�o de configura��o!");
		}
	}

	/**
	 * Retorna a classe que implementa a classe informada.
	 * 
	 * @param <T>
	 * @param p_class
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public <T> T getClassImplentation(Class p_class) throws ClassNotFoundException {

		T l_className = getClassImplentation("impl", p_class);

		return l_className;
	}

	/**
	 * @param <T>
	 * @param p_prefix
	 * @param p_class
	 * @return
	 * @throws ClassNotFoundException
	 */
	public <T> T getClassImplentation(String p_prefix, Class<?> p_class) throws ClassNotFoundException {
		return getClassImplentation(p_prefix, p_class.getName());
	}

	/**
	 * @param <T>
	 * @param p_prefix
	 * @param p_class
	 * 
	 * @return p_class implemented
	 * 
	 * @throws ClassNotFoundException
	 */
	public <T> T getClassImplentation(String p_prefix, String p_class) throws ClassNotFoundException {
		String l_property = p_prefix + "." + p_class;

		log.info("Obtendo a propriedade: " + l_property);

		if (implProp == null)
			try {
				loadImplProperties();
			} catch (PropertiesNotFoundException e) {
				log.warn("não foi possivel carregar o arquivo de configurações das implementações:", e);
			}

		String l_strClassName = implProp.getProperty(l_property);
		if (l_strClassName == null){
			l_strClassName = p_class+"Impl";
//			throw new ClassNotFoundException("Não encontramos implementação para " + p_class);
		}
		
		
		log.info("Obtendo classe: " + l_strClassName);
		@SuppressWarnings("unchecked")
		T l_className = (T) Class.forName(l_strClassName);

		return l_className;
	}

	/**
	 * Retorna o valor da propriedade informada
	 * 
	 * @param p_property
	 * @return
	 */
	public String getProperty(String p_property) {
		log.info("Obtendo a propriedade: " + p_property);

		if (prop == null)
			try {
				loadDefaltProperties();
			} catch (PropertiesNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		return prop.getProperty(p_property);
	}

	abstract protected void loadDefaltProperties() throws PropertiesNotFoundException;

	abstract protected void loadImplProperties() throws PropertiesNotFoundException;

	/**
	 * @param p_property
	 * @param p_value
	 */
	public void setProperty(String p_property, String p_value) {
		String l_old = getProperty(p_property);
		prop.setProperty(p_property, p_value);

		firePropertyChange(p_property, l_old, p_value);
	}

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String,
	 *      java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * @param propertyName
	 * @param oldValue
	 * @param newValue
	 * @see java.beans.PropertyChangeSupport#firePropertyChange(java.lang.String,
	 *      boolean, boolean)
	 */
	public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
		pcs.firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * @param propertyName
	 * @param oldValue
	 * @param newValue
	 * @see java.beans.PropertyChangeSupport#firePropertyChange(java.lang.String,
	 *      int, int)
	 */
	public void firePropertyChange(String propertyName, int oldValue, int newValue) {
		pcs.firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * @param propertyName
	 * @param oldValue
	 * @param newValue
	 * @see java.beans.PropertyChangeSupport#firePropertyChange(java.lang.String,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		pcs.firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * @return
	 * @see java.beans.PropertyChangeSupport#getPropertyChangeListeners()
	 */
	public PropertyChangeListener[] getPropertyChangeListeners() {
		return pcs.getPropertyChangeListeners();
	}

	/**
	 * @param propertyName
	 * @return
	 * @see java.beans.PropertyChangeSupport#getPropertyChangeListeners(java.lang.String)
	 */
	public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
		return pcs.getPropertyChangeListeners(propertyName);
	}

	/**
	 * @param propertyName
	 * @return
	 * @see java.beans.PropertyChangeSupport#hasListeners(java.lang.String)
	 */
	public boolean hasListeners(String propertyName) {
		return pcs.hasListeners(propertyName);
	}

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String,
	 *      java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}

	public void setProperty(String string, Object codigo) {
		setProperty(string, codigo.toString());
	}

	/**
	 * Seta diretamente no arquivo de propriedades se verdadeiro.
	 * 
	 * @param p_prop
	 * @param p_value
	 * @param b
	 */
	public void setProperty(String p_prop, Integer p_value, boolean b) {
		setProperty(p_prop, p_value);
		if (b)
			setPropertyOnFile(p_prop, p_value);
	}

	public abstract void setPropertyOnFile(String p_prop, Object p_value);

	public abstract void setPropertyOnFile(String p_prop, String p_value);

	/**
	 * retorna um array de uma sequencia de propriedades
	 * 
	 * O nome da propriedade é composta por p_prop.p_count
	 * 
	 * Como p_count é um array, deve ser passado um par de limites, ou
	 * sequencias a serem obtidas. nesta versão somente funciona um par de
	 * limites.
	 * 
	 * ou seja, se o metodo for chamado da seguinte forma:
	 * #getSequencialProperty("nome.prop", 3,6);
	 * 
	 * ele irá retornar a propridades:
	 * <li>nome.prop.3
	 * <li>nome.prop.4
	 * <li>nome.prop.5
	 * <li>nome.prop.6
	 * 
	 * se o método for chamado da seguinte forma:
	 * #getSequencialProperty("nome.prop", 3);
	 * <li>nome.prop.0
	 * <li>nome.prop.1
	 * <li>nome.prop.2
	 * 
	 * @param p_prop
	 * @param p_count
	 * @return
	 */
	public String[] getSequencialProperty(String p_prop, int... p_count) {
		String[] l_value = null;
		if (p_count.length == 0) {
			return getAllSeguencialProperties(p_prop);
		} else if (p_count.length == 1) {
			if (p_count[0] < 1)
				throw new InvalidParameterException("Total maximo de index deve ser maior ou igual a 1, "
						+ "valor informado: " + p_count);

			return getSequencialProperty(p_prop, 0, p_count[0] - 1);
		} else if (p_count.length == 2) {
			int l_max = Math.max(p_count[0], p_count[1]);
			int l_min = Math.min(p_count[1], p_count[0]);
			if (l_min < 0)
				throw new InvalidParameterException("Indice inferior deve ser maior ou igual a 0, "
						+ "valor informado: " + l_min);

			StringBuilder l_sbd = new StringBuilder(p_prop).append(".0");
			int l_start = l_sbd.lastIndexOf(".0") + 1;

			int l_count = l_max - l_min;
			List<String> l_values = new ArrayList<String>(l_count);
			for (int i = l_min; i <= l_max; i++) {
				int l_end = l_sbd.length();
				String l_index = String.valueOf(i);
				String l_prop = l_sbd.replace(l_start, l_end, l_index).toString();
				String l_valueTmp = getProperty(l_prop);
				l_values.add(l_valueTmp);
			}
			l_value = l_values.toArray(new String[l_count]);
		}
		return l_value;
	}

	/**
	 * Por implementar
	 * 
	 * @param p_prop
	 * @return
	 */
	private String[] getAllSeguencialProperties(String p_prop) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Retorna o valor de uma propriedade como do tipo Number.
	 * 
	 * Caso esta propriedade não possa ser convertida para Number, retorna null;
	 * 
	 * @param _prop -
	 *            Nome da propriedade
	 * @param _type -
	 *            Tipo no qual a propriedade deve retornar
	 * @param <num> -
	 *            tipo no qual a propriedade deve retornar
	 * 
	 * @return o valor conforme o tipo;
	 */
	public <num extends Number> num getNumberProperty(String _prop, Class<num> _type) {

		String l_prop = getProperty(_prop);
		log.info("Valor: " + l_prop);

		num l_number = null;
		if (l_prop != null && l_prop.length() > 0) {
			try {
				Constructor<num> l_constr = _type.getConstructor(String.class);
				l_number = l_constr.newInstance(l_prop);
			} catch (Exception _e) {
				log.warn("Problemas ao obter a propriedade: " + _prop, _e);
			}
		}
		return l_number;
	}

	/**
	 * @param _prop
	 * @param _default
	 * @param _type
	 * @param <num>
	 * @return
	 */
	public <num extends Number> num getNumberProperty(String _prop, num _default, Class<num> _type) {
		String l_prop = getProperty(_prop);
		log.info("Valor: " + l_prop);

		num l_number = null;
		if (l_prop != null && l_prop.length() > 0) {
			try {
				Constructor<num> l_constr = _type.getConstructor(String.class);
				l_number = l_constr.newInstance(l_prop);
			} catch (Exception _e) {
				log.warn("Problemas ao obter a propriedade: " + _prop, _e);
			}
		} else
			l_number = _default;
		return l_number;
	}

	/**
	 * @param _prop
	 * @return
	 */
	public Boolean getBooleanProperty(String _prop) {
		String l_prop = getProperty(_prop);
		log.info("Valor: " + l_prop);

		Boolean l_number = null;
		if (l_prop != null && l_prop.length() > 0) {
			try {

				l_number = new Boolean(l_prop);
			} catch (Exception _e) {
				log.warn("Problemas ao obter a propriedade: " + _prop, _e);
			}
		}
		return l_number;
	}

	/**
	 * 
	 * @param _prop
	 * @param _default
	 * @return
	 */
	public Boolean getBooleanProperty(String _prop, Boolean _default) {
		String l_prop = getProperty(_prop);
		log.info("Valor: " + l_prop);

		Boolean l_bool = null;
		if (l_prop != null && l_prop.length() > 0) {
			try {

				l_bool = new Boolean(l_prop);
			} catch (Exception _e) {
				log.warn("Problemas ao obter a propriedade: " + _prop, _e);
			}
		} else {
			l_bool = _default;
		}
		return l_bool;
	}

	/**
	 * @param p_string
	 * @return
	 */
	public String[] getArrayProperty(String p_string) {
		return getArrayProperty(p_string, ";");
	}

	/**
	 * @param p_key
	 * @param p_sep
	 * @return
	 */
	public String[] getArrayProperty(String p_key, String p_sep) {
		log.info("Obtendo " + p_key + ", Transformando em Array");
		String l_value = getProperty(p_key);

		return l_value.split(",");
	}

	/**
	 * @param p_prop
	 * @param p_count
	 * @return
	 */
	public String[] getSequencialProperty(String p_prop, String... p_count) {
		String[] l_value = null;
		if (p_count.length == 0) {
			throw new RuntimeException("Lista com posfixo não pode ser vazio!");
		} else if (p_count.length == 1) {

		} else if (p_count.length > 2) {

			StringBuilder l_sbd = new StringBuilder(p_prop).append(".");

			List<String> l_values = new ArrayList<String>();
			for (String l_idx : p_count) {
				String l_prop = l_sbd.toString() + l_idx;

				String l_valueTmp = getProperty(l_prop);

				l_values.add(l_valueTmp);
			}
			l_value = l_values.toArray(new String[l_values.size()]);
		}
		return l_value;
	}

}
