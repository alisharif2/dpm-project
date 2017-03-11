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
	
  public static final double WHEEL_RADIUS                     = 2.1;  // In centimeters
  public static final double WHEEL_BASE                       = 15.0; // In centimeters
  public static final double LIGHT_SENSOR_OFFSET              = 12.0; // In centimeters
  public static final double DEG_ERR                          = 2.0;  // In degrees
  public static final double LIN_ERR                          = 1.0;  // In centimeters
  public static final double ACCELERATION                     = 4000; // In degrees per second per second
  public static final double FORWARD_SPEED                    = 75.0; // In degrees per second
  public static final double TURN_SPEED                       = 50.0; // In degrees per second

  public static final int SWING_TIME                          = 500;  // In milliseconds
  
  private static final String leftMotorPort                   = "A";
  private static final String rightMotorPort                  = "D";
  private static final String launcherMotorPort               = "C";
  private static final String colorSensorPort                 = "1";
  private static final String usSensorPort                    = "3";

  public static final EV3LargeRegulatedMotor LEFT_MOTOR       = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(leftMotorPort));
  public static final EV3LargeRegulatedMotor RIGHT_MOTOR      = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(rightMotorPort));
  public static final UnregulatedMotor LAUNCHER_MOTOR         = new UnregulatedMotor(LocalEV3.get().getPort(launcherMotorPort));
  public static final EV3ColorSensor COLOR_SENSOR             = new EV3ColorSensor(LocalEV3.get().getPort(colorSensorPort));
  public static final EV3UltrasonicSensor US_SENSOR           = new EV3UltrasonicSensor(LocalEV3.get().getPort(usSensorPort));

}
