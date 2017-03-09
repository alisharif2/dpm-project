/**
 * 
 */
package interfacePackages;

/**
 * @author Ali
 *
 */
public interface OdometerCorrectionInterface {
	
	/**
	 * The OdometerCorrector Thread will now correct the x coordinate using the gridlines
	 */
	public void correctX();
	
	/**
	 * The OdometerCorrector Thread will now correct the y coordinate using the gridlines
	 */
	public void correctY();
	
	/**
	 * Pause the correction during obstacle avoidance
	 */
	public void pause();
	
	/**
	 * Resume the correction
	 */
	public void resume();

}
