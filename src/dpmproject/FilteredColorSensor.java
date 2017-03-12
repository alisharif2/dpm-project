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
public class FilteredColorSensor extends Thread implements SensorInterface {
	
	private static final long SENSOR_POLL_PERIOD = 10, NUMBER_OF_SAMPLES = 5;
	private SampleProvider colorSensorSampleProvider;
	private float[] colorData;
	
	private double reading;
	
	public FilteredColorSensor(EV3ColorSensor colorSensor) {
		// TODO Auto-generated constructor stub
		this.colorSensorSampleProvider = colorSensor.getMode("RGB");
		this.colorData = new float[colorSensorSampleProvider.sampleSize()];
	}
	
	@Override
	public void run() {
		long updateStart, updateEnd;

		while (true) {
			updateStart = System.currentTimeMillis();
			
			synchronized (this) {
				this.reading = getFilteredData();
			}

			// this ensures that the odometer only runs once every period
			updateEnd = System.currentTimeMillis();
			if (updateEnd - updateStart < SENSOR_POLL_PERIOD * 10) {
				try {
					Thread.sleep(SENSOR_POLL_PERIOD * 10 - (updateEnd - updateStart));
				} catch (InterruptedException e) {
					// there is nothing to be done here because it is not
					// expected that the odometer will be interrupted by
					// another thread
				}
			}
		}
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
