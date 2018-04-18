package com.ca.devboard.serial.demo.alert;

import com.ca.devboard.serial.SerialIO;
import com.ca.devboard.serial.demo.AlertUtils;
import static com.ca.devboard.serial.demo.alert.Alert.*;
import com.ca.devboard.serial.demo.data.client.GraphiteClient;
import java.io.IOException;

public class GraphiteDatabaseCpuAlert extends AlertExecution
{
	private static final long REFRESH_DELAY = 3 * 60;
	private static final String BOI_RECONDB = "boi-recondb";
	private static final String BOI_REPORTDB = "boi-reportdb";
	private static final String BOI_MSSQL_DB3 = "boi-mssql-db3";
	
	private final GraphiteClient graphiteClient = new GraphiteClient();

	@Override
	public void execute(SerialIO serialIo) throws IOException, InterruptedException
	{
		executeDatabase(serialIo, BOI_RECONDB, DATABASE_CPU_RECONDB.getId());
		executeDatabase(serialIo, BOI_REPORTDB, DATABASE_CPU_REPORTDB.getId());
		executeDatabase(serialIo, BOI_MSSQL_DB3, DATABASE_CPU_MSSQLDB3.getId());
	}
	
	private void executeDatabase(SerialIO serialIo, String databaseName, int alertId) throws IOException, InterruptedException
	{
		GraphiteClient.GraphiteMetric graphiteMetric = graphiteClient.getLatestDatabaseCpu(databaseName);
		int alertLevel = graphiteMetric.getValue() > 50 ? 1 : 0;
		String string = "DB CPU - " + databaseName + " " + AlertUtils.generate4DigitString(graphiteMetric.getValue());
		serialIo.sendAsciiString(alertId, alertLevel, string);
	}

	@Override
	public long getRefreshDelay()
	{
		return REFRESH_DELAY;
	}
}
