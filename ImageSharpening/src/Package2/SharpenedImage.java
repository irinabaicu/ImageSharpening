package Package2;

public class SharpenedImage extends RawImage implements ImageFiltering {
	protected int[] sharpImage; // stocheaza pixelii in urma procesarii imaginii initiale

	public SharpenedImage(byte[] headerImage, int[] pixels) {
		super.headerBytes = headerImage;
		super.readImageInfo(headerImage);
		sharpImage = new int[super.width * super.height]; // se aloca suficienta memorie pentru
														  // stocarea pixelilor procesati
		super.pixels = new int[super.width * super.height]; // se aloca memorie pentru stocarea
															// pixelilor neprocesati
		super.pixels = pixels;
	}

	// Metoda intoarce valoarea byte-ului de culoare (albastru, verde sau rosu)
	// din pixelul de pe linia row si coloana col
	private int getChannelValue(String channel, int row, int col) {
		int shift = 0;
		if (channel.equals("blue"))
			shift = 16; // culoarea albastru este extrasa aducand al treilea
						// byte pe positia LSB
		else if (channel.equals("green"))
			shift = 8; // culoarea verde este extrasa aducand al doilea byte pe
						// positia LSB
		else if (channel.equals("red"))
			shift = 0; // culoarea rosie este extrasa de pe LSB, deci nu este
						// nevoie de shiftare

		return ((super.pixels[row * super.width + col] >> shift) & 0xFF); // se extrage si returneaza
																		  // byte-ul corespunzator
																		  // culorii alese
	}

	// Metoda aplica kernelul de sharpening peste pixelii imaginii obtinandu-se
	// astfel pixelii imaginii procesate
	// Kernelul folosit este un kernel de convolutie 3x3 de forma :
	// | 0 -sides 0 |
	// | -sides center -sides |
	// | 0 -sides 0 |
	@Override
	public void sharpImage() {
		double center = 5; // elementul din centru al matricii kernel
		double sides = 1; // elementul de la extremitatile matricii kernel

		// se parcurge vectorul initial si se realizeaza procesarea pixel cu pixel
		// calculand valorile noi pe baza valorilor vecinilor folosindu-se o rama suplimentara
		// formata din elementele de la marginea matricei initiale
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

				// Se realizeaza limitarea (clamping) atunci cand se obtine o
				// valoare negativa sau se trece peste valoarea
				// maxima posibila a unui byte (255 = 2^8 - 1)
				if (blue < 0)
					blue = 0;
				if (blue > 255)
					blue = 255;
				if (green < 0)
					green = 0;
				if (green > 255)
					green = 255;
				if (red < 0)
					red = 0;
				if (red > 255)
					red = 255;
				// Se stocheaza valorile culorilor obtinute in vectorul
				// sharpImage folosind acelasi format
				this.sharpImage[i * super.width + j] = 0x00000000 + ((blue & 0xFF) << 16) + ((green & 0xFF) << 8)
						+ (red & 0xFF);
			}
		}
	}

	// Returneaza vectorul de pixeli procesat
	public int[] getSharpenedImage() {
		return this.sharpImage;
	}

}
