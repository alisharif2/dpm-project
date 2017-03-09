/**
 * 
 */
package interfacePackages;

import utilityPackages.Coordinate;

/**
 * @author Ali
 *
 */
public interface PathfinderInterface {
	
	/**
	 * Pull data from the generated path's coordinate stack
	 * @return The next coordinate to travel to on the path
	 */
	public Coordinate getNextStep();
	
	/**
	 * Checks if the path's coordinate stack is empty
	 * @return False if teh stack is empty
	 */
	public boolean hasNextStep();

}
