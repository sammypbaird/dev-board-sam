package com.ca.devboard.serial.demo.alert;

import com.ca.devboard.serial.SerialIO;
import com.ca.devboard.serial.demo.AlertUtils;
import com.ca.devboard.serial.demo.data.client.WebRequestLogWsClient;
import java.io.IOException;

public class ReportDurationAlert extends AlertExecution
{
	private static final long REFRESH_DELAY = 10 * 60;
	private final WebRequestLogWsClient webRequestLogWsClient = new WebRequestLogWsClient();
	
	@Override
	public long getRefreshDelay()
	{
		return REFRESH_DELAY;
	}

	@Override
	public void execute(SerialIO serialIo) throws IOException, InterruptedException
	{
		Double reportDuration = webRequestLogWsClient.getReportDuration();
		
		int alertLevel = reportDuration > 3.0 ? 1 : 0;
		String string = "Report Duration " + AlertUtils.generate4DigitString(reportDuration);
		serialIo.sendAsciiString(1, alertLevel, string);
	}

}
