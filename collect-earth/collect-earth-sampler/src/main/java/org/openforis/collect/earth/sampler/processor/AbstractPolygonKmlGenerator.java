package org.openforis.collect.earth.sampler.processor;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openforis.collect.earth.core.utils.CsvReaderUtils;
import org.openforis.collect.earth.sampler.model.SimpleCoordinate;
import org.openforis.collect.earth.sampler.model.SimplePlacemarkObject;
import org.openforis.collect.earth.sampler.utils.KmlGenerationException;
import org.openforis.collect.model.CollectSurvey;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public abstract class AbstractPolygonKmlGenerator extends KmlGenerator {

	private static final Integer DEFAULT_INNER_POINT_SIDE = 2;
	protected Integer innerPointSide;
	protected final String localPort;
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected double distanceBetweenSamplePoints;
	protected double distancePlotBoundary;
	protected int numberOfSamplePoints;
	protected Integer largeCentralPlotSide;
	private String distanceToBuffers;

	public AbstractPolygonKmlGenerator(String epsgCode, String hostAddress, String localPort, Integer innerPointSide, Integer numberOfPoints, double distanceBetweenSamplePoints, double distancePlotBoundary, Integer largeCentralPlotSide, String distanceToBuffers) {
		super(epsgCode);
		this.hostAddress = hostAddress;
		this.localPort = localPort;
		this.innerPointSide = innerPointSide;
		this.distanceBetweenSamplePoints = distanceBetweenSamplePoints;
		this.distancePlotBoundary = distancePlotBoundary;
		this.distanceToBuffers = distanceToBuffers;
		this.setNumberOfSamplePoints(numberOfPoints);
		this.setLargeCentralPlotSide(largeCentralPlotSide);
	}

	public Integer getLargeCentralPlotSide() {
		return largeCentralPlotSide;
	}

	public void setLargeCentralPlotSide(Integer largeCentralPlotSide) {
		this.largeCentralPlotSide = largeCentralPlotSide;
	}

	protected int getPointSide() {
		if (innerPointSide == null) {
			innerPointSide = DEFAULT_INNER_POINT_SIDE;
		}
		return innerPointSide;
	}

	protected List<SimpleCoordinate> getSamplePointPolygon(double[] topLeftPositionLatLong, int samplePointSide) throws TransformException {
		final List<SimpleCoordinate> coords = new ArrayList<>();
		coords.add(new SimpleCoordinate(topLeftPositionLatLong)); // TOP-LEFT
		coords.add(new SimpleCoordinate(getPointWithOffset(topLeftPositionLatLong, samplePointSide, 0))); // TOP-RIGHT
		coords.add(new SimpleCoordinate(getPointWithOffset(topLeftPositionLatLong, samplePointSide, samplePointSide))); // BOTTOM-RIGHT
		coords.add(new SimpleCoordinate(getPointWithOffset(topLeftPositionLatLong, 0, samplePointSide))); // BOTTOM-LEFT

		// close the square
		coords.add(new SimpleCoordinate(topLeftPositionLatLong)); // TOP-LEFT
		return coords;
	}

	@Override
	protected Map<String, Object> getTemplateData(String csvFile, CollectSurvey collectSurvey, boolean kmlExport) throws KmlGenerationException {
		final Map<String, Object> data = new HashMap<>();

		SimplePlacemarkObject previousPlacemark = null;

		Rectangle2D viewFrame = new Rectangle2D.Float();
		boolean firstPoint = true;
		// Read CSV file so that we can store the information in a Map that can
		// be used by freemarker to do the "goal-replacement"
		String[] csvRow;
		String[] headerRow =null;
		int rowNumber = 0 ;

		List<SimplePlacemarkObject> placemarks = new ArrayList<>();
		try ( CSVReader reader = CsvReaderUtils.getCsvReader(csvFile) ){

			while ((csvRow = reader.readNext()) != null) {
				try {
					if( headerRow == null ){
						headerRow = csvRow;
					}

					// Check that the row is not just an empty row with no data
					if( CsvReaderUtils.onlyEmptyCells(csvRow)){
						// If the row is empty ( e.g. : ",,,,," ) jump to next row
						continue;
					}

					final SimplePlacemarkObject currentPlacemark = getPlotObject(csvRow, headerRow, collectSurvey, kmlExport);


					Double X = Double.parseDouble( currentPlacemark.getCoord().getLongitude() );
					Double Y = Double.parseDouble( currentPlacemark.getCoord().getLatitude() );

					if( X > 180 || X < -180 || Y > 90 || Y< -90 ){
						throw new KmlGenerationException( "The coordinates are wrong for row number :" + rowNumber + " with latitude " + Y + " and longitude " + X + " (latitude must be a number between  -90 and 90 and longitude between -180 and 180) ");
					}


					if (firstPoint) {
						viewFrame.setRect(X, Y, 0, 0);
						firstPoint = false;
					} else {
						final Rectangle2D rectTemp = new Rectangle2D.Float();
						rectTemp.setRect(X, Y, 0, 0);
						viewFrame = viewFrame.createUnion(rectTemp);
					}


					if (previousPlacemark != null) {
						// Give the current ID to the previous placemark so that
						// we can move from placemark to placemark
						previousPlacemark.setNextPlacemarkId(currentPlacemark.getPlacemarkId());
					}

					previousPlacemark = currentPlacemark;

					fillSamplePoints( currentPlacemark);

					fillExternalLine( currentPlacemark);

					fillBuffersAroundPlot( currentPlacemark );

					placemarks.add(currentPlacemark);

				} catch (final Exception e) {
					if(rowNumber > 0 ){
						logger.error( "Error on the CSV file ", e );
						throw new KmlGenerationException("Error in the CSV " + csvFile + " \r\n for row " + rowNumber + " = " + Arrays.toString( csvRow ), e);
					}else{
						logger.info("Error while reading the first line of the CSV fle, probably cause by the column header names");
					}
				}finally{
					rowNumber++;
				}
			}
		} catch (final IOException | CsvValidationException e) {
			throw new KmlGenerationException("Error reading CSV " + csvFile , e);
		}

		final DecimalFormat df = new DecimalFormat("#.###");

		data.put("placemarks", placemarks);
		data.put("region_north", Double.toString( viewFrame.getMaxY() ) );
		data.put("region_south", Double.toString(viewFrame.getMinY() ));
		data.put("region_west", Double.toString(viewFrame.getMinX() ));
		data.put("region_east",  Double.toString( viewFrame.getMaxX() ));
		data.put("region_center_X", df.format(viewFrame.getCenterX()));
		data.put("region_center_Y", df.format(viewFrame.getCenterY()));
		data.put("host", hostAddress);
		data.put("local_port", localPort );
		data.put("plotFileName", KmlGenerator.getCsvFileName(csvFile));
		return data;
	}

	private void fillBuffersAroundPlot(SimplePlacemarkObject currentPlacemark) {
		final List<SimplePlacemarkObject> buffers = new ArrayList<>();
		// when there is a property in the earth.properties like this : distance_to_buffers=70,112,194
		if( StringUtils.isNotBlank( distanceToBuffers )) {
			String[] distances =  StringUtils.split( distanceToBuffers, ',' );


			try {
				for (String bufDistStr : distances) {

					List<SimpleCoordinate> bufferPoints = new ArrayList<>();

					Integer bufDist = Integer.parseInt( bufDistStr );

					// TOP LEFT
					bufferPoints.add(new SimpleCoordinate( getPointWithOffset( currentPlacemark.getCoord().getCoordinates(), -bufDist, bufDist) ) );

					// TOP RIGHT
					bufferPoints.add(new SimpleCoordinate( getPointWithOffset( currentPlacemark.getCoord().getCoordinates(), bufDist, bufDist) ));

					// BOTTOM RIGHT
					bufferPoints.add( new SimpleCoordinate( getPointWithOffset( currentPlacemark.getCoord().getCoordinates(), bufDist, -bufDist) ) );

					// BOTTOM LEFT
					bufferPoints.add(new SimpleCoordinate(  getPointWithOffset( currentPlacemark.getCoord().getCoordinates(), -bufDist, -bufDist) ) ) ;

					// TOP LEFT -- CLOSE RECTANGLE
					bufferPoints.add( new SimpleCoordinate( getPointWithOffset( currentPlacemark.getCoord().getCoordinates(), -bufDist, bufDist) ) );

					SimplePlacemarkObject spo = new SimplePlacemarkObject();

					spo.setShape(bufferPoints);

					buffers.add( spo );

				}
			} catch (NumberFormatException e) {
				logger.error("Error reading number", e);
			} catch (TransformException e) {
				logger.error("Error transforming coordinate", e);
			}
		}

		// TOP LEFT -- CLOSE RECTANGLE
		currentPlacemark.setBuffers( buffers );
	}


	protected int getNumberOfSamplePoints() {
		return numberOfSamplePoints;
	}

	protected void setNumberOfSamplePoints(int numberOfSamplePoints) {
		this.numberOfSamplePoints = numberOfSamplePoints;
	}


}
