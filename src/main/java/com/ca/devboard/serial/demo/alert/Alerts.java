package com.ca.devboard.serial.demo.alert;

import com.ca.devboard.serial.SerialIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Alerts 
{
	private final List<AlertExecution> alertExecutions = new ArrayList<>();
	
	public Alerts()
	{
		alertExecutions.add(new WeatherAlert());
	}
	
	public void update(SerialIO serialIO) throws IOException, InterruptedException
	{
		for (AlertExecution alertExecution : alertExecutions)
		{
			if (alertExecution.needsRefresh())
			{
				alertExecution.execute(serialIO);
			}
		}
	}
}
