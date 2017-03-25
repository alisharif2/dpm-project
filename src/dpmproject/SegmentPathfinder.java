/**
 * 
 */
package dpmproject;

import java.util.Stack;

import interfacePackages.PathfinderInterface;
import utilityPackages.Coordinate;

/**
 * @author ali
 *
 */
public class SegmentPathfinder implements PathfinderInterface {

	Stack<Coordinate> coords = new Stack<Coordinate>();
	/**
	 * 
	 */
	public SegmentPathfinder(Coordinate target, Odometer odo) {
		// TODO Auto-generated constructor stub
		double xTravel = target.x - odo.getX();
		double yTravel = target.y - odo.getY();
		
		// # of tiles to move in each axis
		int xTiles = (int) (xTravel / GlobalDefinitions.TILE_SIZE);
		int yTiles = (int) (yTravel / GlobalDefinitions.TILE_SIZE);
		// Expected y position of the robot after moving along y axis
		double finalY = yTiles * GlobalDefinitions.TILE_SIZE;
		
		coords.push(target);
		
		// move along x axis last
		for(int i = xTiles;i != 0;i = converge(i)) {
			coords.push(new Coordinate(i * GlobalDefinitions.TILE_SIZE, finalY));
		}
		
		// move along y axis first
		for(int i = yTiles;i != 0;i = converge(i)) {
			coords.push(new Coordinate(odo.getX(), i * GlobalDefinitions.TILE_SIZE));
		}
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
	
	// Converges any given integer towards zero
	private int converge(int n) {
		if(n > 0) return n - 1;
		if(n < 0) return n + 1;
		return 0;
	}

}
