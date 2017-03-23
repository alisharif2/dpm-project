package dpmproject;
/**
 * 
 */
import interfacePackages.BallLauncherInterface;
/**
 * @author
 *
 */
public class DemoBallLauncher extends Thread implements BallLauncherInterface {

	@Override
	public boolean reload() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void fire() {
		
		GlobalDefinitions.SHOOTMOTOR.rotate(GlobalDefinitions.DEG_SHOOT,false);
	}

	@Override
	public boolean reset() {
		// TODO Auto-generated method stub
		return false;
	}

}
