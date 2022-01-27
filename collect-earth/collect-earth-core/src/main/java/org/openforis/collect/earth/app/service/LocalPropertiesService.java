package org.openforis.collect.earth.app.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Properties;
import java.util.TreeSet;

import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.openforis.collect.earth.app.EarthConstants.CollectDBDriver;
import org.openforis.collect.earth.app.EarthConstants.OperationMode;
import org.openforis.collect.earth.app.EarthConstants.SAMPLE_SHAPE;
import org.openforis.collect.earth.app.EarthConstants.UI_LANGUAGE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Class to access Collect Earth configuration. This class is used all over the
 * code in order to fetch the values of the properties that can be configured by
 * the user, by directly editing the earth.properties file or by using the
 * Tools--Properties menu option in the Collect Earth Window.
 *
 * @author Alfonso Sanchez-Paus Diaz
 *
 */
@Component
public class LocalPropertiesService extends Observable {

	/**
	 * Enumeration containing the names of all the possible values that can be
	 * configured in Collect Earth.
	 *
	 * @author Alfonso Sanchez-Paus Diaz
	 *
	 */
	public enum EarthProperty {
		ACTIVE_PROJECT_DEFINITION("active_project_definition"), ALTERNATIVE_BALLOON_FOR_BROWSER(
				"alternative_balloon_for_browser"), AUTOMATIC_BACKUP("automatic_backup"), BALLOON_TEMPLATE_KEY(
				"balloon"), BALLOON_TEMPLATE_KEY_CHECKSUM("balloon_checksum"), BING_MAPS_KEY(
				"bing_maps_key"), BROWSER_TO_USE("use_browser"), CHROME_BINARY_PATH(
				"chrome_exe_path"), CRS_KEY("coordinates_reference_system"), CSV_KEY_CHECKSUM(
				"csv_checksum"), DB_DRIVER("db_driver"), DB_HOST(
				"db_host"), DB_NAME("db_name"), DB_PASSWORD(
				"db_password"), DB_PORT("db_port"), DB_USERNAME(
				"db_username"), DISTANCE_BETWEEN_PLOTS(
				"distance_between_plots"), DISTANCE_BETWEEN_SAMPLE_POINTS(
				"distance_between_sample_points"), DISTANCE_TO_PLOT_BOUNDARIES(
				"distance_to_plot_boundaries"), ELEVATION_GEOTIF_DIRECTORY(
				"elevation_geotif_directory"), EXCEPTION_SHOWN(
				"exception_shown"), EXTRA_MAP_URL(
				"extra_map_url"), FIREFOX_BINARY_PATH(
				"firefox_exe_path"), GEE_EXPLORER_URL(
				"gee_explorer_url"), GEE_FUNCTION_PICK(
				"gee_js_pickFunction"), GEE_INITIAL_ZOOM(
				"gee_initial_zoom"), GEE_JS_LIBRARY_URL(
				"gee_js_library_url"), GEE_PLAYGROUND_URL(
				"gee_playground_url"), GEE_ZOOM_METHOD(
				"gee_js_zoom_method"), GEE_ZOOM_OBJECT(
				"gee_js_zoom_object"), GENERATED_KEY(
				"generated_on"), GOOGLE_MAPS_API_KEY(
				"google_maps_api_key"), HERE_MAPS_APP_CODE(
				"here_app_code"), HERE_MAPS_APP_ID(
				"here_app_id"), HOST_KEY(
				"host"), HOST_PORT_KEY(
				"port"), INNER_SUBPLOT_SIDE(
				"inner_point_side"), JUMP_TO_NEXT(
				"jump_to_next_plot"), KML_TEMPLATE_KEY(
				"template"), KML_TEMPLATE_KEY_CHECKSUM(
				"template_checksum"), LAST_EXPORTED_DATE(
				"last_exported_survey_date"), LAST_IGNORED_UPDATE(
				"last_ignored_update_version"), LAST_USED_FOLDER(
				"last_used_folder"), LOADED_PROJECTS(
				"loaded_projects"), LOCAL_PORT_KEY(
				"local_port"), METADATA_FILE(
				"metadata_file"), MODEL_VERSION_NAME(
				"model_version_name"), NUMBER_OF_SAMPLING_POINTS_IN_PLOT(
				"number_of_sampling_points_in_plot"), OPEN_BALLOON_IN_BROWSER(
				"open_separate_browser_form"), OPEN_BING_MAPS(
				"open_bing_maps"), OPEN_GEE_EXPLORER(
				"open_earth_engine"), OPEN_GEE_CODE_EDITOR(
				"open_gee_playground"), OPEN_HERE_MAPS(
				"open_here_maps"), OPEN_STREET_VIEW(
				"open_street_view"), OPEN_TIMELAPSE(
				"open_timelapse"), OPEN_YANDEX_MAPS(
				"open_yandex_maps"), OPERATION_MODE(
				"operation_mode"), OPERATOR_KEY(
				"operator"), SAIKU_SERVER_FOLDER(
				"saiku_server_folder"), SAMPLE_FILE(
				"csv"), SAMPLE_SHAPE(
				"sample_shape"), SURVEY_NAME(
				"survey_name"), UI_LANGUAGE(
				"ui_language"), LARGE_CENTRAL_PLOT_SIDE(
				"large_central_plot_side"), OPEN_BAIDU_MAPS(
				"open_baidu_maps"), DISTANCE_TO_BUFFERS(
				"distance_to_buffers"), OPEN_PLANET_MAPS(
				"open_planet_maps"), PLANET_MAPS_KEY(
				"planet_maps_key"), PLANET_MAPS_MONHLY("planet_maps_monthly"), PLANET_MAPS_CE_KEY("planet_maps_ce_key"), OPEN_GEE_APP(
				"open_gee_app"), GEE_MAP_URL(
				"gee_app_url"), OPEN_MAXAR_SECUREWATCH(
				"open_maxar_securewatch"),MAXAR_SECUREWATCH_URL("secure_watch_url"), EARTH_MAP_URL("earth_map_url"),
				PLANET_NICFI_URL("planet_nicfi_url"),
				OPEN_EARTH_MAP("open_earth_map"),
				EARTH_MAP_LAYERS("earth_map_layers"),
				EARTH_MAP_SCRIPTS("earth_map_scripts"),
				EARTH_MAP_AOI("earth_map_aoi");

		private String name;

		private EarthProperty(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

	}

	public static final String DEFAULT_LOCAL_PORT = "8028";

	public static final String LOCAL_HOST = "127.0.0.1";

	private static final String PROPERTIES_FILE_PATH = FolderFinder.getCollectEarthDataFolder() + File.separator
			+ "earth.properties";
	private static final String PROPERTIES_FILE_PATH_FORCED_UPDATE = "earth.properties_forced_update";
	private static final String PROPERTIES_FILE_PATH_INITIAL = "earth.properties_initial";
	private Logger logger = null;
	private Properties properties;

	public LocalPropertiesService() {
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Error initilizeng Local Properties", e);
		}
	}

	public String convertToOSPath(String path) {
		String pathSeparator = File.separator;
		path = path.replace("/", pathSeparator);
		path = path.replace("\\", pathSeparator);
		return path;
	}

	public String getBalloonFile() {
		return convertToOSPath(getValue(EarthProperty.BALLOON_TEMPLATE_KEY));
	}

	public String getBalloonFileChecksum() {
		return getValue(EarthProperty.BALLOON_TEMPLATE_KEY_CHECKSUM);
	}

	public CollectDBDriver getCollectDBDriver() {

		final String collectDbDriver = getValue(EarthProperty.DB_DRIVER);
		if (collectDbDriver.length() == 0) {
			return CollectDBDriver.SQLITE;
		}
		return CollectDBDriver.valueOf(collectDbDriver);

	}

	public String getCrs() {
		return getValue(EarthProperty.CRS_KEY);
	}

	public String getCsvFile() {
		return convertToOSPath(getValue(EarthProperty.SAMPLE_FILE));
	}

	public String getCsvFileChecksum() {
		return getValue(EarthProperty.CSV_KEY_CHECKSUM);
	}

	private String getExportedSurveyName(String surveyName) {
		return EarthProperty.LAST_EXPORTED_DATE + "_" + surveyName;
	}

	public String getEarthMapAOI() {
		return getValue(EarthProperty.EARTH_MAP_AOI);
	}

	public String getEarthMapLayers() {
		return getValue(EarthProperty.EARTH_MAP_LAYERS);
	}

	public String getEarthMapScripts() {
		return getValue(EarthProperty.EARTH_MAP_SCRIPTS);
	}

	public String getEarthMapURL() {
		return getValue(EarthProperty.EARTH_MAP_URL);
	}

	public String getExtraMap() {
		return getValue(EarthProperty.EXTRA_MAP_URL);
	}

	public String getGEEAppURL() {
		return getValue(EarthProperty.GEE_MAP_URL);
	}

	public String getPlanetMapsKey() {
		return getValue(EarthProperty.PLANET_MAPS_KEY);
	}

	public String getPlanetMapsCeKey() {
		return getValue(EarthProperty.PLANET_MAPS_CE_KEY);
	}

	public String getGeePlaygoundUrl() {

		if (isPropertyActivated(EarthProperty.GEE_PLAYGROUND_URL)) {
			return getValue(EarthProperty.GEE_PLAYGROUND_URL);
		} else {
			return "https://code.earthengine.google.com/";
		}
	}

	public String getGeneratedOn() {
		return getValue(EarthProperty.GENERATED_KEY);
	}

	public String getHost() {
		if (getOperationMode().equals(OperationMode.CLIENT_MODE)) {
			return getValue(EarthProperty.HOST_KEY);
		} else {
			return LOCAL_HOST;
		}
	}

	public String getImdFile() {
		return convertToOSPath(getValue(EarthProperty.METADATA_FILE));
	}

	public Date getLastExportedDate(String surveyName) {
		final String value = (String) properties.get(getExportedSurveyName(surveyName));
		Date lastExported = null;
		try {
			if (!StringUtils.isBlank(value)) {
				lastExported = new Date(Long.parseLong(value));
			}
		} catch (final NumberFormatException e) {
			logger.error("Error parsing date", e);
		}

		return lastExported;
	}

	public String getLocalPort() {
		if (getOperationMode().equals(OperationMode.SERVER_MODE)) {
			return getPort();
		} else {
			return getValue(EarthProperty.LOCAL_PORT_KEY);
		}
	}

	public String getModelVersionName() {
		String modelVersion = (java.lang.String) properties.get(EarthProperty.MODEL_VERSION_NAME.toString());
		if (modelVersion != null && modelVersion.trim().length() == 0) {
			modelVersion = null;
		}
		return modelVersion;
	}

	public OperationMode getOperationMode() {
		final String instanceType = getValue(EarthProperty.OPERATION_MODE);
		if (instanceType.length() == 0) {
			return OperationMode.SERVER_MODE;
		}
		return OperationMode.valueOf(instanceType);
	}

	public String getOperator() {
		return getValue(EarthProperty.OPERATOR_KEY);
	}

	public String getPort() {
		String port = getValue(EarthProperty.HOST_PORT_KEY);
		if (StringUtils.isEmpty(port)) {
			port = DEFAULT_LOCAL_PORT;
		}
		return port;
	}

	public String getProjectFolder() {
		final File metadataFile = new File(getImdFile());
		return metadataFile.getParent();
	}

	public String getSaikuFolder() {
		final String configuredSaikuFolder = convertToOSPath(getValue(EarthProperty.SAIKU_SERVER_FOLDER));
		if (StringUtils.isBlank(configuredSaikuFolder)) {
			return ""; //$NON-NLS-1$
		} else {
			final File saikuFolder = new File(configuredSaikuFolder);
			return saikuFolder.getAbsolutePath();
		}
	}

	public SAMPLE_SHAPE getSampleShape() {
		final String value = getValue(EarthProperty.SAMPLE_SHAPE);
		if (StringUtils.isBlank(value)) {
			return SAMPLE_SHAPE.SQUARE;
		} else {
			return SAMPLE_SHAPE.valueOf(value);
		}
	}

	public String getSecureWatchURL() {
		return getValue(EarthProperty.MAXAR_SECUREWATCH_URL);
	}

	public String getPlanetNicfiURL() {
		return getValue(EarthProperty.PLANET_NICFI_URL);
	}

	public String getTemplateFile() {
		return convertToOSPath(getValue(EarthProperty.KML_TEMPLATE_KEY));
	}

	public String getTemplateFileChecksum() {
		return getValue(EarthProperty.KML_TEMPLATE_KEY_CHECKSUM);
	}

	public UI_LANGUAGE getUiLanguage() {
		final String value = getValue(EarthProperty.UI_LANGUAGE);
		if (StringUtils.isBlank(value)) {
			return UI_LANGUAGE.EN;
		} else {
			UI_LANGUAGE selected = null;
			try {
				selected = UI_LANGUAGE.valueOf(value.toUpperCase());
			} catch (Exception e) {
				logger.warn("Unknown UI Language " + value);
			}

			if (selected != null) {
				return selected;
			} else {
				return UI_LANGUAGE.EN;
			}
		}
	}

	public String getValue(EarthProperty key) {
		String value = (String) properties.get(key.toString());
		if (value == null) {
			value = "";
		}

		return value;
	}

	private void init() throws IOException {
		properties = new Properties() {
			/**
			 *
			 */
			private static final long serialVersionUID = 4358906731731542445L;

			@Override
			public synchronized Enumeration<Object> keys() {
				return Collections.enumeration(new TreeSet<Object>(super.keySet()));
			}

		};

		boolean newInstallation = false;

		File propertiesFileInitial = new File(PROPERTIES_FILE_PATH_INITIAL);
		logger = LoggerFactory.getLogger(LocalPropertiesService.class);
		try {

			File propertiesFile = new File(PROPERTIES_FILE_PATH);
			if (!propertiesFile.exists() || propertiesFile.length() < 300) {

				if (!propertiesFile.exists()) {
					final boolean success = propertiesFile.createNewFile();
					if (!success) {
						throw new IOException("Could not create file " + propertiesFile.getAbsolutePath());
					}
				}
				// The earth.properties file does not exists, this mean that the
				// earth_properties.initial file is used, only the first time
				propertiesFile = propertiesFileInitial;
				newInstallation = true;

			}

			try( FileReader fr = new FileReader(propertiesFile); ){
				properties.load(fr);

				if (!newInstallation) {
					// Add properties in initial_properties that are not present in earth.properites
					// so that adding new properties in coming version does not generate issues with
					// older versions
					if (propertiesFileInitial.exists()) {
						Properties initialProperties = new Properties();
						try( FileReader frPropsFileInit = new FileReader(propertiesFileInitial) ){
							initialProperties.load(frPropsFileInit);
							Enumeration<String> initialPropertyNames = (Enumeration<String>) initialProperties.propertyNames();
							while (initialPropertyNames.hasMoreElements()) {
								String nextElement = initialPropertyNames.nextElement();
								if (properties.get(nextElement) == null) {
									properties.put(nextElement, initialProperties.getProperty(nextElement));
								}
							}
						}
					}

					// UPDATERS!
					// Emergency procedure for forcing the change of a value for updaters!
					File propertiesForceChange = new File(PROPERTIES_FILE_PATH_FORCED_UPDATE);
					if (propertiesForceChange.exists()) {
						try( FileReader frProps = new FileReader(propertiesForceChange);){
							properties.load(frProps);
							// This procedure will only happen right after update
							propertiesForceChange.deleteOnExit();
						}
					}

				}
			}
		} catch (final FileNotFoundException e) {
			logger.error("Could not find properties file", e);
		} catch (final IOException e) {
			logger.error("Could not open properties file", e);
		}
	}

	public boolean isBaiduMapsSupported() {
		return isPropertyActivated(EarthProperty.OPEN_BAIDU_MAPS);
	}

	public boolean isBingMapsSupported() {
		return isPropertyActivated(EarthProperty.OPEN_BING_MAPS);
	}

	public boolean isGEEAppSupported() {
		return isPropertyActivated(EarthProperty.OPEN_GEE_APP);
	}

	public boolean isCodeEditorSupported() {
		return isPropertyActivated(EarthProperty.OPEN_GEE_CODE_EDITOR);
	}

	public boolean isEarthMapSupported() {
		return isPropertyActivated(EarthProperty.OPEN_EARTH_MAP);
	}


	public boolean isExplorerSupported() {
		return isPropertyActivated(EarthProperty.OPEN_GEE_EXPLORER);
	}

	public Boolean isExceptionShown() {
		return isPropertyActivated(EarthProperty.EXCEPTION_SHOWN);
	}

	public boolean isHereMapsSupported() {
		return isPropertyActivated(EarthProperty.OPEN_HERE_MAPS);
	}

	public boolean isPlanetMapsSupported() {
		return isPropertyActivated(EarthProperty.OPEN_PLANET_MAPS);
	}

	public boolean isPlanetMapsMonthlyOpen() {
		return isPropertyActivated(EarthProperty.PLANET_MAPS_MONHLY);
	}

	private boolean isPropertyActivated(EarthProperty earthProperty) {
		boolean supported = false;
		String value = getValue(earthProperty);
		if (StringUtils.isNotBlank(value)) {
			supported = Boolean.parseBoolean(value);
		}
		return supported;
	}

	public boolean isSecureWatchSupported() {
		return isPropertyActivated(EarthProperty.OPEN_MAXAR_SECUREWATCH);
	}

	public boolean isStreetViewSupported() {
		return isPropertyActivated(EarthProperty.OPEN_STREET_VIEW);
	}

	public boolean isTimelapseSupported() {
		return isPropertyActivated(EarthProperty.OPEN_TIMELAPSE);
	}

	public boolean isUsingPostgreSqlDB() {
		return getCollectDBDriver().equals(CollectDBDriver.POSTGRESQL);
	}

	public boolean isUsingSqliteDB() {
		return getCollectDBDriver().equals(CollectDBDriver.SQLITE);
	}

	public boolean isYandexMapsSupported() {

		return isPropertyActivated(EarthProperty.OPEN_YANDEX_MAPS);
	}

	public void nullifyChecksumValues() {
		saveBalloonFileChecksum("");
		saveCsvFileCehcksum("");
		saveTemplateFileChecksum("");
		storeProperties();
	}

	/**
	 * Removes the GEE obfuscated method/parameter names so that they are
	 * regenerated when GEE is accessed for the fist time. This way we avoid the bug
	 * when the re-obfuscation of GEE JS code changes the zooming method but does
	 * not provoke an error.
	 */
	@PreDestroy
	public void removeGeeProperties() {

		this.storeProperties();

		/*
		 * this.setValue(EarthProperty.GEE_ZOOM_METHOD, "", false);
		 * this.setValue(EarthProperty.GEE_ZOOM_OBJECT, "", false);
		 * this.setValue(EarthProperty.GEE_FUNCTION_PICK, "", true);
		 */
	}

	public void removeModelVersionName() {
		setValue(EarthProperty.MODEL_VERSION_NAME, "", true);
	}

	public void saveBalloonFileChecksum(String checksum) {
		setValue(EarthProperty.BALLOON_TEMPLATE_KEY_CHECKSUM, checksum);

	}

	public void saveCrs(String crsName) {
		setValue(EarthProperty.CRS_KEY, crsName);

	}

	public void saveCsvFile(String csvFile) {
		setValue(EarthProperty.SAMPLE_FILE, csvFile);

	}

	public void saveCsvFileCehcksum(String checksum) {
		setValue(EarthProperty.CSV_KEY_CHECKSUM, checksum);

	}

	public void saveGeneratedOn(String dateGenerated) {
		setValue(EarthProperty.GENERATED_KEY, dateGenerated);

	}

	public void saveHost(String hostName) {
		setValue(EarthProperty.HOST_KEY, hostName);

	}

	public void saveOperator(String operatorName) {
		setValue(EarthProperty.OPERATOR_KEY, operatorName);

	}

	public void savePort(String portName) {
		setValue(EarthProperty.HOST_PORT_KEY, portName);

	}

	public void saveTemplateFileChecksum(String checksum) {
		setValue(EarthProperty.KML_TEMPLATE_KEY_CHECKSUM, checksum);

	}

	public void setExceptionShown(Boolean showException) {
		setValue(EarthProperty.EXCEPTION_SHOWN, showException.toString(), true);
	}

	public void setJumpToNextPlot(String shouldSkip) {
		String booleanSkip = "";
		if (shouldSkip != null && shouldSkip.length() > 0) {
			if (shouldSkip.equals("on")) {
				booleanSkip = "true";
			} else if (shouldSkip.equals("off")) {
				booleanSkip = "false";
			}
		} else {
			booleanSkip = "false";
		}
		setValue(EarthProperty.JUMP_TO_NEXT, booleanSkip);

	}

	public void setLastExportedDate(String surveyName) {
		setValue(getExportedSurveyName(surveyName), Long.toString(System.currentTimeMillis()), true);
	}

	public void setModelVersionName(String modelVersionName) {
		setValue(EarthProperty.MODEL_VERSION_NAME, modelVersionName);
	}

	public void setSampleShape(SAMPLE_SHAPE shape) {
		setValue(EarthProperty.SAMPLE_SHAPE, shape.name());
	}

	public void setUiLanguage(UI_LANGUAGE language) {
		setValue(EarthProperty.UI_LANGUAGE, language.name(), true);
	}

	public void setValue(EarthProperty key, String value) {
		setValue(key, value, true);
		this.setChanged();
		this.notifyObservers(key);
	}

	private void setValue(EarthProperty key, String value, boolean forceWrite) {
		setValue(key.toString(), value, forceWrite);
	}

	private void setValue(String key, String value, boolean forceWrite) {
		properties.setProperty(key, value);
		if (forceWrite) {
			storeProperties();
		}
	}

	public boolean shouldJumpToNextPlot() {
		boolean jumpToNext = false;
		if (getValue(EarthProperty.JUMP_TO_NEXT) != null && getValue(EarthProperty.JUMP_TO_NEXT).length() > 0) {
			jumpToNext = Boolean.parseBoolean(getValue(EarthProperty.JUMP_TO_NEXT));
		}

		return jumpToNext;
	}

	private synchronized void storeProperties() {
		FileWriter fw = null;
		try {

			File propertiesFile = new File(PROPERTIES_FILE_PATH);
			fw = new FileWriter(propertiesFile);
			properties.store(fw, null);

		} catch (final IOException e) {
			logger.error("The properties could not be saved", e);
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (final IOException e) {
				logger.error("Error closing file writer", e);
			}
		}
	}



}
