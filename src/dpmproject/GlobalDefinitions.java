package dpmproject;
/**
 * 
 */

import utilityPackages.Coordinate;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;

/**
 * @author Ali
 *
 */
public final class GlobalDefinitions {
	
  public static final double WHEEL_RADIUS                     = 4.05/2;  // In centimeters
  public static final double WHEEL_BASE                       = 18.6;
  public static final double LIGHT_SENSOR_OFFSET              = 17.0 ; // In centimeters
  public static final double DEG_ERR                          = 1.5;  // In degrees
  public static final int DEG_SHOOT                           = 270;  // In degrees
  public static final double LIN_ERR                          = 0.5;  // In centimeters
  public static final double ACCELERATION                     = 4000; // In degrees per second per second
  public static final double FORWARD_SPEED                    = 600; // In degrees per second
  public static final double TURN_SPEED                       = 200.0; // In degrees per second
  public static final double TILE_SIZE                        = 30.48;

  public static final Coordinate[] startPoints    = {new Coordinate(0, 0), new Coordinate(10, 0), new Coordinate(10, 10), new Coordinate(0, 10)};
  public static final double[] startOrientations =  {0, 90, 180, 270};
  
  private static final String leftMotorPort                   = "A";
  private static final String rightMotorPort                  = "D";
  private static final String shooterMotorPort                = "C";
  private static final String reloadMotorPort                 = "B";
  private static final String leftColorSensorPort             = "S4";
  private static final String usSensorPort                    = "S3";
  private static final String rightColorSensorPort            = "S1";
  private static final String rearColorSensorPort 			  = "S2";

  public static EV3LargeRegulatedMotor LEFT_MOTOR;
  public static EV3LargeRegulatedMotor RIGHT_MOTOR;
  public static EV3LargeRegulatedMotor RELOADMOTOR;
  public static EV3LargeRegulatedMotor SHOOTMOTOR;
  public static EV3ColorSensor LEFT_COLOR_SENSOR;
  public static EV3ColorSensor RIGHT_COLOR_SENSOR;
  public static EV3ColorSensor REAR_COLOR_SENSOR;
  public static EV3UltrasonicSensor US_SENSOR;
  
  public static int FWD_TEAM = -1;
  public static int FWD_CORNER = 1;
  public static int DEF_TEAM = -1;
  public static int DEF_CORNER = -1;
  public static int d1 = -1;
  public static int w1 = -1;
  public static int w2 = -1;
  public static int bx = -1;
  public static int by = -1;
  public static int omega = -1;
  
  public static boolean isGettingBall;
  public static boolean isShooting;

  
  
  public static void init() {
	  GlobalDefinitions.LEFT_MOTOR         = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(GlobalDefinitions.leftMotorPort));
	  GlobalDefinitions.RIGHT_MOTOR        = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(GlobalDefinitions.rightMotorPort));
	  GlobalDefinitions.RELOADMOTOR        = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(GlobalDefinitions.reloadMotorPort));
	  GlobalDefinitions.SHOOTMOTOR        = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(GlobalDefinitions.shooterMotorPort));
	  GlobalDefinitions.LEFT_COLOR_SENSOR  = new EV3ColorSensor(LocalEV3.get().getPort(GlobalDefinitions.leftColorSensorPort));
	  GlobalDefinitions.RIGHT_COLOR_SENSOR = new EV3ColorSensor(LocalEV3.get().getPort(GlobalDefinitions.rightColorSensorPort));
	  GlobalDefinitions.US_SENSOR          = new EV3UltrasonicSensor(LocalEV3.get().getPort(GlobalDefinitions.usSensorPort));
	  //GlobalDefinitions.REAR_COLOR_SENSOR  = new EV3ColorSensor(LocalEV3.get().getPort(GlobalDefinitions.rearColorSensorPort)); 
  }

}
