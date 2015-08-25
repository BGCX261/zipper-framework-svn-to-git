/**
 * 
 */
package br.srv.full.configmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Carlos Delfino
 * 
 */
public class FileUtils {

	private final static Log log = LogFactory.getLog(FileUtils.class);

	/**
	 * Obtem um arquivo na seguinte ordem:
	 * 
	 * <il>Tenta no classe path com base no classeloader que carregou o
	 * ConfigManager <il>Tenta no diretorio conf no diretorio anterior ao atual.
	 * <il>Tenta no diretorio conf dentro do diretorio atual Tenta no diretorio
	 * atual.
	 * 
	 * Se não achar em nenhum destes lança uma Exception.
	 * 
	 * 
	 * @param l_fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	static public File getFile(String _configName) throws FileNotFoundException {
		File l_file = null;
		// tenta obter aquivo através do classloader que carregou o
		// ConfigManager
		l_file = getFileAsResource(_configName, l_file);
		// caso não encontre tenta dentro diretorio conf no nivel abaixo do
		// diretorio atual
		if (l_file == null || !l_file.canRead()) {
			l_file = getFileAsResource(_configName, l_file, true);

			if (l_file == null || !l_file.canRead()) {
				l_file = new File("../conf/" + _configName);
				// se não encontrar tenta no diretorio conf dentro do diretorio
				// atual
				log.info("Tentando com o arquivo: " + l_file);
				if (l_file == null || !l_file.canRead()) {
					l_file = new File("conf/" + _configName);
					// não encontrando tenta achar o arquivo no diretorio local
					log.info("Tentando com o arquivo: " + l_file);
					if (l_file == null || !l_file.canRead()) {
						l_file = new File(_configName);
						// se não achar lança um exeção.
						log.info("Tentando com o arquivo: " + l_file);
						if (l_file == null || !l_file.canRead())
							throw new FileNotFoundException("Não consigo encontrar o arquivo: " + _configName);

					}
				}
			}
		}
		return l_file;
	}

	/**
	 * @param _configName
	 * @param l_file
	 * @param _b
	 * @return
	 */
	static public File getFileAsResource(String _configName, File l_file, boolean... _b) {
		boolean l_changeSpace = false;
		if (_b != null && _b.length > 0)
			l_changeSpace = _b[0];

		final ClassLoader l_contextCL = FileUtils.class.getClassLoader();
		final URL l_systemResource = l_contextCL.getResource(_configName);

		if (l_systemResource != null) {
			String l_filePath = l_systemResource.getFile();

			if (l_changeSpace) {
				log.info("Trocando codiog %20 para Espaço!");
				l_filePath = l_filePath.replaceAll("%20", " ");
			}

			l_file = new File(l_filePath);
			log.info("Tentando com o arquivo: " + l_file);
		}
		return l_file;
	}

}
