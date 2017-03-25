/**
 * 
 */
package dpmproject;

import java.util.ArrayList;
import java.util.Collections;

import interfacePackages.SensorInterface;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/**
 * @author Ali
 *
 */
public class FilteredUltrasonicSensor extends Thread implements SensorInterface {
	
	private SampleProvider ultrasonicSampleProvider;
	private float[] ultrasonicData;
	private double reading;
	private int filterControl = 0;
	
	private static final int SENSOR_POLL_PERIOD = 10, NUMBER_OF_SAMPLES = 5;
	private static final int FILTER_OUT = 20;
	
	public FilteredUltrasonicSensor(EV3UltrasonicSensor usSensor) {
		// TODO Auto-generated constructor stub
		this.ultrasonicSampleProvider = usSensor.getDistanceMode();
		this.ultrasonicData = new float[ultrasonicSampleProvider.sampleSize()];
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
		// Don't use even numbers
		double filteredDistance;
		double distance;
		ArrayList<Double> samples = new ArrayList<Double>();
		
		for(int i = 0;i < NUMBER_OF_SAMPLES;++i) {
			distance = getRawData();
			
			// I stole this filter from lab1
			filteredDistance = distance;
			if (distance >= 255 && filterControl < FILTER_OUT) {
				// bad value, do not set the distance var, however do increment the
				// filter value
				filterControl++;
			} else if (distance >= 255) {
				// We have repeated large values, so there must actually be nothing
				// there: leave the distance alone
				filteredDistance = distance;
			} else {
				// distance went below 255: reset filter and leave
				// distance alone.
				filterControl = 0;
				filteredDistance = distance;
			}
			samples.add(filteredDistance);
			// Use an extremely short delay to make sure we get useful sensor readings
			Delay.msDelay(SENSOR_POLL_PERIOD);
		}
		
		// Sort and pick middle value
		Collections.sort(samples);
		filteredDistance = samples.get((int)(NUMBER_OF_SAMPLES/2));
		return filteredDistance;
	}

	/* (non-Javadoc)
	 * @see interfacePackages.SensorInterface#getRawData()
	 */
	@Override
	public double getRawData() {
		// TODO Auto-generated method stub
		ultrasonicSampleProvider.fetchSample(ultrasonicData, 0);
		// Convert to centimeters
		return ultrasonicData[0] * 100;
	}

	@Override
	public double read() {
		// TODO Auto-generated method stub
		return 0;
	}

}
