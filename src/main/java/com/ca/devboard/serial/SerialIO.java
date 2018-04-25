package com.ca.devboard.serial;

import java.io.IOException;
import purejavacomm.SerialPortEvent;
import purejavacomm.SerialPortEventListener;

public interface SerialIO extends SerialPortEventListener
{
	public static SerialIOComPortBuilder builder()
	{
		return new SerialIOComPortBuilder();
	}
	
	public void sendAlert(int alertId, int alertLevel) throws IOException, InterruptedException;
	
	public void sendImage(int alertId, int alertLevel, byte[] data) throws IOException, InterruptedException;
	
	public void sendAsciiString(int alertId, int alertLevel, String string) throws IOException, InterruptedException;
	
	public void close();
	
	@Override
	public void serialEvent(SerialPortEvent oEvent);
}
