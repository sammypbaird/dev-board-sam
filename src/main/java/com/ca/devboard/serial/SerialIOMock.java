package com.ca.devboard.serial;

import java.io.IOException;
import purejavacomm.SerialPortEvent;

public class SerialIOMock implements SerialIO
{

	@Override
	public void sendAlert(int alertId, int alertLevel) throws IOException, InterruptedException
	{
		System.out.println("SEND ALERT: alertId " + alertId + ", alertLevel " + alertLevel);
	}

	@Override
	public void sendImage(int alertId, int alertLevel, byte[] data) throws IOException, InterruptedException
	{
		System.out.println("SEND IMAGE: alertId " + alertId + ", alertLevel " + alertLevel);
	}

	@Override
	public void sendAsciiString(int alertId, int alertLevel, String string) throws IOException, InterruptedException
	{
		System.out.println("SEND ASCII STRING: alertId " + alertId + ", alertLevel " + alertLevel + ", message \"" + string + "\"");
	}

	@Override
	public void close()
	{
		System.out.println("CLOSE");
	}

	@Override
	public void serialEvent(SerialPortEvent oEvent)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
