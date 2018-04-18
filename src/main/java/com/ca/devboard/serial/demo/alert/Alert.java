package com.ca.devboard.serial.demo.alert;

import com.ca.devboard.serial.SerialIO;
import java.io.IOException;

public enum Alert 
{
	GARBAGE_COLLECTION(1),
	DATABASE_CPU_RECONDB(2),
	DATABASE_CPU_REPORTDB(3),
	DATABASE_CPU_MSSQLDB3(4),
	WEATHER_CURRENT_TEMP(5),
	WEATHER_HIGH_TEMP(6),
	WEATHER_LOW_TEMP(7),
	WEATHER_FORECAST(8);
	
	private final int id;
	
	Alert(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}
}
