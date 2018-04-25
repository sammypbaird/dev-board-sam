package com.ca.devboard.serial;

public class SerialIOComPortBuilder 
{
	private int baudRate = 115200;
	private String comPort = null;
	private SerialDataReceivedListener serialDataReceivedListener = null;
	
	public SerialIOComPortBuilder baudRate(int baudRate)
	{
		this.baudRate = baudRate;
		return this;
	}
	
	public SerialIOComPortBuilder comPort(String comPort)
	{
		this.comPort = comPort;
		return this;
	}
	
	public SerialIOComPortBuilder serialDataReceivedListener(SerialDataReceivedListener serialDataReceivedListener)
	{
		this.serialDataReceivedListener = serialDataReceivedListener;
		return this;
	}
	
	public SerialIOComPort build()
	{
		return new SerialIOComPort(baudRate, comPort, serialDataReceivedListener);
	}
}
