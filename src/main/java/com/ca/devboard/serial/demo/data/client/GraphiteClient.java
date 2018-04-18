package com.ca.devboard.serial.demo.data.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

public class GraphiteClient 
{
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
//	public static void main(String[] args)
//	{
//		GraphiteMetric graphiteMetric = new GraphiteClient().getLatestGarbageCollection(); //boi-reportdb, boi-mssql-db3
//		System.out.println("Target: " + graphiteMetric.target);
//		System.out.println("Value: " + graphiteMetric.value);
//	}
	
	public GraphiteMetric getLatestDatabaseCpu(String database)
	{
		return request("graphite", "SQLServers.Production." + database + ".OS.Processor:PercentTotal");
	}
	
	public GraphiteMetric getLatestGarbageCollection()
	{
		return request("telegraf", "aliasByNode(highestCurrent(perSecond(scale(telegraf.prod.boi.prod-boi-ext-*.jvm.tomcat.GCTime.*,0.1)),1),3)");
	}
	
	private URI createTargetUrl(String host, String target)
	{
		try
		{
			URIBuilder urlBuilder = new URIBuilder("http://" + host + "/render");
			urlBuilder.setParameter("from", "-2mins");
			urlBuilder.setParameter("until", "now");
			urlBuilder.setParameter("format", "json");
			urlBuilder.setParameter("target", target);
			return urlBuilder.build();
		}
		catch (URISyntaxException ex)
		{
			throw new IllegalArgumentException(ex);
		}
	}

	private GraphiteMetric request(String host, String graphiteTarget)
	{
	
		HttpGet httpGet = new HttpGet(createTargetUrl(host, graphiteTarget));

		try (CloseableHttpResponse response = HttpClient.get().execute(httpGet))
		{
			List<Map<String, Object>> data = MAPPER.readValue(response.getEntity().getContent(), new TypeReference<List<Map<String, Object>>>(){});
			String target = (String) data.get(0).get("target");
			List<List<Object>> datapoints = (List<List<Object>>) data.get(0).get("datapoints");
			for (int i = datapoints.size() - 1; i >= 0; i--)
			{
				List<Object> row = datapoints.get(i);
				if (!row.isEmpty() && row.get(0) instanceof Number)
				{
					return new GraphiteMetric(((Number) row.get(0)).doubleValue(), target);
				}
			}

			return null;
		}
		catch (Exception ex)
		{
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
	}
	
	public static class GraphiteMetric
	{
		private final double value;
		private final String target;

		public GraphiteMetric(double value, String target)
		{
			this.value = value;
			this.target = target;
		}

		public double getValue()
		{
			return value;
		}

		public String getTarget()
		{
			return target;
		}
	}
}
