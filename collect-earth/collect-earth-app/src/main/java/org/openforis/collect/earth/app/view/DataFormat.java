package org.openforis.collect.earth.app.view;

import java.util.Arrays;

public enum DataFormat{
	PROJECT_DEFINITION_FILE(
			new String[]{"cep"}, Messages.getString("JFileChooserExistsAware.0")), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			ZIP_WITH_XML(new String[]{"zip", "collect-data"},Messages.getString("CollectEarthWindow.48")), //$NON-NLS-1$ //$NON-NLS-2$
			CSV(new String[]{"csv"}, Messages.getString("CollectEarthWindow.38")), //$NON-NLS-1$ //$NON-NLS-2$
			FUSION(new String[]{"csv"}, Messages.getString("CollectEarthWindow.49")), //$NON-NLS-1$ //$NON-NLS-2$
			COLLECT_COORDS(new String[]{"ced", "csv"}, "Collect Earth plots"),//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			KML_FILE(new String[]{"kml"}, "Google Earth Kml File"), //$NON-NLS-1$ //$NON-NLS-2$
			GHGI_XML_FILE(new String[]{"xml"}, "XML Land Use file for GHGi"), //$NON-NLS-1$ //$NON-NLS-2$
			COLLECT_BACKUP(new String[]{"collect-data"}, "Collect Backup");

	private String[] fileExtension;
	private String description;
	
	private DataFormat(String[] fileExtension, String description) {
		this.fileExtension = fileExtension;
		this.description = description;
	}

	public String[] getPossibleFileExtensions() {
		return fileExtension;
	}
	
	public String getDefaultExtension() {
		return fileExtension[0];
	}
	
	
	public String getDescription() {
		return description;
	}
	
	public boolean checkFileExtensionMatches( String fileExtensionToCheck){
		for (String ext : fileExtension) {
			if( ext.equalsIgnoreCase( fileExtensionToCheck)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(fileExtension);
	}

}