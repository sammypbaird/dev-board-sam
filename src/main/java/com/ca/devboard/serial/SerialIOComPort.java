package com.ca.devboard.serial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.Duration;
import java.util.TooManyListenersException;
import purejavacomm.SerialPort;
import purejavacomm.SerialPortEvent;

/**
 * Contains the methods to read and write to the serial connection.
 */
public class SerialIOComPort implements SerialIO
{
	private static final long RECONNECT_SEC = 2;

	private SerialPort serialPort = null;
	private String comPort = null;
	private final int baudRate;
	
	/**
	 * Receives messages from serial
	 */
	private final SerialDataReceivedListener serialDataReceivedListener;
	
	/**
	 * A BufferedReader which will be fed by a InputStreamReader converting the
	 * bytes into characters making the displayed results codepage independent
	 */
	private BufferedReader input = null;
	/**
	 * The output stream to the port
	 */
	private OutputStream output = null;
	
	/**
	 * If you want to use defaults for some, use the builder() method to create a SerialIO.
	 */
	public SerialIOComPort(int baudRate, String comPort, SerialDataReceivedListener serialDataReceivedListener)
	{
		this.baudRate = baudRate;
		this.comPort = comPort;
		this.serialDataReceivedListener = serialDataReceivedListener;
	}

	public static SerialIOComPortBuilder builder()
	{
		return new SerialIOComPortBuilder();
	}

	@Override
	public void sendAlert(int alertId, int alertLevel) throws IOException, InterruptedException
	{
		connect();
		try
		{
			output.write(Command.ALERT.getId());
			output.write(alertId);
			output.write(alertLevel);
			output.flush();
		}
		catch (Exception ex)
		{
			serialPort = null;
			ex.printStackTrace();
			Thread.sleep(Duration.ofSeconds(5).toMillis());
		}
	}
	
	@Override
	public void sendImage(int alertId, int alertLevel, byte[] data) throws IOException, InterruptedException
	{
		connect();
		try
		{
			output.write(Command.IMAGE.getId());
			output.write(alertId);
			output.write(alertLevel);
			for (int i=0;i<data.length;i++)
				
			{
				output.write(data[i]);
				if (i%10 == 0)
					output.flush();
				//Thread.sleep(0, 10_000);
			}
		}
		catch (Exception ex)
		{
			serialPort = null;
			ex.printStackTrace();
			Thread.sleep(Duration.ofSeconds(5).toMillis());
		}
	}
	
	/**
	 * Sends bytes as ascii characters. Terminates it with a null character
	 */
	@Override
	public void sendAsciiString(int alertId, int alertLevel, String string) throws IOException, InterruptedException
	{
		connect();
		try
		{
			output.write(Command.ASCII.getId());
			output.write(alertId);
			output.write(alertLevel);
			for (int i=0; i<string.length(); i++)
			{
				char c = Character.toUpperCase(string.charAt(i));
				output.write(c);
			}
			output.write(0);
			output.flush();
		}
		catch (Exception ex)
		{
			serialPort = null;
			ex.printStackTrace();
			Thread.sleep(Duration.ofSeconds(5).toMillis());
		}
	}

	private void connect() throws InterruptedException, IOException
	{
		if (serialPort != null)
			return;
		System.out.println("Serial port connection not established. Attempting to connect...");
		while (true)
		{
			try
			{
				serialPort = SerialConnection.connect(baudRate, comPort);
				input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
				output = serialPort.getOutputStream();
				try
				{
					serialPort.addEventListener(this);
				}
				catch (TooManyListenersException ex)
				{
					throw new IllegalArgumentException("Failed to setup serial event listener", ex);
				}
				break;
			}
			catch (Exception ex)
			{
				System.out.println(String.format("Unable to connect: %s. Trying again in %d seconds",
												 ex.getLocalizedMessage(), RECONNECT_SEC));
				Thread.sleep(Duration.ofSeconds(RECONNECT_SEC).toMillis());
			}
			
		}
	}
	
	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking on platforms like Linux.
	 */
	@Override
	public synchronized void close()
	{
		if (serialPort != null)
		{
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	@Override
	public synchronized void serialEvent(SerialPortEvent oEvent)
	{
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)
		{
			try
			{
				if (input != null && serialDataReceivedListener != null)
					serialDataReceivedListener.dataReceived(input.readLine());
			}
			catch (Exception e)
			{
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}
	
}
