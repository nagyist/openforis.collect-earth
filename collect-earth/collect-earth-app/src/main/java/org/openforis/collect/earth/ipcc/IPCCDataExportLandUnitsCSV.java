package org.openforis.collect.earth.ipcc;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openforis.collect.earth.app.service.ExportType;
import org.openforis.collect.earth.app.service.RegionCalculationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class IPCCDataExportLandUnitsCSV extends IPCCDataExportCSV {

	private String schemaName;

	Logger logger = LoggerFactory.getLogger(IPCCDataExportLandUnitsCSV.class);

	public IPCCDataExportLandUnitsCSV() {
		super();
		setExportTypeUsed(ExportType.IPCC);
	}

	public File generateTimeseriesData( int startYear, int endYear ) throws IOException {
		schemaName = schemaService.getSchemaPrefix(getExportTypeUsed());
		List<String[]> luCombinations = generateLUCombinations(startYear, endYear);
		return createCsv( luCombinations);
	}


	private List<String[]> generateLUCombinations(int startYear, int endYear) {
		String selectedYears = IPCCDataExportTimeSeries.CLIMATE_COLUMN + ", " + IPCCDataExportTimeSeries.SOIL_COLUMN + " , " + IPCCDataExportTimeSeries.GEZ_COLUMN + " , " ;
		for( int year = startYear ; year <= endYear; year++ ) {
			selectedYears += IPCCSurveyAdapter.getIpccCategoryAttrName(year) + ", " 
					+ IPCCSurveyAdapter.getIpccSubdivisionAttrName(year) + ",";
		}

		String sqlSelect = "select " 
				+ selectedYears
				+ " sum( " + RegionCalculationUtils.EXPANSION_FACTOR + ")" 
				+ " from " + schemaName + IPCCDataExportTimeSeries.PLOT_TABLE  
				+ " GROUP BY "
				+ selectedYears.substring(0, selectedYears.length()-1)
				+ " ORDER BY sum( "+ RegionCalculationUtils.EXPANSION_FACTOR + " ) DESC"; // Remove trailing comma from list of years

		List<String[]> luData = getJdbcTemplate().query(
					sqlSelect
					, 
					getRowMapper()
				);

		selectedYears+= "area"; // so the area columns appears too
		luData.add( 0, selectedYears.split(",") );
		return luData;
	}

}
