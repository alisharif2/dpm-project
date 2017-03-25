package dpmproject;

import interfacePackages.OdometerInterface;
import interfacePackages.PathfinderInterface;
import interfacePackages.PilotInterface;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.utility.Delay;
import utilityPackages.Coordinate;

public class Pilot implements PilotInterface {
	private PathfinderInterface pathfinder;
	private Odometer odo;
	private Navigation nav;
	private EV3LargeRegulatedMotor leftMotor = GlobalDefinitions.LEFT_MOTOR;
	private EV3LargeRegulatedMotor rightMotor = GlobalDefinitions.RIGHT_MOTOR;
	private int FORWARD_SPEED = (int) GlobalDefinitions.FORWARD_SPEED;
	private int ROTATE_SPEED = (int) GlobalDefinitions.TURN_SPEED;
	private double track = GlobalDefinitions.WHEEL_BASE;
	private double radius = GlobalDefinitions.WHEEL_RADIUS;


	public Pilot(PathfinderInterface p, Odometer odo) {
		// TODO Auto-generated constructor stub
		this.pathfinder = p;
		this.odo = odo;
		this.nav = new Navigation(odo);
	}

	@Override
	public void travel() {
		// TODO Auto-generated method stub
		while(this.pathfinder.hasNextStep()) {
			Coordinate c = pathfinder.getNextStep();
			nav.travelTo(c.x, c.y);
		}
	}
}
