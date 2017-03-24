/**
 * 
 */
package dpmproject;

import java.util.Stack;

import interfacePackages.OdometerInterface;
import interfacePackages.PathfinderInterface;
import utilityPackages.Coordinate;

/**
 * @author ali
 *
 */
public class SimplePathfinder implements PathfinderInterface {
	
	Stack<Coordinate> coords = new Stack<Coordinate>();
	
	public SimplePathfinder(Coordinate target, OdometerInterface odo) {
		// TODO Auto-generated constructor stub
		
		// First follow y axis
		coords.push(new Coordinate(target.x, target.y));
		coords.push(new Coordinate(odo.getX(), target.y));

	}

	@Override
	public Coordinate getNextStep() {
		// TODO Auto-generated method stub
		return coords.pop();
	}

	@Override
	public boolean hasNextStep() {
		// TODO Auto-generated method stub
		return !coords.isEmpty();
	}
}
