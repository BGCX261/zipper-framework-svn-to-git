/**
 * 
 */
package br.srv.full.base;

import org.apache.commons.logging.Log;

/**
 * @author carlos.pinheiro
 * 
 */
public class LogUtils {

	public static void showLogsAtivos(Log log) {
		if (log.isFatalEnabled())
			log.fatal("Fatal Ativo!");
		if (log.isErrorEnabled())
			log.error("Error Ativo!");
		if (log.isTraceEnabled())
			log.trace("Trace Ativo!");
		if (log.isDebugEnabled())
			log.debug("Debug Ativo!");
		if (log.isInfoEnabled())
			log.info("Info Ativo!");
		if (log.isWarnEnabled())
			log.warn("Warn Ativo!");
	}

}
