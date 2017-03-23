package dpmproject;
/**
 * 
 */

import java.util.Map;

import interfacePackages.PathfinderInterface;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
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
		
		WifiConnection conn = new WifiConnection("192.168.2.3", 2, false);

		// Connect to server and get the data, catching any errors that might
		// occur
		try {
			/*
			 * getData() will connect to the server and wait until the user/TA
			 * presses the "Start" button in the GUI on their laptop with the
			 * data filled in. Once it's waiting, you can kill it by
			 * pressing the upper left hand corner button (back/escape) on the EV3.
			 * getData() will throw exceptions if it can't connect to the server
			 * (e.g. wrong IP address, server not running on laptop, not connected
			 * to WiFi router, etc.). It will also throw an exception if it connects 
			 * but receives corrupted data or a message from the server saying something 
			 * went wrong. For example, if TEAM_NUMBER is set to 1 above but the server expects
			 * teams 17 and 5, this robot will receive a message saying an invalid team number 
			 * was specified and getData() will throw an exception letting you know.
			 */
			
			Map data = conn.getData();

			GlobalDefinitions.d1 = ((Long) data.get("d1")).intValue();
			//GlobalDefinitions.FWD_CORNER = ((Long) data.get("FWD_CORNER")).intValue();

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		Sound.beep();
		
		
		
		GlobalDefinitions.init();
				
		//FilteredColorSensor leftColorSensor = new FilteredColorSensor(GlobalDefinitions.LEFT_COLOR_SENSOR);
		//FilteredColorSensor rightColorSensor = new FilteredColorSensor(GlobalDefinitions.RIGHT_COLOR_SENSOR);
		
		Odometer odo = new Odometer(GlobalDefinitions.LEFT_MOTOR, GlobalDefinitions.RIGHT_MOTOR, 30, true);
		//OdometerCorrection odometerCorrector = new OdometerCorrection(odo, leftColorSensor, rightColorSensor);
		
		//FilteredUltrasonicSensor usSensor = new FilteredUltrasonicSensor(GlobalDefinitions.US_SENSOR);
		//USLocalizer usl = new USLocalizer(odo, usSensor, USLocalizer.LocalizationType.FALLING_EDGE);
		//usl.doLocalization();
		
		Sound.beep();
		
		PathfinderInterface pf = new SimplePathfinder(new Coordinate(5*30.44, GlobalDefinitions.d1 * 30.44), odo);
		Pilot p = new Pilot(pf, odo);
		p.travel();
	
	}

}
