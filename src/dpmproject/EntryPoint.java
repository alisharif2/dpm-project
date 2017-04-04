package dpmproject;
/**
 * 
 */

import java.io.File;
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
		
		//initFromWifi();
		GlobalDefinitions.init();
		Odometer odo = new Odometer(GlobalDefinitions.LEFT_MOTOR, GlobalDefinitions.RIGHT_MOTOR, 30, true);

		FilteredColorSensor leftColor = new FilteredColorSensor(GlobalDefinitions.LEFT_COLOR_SENSOR);
		FilteredColorSensor rightColor = new FilteredColorSensor(GlobalDefinitions.RIGHT_COLOR_SENSOR);
		
		SensorInterface usSensor = new FilteredUltrasonicSensor(GlobalDefinitions.US_SENSOR);
		FrameworkUSLocalizer usl = new FrameworkUSLocalizer(odo, usSensor);
		
		// FOR TESTING 
		
		GlobalDefinitions.FWD_TEAM = 2;
		GlobalDefinitions.DEF_TEAM = 1;
		GlobalDefinitions.d1 = 7;
		GlobalDefinitions.w2 = 4;
		GlobalDefinitions.DEF_CORNER = 1;
		GlobalDefinitions.FWD_CORNER = 1;
		GlobalDefinitions.bx = -1;
		GlobalDefinitions.by = 5;
		
		// END TESTING
		
		
		/*
		if(GlobalDefinitions.FWD_CORNER == 1){
			
		}
		else if(GlobalDefinitions.FWD_CORNER == 2){
			
		}
		else if(GlobalDefinitions.FWD_CORNER == 3){
			
		}
		else{
			
		}
		*/
		
		TestDriver td = new TestDriver(odo);
		BallLauncher bl = new BallLauncher();
		Sound.beep();
		
		if(GlobalDefinitions.DEF_TEAM == 2){

			usl.doLocalization(GlobalDefinitions.DEF_CORNER);
			
			
			System.out.println(odo.getX());
			System.out.println(odo.getY());
			System.out.println(odo.getAng());
			
			td.diagonalTravelTo(new Coordinate(0,0));

			
			Coordinate defensePosition = new Coordinate(5 * 30.44, (((10 - (GlobalDefinitions.d1)) + (10 - GlobalDefinitions.w2))/2) * 30.44 );
			
			td.diagonalTravelTo(defensePosition);
			
			td.turnTo(90, true);
			
			bl.lower();
			
			bl.raise();
			
		}
		
		else{
	
			//usl.doLocalization(GlobalDefinitions.FWD_CORNER);
			
			Coordinate ballDispenser = new Coordinate(GlobalDefinitions.bx * 30.44, GlobalDefinitions.by * 30.44);
			Coordinate originalBallDispenser = new Coordinate(GlobalDefinitions.bx * 30.44, GlobalDefinitions.by * 30.44);
			Coordinate shootingPosition = new Coordinate(5 * 30.44, (9.3 - GlobalDefinitions.d1)* 30.44);
			
			if(GlobalDefinitions.bx == -1){
				
				ballDispenser.x += 28;
				
			}
			else if(GlobalDefinitions.bx == 11){
				
				ballDispenser.x -= 28;
				
			}
			else if(GlobalDefinitions.by == -1){
				
				ballDispenser.y += 28;
				
			}
			else{
				
				ballDispenser.y -= 28;
				
			}
			
			GlobalDefinitions.isGettingBall = true;
			GlobalDefinitions.isShooting = false;
			
			while(true){
				
				
				while(GlobalDefinitions.isGettingBall){
					
					td.travelTo(ballDispenser);
					td.turnTo((td.getAng(originalBallDispenser)) + Math.PI,true);
					
					bl.lower();
					bl.raise();
					
					GlobalDefinitions.isGettingBall = false;
					GlobalDefinitions.isShooting = true;
					
				}
				
				Sound.beep();
				Sound.beep();
				Sound.beep();
					
				while(GlobalDefinitions.isShooting){
					/*
					
					bl.reload();
					td.travelTo(shootingPosition);
					bl.fire();
					
					GlobalDefinitions.isShooting = false;
					GlobalDefinitions.isGettingBall = true;
					
					*/
				}
			}
		}
	}
	
	public static void initFromWifi() {
		Map data = null;
		
		WifiConnection conn = new WifiConnection("192.168.2.7", 2, false);
		// Connect to server and get the data, catching any errors that might
		// occur
		try {
			data = conn.getData();
			GlobalDefinitions.FWD_TEAM = ((Long) data.get("FWD_TEAM")).intValue();
			GlobalDefinitions.DEF_TEAM = ((Long) data.get("DEF_TEAM")).intValue();
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
