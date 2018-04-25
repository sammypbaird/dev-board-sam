package com.ca.devboard.serial.demo;

import com.ca.devboard.serial.SerialIO;
import com.ca.devboard.serial.demo.alert.Alerts;
import java.io.IOException;

public class SamsAlerts 
{
	private static final Alerts alerts = new Alerts();
	
	public static void run(SerialIO serialIo) throws IOException, InterruptedException
	{
		System.out.println("Started!");
		alerts.init();
		
		while (true)
		{
			alerts.update(serialIo);
			Thread.sleep(2000);
		}
	}
	
//	private static void sendRandomAlert(SerialIO serialIo, int alertId, String label) throws IOException, InterruptedException
//	{
//		double number = random.nextDouble() * 100;
//		serialIo.sendAsciiString(alertId, (int) number, label + AlertUtils.generate4DigitString(number));
//		System.out.println(number);
//	}
}
