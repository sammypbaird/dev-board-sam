package com.ca.devboard.serial.demo.alert;

import com.ca.devboard.serial.SerialIO;
import com.ca.devboard.serial.demo.AlertUtils;
import static com.ca.devboard.serial.demo.alert.Alert.*;
import com.ca.devboard.serial.demo.data.client.Forecast;
import com.ca.devboard.serial.demo.data.client.WeatherClient;
import java.io.IOException;

public class WeatherAlert extends AlertExecution
{
	private static final long REFRESH_DELAY = 10 * 60;
	private final WeatherClient weatherClient = new WeatherClient();
	
	@Override
	public long getRefreshDelay()
	{
		return REFRESH_DELAY;
	}

	@Override
	public void execute(SerialIO serialIo) throws IOException, InterruptedException
	{
		Forecast forecast = weatherClient.downloadForecast();
		serialIo.sendAsciiString(WEATHER_CURRENT_TEMP.getId(), 0, "Current temp " + AlertUtils.generate4DigitString(forecast.getCurrentTemperature()));
		serialIo.sendAsciiString(WEATHER_LOW_TEMP.getId(), 0, "Low temp " + AlertUtils.generate4DigitString(forecast.getLowTemperature()));
		serialIo.sendAsciiString(WEATHER_HIGH_TEMP.getId(), 0, "High temp " + AlertUtils.generate4DigitString(forecast.getHighTemperature()));
		serialIo.sendAsciiString(WEATHER_FORECAST.getId(), 0, "Forecast " + forecast.getForecast());
		updateRefreshTimestamp();
	}
}
