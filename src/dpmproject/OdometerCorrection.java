package dpmproject;
import interfacePackages.OdometerCorrectionInterface;
import interfacePackages.OdometerInterface;
import lejos.hardware.Sound;

/**
 * 
 */

/**
 * @author Ali
 *
 */
public class OdometerCorrection extends Thread implements OdometerCorrectionInterface {
	
	private static final long RUN_PERIOD = 100;
	private FilteredColorSensor leftColorSensor;
	private FilteredColorSensor rightColorSensor;
	private Odometer odo;
	private CORRECTION_TYPE axis = CORRECTION_TYPE.NONE;
	private enum CORRECTION_TYPE {X, Y, NONE};
	
	private double lineCountX = 0;
	private double lineCountY = 0;
	
	public OdometerCorrection(Odometer odo, FilteredColorSensor leftColor, FilteredColorSensor rightColor) {
		// TODO Auto-generated constructor stub
		this.leftColorSensor = leftColor;
		this.rightColorSensor = rightColor;
		this.odo = odo;
	}
	
	@Override
	synchronized public void run() {
		long updateStart, updateEnd;
		
		// Variables for y correction
		double leftDistanceY = 0, rightDistanceY = 0, thetaY = 0;
		boolean leftSensorDetectY = false, rightSensorDetectY = false;
		lineCountY = 0;
		
		// Variables for x correction
		double leftDistanceX = 0, rightDistanceX = 0, thetaX = 0;
		boolean leftSensorDetectX = false, rightSensorDetectX = false;
		lineCountX = 0;
		
		double dLine1 = 0;
		double dLine2 = 0;
		double actualD = 0;
		

		while (true) {
			updateStart = System.currentTimeMillis();
			
			if(axis == CORRECTION_TYPE.Y) {
				if(this.leftColorSensor.getFilteredData() < 0.2) {
					leftDistanceY = odo.getY();
					leftSensorDetectY = true;
				}
				if(this.rightColorSensor.getFilteredData() < 0.3) {
					rightDistanceY = odo.getY();
					rightSensorDetectY = true;
				}
				
				
				if(leftSensorDetectY && rightSensorDetectY) {
					double avg = (rightDistanceY + leftDistanceY)/2;
					if(lineCountY == 0) {
						dLine1 = avg;
					} else if (lineCountY == 1) {
						dLine2 = avg;
					}
					
					++lineCountY;
					if(leftDistanceY - rightDistanceY < 5.1) {
						thetaY = odo.getAng() + Math.toDegrees((Math.asin((leftDistanceY - rightDistanceY)/5.1)));
						odo.setPosition(new double[] {0, 0, thetaY}, new boolean[] {false, false, true});
					}
					rightSensorDetectY = false;
					leftSensorDetectY = false;
					
					if(lineCountY == 2) {
						actualD = 30.48 - dLine2 + dLine1;
						lineCountY = 0;
						odo.setPosition(new double[] {0, odo.getY() - actualD, 0}, new boolean[] {false, true, false});
					}
				}
			}
			else if (axis == CORRECTION_TYPE.X) {
				if(this.leftColorSensor.getFilteredData() < 0.2) {
					leftDistanceX = odo.getX();
					leftSensorDetectX = true;
				}
				if(this.rightColorSensor.getFilteredData() < 0.3) {
					rightDistanceX = odo.getX();
					rightSensorDetectX = true;
				}
				
				
				if(leftSensorDetectX && rightSensorDetectX) {
					double avg = (rightDistanceX + leftDistanceX)/2;
					if(lineCountX == 0) {
						dLine1 = avg;
					} else if (lineCountX == 1) {
						dLine2 = avg;
					}
					
					++lineCountX;
					if(leftDistanceX - rightDistanceX < 5.1) {
						thetaX = odo.getAng() + Math.toDegrees((Math.asin((leftDistanceX - rightDistanceX)/5.1)));
						odo.setPosition(new double[] {0, 0, thetaX}, new boolean[] {false, false, true});
					}

					rightSensorDetectX = false;
					leftSensorDetectX = false;
					
					if(lineCountX == 2) {
						actualD = 30.48 - dLine2 + dLine1;
						lineCountX = 0;
						odo.setPosition(new double[] {odo.getX() - actualD, 0, 0}, new boolean[] {true, false, false});
					}
				}
			}
			
			
			
			// this ensures that the odometer only runs once every period
			updateEnd = System.currentTimeMillis();
			if (updateEnd - updateStart < RUN_PERIOD) {
				try {
					Thread.sleep(RUN_PERIOD - (updateEnd - updateStart));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void correctX() {
		// TODO Auto-generated method stub
		this.axis = CORRECTION_TYPE.X;
		this.lineCountX = 0;
	}

	@Override
	public void correctY() {
		// TODO Auto-generated method stub
		this.axis = CORRECTION_TYPE.Y;
		this.lineCountY = 0;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		this.axis = CORRECTION_TYPE.NONE;
	}

}
