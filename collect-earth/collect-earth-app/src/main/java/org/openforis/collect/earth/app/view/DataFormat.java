package org.openforis.collect.earth.app.view;

enum DataFormat{
	ZIP_WITH_XML(new String[]{"zip"}),CSV(new String[]{"csv"}),FUSION(new String[]{"csv"}),COLLECT_COORDS(new String[]{"ced", "csv"}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

	private String[] fileExtension;
	private DataFormat(String[] fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String[] getPossibleFileExtensions() {
		return fileExtension;
	}
	
	public String getDefaultExtension() {
		return fileExtension[0];
	}
}