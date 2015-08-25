/**
 * 
 */
package br.srv.full.zipper.jsf.config;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import br.srv.full.virtual.entities.faces.ConfigNavigationCaseVirtual;

import com.sun.faces.application.ConfigNavigationCase;

/**
 * @author Carlos Delfino
 * 
 */
public class FacesConfigManagerImpl implements FacesConfigManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.srv.full.zipper.jsf.config.FacesConfigManager#converge(java.util.Map)
	 */
	@Override
	public Map<String, List<ConfigNavigationCaseVirtual>> converte(
			Map<String, List<ConfigNavigationCase>> p_caseListMapConfig) {

		if (p_caseListMapConfig == null)
			return new HashMap<String, List<ConfigNavigationCaseVirtual>>();

		Map<String, List<ConfigNavigationCaseVirtual>> l_caseListMapDB = new HashMap<String, List<ConfigNavigationCaseVirtual>>(
				p_caseListMapConfig.size());

		if (p_caseListMapConfig.isEmpty())
			return l_caseListMapDB;

		converte(p_caseListMapConfig, l_caseListMapDB);

		return l_caseListMapDB;
	}

	/**
	 * @param p_caseListMapConfig
	 * @param l_caseListMapDB
	 */
	private void converte(Map<String, List<ConfigNavigationCase>> p_caseListMapConfig,
			Map<String, List<ConfigNavigationCaseVirtual>> l_caseListMapDB) {
		for (String l_key : p_caseListMapConfig.keySet()) {
			List<ConfigNavigationCase> l_config = p_caseListMapConfig.get(l_key);
			List<ConfigNavigationCaseVirtual> l_db = new ArrayList<ConfigNavigationCaseVirtual>(l_config.size());

			for (ConfigNavigationCase l_configNavigationCase : l_config) {
				ConfigNavigationCaseVirtual l_dbNavigationCase;

				l_dbNavigationCase = findOnDb(l_configNavigationCase);
				try {
					if (l_dbNavigationCase == null) {
						l_dbNavigationCase = new ConfigNavigationCaseVirtual();
						BeanUtils.copyProperties(l_dbNavigationCase, l_configNavigationCase);
						l_dbNavigationCase = saveOnDB(l_dbNavigationCase);
					}
					l_db.add(l_dbNavigationCase);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			l_caseListMapDB.put(l_key, l_db);
		}
	}

	/**
	 * @param p_configNavigationCase
	 * @return
	 */
	private ConfigNavigationCaseVirtual findOnDb(ConfigNavigationCase p_configNavigationCase) {
		// TODO Criar o processo de pesquisa no banco de mapas de navegação das paginas
		
		return null;
	}

	/**
	 * @param p_dbNavigationCase
	 * @return
	 */
	private ConfigNavigationCaseVirtual saveOnDB(ConfigNavigationCaseVirtual p_dbNavigationCase) {
		// TODO criar o processo de persitencia no Banco de Dados;
		return p_dbNavigationCase;
	}

}
