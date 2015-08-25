package br.srv.full.faces.utils;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

public class FacesMessageUtils {

//	private static FacesMessageUtils instance;

	/**
	 * @param _serverity
	 * @param _resum
	 * @param _detail
	 * @return
	 */
	public static FacesMessage getMessage(Severity _serverity, String _resum, String _detail) {
		return new FacesMessage(_serverity, _resum, _detail);
	}

	/**
	 * Retorna a mensagem conforme chave armazenada em um MessageBundle
	 * 
	 * @param _serverity
	 * @param _bundle
	 * @param _resum
	 * @return
	 */
	public FacesMessage getMessageBundle(Severity _serverity, String _bundle, String _resum) {
		String summary = FacesUtils.getString(_bundle, _resum);

		String detail = FacesUtils.getString(_bundle, _resum + ".Detail");

		return new FacesMessage(_serverity, summary, detail);
	}

	private FacesMessageUtils() {

	}
//
//	private static FacesMessageUtils getInstance() {
//		if (instance == null) {
//			instance = new FacesMessageUtils();
//		}
//
//		return instance;
//	}

	/**
	 * 
	 * 
	 * @param p_id -
	 *            Label or component ID
	 * @param p_severity -
	 *            {@link FacesMessage}: {@link FacesMessage#SEVERITY_ERROR},
	 *            {@link FacesMessage#SEVERITY_ERROR},
	 *            {@link FacesMessage#SEVERITY_FATAL},
	 *            {@link FacesMessage#SEVERITY_INFO},
	 *            {@link FacesMessage#SEVERITY_WARN},
	 * @param p_shortMsg
	 * @param p_string3
	 * @return
	 */
	public static FacesMessage addMessage(String p_id, Severity p_severity, String p_shortMsg, String p_string3) {
		FacesMessage l_msg = FacesMessageUtils.getMessage(p_severity, p_shortMsg, p_string3);
		FacesUtils.addMessage(p_id, l_msg);
		return l_msg;
	}

}
