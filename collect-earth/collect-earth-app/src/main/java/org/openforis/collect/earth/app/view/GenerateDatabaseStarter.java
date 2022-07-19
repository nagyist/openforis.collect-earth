package org.openforis.collect.earth.app.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openforis.collect.earth.app.service.ExportType;
import org.openforis.collect.earth.app.service.GenerateDatabase;
import org.openforis.collect.earth.ipcc.RdbExportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class GenerateDatabaseStarter {
	
	private Logger logger = LoggerFactory.getLogger( GenerateDatabaseStarter.class);
	private JFrame frame;
	private GenerateDatabase generateDatabase;
	private boolean  shouldRefreshDb;
	private boolean starting = false;
	private InfiniteProgressMonitor progressMonitor;

	public boolean isShouldRefreshDb() {
		return shouldRefreshDb;
	}

	public void setShouldRefreshDb(boolean shouldRefreshDb) {
		this.shouldRefreshDb = shouldRefreshDb;
	}

	GenerateDatabaseStarter( final GenerateDatabase saikuService, final JFrame frame) {
		super();
		this.generateDatabase = saikuService;
		this.frame = frame;
	}
	
	public boolean shouldShowRdbGenerationOption(ExportType exportType){
		return generateDatabase.isRdbAlreadyGenerated( exportType );
	}

	public boolean isStarting() {
		return starting;
	}
	
	public void initializeAndOpen() {
		progressMonitor = new InfiniteProgressMonitor( frame, Messages.getString("GenerateDatabaseStarter.1"), Messages.getString("GenerateDatabaseStarter.2")); //$NON-NLS-1$ //$NON-NLS-2$
		
		Thread threadGeneratingDB = new Thread("Initialize RDB"){ //$NON-NLS-1$
			@Override
			public void run() {
				starting = true;
				generateDatabase.setRefreshDatabase( shouldRefreshDb  );
				try {
					generateDatabase.prepareDataForAnalysis(progressMonitor);
				}catch ( RdbExportException e1) {
					JOptionPane.showMessageDialog(  frame , "<html>" + Messages.getString("CollectEarthWindow.29") + "<br>" +Messages.getString("CollectEarthWindow.40") + "<br/>" + e1.getMessage() + "</html>", Messages.getString("CollectEarthWindow.47"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
					logger.warn("The saiku server is not configured", e1); //$NON-NLS-1$ 
				} catch ( RuntimeException e) {
					logger.error("Error starting Saiku", e); //$NON-NLS-1$ 
				} catch ( Exception e) {
					logger.error("Error starting Saiku", e); //$NON-NLS-1$ 
				} finally{
					starting = false;
					if( progressMonitor != null ){
						progressMonitor.close();
					}
				}
			}
		};
		
		threadGeneratingDB.start();

		progressMonitor.showLater();		
		
		if( progressMonitor.isUserCancelled() ){
			generateDatabase.setUserCancelledOperation(true);
		}
	}

}