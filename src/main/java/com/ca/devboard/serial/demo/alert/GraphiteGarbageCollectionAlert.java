package com.ca.devboard.serial.demo.alert;

import com.ca.devboard.serial.SerialIO;
import com.ca.devboard.serial.demo.AlertUtils;
import com.ca.devboard.serial.demo.data.client.GraphiteClient;
import java.io.IOException;

public class GraphiteGarbageCollectionAlert extends AlertExecution
{
	private static final long REFRESH_DELAY = 10 * 60;
	private final GraphiteClient graphiteClient = new GraphiteClient();

	@Override
	public long getRefreshDelay()
	{
		return REFRESH_DELAY;
	}
	
	@Override
	public void execute(SerialIO serialIo) throws IOException, InterruptedException
	{
		GraphiteClient.GraphiteMetric graphiteMetric = graphiteClient.getLatestGarbageCollection();
		int alertLevel = graphiteMetric.getValue() > 50 ? 1 : 0;
		String string = "Garbage Collection " + AlertUtils.generate4DigitString(graphiteMetric.getValue());
		serialIo.sendAsciiString(1, alertLevel, string);
	}
}
