package com.ca.devboard.serial;

public class SerialIOBuilder 
{
	private int baudRate = 115200;
	private String comPort = null;
	private SerialDataReceivedListener serialDataReceivedListener = null;
	
	public SerialIOBuilder baudRate(int baudRate)
	{
		this.baudRate = baudRate;
		return this;
	}
	
	public SerialIOBuilder comPort(String comPort)
	{
		this.comPort = comPort;
		return this;
	}
	
	public SerialIOBuilder serialDataReceivedListener(SerialDataReceivedListener serialDataReceivedListener)
	{
		this.serialDataReceivedListener = serialDataReceivedListener;
		return this;
	}
	
	public SerialIO build()
	{
		return new SerialIO(baudRate, comPort, serialDataReceivedListener);
	}
}
