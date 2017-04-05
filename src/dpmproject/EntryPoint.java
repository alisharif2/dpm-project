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
		JUJUNAV nav = new JUJUNAV(odo);
		BallLauncher BL=new BallLauncher();
		FilteredUltrasonicSensor usSensor = new FilteredUltrasonicSensor(GlobalDefinitions.US_SENSOR);
		FrameworkUSLocalizer loc= new FrameworkUSLocalizer(odo, usSensor);
		Navigation nav2= new Navigation(odo);
		GlobalDefinitions.SHOOTMOTOR.rotate(620);
		initFromWifi();
		//nav.turnTo(1, odo);
        //nav.turnTo(2, odo);
		if(GlobalDefinitions.FWD_TEAM==2){
			
			if(GlobalDefinitions.bx<0){
				GlobalDefinitions.bx=3;
				nav.moveTo(GlobalDefinitions.by, GlobalDefinitions.bx+20, odo);
			}
			else if(GlobalDefinitions.bx>GlobalDefinitions.TILE_SIZE*10){
				GlobalDefinitions.bx-=(GlobalDefinitions.TILE_SIZE+3);
				nav.moveTo(GlobalDefinitions.by, GlobalDefinitions.bx-20, odo);

			}
			else if(GlobalDefinitions.by<0){
				GlobalDefinitions.by=3;
				nav.moveTo(GlobalDefinitions.by+20, GlobalDefinitions.bx, odo);
			}
			else{
				GlobalDefinitions.by-=(GlobalDefinitions.TILE_SIZE+3);
				nav.moveTo(GlobalDefinitions.by-20, GlobalDefinitions.bx, odo);

			}
					

			loc.doLocalization(GlobalDefinitions.FWD_CORNER);
			
			while(true){
			nav.moveTo(GlobalDefinitions.by, GlobalDefinitions.bx, odo);
			GlobalDefinitions.SHOOTMOTOR.rotate(-620);
			if(GlobalDefinitions.omega=="N")
				nav.turnTo(0, odo);
			if(GlobalDefinitions.omega=="W")
				nav.turnTo(270, odo);
			if(GlobalDefinitions.omega=="S")
				nav.turnTo(180, odo);
			if(GlobalDefinitions.omega=="E")
				nav.turnTo(90, odo);
			

			for(int i=0;i<20;i++){
				Sound.beep();
			}
			GlobalDefinitions.SHOOTMOTOR.rotate(620);
			GlobalDefinitions.SHOOTMOTOR.rotate(-620);

			for(int i=0;i<20;i++){
				Sound.beep();
			}
			GlobalDefinitions.SHOOTMOTOR.rotate(620);
			GlobalDefinitions.SHOOTMOTOR.rotate(-620);

			for(int i=0;i<20;i++){
				Sound.beep();
			}
			GlobalDefinitions.SHOOTMOTOR.rotate(620);
			GlobalDefinitions.SHOOTMOTOR.rotate(-620);
			
			nav.moveTo(3*GlobalDefinitions.TILE_SIZE, 2.8*GlobalDefinitions.TILE_SIZE, odo);
			nav.turnTo(23, odo);
			BL.fire();
			BL.reload();
			BL.fire();
			BL.reload();
			BL.fire();
			BL.pullElasticBack();
			}
		}
		else if (GlobalDefinitions.DEF_TEAM==2){
			loc.doLocalization(GlobalDefinitions.DEF_CORNER);
			nav.moveTo(5*GlobalDefinitions.TILE_SIZE, 5*GlobalDefinitions.TILE_SIZE, odo);
			nav.turnTo(90, odo);
			while(true){
				nav.moveTo(5*GlobalDefinitions.TILE_SIZE, 4*GlobalDefinitions.TILE_SIZE, odo);
			    nav.moveTo(5*GlobalDefinitions.TILE_SIZE, 6*GlobalDefinitions.TILE_SIZE, odo);}
		}
		
		
	    
		
	}
	
	public static void initFromWifi() {
		Map data = null;
		
		WifiConnection conn = new WifiConnection("192.168.2.3", 2, false);
		// Connect to server and get the data, catching any errors that might
		// occur
		try {
			data = conn.getData();
			
			GlobalDefinitions.d1 = ((Long) data.get("d1")).intValue();
			GlobalDefinitions.FWD_CORNER = ((Long) data.get("FWD_CORNER")).intValue();
			GlobalDefinitions.DEF_CORNER = ((Long) data.get("DEF_CORNER")).intValue();
			GlobalDefinitions.FWD_TEAM = ((Long) data.get("FWD_TEAM")).intValue();
			GlobalDefinitions.DEF_TEAM = ((Long) data.get("DEF_TEAM")).intValue();


			GlobalDefinitions.w1 = ((Long) data.get("w1")).intValue();
			GlobalDefinitions.w2 = ((Long) data.get("w2")).intValue();
			GlobalDefinitions.bx = ((Long) data.get("bx")).intValue();
			GlobalDefinitions.by = ((Long) data.get("by")).intValue();
			GlobalDefinitions.omega = ((Long) data.get("omega")).toString();
		} catch(NullPointerException e) {
			System.err.println("Data was not loaded from wifi. Data variable is null");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		Sound.beep();

	}
}
