package com.ca.devboard.serial.demo.alert;

import com.ca.devboard.serial.SerialIO;
import java.io.IOException;

public abstract class AlertExecution 
{
	//specifies the timestamp when we should execute a refresh
	private long refreshTimestamp = 0;
	
	/**
	 * Get the refresh delay (how long between refreshing the alert) in seconds
	 */
	public abstract long getRefreshDelay();
	
	public void setRefreshTimestamp(long refreshTimestamp)
	{
		this.refreshTimestamp = refreshTimestamp;
	}
	
	/**
	 * Upon executing, this should gather the metrics, and alert to the dev-board
	 */
	public abstract void execute(SerialIO serialIo) throws IOException, InterruptedException;
	
	/**
	 * Calling this method updates the refresh timestamp to be in the future 
	 * (by how ever many refresh seconds are defined on the alert)
	 */
	protected void updateRefreshTimestamp()
	{
		refreshTimestamp = System.currentTimeMillis() + (getRefreshDelay() * 1000);
	}
	
	/**
	 * Returns true if we need to refresh the alert (i.e. call the execute method).
	 */
	public boolean needsRefresh()
	{
		return System.currentTimeMillis() > refreshTimestamp;
	}
}
