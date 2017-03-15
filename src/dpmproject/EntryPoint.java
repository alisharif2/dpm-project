package dpmproject;
/**
 * 
 */

import interfacePackages.PathfinderInterface;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import utilityPackages.Coordinate;

/**
 * @author Team 2
 *
 */
public class EntryPoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		GlobalDefinitions.init();
				
		//FilteredColorSensor leftColorSensor = new FilteredColorSensor(GlobalDefinitions.LEFT_COLOR_SENSOR);
		//FilteredColorSensor rightColorSensor = new FilteredColorSensor(GlobalDefinitions.RIGHT_COLOR_SENSOR);
		
		Odometer odo = new Odometer(GlobalDefinitions.LEFT_MOTOR, GlobalDefinitions.RIGHT_MOTOR, 30, true);
		//OdometerCorrection odometerCorrector = new OdometerCorrection(odo, leftColorSensor, rightColorSensor);
		
		//FilteredUltrasonicSensor usSensor = new FilteredUltrasonicSensor(GlobalDefinitions.US_SENSOR);
		//USLocalizer usl = new USLocalizer(odo, usSensor, USLocalizer.LocalizationType.FALLING_EDGE);
		//usl.doLocalization();
		
		PathfinderInterface pf = new SimplePathfinder(new Coordinate(80, 80), odo);
		Pilot p = new Pilot(pf, odo);
		p.turnTo(Math.PI/2);
	}

}
