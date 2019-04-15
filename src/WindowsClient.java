import net.java.games.input.Event;
import net.java.games.input.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class WindowsClient
{
	private static double yval = 0;
	private static double xval = 0;
	private static double zval = 0;

	private static volatile boolean[] keypresses = new boolean[6];

	public static void main(String[] args)
	{
		//Initialize the frame
		JFrame frame = new JFrame();

		Dimension frameSize = Toolkit.getDefaultToolkit().getScreenSize();
		frameSize.setSize(frameSize.getWidth() / 2, frameSize.getHeight() / 2);
		frame.setSize(frameSize);


		//Create the listener for controls
		KeyListener listener = new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{

			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				//set values based on key presses
				if(e.getKeyCode() == KeyEvent.VK_W)
					keypresses[0] = true;
				if(e.getKeyCode() == KeyEvent.VK_S)
					keypresses[1] = true;
				if(e.getKeyCode() == KeyEvent.VK_A)
					keypresses[2] = true;
				if(e.getKeyCode() == KeyEvent.VK_D)
					keypresses[3] = true;
				if(e.getKeyCode() == KeyEvent.VK_Q)
					keypresses[4] = true;
				if(e.getKeyCode() == KeyEvent.VK_E)
					keypresses[5] = true;
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_W)
					keypresses[0] = false;
				if(e.getKeyCode() == KeyEvent.VK_S)
					keypresses[1] = false;
				if(e.getKeyCode() == KeyEvent.VK_A)
					keypresses[2] = false;
				if(e.getKeyCode() == KeyEvent.VK_D)
					keypresses[3] = false;
				if(e.getKeyCode() == KeyEvent.VK_Q)
					keypresses[4] = false;
				if(e.getKeyCode() == KeyEvent.VK_E)
					keypresses[5] = false;
			}
		};

		frame.addKeyListener(listener);
		frame.setVisible(true);

		Socket connection = null;
		try
		{
			connection = new Socket("10.21.41.49", 6000);
			ObjectOutputStream oos = new ObjectOutputStream(connection.getOutputStream());
			oos.flush();

			while(true)
			{
				yval += ((keypresses[0]) ? -1 : 0) + ((keypresses[1]) ? 1 : 0);
				xval += ((keypresses[2]) ? -1 : 0) + ((keypresses[3]) ? 1 : 0);
				zval += ((keypresses[4]) ? -1 : 0) + ((keypresses[5]) ? 1 : 0);

				double[] output = new double[15];
				output[11] = xval;
				output[12] = yval;
				output[14] = zval;

				oos.writeObject(output);
				oos.flush();

				xval = 0;
				yval = 0;
				zval = 0;

				Thread.sleep(50);
			}


		} catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}

//		for(int i = 0; i < JXInputManager.getNumberOfDevices(); i++)
//		{
//			System.out.println(i + " " + JXInputManager.getJXInputDevice(i).getName());
//		}
//
//		Scanner input = new Scanner(System.in);
//		System.out.println("Choose a Controller: ");
//		int conNumber = input.nextInt();
//		JXInputDevice selectedController = JXInputManager.getJXInputDevice(conNumber);
//
//		for(int i = 0; i < selectedController.getNumberOfAxes(); i++)
//		{
//			if(selectedController.getAxis(i) != null)
//			System.out.println(i + " " + selectedController.getAxis(i).getName());
//		}
//
//		int axisNum = input.nextInt();
//		while(true)
//		{
//			JXInputManager.updateFeatures();
//			try
//			{
//				Thread.sleep(50);
//			} catch (InterruptedException e)
//			{
//				e.printStackTrace();
//			}
//				System.out.println(selectedController.getAxis(axisNum).getValue());
//		}

	}
}
