package com.ca.devboard.serial.demo;

import com.ca.devboard.serial.SerialIO;
import com.ca.devboard.serial.SerialIOMock;
import java.io.IOException;

public class MainMock 
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		SerialIO serialIo = new SerialIOMock();
		SamsAlerts.run(serialIo);
	}
}
