/**
 * Main class to generate the IPCC Inventory Software compliant file
 */
package org.openforis.collect.earth.ipcc;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.openforis.collect.earth.app.CollectEarthUtils;
import org.openforis.collect.earth.app.service.LocalPropertiesService;
import org.openforis.collect.earth.app.view.DataFormat;
import org.openforis.collect.earth.app.view.InfiniteProgressMonitor;
import org.openforis.collect.earth.app.view.JFileChooserExistsAware;
import org.openforis.collect.earth.ipcc.controller.LandUseSubdivisionUtils;
import org.openforis.collect.earth.ipcc.view.AssignSubdivisionTypesWizard;
import org.openforis.idm.metamodel.Survey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Alfonso Sanchez-Paus
 *
 */
@Component
public class IPCCGenerator {

	@Autowired
	IPCCRDBGenerator ipccRdbGenerator;
	
	@Autowired
	IPCCDataExportTimeSeriesXML ipccDataExportToXML;
	
	@Autowired
	LocalPropertiesService localPropertiesService;

	@Autowired
	IPCCDataExportMatrixExcel dataExportMatrixExcel;
	
	@Autowired
	IPCCLandUses landUses;
	
	IPCCSurveyAdapter ipccSurveyAdapter;

	Logger logger = LoggerFactory.getLogger( IPCCGenerator.class );

	public static final int END_YEAR = Calendar.getInstance().get(Calendar.YEAR); // Assume the last year is current year
	public static final int START_YEAR = 2000;  // Assume start year at 2000

	public File generateRDB( Survey survey, InfiniteProgressMonitor progressListener) throws IPCCGeneratorException {

		ipccSurveyAdapter = new IPCCSurveyAdapter();

		// Add attributes for each year containing the LU Category and Subdivision if not present
		Survey modifiedSurvey = ipccSurveyAdapter.addIPCCAttributesToSurvey( survey );

		// Generate Relational Database of the survey data
		ipccRdbGenerator.generateRelationalDatabase( modifiedSurvey, progressListener);

		return null;
	}

	public void produceOutputs( InfiniteProgressMonitor progressListener ) {
		
		progressListener.hide();
		
		// Assign Management types to the Land Use Subdivisions found in the survey data
		
		AssignSubdivisionTypesWizard wizard = new AssignSubdivisionTypesWizard();
		wizard.initializeTypes(landUses.getLandUseSubdivisions());
		
		if( !wizard.isWizardFinished() ) {
			logger.info( "The user closed the wizard without finishing assigning management types");
			return;
		}
		
		File[] exportToFile = JFileChooserExistsAware.getFileChooserResults(DataFormat.GHGI_ZIP_FILE, true, false, "LandUseForGHGi", localPropertiesService, null);

		if( exportToFile== null || exportToFile.length != 1 ) {
			logger.info("The user should choose a ZIP file to export the results to! No file chosen, aborting the rest of the execution");
			return;
		}
		
		try {
			File destinationZip = exportToFile[0];
			
			progressListener.updateProgress(1, 4, "Generating subdivisions file" );
			progressListener.show();
			
			// Generate list of subdivisions in survey
			File subdivisionsFile = LandUseSubdivisionUtils.getSubdivisionsXML();
							
			progressListener.updateProgress(2, 4, "Generating XML timeseries file" );
			// Extract data from the Relational Database into an XML File with information per year
			File timeseriesXMLFile =ipccDataExportToXML.generateTimeseriesData(IPCCGenerator.START_YEAR, IPCCGenerator.END_YEAR );

			progressListener.updateProgress(3, 4, "Generating Excel LU Matrixes per year" );
			// 	Extract data from the Relational Database into an excel file of transition Matrixes per year
			File matrixXLSFile =dataExportMatrixExcel.generateTimeseriesData(null, START_YEAR, END_YEAR);

			try {
				progressListener.updateProgress(3, 4, "Compressing files into selected destination" );
				CollectEarthUtils.addFileToZip( destinationZip , timeseriesXMLFile, "LU_Timeseries.xml");
				CollectEarthUtils.addFileToZip( destinationZip , matrixXLSFile, "LU_Matrixes.xls");
				CollectEarthUtils.addFileToZip( destinationZip , subdivisionsFile, "LU_Subdivisions.xml");
				progressListener.hide();
			} catch (IOException e) {
				logger.error("Error when creating ZIP file with timeseries content " + destinationZip, e); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (Exception e) {
				logger.error("Error when zipping the timeseries content into " + destinationZip, e); //$NON-NLS-1$ //$NON-NLS-2$
			}
			CollectEarthUtils.openFile( destinationZip );

			
		} catch (IOException e) {
			logger.error("Error generating file", e);
		}
	/*
		// Convert CSV time-series into XML compliant format
		File xmlFormattedFile = convertCSVtoXML(csvFileTimeseries);

		return signXMLFile( xmlFormattedFile );
*/
	}

	private File signXMLFile(File xmlFormattedFile) {
		// TODO Auto-generated method stub
		return null;
	}

	private File convertCSVtoXML(File csvFileTimeseries) {
		// TODO Auto-generated method stub
		return null;
	}

	private File generateCSVTimeseries() {
		// TODO Auto-generated method stub
		return null;
	}





}
