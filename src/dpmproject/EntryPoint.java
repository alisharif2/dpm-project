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
		
		GlobalDefinitions.init();
		Odometer odo = new Odometer(GlobalDefinitions.LEFT_MOTOR, GlobalDefinitions.RIGHT_MOTOR, 30, true);
		
		FilteredColorSensor leftColor = new FilteredColorSensor(GlobalDefinitions.LEFT_COLOR_SENSOR);
		FilteredColorSensor rightColor = new FilteredColorSensor(GlobalDefinitions.RIGHT_COLOR_SENSOR);
		OdometerCorrection corrector = new OdometerCorrection(odo, leftColor, rightColor);
		
		GlobalDefinitions.FWD_CORNER = 0;
			
		SensorInterface usSensor = new FilteredUltrasonicSensor(GlobalDefinitions.US_SENSOR);
		FrameworkUSLocalizer usl = new FrameworkUSLocalizer(odo, usSensor);
		//usl.doLocalization();
		Sound.beep();
		
		TestDriver td = new TestDriver(odo);
		
		td.travelTo(new Coordinate(-5*30.44-2,6*30.44-2));
		//td.travelTo(new Coordinate(-15,45));
		
		td.turnTo((td.getAng(new Coordinate(-5*30.44-2,6*30.44-2))) + Math.PI,true);
		
		
	}
	
	public static void initFromWifi() {
		Map data = null;
		
		WifiConnection conn = new WifiConnection("192.168.2.3", 2, false);
		// Connect to server and get the data, catching any errors that might
		// occur
		try {
			data = conn.getData();
			
			GlobalDefinitions.d1 = ((Long) data.get("d1")).intValue();
			GlobalDefinitions.FWD_CORNER = ((Long) data.get("FWD_CORNER")).intValue() - 1;
			GlobalDefinitions.DEF_CORNER = ((Long) data.get("DEF_CORNER")).intValue() - 1;
			GlobalDefinitions.w1 = ((Long) data.get("w1")).intValue();
			GlobalDefinitions.w2 = ((Long) data.get("w2")).intValue();
			GlobalDefinitions.bx = ((Long) data.get("bx")).intValue();
			GlobalDefinitions.by = ((Long) data.get("by")).intValue();
			GlobalDefinitions.omega = ((Long) data.get("omega")).intValue();
		} catch(NullPointerException e) {
			System.err.println("Data was not loaded from wifi. Data variable is null");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		Sound.beep();

	}
}
