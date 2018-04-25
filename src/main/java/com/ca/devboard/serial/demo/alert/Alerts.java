package com.ca.devboard.serial.demo.alert;

import com.ca.devboard.serial.SerialIO;
import com.ca.devboard.serial.demo.data.client.AlertClientException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Alerts 
{
	private final List<AlertExecution> alertExecutions = new ArrayList<>();
	
	public Alerts()
	{
		alertExecutions.add(new WeatherAlert());
		alertExecutions.add(new GraphiteDatabaseCpuAlert());
		alertExecutions.add(new GraphiteGarbageCollectionAlert());
		alertExecutions.add(new ReportDurationAlert());
	}
	
	public void init()
	{
		//find the minimum refresh delay
		long minRefreshDelay = Long.MAX_VALUE;
		for (AlertExecution alertExecution : alertExecutions)
			if (alertExecution.getRefreshDelay() < minRefreshDelay)
				minRefreshDelay = alertExecution.getRefreshDelay();
		
		//initialize the delays to divide all the alerts' delays equally
		long currentTimeMillis = System.currentTimeMillis();
		long commonDelay = minRefreshDelay / alertExecutions.size();
		long delay = 0;
		for (AlertExecution alertExecution : alertExecutions)
		{
			alertExecution.setRefreshTimestamp(currentTimeMillis + delay);
			delay += commonDelay;
		}
	}
	
	public void update(SerialIO serialIO) throws IOException, InterruptedException
	{
		for (AlertExecution alertExecution : alertExecutions)
		{
			if (alertExecution.needsRefresh())
			{
				try
				{
					alertExecution.execute(serialIO);
				}
				catch (AlertClientException ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}
}
