package interfacePackages;

public interface OdometerInterface {

	// return X value
	double getX();

	// return Y value
	double getY();

	// return theta value
	double getAng();

	// set x,y,theta
	/**
	 * Updates the odometer's position. Meant to be used by the OdometerCorrection class
	 * @param position An array containing the new information in this form [x position, y position, heading]
	 * @param update An array describing which values to update. Example: If element 0 is true then update x position
	 */
	void setPosition(double[] position, boolean[] update);

	// return x,y,theta
	/**
	 * Places the latest positional data into the array 'position'
	 * @param position An array that will contain [x position, y position, heading]
	 */
	void getPosition(double[] position);

	/**
	 * See definition for void getPosition(double[])
	 * @return An array containing positional information in the form x position, y position, heading]
	 */
	double[] getPosition();

}