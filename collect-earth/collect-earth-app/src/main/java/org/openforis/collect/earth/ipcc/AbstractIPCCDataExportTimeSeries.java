package org.openforis.collect.earth.ipcc;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openforis.collect.earth.app.service.RegionCalculationUtils;
import org.openforis.collect.earth.ipcc.controller.LandUseSubdivisionUtils;
import org.openforis.collect.earth.ipcc.model.StratumObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractIPCCDataExportTimeSeries<E> extends AbstractIPCCDataExport {

	Logger logger = LoggerFactory.getLogger(AbstractIPCCDataExportTimeSeries.class);


	public File generateTimeseriesData( int startYear, int endYear ) throws IOException {

		initSchemaName();

		List<StratumObject> strataClimate = getStrataClimate();
		List<StratumObject> strataSoil = getStrataSoil();
		List<StratumObject> strataGEZ = getStrataGEZ();

		List<E> strataData = new ArrayList<E>();

		for (int year = startYear; year < endYear; year++) {
			for (StratumObject gez : strataGEZ) {
				for (StratumObject climate : strataClimate) {
					for (StratumObject soil : strataSoil) {
						E yearLuData = (E) generateLUTimeseriesForStrata(year, gez, climate, soil);
						if (yearLuData != null)
							strataData.add(yearLuData);
					}
				}
			}
		}

		return generateFile( strataData);

	}

	protected abstract File generateFile( List<E> strataData) throws IOException;

	private StratumPerYearData generateLUTimeseriesForStrata(int year, StratumObject gez, StratumObject climate,StratumObject soil) {

		List<LUDataPerYear> luData = getJdbcTemplate().query(
				
			"select " 
				+ IPCCSurveyAdapter.getIpccCategoryAttrName(year) + ", "
				+ IPCCSurveyAdapter.getIpccCategoryAttrName(year + 1) + ","
				+ IPCCSurveyAdapter.getIpccSubdivisionAttrName(year) + ","
				+ IPCCSurveyAdapter.getIpccSubdivisionAttrName(year + 1) + ", sum( "
				+ RegionCalculationUtils.EXPANSION_FACTOR + ")" 
				+ " from " + getSchemaName() + PLOT_TABLE 
				+ " where " 
				+ CLIMATE_COLUMN + " = " + climate.getValue() + " and " + SOIL_COLUMN + " = " + soil.getValue() + " and " + GEZ_COLUMN + " = " + gez.getValue() 
				+ " GROUP BY "
				+ IPCCSurveyAdapter.getIpccCategoryAttrName(year) + ","
				+ IPCCSurveyAdapter.getIpccCategoryAttrName(year + 1) + ","
				+ IPCCSurveyAdapter.getIpccSubdivisionAttrName(year) + ","
				+ IPCCSurveyAdapter.getIpccSubdivisionAttrName(year + 1), 
			
			getRowMapper()
			
		);

		if (luData.size() == 0) { // No LU data for the climate, soil, gez combination
			return null;
		}

		StratumPerYearData strataPerYearData = new StratumPerYearData(year, climate.getLabel(), soil.getLabel(), gez.getLabel());
		strataPerYearData.setLuData(luData);
		return strataPerYearData;
	}

	protected RowMapper<LUDataPerYear> getRowMapper() {
		return new RowMapper<LUDataPerYear>() {
			@Override
			public LUDataPerYear mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				String categoryInitial = rs.getString(1);
				String categoryFinal = rs.getString(2);
				String subdivInitial = rs.getString(3);
				String subdivFinal = rs.getString(4);
				
				return new LUDataPerYear(
						LandUseSubdivisionUtils.getSubdivision(categoryInitial, subdivInitial),
						LandUseSubdivisionUtils.getSubdivision(categoryFinal, subdivFinal),
						rs.getDouble(5) // area
						);
			}
		};
	}

}
