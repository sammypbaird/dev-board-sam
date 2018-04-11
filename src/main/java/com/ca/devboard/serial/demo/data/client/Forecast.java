package com.ca.devboard.serial.demo.data.client;

public class Forecast 
{
	private Double currentTemperature;
	private Double lowTemperature;
	private Double highTemperature;
	private String forecast;

	public void setCurrentTemperature(Double currentTemperature)
	{
		this.currentTemperature = currentTemperature;
	}

	public void setLowTemperature(Double lowTemperature)
	{
		this.lowTemperature = lowTemperature;
	}

	public void setHighTemperature(Double highTemperature)
	{
		this.highTemperature = highTemperature;
	}

	public void setForecast(String forecast)
	{
		this.forecast = forecast;
	}

	public Double getCurrentTemperature()
	{
		return currentTemperature;
	}

	public Double getLowTemperature()
	{
		return lowTemperature;
	}

	public Double getHighTemperature()
	{
		return highTemperature;
	}

	public String getForecast()
	{
		return forecast;
	}
	
	
}
