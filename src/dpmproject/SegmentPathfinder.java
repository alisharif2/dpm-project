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
		int xTiles = (int) (xTravel / GlobalDefinitions.TILE_SIZE);
		int yTiles = (int) (yTravel / GlobalDefinitions.TILE_SIZE);
		
		double finalX = odo.getX() + xTiles * GlobalDefinitions.TILE_SIZE;
		double finalY = odo.getY() + yTiles * GlobalDefinitions.TILE_SIZE;
		
		// Assume the robot is currently in the center of a tile
		for(int i = 1;i <= Math.abs(xTiles);++i) {
			coords.push(new Coordinate(odo.getX() + GlobalDefinitions.TILE_SIZE * i * Math.signum(xTravel), odo.getY()));
		}
		
		for(int i = 1;i <= Math.abs(yTiles);++i) {
			coords.push(new Coordinate(finalX, odo.getY() + GlobalDefinitions.TILE_SIZE * i * Math.signum(yTravel)));
		}
		
		coords.push(target);
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
