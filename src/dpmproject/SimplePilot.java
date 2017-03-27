package dpmproject;

import interfacePackages.OdometerInterface;
import interfacePackages.PathfinderInterface;
import interfacePackages.PilotInterface;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.utility.Delay;
import utilityPackages.Coordinate;

public class SimplePilot implements PilotInterface {
	private PathfinderInterface pathfinder;
	private Odometer odo;
	private EV3LargeRegulatedMotor leftMotor = GlobalDefinitions.LEFT_MOTOR;
	private EV3LargeRegulatedMotor rightMotor = GlobalDefinitions.RIGHT_MOTOR;
	private int FORWARD_SPEED = (int) GlobalDefinitions.FORWARD_SPEED;
	private int ROTATE_SPEED = (int) GlobalDefinitions.TURN_SPEED;
	private double track = GlobalDefinitions.WHEEL_BASE;
	private double radius = GlobalDefinitions.WHEEL_RADIUS;
	private OdometerCorrection corrector;


	public SimplePilot(PathfinderInterface p, Odometer odo, OdometerCorrection corrector) {
		// TODO Auto-generated constructor stub
		this.pathfinder = p;
		this.odo = odo;
		this.corrector = corrector;
	}

	@Override
	public void travel() {
		// TODO Auto-generated method stub
		while(this.pathfinder.hasNextStep()) {
			corrector.correctY();
			Coordinate c = pathfinder.getNextStep();
			travelTo(c.x, c.y);
			
			corrector.correctX();
			c = pathfinder.getNextStep();
			travelTo(c.x, c.y);
		}
	}
	
	// This method does a bunch of math and moves the robot
	// This method is blocking
	public void travelTo(double x, double y) {

		// Calculate the new heading using getAngle
		double newHeading = getAngle(x, y);
		// Turn to the new heading
		turnTo(newHeading);

		// Get the distance between the points
		double distance = Math.sqrt(Math.pow(odo.getX() - x, 2) + Math.pow(odo.getY() - y, 2));

		leftMotor .setSpeed(FORWARD_SPEED);
		rightMotor.setSpeed(FORWARD_SPEED);

		leftMotor.rotate(convertDistance(radius, distance), true);
		rightMotor.rotate(convertDistance(radius, distance), false);
	}

	// Turn by a certain amount of radians
	public void turnTo(double theta) {
		leftMotor.setSpeed(ROTATE_SPEED);
		rightMotor.setSpeed(ROTATE_SPEED);

		leftMotor.rotate(-convertAngle(radius, track, Math.toDegrees(theta)), true);
		rightMotor.rotate(convertAngle(radius, track, Math.toDegrees(theta)), false);
	}

	// Get angle between robot's direction vector and target point's relative
	// position vector
	private double getAngle(double x, double y) {
		double heading = odo.getAng();
		double theta_d = Math.atan2(y - odo.getY(), x - odo.getX()); // returns
		// angle
		double err = theta_d - heading;
		return err;
	}


	// Conversion formulae taken from lab 2
	private static int convertDistance(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}

	private static int convertAngle(double radius, double track, double angle) {
		return convertDistance(radius, Math.PI * track * angle / 360.0);
	}

	public void halt() {
			leftMotor.stop();
			rightMotor.stop();
	}
	
}
