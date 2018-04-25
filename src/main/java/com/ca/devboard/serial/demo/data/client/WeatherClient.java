package com.ca.devboard.serial.demo.data.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

public class WeatherClient
{
	//https://api.weather.gov/stations/KBOI/observations/current
	//https://api.weather.gov/gridpoints/BOI/131,83/forecast
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public Forecast downloadForecast()
	{
		Forecast forecast = downloadLowHighForecast();
		forecast.setCurrentTemperature(downloadTemperature());
		return forecast;
	}
	
	private Forecast downloadLowHighForecast()
	{
		Forecast forecast = new Forecast();
		try
		{
			URI uri = new URIBuilder().setScheme("http").setHost("api.weather.gov").setPath("/gridpoints/BOI/131,83/forecast").build();
			HttpGet get = new HttpGet(uri);
			get.addHeader("Accept", "application/geo+json;version=1");
			get.addHeader("User-Agent", "sammypbaird@gmail.com");
			HttpResponse response = HttpClient.get().execute(get);
			Map<String, Object> json = mapper.readValue(response.getEntity().getContent(), Map.class);
			parseHighLowForecast(json, forecast);
		}
		catch (IOException|URISyntaxException ex)
		{
			throw new AlertClientException(ex);
		}
		return forecast;
	}
	
	private void parseHighLowForecast(Map<String, Object> json, Forecast forecast)
	{
		Map<String, Object> properties = (Map<String, Object>) json.get("properties");
		if (properties == null)
			return;
		List<Map<String, Object>> periods = (List<Map<String, Object>>) properties.get("periods");
		if (periods == null)
			return;
		if (periods.isEmpty())
			return;
		
		//today
		Map<String, Object> today = periods.get(0);
		forecast.setHighTemperature(((Number) today.get("temperature")).doubleValue());
		forecast.setForecast((String) today.get("shortForecast"));
		
		//tonight
		if (periods.size() <= 1)
			return;
		Map<String, Object> tonight = periods.get(1);
		forecast.setLowTemperature(((Number) tonight.get("temperature")).doubleValue());
	}
	
	private Double downloadTemperature()
	{
		try
		{
			URI uri = new URIBuilder().setScheme("http").setHost("api.weather.gov").setPath("/stations/KBOI/observations/current").build();
			HttpGet get = new HttpGet(uri);
			get.addHeader("Accept", "application/geo+json;version=1");
			get.addHeader("User-Agent", "sammypbaird@gmail.com");
			HttpResponse response = HttpClient.get().execute(get);
			Map<String, Object> json = mapper.readValue(response.getEntity().getContent(), Map.class);
			return parseTemperature(json);
		}
		catch (IOException|URISyntaxException ex)
		{
			throw new IllegalArgumentException(ex);
		}
	}
	
	private Double parseTemperature(Map<String, Object> json)
	{
		Map<String, Object> properties = (Map<String, Object>) json.get("properties");
		if (properties == null)
			return null;
		Map<String, Object> temperature = (Map<String, Object>) properties.get("temperature");
		if (temperature == null)
			return null;
		Number tempCelcius = (Number) temperature.get("value");
		if (tempCelcius == null)
			return null;
		return tempCelcius.doubleValue() * 1.8 + 32;
	}
}
