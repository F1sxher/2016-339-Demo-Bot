package hardware;

/**
 * A controller class for a mecanum-type drive train.
 * Allows the robot to move in 3 axis:
 * -Forwards/Backwards
 * -Left/Right
 * -Rotate Clockwise/Counter clockwise
 *
 * @author Ryan McGee
 */
public class MecanumTransmission
{
	private final double MAG_DEADBAND = .2;

	private MotorController leftFront, leftRear, rightFront, rightRear;

	/**
	 * Creates the Mecanum Controller object
	 *
	 * @param leftFront  the front left wheel
	 * @param leftRear   the back left wheel
	 * @param rightFront the front right wheel
	 * @param rightRear  the back right wheel
	 */
	public MecanumTransmission(MotorController leftFront, MotorController leftRear, MotorController rightFront,
	                           MotorController rightRear)
	{
		this.leftFront = leftFront;
		this.rightFront = rightFront;
		this.leftRear = leftRear;
		this.rightRear = rightRear;
	}

	/**
	 * Drives the robot based on mecanum drive
	 *
	 * @param direction Which way the joystick is pointing, in radians
	 * @param magnitude How far the joystick is being pushed
	 * @param rotation  A separate axis dedicated to rotating the robot
	 */
	public void drive(double direction, double magnitude, double rotation)
	{
		double leftFrontVal = Math.cos(direction - (Math.PI / 4)) * magnitude;
		double leftRearVal = Math.cos(direction + (Math.PI / 4)) * magnitude;
		double rightFrontVal = leftRearVal;
		double rightRearval = leftFrontVal;

		if (magnitude > this.MAG_DEADBAND || Math.abs(rotation) > this.MAG_DEADBAND)
		{
			leftFront.set(limit(leftFrontVal + rotation));
			leftRear.set(limit(leftRearVal + rotation));
			rightFront.set(limit(rightFrontVal - rotation));
			rightRear.set(limit(rightRearval - rotation));
		} else
		{
			this.driveRaw(0.0, 0.0);
		}
	}

	/**
	 * Drives the robot without a deadband or mecanum code.
	 *
	 * @param leftVal  Value sent to the front left & back left wheels
	 * @param rightVal Value sent to the front right and back right wheels
	 */
	public void driveRaw(double leftVal, double rightVal)
	{
		leftFront.set(leftVal);
		leftRear.set(leftVal);
		rightFront.set(rightVal);
		rightRear.set(rightVal);
	}

	/**
	 * Limits the value given to between -1 and 1.
	 *
	 * @param in The value to be limited
	 * @return The limited value
	 */
	private double limit(double in)
	{
		if (in > 0)
			return Math.min(in, 1);
		return Math.max(in, -1);
	}
}
