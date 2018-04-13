package com.ca.devboard.serial.demo.data.client;

import com.sun.xml.internal.ws.util.StreamUtils;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

public class WebRequestLogWsClient 
{
	public Double getReportDuration()
	{
		try
		{
			URI uri = new URIBuilder().setScheme("http").setHost("prod-boi-ext-webrequestlog-app1").setPath("/web-request-log-ws/prometheus/reportDuration").build();
			HttpGet get = new HttpGet(uri);
			HttpResponse response = HttpClient.get().execute(get);
			if (response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() >= 300)
				throw new IllegalArgumentException("Status code " + response.getStatusLine());
			List<String> lines = IOUtils.readLines(response.getEntity().getContent(), Charset.defaultCharset());
			if (lines == null || lines.size() < 1)
				return null;
			String line = lines.get(0);
			String[] pair = line.split("\\w");
			if (pair.length < 2)
				return null;
			return Double.parseDouble(pair[1]);
		}
		catch (URISyntaxException|IOException ex)
		{
			throw new IllegalArgumentException(ex);
		}
	}
}
