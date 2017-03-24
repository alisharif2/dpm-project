package dpmproject;
import java.util.ArrayList;
import java.util.Collections;

import interfacePackages.SensorInterface;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class FrameworkUSLocalizer {
	public enum LocalizationType { FALLING_EDGE, RISING_EDGE }
	
	private final float ROTATION_SPEED = (float) GlobalDefinitions.TURN_SPEED;
	private final int MIN_WALL_DIST = 45;

	private Odometer odo;
	private SensorInterface usSensor;
	private LocalizationType locType;
	
	public FrameworkUSLocalizer(Odometer odo, SensorInterface us, LocalizationType locType) {
		this.odo = odo;
		this.usSensor = us;
		this.locType = locType;
	}
	
	public void doLocalization() {
		double alpha, beta;
		odo.getAng();
		
		// Initialize our locomotor
		Navigation localizerLocomotor = new Navigation(odo);
	
		if (locType == LocalizationType.FALLING_EDGE) {
			// rotate the robot until it sees no wall
			localizerLocomotor.setSpeeds(ROTATION_SPEED, -ROTATION_SPEED);	// TODO use the turnTo method instead of this garbage
			
			// keep rotating until the robot sees a wall, then latch the angle
			while(true) {
				if(usSensor.getFilteredData() < MIN_WALL_DIST) {
					alpha = odo.getAng();
					break;
				}
			}
		
			// switch direction and wait until it sees no wall
			localizerLocomotor.setSpeeds(-ROTATION_SPEED, ROTATION_SPEED);	// TODO use the turnTo method instead of this garbage
			Delay.msDelay(2000);
			
			// keep rotating until the robot sees a wall, then latch the angle
			while(true) {
				if(usSensor.getFilteredData() < MIN_WALL_DIST) {
					beta = odo.getAng();
					break;	
				}
			}
			localizerLocomotor.halt();
	
			// angleA is clockwise from angleB, so assume the average of the
			// angles to the right of angleB is 45 degrees past 'north'
			double headingCorrection = 0;
			if(alpha > beta) {
				headingCorrection = 225 - (alpha + beta)/2;
			} else {
				headingCorrection = 45 - (alpha + beta)/2;
			}
						
			// update the odometer position with our new heading(zero degrees)
			odo.setPosition(new double [] {0, 0, headingCorrection + beta - 90}, new boolean [] {false, false, true});
			localizerLocomotor.turnTo(0, true);
		} else {
			/*
			 * The robot should turn until it sees the wall, then look for the
			 * "rising edges:" the points where it no longer sees the wall.
			 * This is very similar to the FALLING_EDGE routine, but the robot
			 * will face toward the wall for most of it.
			 */
			
			//
			// FILL THIS IN
			//
			
			// rotate the robot until it sees no wall
			localizerLocomotor.setSpeeds(ROTATION_SPEED, -ROTATION_SPEED);	// TODO use the turnTo method instead of this garbage
			
			// keep rotating until the robot sees a wall, then latch the angle
			while(true) {
				if(usSensor.getFilteredData() < MIN_WALL_DIST) {
					alpha = odo.getAng();
					break;
				}
			}
		
			// switch direction and wait until it sees no wall
			Delay.msDelay(1000);
			
			// keep rotating until the robot no longer sees a wall, then latch the angle
			while(true) {
				if(usSensor.getFilteredData() > MIN_WALL_DIST) {
					beta = odo.getAng();
					break;
				}
			}
			localizerLocomotor.halt();
	
			// angleA is clockwise from angleB, so assume the average of the
			// angles to the right of angleB is 45 degrees past 'north'
			double headingCorrection = 0;
			if(alpha > beta) {
				headingCorrection = 225 - (alpha + beta)/2;
			} else {
				headingCorrection = 45 - (alpha + beta)/2;
			}
			
			// update the odometer position with our new heading(zero degrees)
			int SYSTEMATIC_ERR = 11;
			odo.setPosition(new double [] {0.0, 0.0, headingCorrection + beta - 90 - SYSTEMATIC_ERR}, new boolean [] {false, false, true});
			localizerLocomotor.turnTo(0, true);
		}
	}

}
