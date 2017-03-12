package dpmproject;
/**
 * 
 */

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

/**
 * @author Ali
 *
 */
public final class GlobalDefinitions {
	
  public static final double WHEEL_RADIUS                     = 2.4;  // In centimeters
  public static final double WHEEL_BASE                       = 18.5; // In centimeters
  public static final double LIGHT_SENSOR_OFFSET              = 19.0 ; // In centimeters
  public static final double DEG_ERR                          = 3.0;  // In degrees
  public static final double LIN_ERR                          = 1.0;  // In centimeters
  public static final double ACCELERATION                     = 4000; // In degrees per second per second
  public static final double FORWARD_SPEED                    = 175.0; // In degrees per second
  public static final double TURN_SPEED                       = 100.0; // In degrees per second

  public static final int SWING_TIME                          = 500;  // In milliseconds
  
  private static final String leftMotorPort                   = "A";
  private static final String rightMotorPort                  = "D";
  private static final String launcherMotorPort               = "C";
  private static final String leftColorSensorPort             = "S4";
  private static final String usSensorPort                    = "S3";
  private static final String rightColorSensorPort            = "S1";

  public static EV3LargeRegulatedMotor LEFT_MOTOR;
  public static EV3LargeRegulatedMotor RIGHT_MOTOR;
  public static UnregulatedMotor LAUNCHER_MOTOR;
  public static EV3ColorSensor LEFT_COLOR_SENSOR;
  public static EV3ColorSensor RIGHT_COLOR_SENSOR;
  public static EV3UltrasonicSensor US_SENSOR;
  
  public static void init() {
	  GlobalDefinitions.LEFT_MOTOR         = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(GlobalDefinitions.leftMotorPort));
	  GlobalDefinitions.RIGHT_MOTOR        = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(GlobalDefinitions.rightMotorPort));
	  GlobalDefinitions.LAUNCHER_MOTOR     = new UnregulatedMotor(LocalEV3.get().getPort(GlobalDefinitions.launcherMotorPort));
	  GlobalDefinitions.LEFT_COLOR_SENSOR  = new EV3ColorSensor(LocalEV3.get().getPort(GlobalDefinitions.leftColorSensorPort));
	  GlobalDefinitions.RIGHT_COLOR_SENSOR = new EV3ColorSensor(LocalEV3.get().getPort(GlobalDefinitions.rightColorSensorPort));
	  GlobalDefinitions.US_SENSOR          = new EV3UltrasonicSensor(LocalEV3.get().getPort(GlobalDefinitions.usSensorPort));
  }

}
