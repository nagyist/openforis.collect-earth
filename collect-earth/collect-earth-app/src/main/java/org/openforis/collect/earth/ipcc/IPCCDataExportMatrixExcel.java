package org.openforis.collect.earth.ipcc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openforis.collect.earth.app.service.ExportType;
import org.openforis.collect.earth.app.service.RDBConnector;
import org.openforis.collect.earth.app.service.RegionCalculationUtils;
import org.openforis.collect.earth.app.service.SchemaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import liquibase.pro.packaged.E;

@Component
public class IPCCDataExportMatrixExcel extends RDBConnector {

	private String schemaName;

	Logger logger = LoggerFactory.getLogger(IPCCDataExportMatrixExcel.class);

	@Autowired
	private SchemaService schemaService;

	public IPCCDataExportMatrixExcel() {
		setExportTypeUsed(ExportType.IPCC);
	}

	public void generateTimeseriesData( File excelFileDestination, int startYear, int endYear ) {
		schemaName = schemaService.getSchemaPrefix(getExportTypeUsed());

		List<MatrixSheet> matrixSheets = new ArrayList<MatrixSheet>();

		for (int year = startYear; year < endYear; year++) {
			MatrixSheet yearMatrixData = generateLUMatrixForYear(year);
			if (yearMatrixData != null)
				matrixSheets.add(yearMatrixData);
		}

		createExcel( matrixSheets);
	}


	private MatrixSheet generateLUMatrixForYear(int year) {

		List<LUDataPerYear> luData = getJdbcTemplate().query(
				"select " 
						+ IPCCSurveyAdapter.getIpccCategoryAttrName(year) + ", " 
						+ IPCCSurveyAdapter.getIpccCategoryAttrName(year + 1) + ","
						+ IPCCSurveyAdapter.getIpccSubdivisionAttrName(year) + ","
						+ IPCCSurveyAdapter.getIpccSubdivisionAttrName(year + 1) + ","
						+ "sum( " + RegionCalculationUtils.EXPANSION_FACTOR + ")" 
						+ " from " + schemaName + "plot " 
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

		YearData yearData = new YearData(year, luData);

		return new MatrixSheet( yearData );
	}


	protected RowMapper<LUDataPerYear> getRowMapper() {
		return new RowMapper<LUDataPerYear>() {
			@Override
			public LUDataPerYear mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new LUDataPerYear(
						new LUSubdivision( rs.getString(1), rs.getString(3) ),
						new LUSubdivision( rs.getString(2), rs.getString(4) ) ,
						rs.getDouble(5) // area
						);
			}
		};
	}

	private LUDataPerYear findLuData( LUSubdivision initialSubdivision, LUSubdivision finalSubdivision, List<LUDataPerYear> luData ) {
		Collection<E> result = CollectionUtils.select(luData, new Predicate() {
			public boolean evaluate(Object a) {
				return 
						( (LUDataPerYear) a ).getLu().equals(initialSubdivision) 
						&& 
						( (LUDataPerYear) a ).getLu().equals(initialSubdivision);
			}
		});
		if( result.size() == 1 )
			return (LUDataPerYear) result.toArray(new LUDataPerYear[result.size()])[0];
		else
			return new LUDataPerYear(initialSubdivision, finalSubdivision, 0);

	}

	private void createExcel( List<MatrixSheet> matrixData ) {
		try {
			// Create a Workbook
			Workbook workbook = new HSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

			/* CreationHelper helps us create instances of various things like DataFormat, 
			   Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
			//CreationHelper createHelper = workbook.getCreationHelper();

			for (MatrixSheet matrix : matrixData) {
				// Create a Sheet
				Sheet sheet = workbook.createSheet("LU Matrix " + matrix.getYearData().getYear() + "/" + (matrix.getYearData().getYear()+1 ) );

				// Create a Font for styling header cells
				Font headerFont = workbook.createFont();
				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 14);
				headerFont.setColor(IndexedColors.RED.getIndex());

				// Create a CellStyle with the font
				CellStyle headerCellStyle = workbook.createCellStyle();
				headerCellStyle.setFont(headerFont);

				// Create a Row
				Row headerRow = sheet.createRow(0);
				Cell cell = headerRow.createCell(0);
				cell.setCellValue( "Transition " +  matrix.getYearData().getYear() + "/" + ( matrix.getYearData().getYear() +1 ) );

				int i = 1;
				for (LUSubdivision subdivision : matrix.getSubdivisions()) {

					cell = headerRow.createCell(i++);
					cell.setCellValue(subdivision.toString());
					cell.setCellStyle(headerCellStyle);

				}

				// Create Other rows and cells with employees data
				int rowNum = 1;
				int colNum = 1;
				for (LUSubdivision subdivisionH : matrix.getSubdivisions()) {
					Row row = sheet.createRow(rowNum++);

					row.createCell(0)
					.setCellValue(subdivisionH.toString());

					colNum = 1;
					for (LUSubdivision subdivisionV : matrix.getSubdivisions()) {

						cell = row.createCell(colNum++);
						cell.setCellValue( findLuData( subdivisionH, subdivisionV, matrix.getYearData().getLuData() ).getAreaHa() );
						cell.setCellStyle(headerCellStyle);					
					}

				}

				// Resize all columns to fit the content size
				for(int j = 0; j < colNum; j++) {
					sheet.autoSizeColumn(j);
				}

			}

			// Write the output to a file
			try( FileOutputStream fileOut = new FileOutputStream("poi-generated-file.xls") ){
				workbook.write(fileOut);
				// Closing the workbook
				workbook.close();
			} catch (IOException e) {
				logger.error("Error generating Excel file", e);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
