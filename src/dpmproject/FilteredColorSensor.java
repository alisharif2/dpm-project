/**
 * 
 */
package dpmproject;

import interfacePackages.SensorInterface;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/**
 * @author Ali
 *
 */
public class FilteredColorSensor implements SensorInterface {
	
	private static final long SENSOR_POLL_PERIOD = 5, NUMBER_OF_SAMPLES = 5;
	private SampleProvider colorSensorSampleProvider;
	private float[] colorData;
	
	private double reading;
	
	public FilteredColorSensor(EV3ColorSensor colorSensor) {
		// TODO Auto-generated constructor stub
		this.colorSensorSampleProvider = colorSensor.getMode("RGB");
		this.colorData = new float[colorSensorSampleProvider.sampleSize()];
	}

	/* (non-Javadoc)
	 * @see interfacePackages.SensorInterface#getFilteredData()
	 */
	@Override
	public double getFilteredData() {
		// TODO Auto-generated method stub
		float filteredData = 0;
		
		// Apply mean filtering
		for(int i = 0;i < NUMBER_OF_SAMPLES;++i) {
			// Sum RGB components to get net RGB color value
			// Good enough to identify color intensity
			filteredData += getRawData();
			Delay.msDelay(SENSOR_POLL_PERIOD);
		}
		
		filteredData = filteredData / NUMBER_OF_SAMPLES;
		return filteredData;
	}

	/* (non-Javadoc)
	 * @see interfacePackages.SensorInterface#getRawData()
	 */
	@Override
	public double getRawData() {
		// TODO Auto-generated method stub
		colorSensorSampleProvider.fetchSample(colorData,  0);
		return colorData[0] + colorData[1] + colorData[2];
	}

	@Override
	public double read() {
		synchronized (this) {
			return reading;
		}
	}

}
