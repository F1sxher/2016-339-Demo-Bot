import de.hardcode.jxinput.*;
import de.hardcode.jxinput.directinput.DirectInputDevice;

import java.util.Scanner;

public class WindowsClient
{
	public static void main(String[] args)
	{
		for(int i = 0; i < JXInputManager.getNumberOfDevices(); i++)
		{
			System.out.println(i + " " + JXInputManager.getJXInputDevice(i).getName());
		}

		Scanner input = new Scanner(System.in);
		System.out.println("Choose a Controller: ");
		int conNumber = input.nextInt();
		JXInputDevice selectedController = JXInputManager.getJXInputDevice(conNumber);

		for(int i = 0; i < selectedController.getNumberOfButtons(); i++)
		{
			if(selectedController.getButton(i) != null)
			System.out.println(i + " " + selectedController.getButton(i).getName());
		}

		int axisNum = input.nextInt();
		while(true)
		{
			JXInputManager.updateFeatures();
			try
			{
				Thread.sleep(50);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			System.out.println(selectedController.getButton(axisNum).getState());
		}

	}
}
