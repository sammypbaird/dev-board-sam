package com.ca.devboard.serial.demo.data.client;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClient 
{
	private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();
	
	public static CloseableHttpClient get()
	{
		return HTTP_CLIENT;
	}
}
