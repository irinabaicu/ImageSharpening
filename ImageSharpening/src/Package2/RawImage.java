package Package2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class RawImage extends ImageHeader {
	byte[] headerBytes = new byte[54]; // vector ce contine informatiile din hederul imaginii
	boolean isBMP = false;
	int[] pixels;
	int height;
	int width;
	int currentRow;

	public RawImage() {
		super();
	}

	public void readImageInfo(InputStream input) {

		try {
			// headerBytes este populat cu informatiile din headerul fisierului
			// BMP
			input.read(headerBytes, 0, 54);

			// imageType retine primii 2 biti ce reprezinta literele B si M
			super.imageType = Arrays.copyOfRange(headerBytes, 0, 2);
			// este apelata metoda din clasa abstracta pentru a verifica daca
			// fisierul introdus este fisier BMP
			isBMP = isBMP(super.imageType);

			// Verifica daca imaginea este fisier BMP
			if (isBMP == false) {
				System.out.println("The input file is not a BMP file.");
				System.exit(0);
			}

			// noOfBitsPerPixel preia numarul de biti din fiecare pixel pentru a
			// verifica daca imaginea corespunde cerintei de a fi pe 24 biti
			super.noOfBitsPerPixel = Arrays.copyOfRange(headerBytes, 28, 30);
			// verifica daca imaginea este 24bit BMP
			if ((convertLittleToBigEndian(super.noOfBitsPerPixel)) != 24) {
				System.out.println("The input file is not a 24bit BMP file.");
				System.exit(0);
			}

			// preia in vectoru de bytes, widthBytes, latimea imaginii din
			// headerBytes
			super.widthBytes = Arrays.copyOfRange(headerBytes, 18, 22);
			// preia in vectoru de bytes, heightBytes, inaltimea imaginii din
			// headerBytes
			super.heightBytes = Arrays.copyOfRange(headerBytes, 22, 26);
			// width preia valoarea in int a latimii imagini pentru a putea face
			// procesarea imaginii mai usor
			this.width = convertLittleToBigEndian(super.widthBytes);
			// height preia valoarea in int a latimii imagini pentru a putea
			// face procesarea imaginii mai usor
			this.height = convertLittleToBigEndian(super.heightBytes);
			// setez dimensiunea vectorului de pixeli = inaltime*latime
			pixels = new int[width * height];
			// salvez linia citita
			this.currentRow = this.height - 1;

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// metoda face acelasi lucru, dar extrage informatiile dintr-un vector de
	// bytes
	public void readImageInfo(byte[] headerBytes) {
		// initializeaza fiecare element din header-ul fisierului BMP rezultat
		// cu elementele BMP-ului initial
		super.imageType = Arrays.copyOfRange(headerBytes, 0, 2);
		isBMP = isBMP(super.imageType);
		if (isBMP == false) {
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

		this.currentRow = this.height - 1;

	}

	// metoda abstracta ce ajuta la citirea a 1/4 din informatie
	// este apelata de clasa Producer
	@Override
	protected int readImage(InputStream input, int dimension) throws IOException {
		// fiecare fisier binar are un numar de biti de padding
		int noOfPaddingBytes = 0;
		// algoritm pentru aflarea numarului de biti de padding din imaginea
		// introdusa
		if (3 * this.width % 4 != 0)
			noOfPaddingBytes = 4 - (3 * this.width) % 4;
		// popularea vectorului de pixeli cu date din fisierul sursa
		// popularea se face conform cu ordinea pixelirol din fisierul BMP
		for (int i = this.currentRow, counter = 0; i >= 0; i--, counter++) {
			int padding = noOfPaddingBytes;
			int index = i * this.width;
			if (counter == dimension && i != 0) {
				this.currentRow = i;
				return this.currentRow + 1;
			}
			for (int j = 0; j < this.width; j++) {
				// preia valoarea blue
				int blue = input.read();
				// pria valoarea green
				int green = input.read();
				// preia valoarea red
				int red = input.read();
				// valorile RGB sunt stocate intr-un pixel : MSB este 00, iar
				// urmatorii bytes reprezinta valorile
				// culorilor albastru, verde, rosu in aceasta ordine
				pixels[index++] = 0x00000000 + (blue << 16) + (green << 8) + red;
			}
			// pentru ca citirea sa fie corecta trebuie sa sarim bitii de
			// padding
			while (padding > 0) {
				input.read();// skip next byte
				padding--;
			}
		}
		// setez currentRow la valoarea initiala pentru a putea fi refolosita
		// metoda de citire daca este cazul
		this.currentRow = this.height - 1;
		return 0;
	}

	// Metode de tip Getter pentru accesarea campurilor din afara clasei
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
