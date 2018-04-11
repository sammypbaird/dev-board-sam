package com.ca.devboard.serial.demo;

import java.awt.Color;
import static java.awt.Color.white;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Mandlebrot
{
	private static final int WIDTH = 160;
	private static final int HEIGHT = 128;
	private static final int MAX_ITERATIONS = 1000;

	public void drawMandlebrot() throws IOException
	{
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
         int black = 0;
        int[] colors = new int[MAX_ITERATIONS];
        for (int i = 0; i<MAX_ITERATIONS; i++) {
            colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
        }
		for (int row = 0; row < HEIGHT; row++)
		{
			for (int col = 0; col < WIDTH; col++)
			{
				double c_re = (col - WIDTH / 2.0) * 4.0 / WIDTH;
				double c_im = (row - HEIGHT / 2.0) * 4.0 / HEIGHT;
				double x = 0, y = 0;
				int iteration = 0;
				while (x * x + y * y <= 4 && iteration < MAX_ITERATIONS)
				{
					double x_new = x * x - y * y + c_re;
					y = 2 * x * y + c_im;
					x = x_new;
					iteration++;
				}
				if (iteration < MAX_ITERATIONS) 
					image.setRGB(col, row, colors[iteration]);
                else 
					image.setRGB(col, row, black);
			}
		}
		
		ImageIO.write(image, "png", new File("mandelbrot.png"));
	}
	
	public static void main(String[] args) throws IOException
	{
		new Mandlebrot().drawMandlebrot();
	}
}
