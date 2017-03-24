package dpmproject;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.SampleProvider;

public class RoughUSLocalizer {
	public enum LocalizationType { FALLING_EDGE, RISING_EDGE };
	public static int ROTATION_SPEED = 100;

	private Odometer odo;
	private SampleProvider usSensor;
	private float[] usData;
	private LocalizationType locType;
	private EV3LargeRegulatedMotor leftMotor;
	private EV3LargeRegulatedMotor rightMotor;

	
	public RoughUSLocalizer(Odometer odo,  SampleProvider usSensor, float[] usData, LocalizationType locType,EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor) {
		this.odo = odo;
		this.usSensor = usSensor;
		this.usData = usData;
		this.locType = locType;
		this.leftMotor=leftMotor;
		this.rightMotor=rightMotor;

	}
	
	public void doLocalization() {
		double [] pos = new double [3];
		double angleA, angleB;
		double theta;
		
		if (locType == LocalizationType.FALLING_EDGE) {
			// rotate the robot until it sees no wall
			
			while(getFilteredData()<55){
				turnRight(5.0);
			
			}
			
			// keep rotating until the robot sees a wall, then latch the angle
			while(getFilteredData()>47){
				turnRight(2.0);
				
			}
			angleA=odo.getAng();
				
				
			// switch direction and wait until it sees no wall
			while(getFilteredData()<43){
				turnLeft(2.0);
			}
			while(getFilteredData()<47){
				turnLeft(2.0);
			}
			// keep rotating until the robot sees a wall, then latch the angle
			while(getFilteredData()>45){
				turnLeft(2.0);
			}
			angleB=odo.getAng();
			
			// angleA is clockwise from angleB, so assume the average of the
			// angles to the right of angleB is 45 degrees past 'north'
			if(angleA<angleB){
				theta=-2-(angleA+angleB)/2; //calibrated angle to turn
			}
			else{
				theta=225-47-(angleA+angleB)/2; // experimentally calibrated value
			}
			
			// update the odometer position (example to follow:)
			turnRight(odo.getAng()+theta);

			odo.setPosition(new double [] {0.0, 0.0,0}, new boolean [] {true, true, true});
		} else {
			/*
			 * The robot should turn until it sees the wall, then look for the
			 * "rising edges:" the points where it no longer sees the wall.
			 * This is very similar to the FALLING_EDGE routine, but the robot
			 * will face toward the wall for most of it.
			 */
			while(getFilteredData()>33){ //make robot face wall
				turnRight(5);
			}
			while(getFilteredData()<33){//turn until rising edge
				turnRight(2);
			}
			angleA=odo.getAng(); // latch angle
			while(getFilteredData()>28){
				turnLeft(2);
			}
			while(getFilteredData()<33){//switch directions
				turnLeft(2);
			}
			angleB=odo.getAng();//latch angle once found rising edge
			if(angleB<angleA){
				theta=-(angleA+angleB)/2;//calibrated angle to turn
			}
			else{
				theta=225-45-(angleA+angleB)/2;//calibrated angle to turn
			}
			
			// update the odometer position (example to follow:)
			turnRight(odo.getAng()+theta+3);
			odo.setPosition(new double [] {0.0, 0.0,0}, new boolean [] {true, true, true});
		
		}
	}
	
	private void turnLeft(double degrees) {
		leftMotor.setSpeed(ROTATION_SPEED);
		rightMotor.setSpeed(ROTATION_SPEED);

		leftMotor.rotate(convertAngle(2.1, 17.0, (int)degrees%360), true);
		rightMotor.rotate(-convertAngle(2.1, 17.0,(int) degrees%360), false);		
	}

	private float getFilteredData() {
		usSensor.fetchSample(usData, 0);
		float distance = usData[0];
		if (distance>250){
					distance=50;
				}
		return distance*100;
	}
	
	private void turnRight(double degrees){
		leftMotor.setSpeed(ROTATION_SPEED);
		rightMotor.setSpeed(ROTATION_SPEED);

		leftMotor.rotate(-convertAngle(2.1, 17.0, (int)degrees%360), true);
		rightMotor.rotate(convertAngle(2.1, 17.0,(int) degrees%360), false);
	}
	private static int convertAngle(double radius, double width, double angle) {
		return convertDistance(radius, Math.PI * width * angle / 360.0);
	}
	
	private static int convertDistance(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}
}