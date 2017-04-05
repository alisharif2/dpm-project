package dpmproject;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class JUJUNAV {
	
	private EV3LargeRegulatedMotor lm= GlobalDefinitions.LEFT_MOTOR;
	private EV3LargeRegulatedMotor rm= GlobalDefinitions.RIGHT_MOTOR;
	private Odometer odo;
	private	Navigation nav;

	public JUJUNAV(Odometer odo){
		this.odo=odo;
		this.nav=new Navigation(odo);
	}
	
	public void moveTo(double x, double y,Odometer odo){
		if(x>odo.getX()&&y>odo.getY()){
			turnTo(0,odo);
			nav.setSpeeds(200, 200);
			while(odo.getX()<x){
				
			}
			nav.halt();
			turnTo(90,odo);
			nav.setSpeeds(200, 200);
			while(odo.getY()<y){
				
			}
			nav.halt();
			
		}
		else if(x<odo.getX()&&y>odo.getY()){
			turnTo(180,odo);
			nav.setSpeeds(200, 200);
			while(odo.getX()>x){
				
			}
			nav.halt();
			turnTo(90,odo);
			nav.setSpeeds(200, 200);
			while(odo.getY()<y){
				
			}
			nav.halt();
		}
		else if(x>odo.getX()&&y<odo.getY()){
			turnTo(0,odo);
			nav.setSpeeds(200, 200);
			while(odo.getX()<x){
				
			}
			nav.halt();
			turnTo(270,odo);
			nav.setSpeeds(200, 200);
			while(odo.getY()>y){
				
			}
			nav.halt();
			
		}
		else{
			turnTo(180,odo);
			nav.setSpeeds(200, 200);
			while(odo.getX()>x){
				
			}
			nav.halt();
			turnTo(270,odo);
			nav.setSpeeds(200, 200);
			while(odo.getY()>y){
				
			}
			nav.halt();
		}
	}
    
	public void turnTo(int angle,Odometer odo){
		
		nav.setSpeeds(200, -200);	

		while(true){
			if ((odo.getAng()+1)%360>angle&&odo.getAng()-1<angle){
				break;
			}
		}
		nav.halt();
	}
}
