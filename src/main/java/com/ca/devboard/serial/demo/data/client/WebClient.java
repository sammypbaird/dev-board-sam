package com.ca.devboard.serial.demo.data.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;

public class WebClient
{
	//hourly temps
	//http://forecast.weather.gov/MapClick.php?lat=43.56704&lon=-116.24053&FcstType=digitalDWML
	
	//https://api.weather.gov/
	
	//https://kimballrexford.com/national-weather-service-nws-new-api/
	
	private static HttpClient client = HttpClients.createDefault();
	private static ObjectMapper mapper = new ObjectMapper();
	
	public void downloadTemperature()
	{
		try
		{
			URI uri = new URIBuilder().setScheme("http").setHost("api.weather.gov").setPath("/gridpoints/BOI/131,83/forecast/hourly").build();
			HttpGet get = new HttpGet(uri);
			get.addHeader("Accept", "application/geo+json;version=1");
			get.addHeader("User-Agent", "sammypbaird@gmail.com");
			HttpResponse response = client.execute(get);
			Map<String, Object> json = mapper.readValue(response.getEntity().getContent(), Map.class);
		}
		catch (IOException|URISyntaxException ex)
		{
			throw new IllegalArgumentException(ex);
		}

	}	
	
	
}
