package com.ca.devboard.serial.demo;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class AlertUtils 
{
	private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("0.000");
	
	private AlertUtils(){}
	
	public static String generate4DigitString(double number)
	{
		StringBuilder sb = new StringBuilder();
		String strNumber = NUMBER_FORMAT.format(number);
		
		//if the first 4 digits contains a period, send 5 characters, because the period will be combined with one of the digits
		int size = strNumber.substring(0, 4).contains(".") ? 5 : 4;
		for (int i=0; i<size; i++)
			sb.append(strNumber.charAt(i));
		
		return sb.toString();
	}
}
