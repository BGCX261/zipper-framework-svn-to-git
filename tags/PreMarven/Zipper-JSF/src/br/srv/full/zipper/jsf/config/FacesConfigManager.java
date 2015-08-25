/**
 * 
 */
package br.srv.full.zipper.jsf.config;

import java.util.List;
import java.util.Map;

import br.srv.full.virtual.entities.faces.ConfigNavigationCaseVirtual;

import com.sun.faces.application.ConfigNavigationCase;

/**
 * @author Carlos Delfino
 * 
 */
public interface FacesConfigManager {

	/**
	 * Converte as configurações dos arquivos FacesConfig.xml para o banco de
	 * dados e retorna o objeto adequado ao uso do JSFVirtual
	 * 
	 * @param p_caseListMapConfig
	 * @return
	 */
	public Map<String, List<ConfigNavigationCaseVirtual>> converte(
			Map<String, List<ConfigNavigationCase>> p_caseListMapConfig);

}
