package client;

/**
 * An interface designed to integrate multiple inputs into one control system.
 *
 * @author Ryan McGee
 */
public abstract class Controller
{
	/**
	 * TODO: Figure out if PS5 Dualsense is Supported at all
	 * TODO: Figure out how to search for PS5 Controllers
	 * TODO: Get Correct Byte Data of PS5 Buttons
	 */
	// BUTTONS
	public static final byte PS = 0; // the Playstation button
	public static final byte TRIANGLE = 1; // the Triangle button
	public static final byte CIRCLE = 2; // the Circle button
	public static final byte CROSS = 3; // the Cross button
	public static final byte SQUARE = 4; // the Square button
	public static final byte LBUMP = 5; // the left bumper
	public static final byte RBUMP = 6; // the right bumper
	public static final byte BACK = 7; // the back button, left of the XBOX
	// button
	public static final byte START = 8; // the start button, right of the XBOX
	// button
	public static final byte LSTICK = 9; // the left stick button
	public static final byte RSTICK = 10; // the right stick button
	// ========

	// AXIS
	public static final byte LX_AXIS = 11; // the left stick X axis
	public static final byte LY_AXIS = 12; // the left stick Y axis
	public static final byte LTRIGGER = 13; // the left trigger axis

	public static final byte RX_AXIS = 14; // the right stick X axis
	public static final byte RY_AXIS = 15; // the right stick Y axis
	public static final byte RTRIGGER = 16; // the right trigger axis
	// ========

	// CONTROLLER TYPES
	public static enum controllerTypes
	{
		NotConfigured, DriverController, OperatorController
	}
	private static controllerTypes controllerType = controllerTypes.NotConfigured;
	// ========

	// DPAD
	public static final byte DPAD = 17; // the DPad buttons (listed as an axis)

	public static enum DPad
	{
		LEFT, RIGHT, UP, DOWN
	}
	// ========

	/**
	 * Gets an axis of an analog stick.
	 *
	 * @param axis Which axis to get, defined in class.
	 * @return The axis' current value, in percent-degrees (-1.0 to 1.0)
	 */
	public abstract double getAxis(byte axis);

	/**
	 * Gets a button as a digital signal
	 *
	 * @param button Which button to get, defined in class.
	 * @return The button's current state,true being pressed.
	 */
	public abstract boolean getButton(byte button);

	/**
	 * Gets whether a D-pad button is pressed
	 *
	 * @param button Which button
	 * @return The button's current state
	 */
	public abstract boolean getDPad(DPad button);

	/**
	 * Translates the joystick into a vector.
	 *
	 * @param stick Which stick should the direction be calculated: either LSTICK or
	 *              RSTICK
	 * @return The Magnitude of the joystick, in decimal percentage (0.0 to 1.0)
	 */
	public double getMagnitude(byte stick)
	{
		if (stick == LSTICK)
			return Math.sqrt(Math.pow(getAxis(LX_AXIS), 2) + Math.pow(getAxis(LY_AXIS), 2));
		else if (stick == RSTICK)
			return Math.sqrt(Math.pow(getAxis(RX_AXIS), 2) + Math.pow(getAxis(RY_AXIS), 2));

		return -255;
	}

	/**
	 * Translates the joystick into a vector.
	 *
	 * @param stick Which stick should the direction be calculated: either LSTICK or
	 *              RSTICK
	 * @return The direction of the joystick, in Radians.
	 */
	public double getDirection(byte stick)
	{
		if (stick == LSTICK)
			return Math.atan2(getAxis(LX_AXIS), -getAxis(LY_AXIS));
		else if (stick == RSTICK)
			return Math.atan2(getAxis(RX_AXIS), -getAxis(RY_AXIS));

		return -255;
	}

	/**
	 * Check wether or not the controller is configured.
	 * @return Boolean value of whether or not the controller is configured
	 */
	public boolean isConfigured()
	{
		return controllerType != controllerTypes.NotConfigured;
	}

	/**
	 * Check wether or not the controller is a Driver Controller
	 * @return Boolean value of whether or not the controller is a Driver Controller
	 */
	public boolean isDriverController()
	{
		return controllerType == controllerTypes.DriverController;
	}

	/**
	 * Check wether or not the controller is a Operator Controller
	 * @return Boolean value of whether or not the controller is a Operator Controller
	 */
	public boolean isOperatorController()
	{
		return controllerType == controllerTypes.OperatorController;
	}

	/**
	 * Set the controller type.
	 * @param newControllerType - The controller type to set
	 * @return The controller type that was set
	 */
	public controllerTypes setControllerType(controllerTypes newControllerType)
	{
		controllerType = newControllerType;
		return controllerType;
	}
}
