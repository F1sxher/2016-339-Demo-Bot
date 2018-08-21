package robot;

import com.pi4j.wiringpi.Gpio;

import client.Controller;
import client.WifiController;
import client.XboxController;
import hardware.MecanumTransmission;
import hardware.MotorController;
import hardware.Servo;

/**
 * Controls all the functions of the robot: contains the main method.
 *
 * @author Ryan McGee
 */
public class Robot
{
	static final byte GREEN_LED = 0;
	static final byte BLUE_LED = 2;
	static final byte RED_LED = 3;

	static final byte WIFI_BUTTON = 12;
	static final byte BLUETOOTH_BUTTON = 13;

	static boolean isUsingWifi = false;
	static boolean isUsingBluetooth = false;

	static MotorController leftFront;
	static MotorController leftRear;
	static MotorController rightFront;
	static MotorController rightRear;

	static Servo cameraServo;

	static MecanumTransmission transmission;

	static Controller controller;

	/**
	 * The main method of the robot.
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		init();
		while (loop())
			;

	}

	/**
	 * Initializes all aspects of the robot.
	 */
	private static void init()
	{
		if (Gpio.wiringPiSetup() == -1)
		{
			System.out.println("Error setting up WiringPi");
			return;
		}

		Gpio.pinMode(BLUE_LED, Gpio.OUTPUT);
		Gpio.pinMode(GREEN_LED, Gpio.OUTPUT);
		Gpio.pinMode(RED_LED, Gpio.OUTPUT);
		Gpio.pinMode(WIFI_BUTTON, Gpio.INPUT);
		Gpio.pinMode(BLUETOOTH_BUTTON, Gpio.INPUT);

		// Put init code after this.

		// Motor controllers
		leftFront = new MotorController(5, 6);
		leftRear = new MotorController(27, 28);
		rightFront = new MotorController(10, 11);
		rightRear = new MotorController(1, 4);
		// end motor controllers

		// Servos
		cameraServo = new Servo(29, 10, 5, 16, 100, 5.0);

		// Transmission
		transmission = new MecanumTransmission(leftFront, leftRear, rightFront, rightRear);

		// Reverse motors
		rightFront.setReversed(true);
		rightRear.setReversed(true);
		leftFront.setReversed(false);
		leftRear.setReversed(false);

		Gpio.digitalWrite(BLUE_LED, true);
		Gpio.digitalWrite(GREEN_LED, true);
		Gpio.digitalWrite(RED_LED, true);

		while (true)
		{
			if (Gpio.digitalRead(WIFI_BUTTON) == 0)
			{
				Gpio.digitalWrite(GREEN_LED, false);
				Gpio.digitalWrite(BLUE_LED, true);
				controller = new WifiController();
				isUsingWifi = true;
				break;
			} else if (Gpio.digitalRead(BLUETOOTH_BUTTON) == 0)
			{
				Gpio.digitalWrite(BLUE_LED, false);
				Gpio.digitalWrite(GREEN_LED, true);
				controller = new XboxController();
				((XboxController) controller).init();
				isUsingBluetooth = true;
				break;
			}

			if ((System.currentTimeMillis() / 1000) % 2 == 1)
			{
				Gpio.digitalWrite(BLUE_LED, false);
			} else
			{
				Gpio.digitalWrite(BLUE_LED, true);
			}

		}

	}

	/**
	 * The main loop that runs after the initialization.
	 *
	 * @return whether or not to continue the loop.
	 */
	private static boolean loop()
	{
		// ========LOOP========

		// ========DRIVING FUNCTIONS========
		transmission.drive(controller.getDirection(Controller.LSTICK), controller.getMagnitude(Controller.LSTICK),
				controller.getAxis(Controller.RX_AXIS));

		// ========END DRIVING FUNCTIONS========

		// ========DEBUG========

		printStatements();

		// ========END DEBUG========

		// Limit the thread to update only every 25 milliseconds.
		try
		{
			Thread.sleep(25);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		// =======END LOOP========
		return true;
	}

	/**
	 * Used for debug purposes.
	 */
	private static void printStatements()
	{

	}
}
