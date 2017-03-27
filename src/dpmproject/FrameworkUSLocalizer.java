package dpmproject;
import java.util.ArrayList;
import java.util.Collections;

import interfacePackages.SensorInterface;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class FrameworkUSLocalizer {
	
	private final float ROTATION_SPEED = (float) GlobalDefinitions.TURN_SPEED;
	private final int MIN_WALL_DIST = 32;

	private Odometer odo;
	private SensorInterface usSensor;
	
	public FrameworkUSLocalizer(Odometer odo, SensorInterface us) {
		this.odo = odo;
		this.usSensor = us;
	}
	
	public void doLocalization() {
		double alpha, beta;
		odo.getAng();
		
		// Initialize our locomotor
		Navigation localizerLocomotor = new Navigation(odo);

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
		odo.setPosition(new double [] {0, 0, headingCorrection + beta}, new boolean [] {false, false, true});
		Delay.msDelay(200);
		localizerLocomotor.turnTo(180, true);
		
		double x = usSensor.getFilteredData() - GlobalDefinitions.TILE_SIZE;
		odo.setPosition(new double[] {x, x, 0}, new boolean [] {true, true, false});
		
		localizerLocomotor.travelTo(0, 0);
		localizerLocomotor.turnTo(0, true);
		Delay.msDelay(200);
		
		odo.setPosition(new double[] {GlobalDefinitions.startPoints[GlobalDefinitions.FWD_CORNER].x, GlobalDefinitions.startPoints[GlobalDefinitions.FWD_CORNER].y, GlobalDefinitions.startOrientations[GlobalDefinitions.FWD_CORNER]},
				new boolean[] {true, true, true});		
	}

}
