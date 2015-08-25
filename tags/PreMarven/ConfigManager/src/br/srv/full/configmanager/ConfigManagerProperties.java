package br.srv.full.configmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author CarlosDelfino
 * 
 */
public class ConfigManagerProperties extends ConfigManager {
	private static final String IMPLEMENTATIONS = "implementations.properties";
	private static String PROPERTIES = "config.properties";

	private static ConfigManagerProperties instance;

	private static final Log s_log = LogFactory.getLog(ConfigManagerProperties.class);

	static {
		String l_tmp = System.getProperty("ConfigManager.PropertyFile.Name", PROPERTIES);
		if (l_tmp != PROPERTIES)
			setPropertyFileName(l_tmp);
		else
			s_log.info("Usando o nome do arquivo para: " + l_tmp);
	}
	/*
	 * Flag que indica se pode ou não alterar o nome do arquivo de propriedades.
	 */
	private static boolean properties_not_allow_changing;

	/**
	 * @param p_name
	 */
	public synchronized static void setPropertyFileName(final String p_name) {
		if (properties_not_allow_changing)
			throw new RuntimeException("Nome do arquivo de propriedade so pode ser alterado uma vez!");
		s_log.info("Setando nome do arquivo para: " + p_name);

		properties_not_allow_changing = true;
		PROPERTIES = p_name;
	}

	public static ConfigManager getInstance() throws FileNotFoundException, IOException {
		if (instance == null) {
			instance = new ConfigManagerProperties();
		}
		return instance;
	}

	private File file;
	private File implFile;

	
	public String getServiceName(String p_serviceName) {
		String property = getProperty("xml.rpc." + p_serviceName);
		log.info(property);
		return property;
	}

	protected void loadDefaltProperties() throws PropertiesNotFoundException {
		prop = new Properties();
		try {
			InputStream fileReader = getDefaultPropertyInputStream();
			if (fileReader != null)
				prop.load(fileReader);
		} catch (FileNotFoundException _e) {
			// TODO Auto-generated catch block
			_e.printStackTrace();
		} catch (IOException _e) {
			throw new PropertiesNotFoundException(_e);
		}
	}

	@Override
	protected void loadImplProperties() throws PropertiesNotFoundException {
		implProp = new Properties();
		try {
			InputStream fileReader = getDefaultImplProperties();
			implProp.load(fileReader);
		} catch (IOException e) {
			throw new PropertiesNotFoundException(e);
		}
	}

	/**
	 * @return
	 */
	private InputStream getDefaultImplProperties() {
		InputStream fileReader;
		try {
			File l_file = getImplPropertyFile();
			fileReader = new FileInputStream(l_file);
		} catch (FileNotFoundException _e) {
			log.info("não achei o arquirvo simples! vou tentar pegar o FileInputStream!");

			fileReader = getFileInputStream(IMPLEMENTATIONS);
		}
		return fileReader;
	}

	/**
	 * @return
	 */
	private InputStream getDefaultPropertyInputStream() {
		InputStream fileReader = null;
		try {
			File l_file = getDefaultPropertyFile();
			fileReader = new FileInputStream(l_file);
		} catch (FileNotFoundException _e) {
			log.info("não consegui acessar o arquirvo!" + " Tentando pegar o FileInputStream!");

			try {
				fileReader = getFileInputStream(PROPERTIES);
			} catch (Exception e) {
				log.info("Problemas ao obter o arquivo de configuração: ", e);
			}
		}
		return fileReader;
	}

	/**
	 * @param _properties
	 * @return
	 */
	private InputStream getFileInputStream(String _properties) {
		log.info("Tentando obter o Resource As Stream: " + _properties);

		return getClass().getResourceAsStream(_properties);
	}

	private File getDefaultPropertyFile() throws FileNotFoundException {
		if (file == null) {
			file = FileUtils.getFile(PROPERTIES);
		}
		return file;
	}

	


	/**
	 * @see br.srv.full.configmanager.ConfigManager#setPropertyOnFile(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public void setPropertyOnFile(String p_prop, Object p_value) {
		setPropertyOnFile(p_prop, p_value.toString());
	}

	/**
	 * 
	 * @see br.srv.full.configmanager.ConfigManager#setPropertyOnFile(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void setPropertyOnFile(String p_prop, String p_value) {
		File l_file;
		try {
			l_file = getDefaultPropertyFile();
		} catch (FileNotFoundException _e) {
			throw new RuntimeException(_e);
		}
		try {
			FileInputStream l_fileReader = new FileInputStream(l_file);
			Properties l_prop = new Properties();
			l_prop.load(l_fileReader);
			l_prop.setProperty(p_prop, p_value);
			FileOutputStream l_fileWriter = new FileOutputStream(l_file);
			l_prop.store(l_fileWriter, "última Propriedade Alterada: " + p_prop);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private File getImplPropertyFile() throws FileNotFoundException {
		if (implFile == null) {
			implFile = FileUtils.getFile(IMPLEMENTATIONS);
		}
		return implFile;
	}
}
