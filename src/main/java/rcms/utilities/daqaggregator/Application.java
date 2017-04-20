package rcms.utilities.daqaggregator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import rcms.utilities.daqaggregator.datasource.FlashlistType;
import rcms.utilities.daqaggregator.datasource.LiveAccessServiceExplorer;

public class Application {

	private final Properties prop;

	private static Application instance;

	private static final Logger logger = Logger.getLogger(Application.class);

	private Application(String propertiesFile) {
		prop = load(propertiesFile);
	}

	public static Application get() {
		if (instance == null) {
			throw new RuntimeException("Not initialized");
		}
		return instance;
	}

	public static void initialize(String propertiesFile) {
		instance = new Application(propertiesFile);
		checkRequiredSettings();

		/*
		 * Setup proxy
		 */
		ProxyManager.get().startProxy();
		autodiscoverFlashilstsInLas();
	}

	private Properties load(String propertiesFile) {

		try {
			FileInputStream propertiesInputStream = new FileInputStream(propertiesFile);
			Properties properties = new Properties();
			properties.load(propertiesInputStream);

			return properties;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot run application without configuration file");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot run application without configuration file");
		}
	}

	/**
	 * Check if all required settings are present in configuration file
	 */
	private static void checkRequiredSettings() {
		for (Settings setting : Settings.values()) {
			if (setting.isRequired()) {
				if (!instance.prop.containsKey(setting.getKey()))
					throw new DAQException(DAQExceptionCode.MissingProperty, ": Required property missing " + setting);
			}
		}
	}

	/**
	 * Initialize Live Access Service urls from configuration file
	 */
	private static void autodiscoverFlashilstsInLas() {

		// read possible url from configuration file
		List<String> lasUrls = new ArrayList<String>();
		String[] lasURLs = instance.getProp(Settings.LAS_URL).split(" +");

		for (String url : lasURLs) {
			// System.out.println(url);
			lasUrls.add(url);
		}

		logger.info(
				lasUrls.size() + " LAS urls will be explored to find " + FlashlistType.values().length + " flashlists");

		LiveAccessServiceExplorer flashlistDiscovery = new LiveAccessServiceExplorer(lasUrls);
		flashlistDiscovery.exploreLiveAccessServices();

		for (FlashlistType flashlistType : FlashlistType.values()) {

			String lasUrl = flashlistDiscovery.getFlashlistUrl(flashlistType.getFlashlistName());
			if (lasUrl == null) {
				throw new DAQException(DAQExceptionCode.FlashlistNotFound,
						"Cannot find flashlist " + flashlistType.getFlashlistName() + " in any of the given LASes");
			}
			flashlistType.setUrl(lasUrl);

		}

		logger.info("All flashlists sucessfully discovered:");
		for (FlashlistType flashlistType : FlashlistType.values()) {
			logger.info(String.format("%1$-26s", flashlistType.getFlashlistName()) + " " + flashlistType.getUrl());
		}
	}

	public String getProp(Settings setting) {
		Object property = prop.get(setting.getKey());
		if (property != null) {
			return property.toString();
		} else
			return null;
	}

	public Properties getProp() {
		return prop;
	}
}
