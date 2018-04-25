package com.ca.devboard.serial.demo.data.client;

public class AlertClientException extends RuntimeException
{
	public AlertClientException(Throwable throwable)
	{
		super(throwable);
	}
	
	public AlertClientException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
}
