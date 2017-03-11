package dpmproject;
/**
 * 
 */

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.robotics.SampleProvider;

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


  // TODO fill in sensor and motor initialization
  /*
  public static final EV3LargeRegulatedMotor LEFT_MOTOR;
  public static final EV3LargeRegulatedMotor RIGHT_MOTOR;
  public static final UnregulatedMotor LAUNCHER_MOTOR;
  public static final SampleProvider LIGHT_SENSOR;
  public static final SampleProvider US_SENSOR;
  */

}
