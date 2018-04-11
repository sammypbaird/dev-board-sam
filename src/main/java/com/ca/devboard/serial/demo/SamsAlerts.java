package com.ca.devboard.serial.demo;

import com.ca.devboard.serial.Command;
import com.ca.devboard.serial.SerialIO;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class SamsAlerts 
{
	private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("0.000");
	private static final Random random = new Random();
	private static final long POLL_INTERVAL_MS = 5000;
	
	public static void run(SerialIO serialIo) throws IOException, InterruptedException
	{
		serialIo.sendAlert(0,0);
		Thread.sleep(2000);
		System.out.println("Started!");
		byte[] imageData = new ImageLoader().loadImageRGB565("walle.png");
		serialIo.sendImage(0, 0, imageData);
		Thread.sleep(2000);
		imageData = new ImageLoader().loadImageRGB565("walle2.png");
		serialIo.sendImage(0, 0, imageData);
		Thread.sleep(2000);
		imageData = new ImageLoader().loadImageRGB565("walle3.png");
		serialIo.sendImage(0, 0, imageData);
		Thread.sleep(2000);
		imageData = new ImageLoader().loadImageRGB565("walle4.png");
		serialIo.sendImage(0, 0, imageData);
		Thread.sleep(2000);
//		System.out.println("Finished!");
		while (true)
		{
			sendRandomAlert(serialIo, 0, "Duration ");
			Thread.sleep(POLL_INTERVAL_MS);
			sendRandomAlert(serialIo, 1, "DB CPU ");
			Thread.sleep(POLL_INTERVAL_MS);
			sendRandomAlert(serialIo, 2, "Temp ");
			Thread.sleep(POLL_INTERVAL_MS);
		}
	}
	
	private static void sendRandomAlert(SerialIO serialIo, int alertId, String label) throws IOException, InterruptedException
	{
		double number = random.nextDouble() * 100;
		serialIo.sendAsciiString(alertId, (int) number, label + generate4DigitString(number));
		System.out.println(number);
	}
	
	private static String generate4DigitString(double number)
	{
		StringBuilder sb = new StringBuilder();
		String strNumber = NUMBER_FORMAT.format(number);
		
		//if the first 4 digits contains a period, send 5 characters, because the period will be combined with one of the digits
		int size = strNumber.substring(0, 4).contains(".") ? 5 : 4;
		for (int i=0; i<size; i++)
			sb.append(strNumber.charAt(i));
		
		return sb.toString();
	}
}
