package dpmproject;

import interfacePackages.OdometerInterface;
import interfacePackages.PathfinderInterface;
import interfacePackages.PilotInterface;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.utility.Delay;
import utilityPackages.Coordinate;

public class Pilot implements PilotInterface {
	private PathfinderInterface pathfinder;
	private Navigation nav;
	public Pilot(PathfinderInterface p, Odometer odo) {
		// TODO Auto-generated constructor stub
		this.pathfinder = p;
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
