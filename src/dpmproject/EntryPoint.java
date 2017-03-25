package dpmproject;
/**
 * 
 */

import java.util.Map;

import interfacePackages.PathfinderInterface;
import interfacePackages.SensorInterface;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.utility.Delay;
import utilityPackages.Coordinate;
import wifi.WifiConnection;

/**
 * @author Team 2
 *
 */
public class EntryPoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		WifiConnection conn = new WifiConnection("192.168.2.14", 2, false);

		// Connect to server and get the data, catching any errors that might
		// occur
		try {
			Map data = conn.getData();

			GlobalDefinitions.d1 = ((Long) data.get("d1")).intValue();
			GlobalDefinitions.FWD_CORNER = ((Long) data.get("FWD_CORNER")).intValue();

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		Sound.beep();
		
		GlobalDefinitions.init();
				
		Odometer odo = new Odometer(GlobalDefinitions.LEFT_MOTOR, GlobalDefinitions.RIGHT_MOTOR, 30, true);
		
		Navigation nav = new Navigation(odo);
		
		//FilteredColorSensor rearColor = new FilteredColorSensor(GlobalDefinitions.REAR_COLOR_SENSOR);
		//LightLocalizer lsl = new LightLocalizer(odo, rearColor);
		//lsl.doLocalization();
		
		float[] data = new float[GlobalDefinitions.US_SENSOR.sampleSize()];
		FilteredUltrasonicSensor usSensor = new FilteredUltrasonicSensor(GlobalDefinitions.US_SENSOR);
		FrameworkUSLocalizer usl = new FrameworkUSLocalizer(odo,usSensor);
		usl.doLocalization(GlobalDefinitions.FWD_CORNER);
		
		Sound.beep();
		
		nav.travelTo(0, GlobalDefinitions.d1*GlobalDefinitions.TILE_SIZE );
		//nav.turnTo(0, true);
		
		DemoBallLauncher d = new DemoBallLauncher();
		//d.fire();
		
	}

}
