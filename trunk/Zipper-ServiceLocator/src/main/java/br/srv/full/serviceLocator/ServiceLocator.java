/**
 * 
 */
package br.srv.full.serviceLocator;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.srv.full.configmanager.ConfigManager;
import br.srv.full.serviceLocator.annotations.ServiceType;

/**
 * @author Delfino
 * 
 */
public class ServiceLocator {
	private static final Log log = LogFactory.getLog(ServiceLocator.class);

	@SuppressWarnings("unchecked")
	public static <service> service getService(String p_serviceName) {
		service obj = null;
		try {
			InitialContext initialCtx = new InitialContext();

			log.info("Inicializando");
			log.info(initialCtx);
			log.info("Usando initialCTX");
			obj = (service) initialCtx.lookup(p_serviceName);
			log.info(obj.getClass().getInterfaces());
		} catch (Exception e) {
			log.warn(e);
			e.printStackTrace();
		}

		return obj;
	}

	public static <service> service getService(Class<service> p_serviceClass) {
		service obj = null;
		try {
			obj = getConfigManager().getInstance(p_serviceClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// obj = getService(p_serviceClass, "remote");
		return obj;

	}

	/**
	 * @return
	 * @throws Exception
	 */
	private static ConfigManager getConfigManager() throws Exception {
		return ConfigManager.getInstance();
	}

	/**
	 * @param p_serviceClass
	 * @param p_sufixo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <service> service getService(Class<service> p_serviceClass, String p_sufixo) {
		service obj = null;
		try {

			log.info("Inicializando");

			InitialContext initialCtx = new InitialContext();
			log.info("Usando initialCTX " + initialCtx);

			String l_servicePath = p_serviceClass.getSimpleName() + "/" + p_sufixo;

			ConfigManager l_configManager = getConfigManager();
			String l_basePath = l_configManager.getProperty("base.path.initialContext");
			if (l_basePath != null && !l_basePath.isEmpty()) {
				l_servicePath = l_basePath.trim() + "/" + l_servicePath.trim();
				l_servicePath = l_servicePath.replace("//", "/");
			}

			obj = (service) initialCtx.lookup(l_servicePath);
			Class<?>[] l_interfaces = obj.getClass().getInterfaces();
			log.info("Usando Interfaces:");
			for (Class<?> l_class : l_interfaces) {
				log.info(l_class);
			}
		} catch (Exception e) {
			log.warn(e);
			e.printStackTrace();
		}

		return obj;
	}

	/**
	 * @param p_class
	 * @param p_remote
	 */
	public static <service> service getService(Class<service> p_serviceClass, ServiceType p_sufixo) {
		return getService(p_serviceClass, p_sufixo.toString());

	}

}
