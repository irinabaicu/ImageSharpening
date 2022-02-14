package Package2;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Producer extends Thread {
	private Buffer buffer;
	private RGBImage image;
	private int imageDimension;
	private String imagePath;

	public Producer(Buffer buffer, String path) {
		this.buffer = buffer;
		this.image = new RGBImage();
		this.imagePath = path;
		// folosesti path-ul
	}

	public void run() {
		InputStream input = null;

		try {
			input = new BufferedInputStream(new FileInputStream(imagePath)); // "a.bmp"

			image.readImageInfo(input);

			buffer.putImageHeader(image.getHeader());

			imageDimension = image.getImageDimension();
			int portion = image.getHeight() / 4;
			// pixel = new int[imageDimension];
			int ct = 0;
			int left = image.getHeight() % 4;

			while (ct != 4) {
				int start;
				if (left > 0)
					start = image.readImage(input, portion + 1);
				else
					start = image.readImage(input, portion);
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				if (left > 0)
					for (int i = start * image.getWidth(); i < start * image.getWidth() + portion + 1; i++) {
						System.out.print("Producatorul a pus:\t");
						System.out.println(image.getPixel(i));
					}
				else
					for (int i = start * image.getWidth(); i < start * image.getWidth() + portion; i++) {
						System.out.print("Producatorul a pus:\t");
						System.out.println(image.getPixel(i));
					}

				System.out.println(ct);
				if (left > 0)
					buffer.put(image.getPixels(), start * image.getWidth(), portion + 1);
				else
					buffer.put(image.getPixels(), start * image.getWidth(), portion);

				left--;

				try {
					sleep(1000);
					System.out.println("SLEEP");
				} catch (InterruptedException e) {
				}
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				ct++;

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
