package dpmproject;
/**
 * 
 */
import interfacePackages.BallLauncherInterface;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
/**
 * @author Ali
 *
 */
public class BallLauncher extends Thread implements BallLauncherInterface {

	
	@Override
	public boolean reload() {
		pullElasticBack();
		dropBall();
		return false;
	}

	@Override
	public void fire() {
		GlobalDefinitions.SHOOTMOTOR.rotate(-250);
		Sound.beep();
		GlobalDefinitions.SHOOTMOTOR.rotate(250);
		
	}

	@Override
	public boolean reset() {
		// TODO Auto-generated method stub
		return false;
	}

	public void pullElasticBack(){
		GlobalDefinitions.RELOADMOTOR.setSpeed(600);
		GlobalDefinitions.RELOADMOTOR.rotate(362*27);
		Sound.beep();
		GlobalDefinitions.RELOADMOTOR.rotate(-362*27);
	}
	
	public void dropBall(){
		GlobalDefinitions.SHOOTMOTOR.setSpeed(300);
		GlobalDefinitions.SHOOTMOTOR.rotate(500);
		Sound.beep();
		GlobalDefinitions.SHOOTMOTOR.setSpeed(50);

		GlobalDefinitions.SHOOTMOTOR.rotate(-500);
		GlobalDefinitions.SHOOTMOTOR.setSpeed(300);

	}
	public void raise(){
		GlobalDefinitions.SHOOTMOTOR.setSpeed(300);
		GlobalDefinitions.SHOOTMOTOR.rotate(850);

	}
	public void lower(){
		GlobalDefinitions.SHOOTMOTOR.setSpeed(300);
		GlobalDefinitions.SHOOTMOTOR.rotate(-850);
		GlobalDefinitions.SHOOTMOTOR.setSpeed(300);
	}
	
}
