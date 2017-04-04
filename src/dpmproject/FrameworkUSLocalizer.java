package dpmproject;
import java.util.ArrayList;
import java.util.Collections;

import interfacePackages.SensorInterface;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class FrameworkUSLocalizer {
	
	private final float ROTATION_SPEED = (float) GlobalDefinitions.TURN_SPEED;
	private final int MIN_WALL_DIST = 32;
	private double[] cornerPos;
	private double cornerAngle;
	private Odometer odo;
	private SensorInterface usSensor;
	
	public FrameworkUSLocalizer(Odometer odo, SensorInterface us) {
		this.odo = odo;
		this.usSensor = us;
	}
	
	public void doLocalization(int corner) {
		double alpha, beta;
		odo.getAng();
		if (corner==1){
			cornerPos=new double[] {0,0};
			cornerAngle=0;
		}
		if (corner==2){
			cornerPos=new double[] {10*GlobalDefinitions.TILE_SIZE+12,0};
			cornerAngle=270;
		}
		if (corner==3){
			cornerPos=new double[] {10*GlobalDefinitions.TILE_SIZE+12,10*GlobalDefinitions.TILE_SIZE+12};
			cornerAngle=180;
		}
		if (corner==4){
			cornerPos=new double[] {0,10*GlobalDefinitions.TILE_SIZE+12};
			cornerAngle=90;
		}
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
		odo.setPosition(new double [] {0, 0, headingCorrection + beta - cornerAngle}, new boolean [] {false, false, true});
		Delay.msDelay(200);
		localizerLocomotor.turnTo(90, true);
		
		
		double[] x=new double[] {cornerPos[0]-6,cornerPos[1]-9};
		odo.setPosition(new double[] {x[0], x[1], 0}, new boolean [] {true, true, false});
		
		localizerLocomotor.turnTo(0, true);
		Delay.msDelay(200);
	}

}