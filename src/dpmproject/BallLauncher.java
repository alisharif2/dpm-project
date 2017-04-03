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
		GlobalDefinitions.SHOOTMOTOR.rotate(-240);
		Sound.beep();
		GlobalDefinitions.SHOOTMOTOR.rotate(240);
		
	}

	@Override
	public boolean reset() {
		// TODO Auto-generated method stub
		return false;
	}

	public void pullElasticBack(){
		GlobalDefinitions.RELOADMOTOR.setSpeed(600);
		GlobalDefinitions.RELOADMOTOR.rotate(360*21);
		Sound.beep();
		GlobalDefinitions.RELOADMOTOR.rotate(-360*21);
	}
	
	public void dropBall(){
		GlobalDefinitions.SHOOTMOTOR.setSpeed(300);
		GlobalDefinitions.SHOOTMOTOR.rotate(480);
		Sound.beep();
		GlobalDefinitions.SHOOTMOTOR.setSpeed(50);

		GlobalDefinitions.SHOOTMOTOR.rotate(-480);
		GlobalDefinitions.SHOOTMOTOR.setSpeed(300);

	}
	
}
