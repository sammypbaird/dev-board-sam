package com.ca.devboard.serial.demo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader
{

	/**
	 * Adafruit TFT 16 bit display is RGB565 The byte array contains 2 bytes per
	 * pixel, starting at the top left corner of the image
	 */
	public byte[] loadImageRGB565(String filename)
	{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		BufferedImage img = null;
		try
		{
			img = ImageIO.read(new File(filename));
			for (int y = 0; y < img.getHeight(); y++)
			{
				for (int x = 0; x < img.getWidth(); x++)
				{
					byte[] rgb565 = convertToRGB565(img.getRGB(x, y));
					byteArrayOutputStream.write(rgb565[0]);
					byteArrayOutputStream.write(rgb565[1]);
				}
			}

		}
		catch (IOException e)
		{
			throw new IllegalArgumentException(e);
		}
		return byteArrayOutputStream.toByteArray();
	}
	
	//bits 24-31 is alpha, 16-23 is Red, 8-15 is Green, 0-7 is blue
	private static byte[] convertToRGB565(int c)
	{
		int red = (c >> 16) & 0x0FF;
		int green = (c >> 8) & 0x0FF;
		int blue = (c & 0x0FF);

		//RGB565 1111100000000000
		red = red >> 3;
		green = green >> 2;
		blue = blue >> 3;

		//put it into a byte
		short pixel_to_send = 0;
		int pixel_to_send_int = 0;
		pixel_to_send_int = (red << 11) | (green << 5) | (blue);
		pixel_to_send = (short) pixel_to_send_int;

		//dividing into bytes
		byte byteH = (byte) ((pixel_to_send >> 8) & 0x0FF);
		byte byteL = (byte) (pixel_to_send & 0x0FF);
		return new byte[]{byteH, byteL};
	}
	
	public static void main(String[] args)
	{
		Color color = Color.GREEN;
		int val = color.getRGB();
		System.out.println("Before:");
		System.out.println(Integer.toBinaryString(val));
		byte[] rgb565 = convertToRGB565(val);
		System.out.println("After:");
		printByte(rgb565[0]);
		printByte(rgb565[1]);
	}
	
	private static void printByte(byte b)
	{
		System.out.println(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
	}
}
