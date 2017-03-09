/**
 * 
 */
package interfacePackages;

/**
 * @author Ali
 *
 */
public interface BallLauncherInterface {
	/**
	 * Places a ball in the cup of the catapult
	 * @return False if there are no more balls, true otherwise
	 */
	public boolean reload();
	
	/**
	 * Launches the ball using the catapult motor
	 */
	public void fire();
	
	/**
	 * Resets the position of the arm
	 * @return Returns true if the arm was able to successfully return to its start position
	 */
	public boolean reset();

}
