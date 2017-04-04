package dpmproject;

import interfacePackages.PathfinderInterface;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import utilityPackages.Coordinate;

public class TestDriver {
	
	private PathfinderInterface pathfinder;
	private Odometer odo;
	private EV3LargeRegulatedMotor leftMotor = GlobalDefinitions.LEFT_MOTOR;
	private EV3LargeRegulatedMotor rightMotor = GlobalDefinitions.RIGHT_MOTOR;
	private int FORWARD_SPEED = (int) GlobalDefinitions.FORWARD_SPEED;
	private int ROTATE_SPEED = (int) GlobalDefinitions.TURN_SPEED;
	private double track = GlobalDefinitions.WHEEL_BASE;
	private double radius = GlobalDefinitions.WHEEL_RADIUS;
	
	final static int FAST = (int) GlobalDefinitions.FORWARD_SPEED, SLOW = (int) GlobalDefinitions.TURN_SPEED, ACCELERATION = (int) GlobalDefinitions.ACCELERATION;
	final static double DEG_ERR = GlobalDefinitions.DEG_ERR, CM_ERR = GlobalDefinitions.LIN_ERR;
	
	public TestDriver(Odometer odo){
		this.odo = odo;

		EV3LargeRegulatedMotor[] motors = this.odo.getMotors();
		this.leftMotor = motors[0];
		this.rightMotor = motors[1];

		// set acceleration
		this.leftMotor.setAcceleration(ACCELERATION);
		this.rightMotor.setAcceleration(ACCELERATION);
	}
	
	public void diagonalTravelTo(Coordinate coordinate){
		
		double minAng = getAng(coordinate);
		leftMotor.setSpeed(ROTATE_SPEED);
		rightMotor.setSpeed(ROTATE_SPEED);
		
		this.turnTo(minAng, true);
		
		leftMotor.setSpeed(FORWARD_SPEED);
		rightMotor.setSpeed(FORWARD_SPEED);
		
		double distance = Math.sqrt(Math.pow(odo.getX() - coordinate.x, 2) + Math.pow(odo.getY() - coordinate.y, 2));
		
		leftMotor.rotate(convertDistance(radius, distance), true);
		rightMotor.rotate(convertDistance(radius, distance), false);
	}
	
	public void travelTo(Coordinate coordinate){
		
		Coordinate coordinateY = new Coordinate(odo.getX(), coordinate.y);
		
		double minAng = getAng(coordinateY);
		
		leftMotor.setSpeed(ROTATE_SPEED);
		rightMotor.setSpeed(ROTATE_SPEED);
		
		this.turnTo(minAng, true);
		
		leftMotor.setSpeed(FORWARD_SPEED);
		rightMotor.setSpeed(FORWARD_SPEED);

		double distance = Math.abs(odo.getY() - coordinate.y);
		
		leftMotor.rotate(convertDistance(radius, distance), true);
		rightMotor.rotate(convertDistance(radius, distance), false);
		
		
		Coordinate coordinateX = new Coordinate(coordinate.x, odo.getY());
		
		minAng = getAng(coordinateX);
		
		leftMotor.setSpeed(ROTATE_SPEED);
		rightMotor.setSpeed(ROTATE_SPEED);
		
		this.turnTo(minAng, true);
		
		leftMotor.setSpeed(FORWARD_SPEED);
		rightMotor.setSpeed(FORWARD_SPEED);

		distance = Math.abs(odo.getX() - coordinate.x);
		
		leftMotor.rotate(convertDistance(radius, distance), true);
		rightMotor.rotate(convertDistance(radius, distance), false);
		
	}
	
	public double getAng(Coordinate coordinate){
		
		double minAng = (Math.atan2(coordinate.y - odo.getY(), coordinate.x - odo.getX())) * (180.0 / Math.PI);
		if (minAng < 0)
			minAng += 360.0;
		
		return minAng;
		
	}
	
	public void turnTo(double angle, boolean stop) {

		double error = angle - this.odo.getAng();

		while (Math.abs(error) > GlobalDefinitions.DEG_ERR) {

			error = angle - this.odo.getAng();

			if (error < -180.0) {
				this.setSpeeds(-SLOW, SLOW);
			} else if (error < 0.0) {
				this.setSpeeds(SLOW, -SLOW);
			} else if (error > 180.0) {
				this.setSpeeds(SLOW, -SLOW);
			} else {
				this.setSpeeds(-SLOW, SLOW);
			}
		}

		if (stop) {
			this.setSpeeds(0, 0);
		}
	}
	
	public void setSpeeds(float lSpd, float rSpd) {
		this.leftMotor.setSpeed(lSpd);
		this.rightMotor.setSpeed(rSpd);
		if (lSpd < 0)
			this.leftMotor.backward();
		else
			this.leftMotor.forward();
		if (rSpd < 0)
			this.rightMotor.backward();
		else
			this.rightMotor.forward();
	}

	public void setSpeeds(int lSpd, int rSpd) {
		this.leftMotor.setSpeed(lSpd);
		this.rightMotor.setSpeed(rSpd);
		if (lSpd < 0)
			this.leftMotor.backward();
		else
			this.leftMotor.forward();
		if (rSpd < 0)
			this.rightMotor.backward();
		else
			this.rightMotor.forward();
	}
		
	
	// Conversion formulae taken from lab 2
	private static int convertDistance(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}

	private static int convertAngle(double radius, double track, double angle) {
		return convertDistance(radius, Math.PI * track * angle / 360.0);
	}
}
