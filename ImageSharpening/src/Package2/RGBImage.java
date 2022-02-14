package Package2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class RGBImage extends ImageHeader {
	byte[] headerBytes = new byte[54];
	boolean isTrue;
	int[] pixels;
	int height;
	int width;
	int row;
	int col;

	public RGBImage() {
		super();

	}

	public void readImageInfo(InputStream input) {

		try {

			input.read(headerBytes, 0, 54);

			super.imageType = Arrays.copyOfRange(headerBytes, 0, 2);
			isTrue = isBMP(super.imageType);
			if (isTrue == false) {
				System.out.println("The input file is not a BMP file.");
				System.exit(0);
			}
			super.noOfBitsPerPixel = Arrays.copyOfRange(headerBytes, 28, 30);
			if ((convertLittleToBigEndian(super.noOfBitsPerPixel)) != 24) {
				System.out.println("The input file is not a 24bit BMP file.");
				System.exit(0);
			}

			super.widthBytes = Arrays.copyOfRange(headerBytes, 18, 22);
			super.heightBytes = Arrays.copyOfRange(headerBytes, 22, 26);
			this.width = convertLittleToBigEndian(super.widthBytes);
			this.height = convertLittleToBigEndian(super.heightBytes);

			pixels = new int[width * height];

			this.row = this.height - 1;

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void readImageInfo(byte[] headerBytes) {

		super.imageType = Arrays.copyOfRange(headerBytes, 0, 2);
		isTrue = isBMP(super.imageType);
		if (isTrue == false) {
			System.out.println("The input file is not a BMP file.");
			System.exit(0);
		}
		super.noOfBitsPerPixel = Arrays.copyOfRange(headerBytes, 28, 30);
		if ((convertLittleToBigEndian(super.noOfBitsPerPixel)) != 24) {
			System.out.println("The input file is not a 24bit BMP file.");
			System.exit(0);
		}

		super.widthBytes = Arrays.copyOfRange(headerBytes, 18, 22);
		super.heightBytes = Arrays.copyOfRange(headerBytes, 22, 26);
		this.width = convertLittleToBigEndian(super.widthBytes);
		this.height = convertLittleToBigEndian(super.heightBytes);

		pixels = new int[width * height];

		this.row = this.height - 1;

	}

	@Override
	protected int readImage(InputStream input, int dimension) throws IOException {

		int noOfPaddingBytes = 0;
		if (3 * this.width % 4 != 0)
			noOfPaddingBytes = 4 - (3 * this.width) % 4;
		for (int i = this.row, counter = 0; i >= 0; i--, counter++) {
			int padding = noOfPaddingBytes;
			int index = i * this.width;
			if (counter == dimension && i != 0) {
				this.row = i;
				return this.row + 1;
			}
			for (int j = 0; j < this.width; j++) {

				int blue = input.read();
				int green = input.read();
				int red = input.read();

				pixels[index++] = 0xff000000 + (blue << 16) + (green << 8) + red;
			}

			while (padding > 0) {
				input.read();
				padding--;
			}
		}
		this.row = this.height - 1;
		return 0;
	}

	public int getImageDimension() {
		return this.width * this.height;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public byte[] getHeader() {
		return this.headerBytes;
	}

	public int getPixel(int index) {
		if (index >= 0 && index < this.width * this.height)
			return pixels[index];

		return 0;
	}

	public int[] getPixels() {
		return this.pixels;
	}
}
