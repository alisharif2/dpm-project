package dpmproject;
import java.util.ArrayList;

import interfacePackages.SensorInterface;
import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class LightLocalizer {
	private Odometer odo;
	private SensorInterface colorSensor;
	private float[] colorData;	
	
	public static final float ROTATION_SPEED = (float) GlobalDefinitions.TURN_SPEED;
	public static final double COLOR_SENSOR_OFFSET = GlobalDefinitions.LIGHT_SENSOR_OFFSET;	// Distance between center of rotation and the color sensor in cm
	private static final double BLACK_RGB = 0.4;
	private static final int AXIS_CROSS_DELAY = 300;
	
	public LightLocalizer(Odometer odo, SensorInterface colorSensor) {
		this.odo = odo;
		this.colorSensor = colorSensor;
	}
	
	public void doLocalization() {
		// drive to location listed in tutorial
		// start rotating and clock all 4 gridlines
		// do trig to compute (0,0) and 0 degrees
		// when done travel to (0,0) and turn to 0 degrees
		
		Navigation localizerLocomotor = new Navigation(this.odo);
		
		/* Store the angles that each axis is crossed at in an array
		 * [0] -> y-
		 * [1] -> x+
		 * [2] -> y+
		 * [3] -> x-
		 */
		ArrayList<Double> angles = new ArrayList<Double>();
		
		// Start the robot rotating
		localizerLocomotor.setSpeeds(-ROTATION_SPEED, ROTATION_SPEED);
		
		// This code assumes wer're in the third quadrant
		while(angles.size() < 4) {
			if(colorSensor.getFilteredData() < BLACK_RGB) {
				Sound.beep();
				angles.add(odo.getAng());
				Delay.msDelay(AXIS_CROSS_DELAY);
			}
		}
		localizerLocomotor.halt();
		
		// Now let's do some math
		// Lets calculate our x position
		double x = (-COLOR_SENSOR_OFFSET) * Math.cos(Math.toRadians(Math.abs(angles.get(0) - angles.get(2)))/2);
		// Lets calculate our y position
		double y = (COLOR_SENSOR_OFFSET) * Math.cos(Math.toRadians(Math.abs(angles.get(1) - angles.get(3)))/2);
		// Let's calculate our heading correction
		double headingCorrection = odo.getAng() + 270 - angles.get(0) + (Math.abs(angles.get(0) - angles.get(2)))/2;
		odo.setPosition(new double [] {x, y, headingCorrection}, new boolean [] {true, true, true});
	}

}
