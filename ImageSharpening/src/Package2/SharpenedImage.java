package Package2;

public class SharpenedImage extends RGBImage implements ImageFiltering {
	private int[] sharpImage;

	public SharpenedImage(byte[] headerImage, int[] pixels) {
		super.headerBytes = headerImage;
		super.readImageInfo(headerImage);
		sharpImage = new int[super.width * super.height];
		super.pixels = new int[super.width * super.height];
		super.pixels = pixels;
	}

	private int getChannelValue(String channel, int row, int col) {
		int shift = 0;
		if (channel == "blue")
			shift = 16;
		else if (channel == "green")
			shift = 8;
		else if (channel == "red")
			shift = 0;
		// System.out.println("______________________________________________________________________");
		// System.out.println(channel + " " + String.format("%x",
		// pixels[row*width + col]) + " : " +
		// String.format("%x", (super.pixels[row*width + col] >> shift) &
		// 0xFF));
		// System.out.println("______________________________________________________________________");
		return ((super.pixels[row * super.width + col] >> shift) & 0xFF);
	}

	@Override
	public void sharpImage() {
		double center = 1.9;
		double sides = 0.225;
		for (int i = 0; i < super.height; i++) {
			for (int j = 0; j < super.width; j++) {
				int blue = 0, green = 0, red = 0;

				// calculate the up-left corner
				if (i == 0 && j == 0) {
					blue = (int) (center * getChannelValue("blue", i, j) - sides * getChannelValue("blue", i + 1, j)
							- sides * getChannelValue("blue", i, j + 1) - sides * 2 * getChannelValue("blue", i, j));
					green = (int) (center * getChannelValue("green", i, j) - sides * getChannelValue("green", i + 1, j)
							- sides * getChannelValue("green", i, j + 1) - sides * 2 * getChannelValue("green", i, j));
					red = (int) (center * getChannelValue("red", i, j) - sides * getChannelValue("red", i + 1, j)
							- sides * getChannelValue("red", i, j + 1) - sides * 2 * getChannelValue("red", i, j));
				}

				// calculate the up-right corner
				else if (i == 0 && j == (super.width - 1)) {
					blue = (int) (center * getChannelValue("blue", i, j) - sides * getChannelValue("blue", i, j - 1)
							- sides * getChannelValue("blue", i + 1, j) - sides * 2 * getChannelValue("blue", i, j));
					green = (int) (center * getChannelValue("green", i, j) - sides * getChannelValue("green", i, j - 1)
							- sides * getChannelValue("green", i + 1, j) - sides * 2 * getChannelValue("green", i, j));
					red = (int) (center * getChannelValue("red", i, j) - sides * getChannelValue("red", i, j - 1)
							- sides * getChannelValue("red", i + 1, j) - sides * 2 * getChannelValue("red", i, j));

				}

				// calculate the down-right corner
				else if ((j == (super.width - 1)) && (i == (super.height - 1))) {

					blue = (int) (center * getChannelValue("blue", i, j) - sides * getChannelValue("blue", i - 1, j)
							- sides * getChannelValue("blue", i, j - 1) - sides * 2 * getChannelValue("blue", i, j));
					green = (int) (center * getChannelValue("green", i, j) - sides * getChannelValue("green", i - 1, j)
							- sides * getChannelValue("green", i, j - 1) - sides * 2 * getChannelValue("green", i, j));
					red = (int) (center * getChannelValue("red", i, j) - sides * getChannelValue("red", i - 1, j)
							- sides * getChannelValue("red", i, j - 1) - sides * 2 * getChannelValue("red", i, j));

				}

				// calculate the down-left corner
				else if (i == (super.height - 1) && j == 0) {

					blue = (int) (center * getChannelValue("blue", i, j) - sides * getChannelValue("blue", i, j + 1)
							- sides * getChannelValue("blue", i - 1, j) - sides * 2 * getChannelValue("blue", i, j));
					green = (int) (center * getChannelValue("green", i, j) - sides * getChannelValue("green", i, j + 1)
							- sides * getChannelValue("green", i - 1, j) - sides * 2 * getChannelValue("green", i, j));
					red = (int) (center * getChannelValue("red", i, j) - sides * getChannelValue("red", i, j + 1)
							- sides * getChannelValue("red", i - 1, j) - sides * 2 * getChannelValue("red", i, j));

				}

				// calculate upper edge
				else if (i == 0 && j > 0 && j < super.width - 1) {

					blue = (int) (center * getChannelValue("blue", i, j) - sides * getChannelValue("blue", i + 1, j)
							- sides * getChannelValue("blue", i, j - 1) - sides * getChannelValue("blue", i, j + 1)
							- sides * getChannelValue("blue", i, j));
					green = (int) (center * getChannelValue("green", i, j) - sides * getChannelValue("green", i + 1, j)
							- sides * getChannelValue("green", i, j - 1) - sides * getChannelValue("green", i, j + 1)
							- sides * getChannelValue("green", i, j));
					red = (int) (center * getChannelValue("red", i, j) - sides * getChannelValue("red", i + 1, j)
							- sides * getChannelValue("red", i, j - 1) - sides * getChannelValue("red", i, j + 1)
							- sides * getChannelValue("red", i, j));

				}

				// calculate bottom edge
				else if (i == (super.height - 1) && j > 0 && j < super.width - 1) {

					blue = (int) (center * getChannelValue("blue", i, j) - sides * getChannelValue("blue", i, j - 1)
							- sides * getChannelValue("blue", i, j + 1) - sides * getChannelValue("blue", i - 1, j)
							- sides * getChannelValue("blue", i, j));
					green = (int) (center * getChannelValue("green", i, j) - sides * getChannelValue("green", i, j - 1)
							- sides * getChannelValue("green", i, j + 1) - sides * getChannelValue("green", i - 1, j)
							- sides * getChannelValue("green", i, j));
					red = (int) (center * getChannelValue("red", i, j) - sides * getChannelValue("red", i, j - 1)
							- sides * getChannelValue("red", i, j + 1) - sides * getChannelValue("red", i - 1, j)
							- sides * getChannelValue("red", i, j));

				}

				// calculate left edge
				else if (j == 0 && i > 0 && i < super.height - 1) {

					blue = (int) (center * getChannelValue("blue", i, j) - sides * getChannelValue("blue", i + 1, j)
							- sides * getChannelValue("blue", i - 1, j) - sides * getChannelValue("blue", i, j + 1)
							- sides * getChannelValue("blue", i, j));
					green = (int) (center * getChannelValue("green", i, j) - sides * getChannelValue("green", i + 1, j)
							- sides * getChannelValue("green", i - 1, j) - sides * getChannelValue("green", i, j + 1)
							- sides * getChannelValue("green", i, j));
					red = (int) (center * getChannelValue("red", i, j) - sides * getChannelValue("red", i + 1, j)
							- sides * getChannelValue("red", i - 1, j) - sides * getChannelValue("red", i, j + 1)
							- sides * getChannelValue("red", i, j));

				}

				// calculate right edge
				else if (j == (super.width - 1) && i > 0 && i < super.height - 1) {

					blue = (int) (center * getChannelValue("blue", i, j) - sides * getChannelValue("blue", i + 1, j)
							- sides * getChannelValue("blue", i - 1, j) - sides * getChannelValue("blue", i, j - 1)
							- sides * getChannelValue("blue", i, j));
					green = (int) (center * getChannelValue("green", i, j) - sides * getChannelValue("green", i + 1, j)
							- sides * getChannelValue("green", i - 1, j) - sides * getChannelValue("green", i, j - 1)
							- sides * getChannelValue("green", i, j));
					red = (int) (center * getChannelValue("red", i, j) - sides * getChannelValue("red", i + 1, j)
							- sides * getChannelValue("red", i - 1, j) - sides * getChannelValue("red", i, j - 1)
							- sides * getChannelValue("red", i, j));

				}

				// calculate the middle pixels
				else if ((i > 0) && (i < super.height - 1) && (j > 0) && (j < super.width - 1)) {

					blue = (int) (center * getChannelValue("blue", i, j) - sides * getChannelValue("blue", i - 1, j)
							- sides * getChannelValue("blue", i + 1, j) - sides * getChannelValue("blue", i, j - 1)
							- sides * getChannelValue("blue", i, j + 1));
					green = (int) (center * getChannelValue("green", i, j) - sides * getChannelValue("green", i - 1, j)
							- sides * getChannelValue("green", i + 1, j) - sides * getChannelValue("green", i, j - 1)
							- sides * getChannelValue("green", i, j + 1));
					red = (int) (center * getChannelValue("red", i, j) - sides * getChannelValue("red", i - 1, j)
							- sides * getChannelValue("red", i + 1, j) - sides * getChannelValue("red", i, j - 1)
							- sides * getChannelValue("red", i, j + 1));

				}

				this.sharpImage[i * super.width + j] = 0xff000000 + (blue << 16) + (green << 8) + red;

			}
		}
	}

	public byte[] getPixelsByte() {
		int padding = 0;
		if (3 * this.width % 4 != 0)
			padding = 4 - (3 * this.width) % 4;
		byte[] bytes = new byte[super.width * super.height * 3 + padding * super.height];
		int index = 0;
		for (int i = super.height - 1; i >= 0; i--) {
			int nrPadding = padding;
			for (int j = 0; j < super.width; j++) {
				// bytes[i*super.width*3 + j*3] = (byte)
				// ((this.pixels[i*super.width + j] >> 16 ) & 0xFF);
				// bytes[i*super.width*3 + j*3 + 1] = (byte)
				// ((this.pixels[i*super.width + j] >> 8 ) & 0xFF);
				// bytes[i*super.width*3 + j*3 + 2] = (byte)
				// ((this.pixels[i*super.width + j]) & 0xFF);
				bytes[index++] = (byte) ((this.sharpImage[i * super.width + j] >> 16) & 0xFF);
				bytes[index++] = (byte) ((this.sharpImage[i * super.width + j] >> 8) & 0xFF);
				bytes[index++] = (byte) ((this.sharpImage[i * super.width + j]) & 0xFF);
			}
			while (nrPadding > 0) {
				bytes[index++] = 0x00;
				nrPadding--;
			}
		}
		return bytes;
	}

}
