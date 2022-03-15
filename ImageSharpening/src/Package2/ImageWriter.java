package Package2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

// Clasa are scopul de a transpune vectorul de pixeli intr-un vector
// de bytes si de a creea un fisier bmp folosind un info-header si 
// vectorul de pixeli ce formeaza imaginea
public class ImageWriter extends SharpenedImage {
	public ImageWriter(byte[] infoHeader, int[] pixelData) {
		super(infoHeader, pixelData); // se apeleaza constructorul clasei
										// parinte
	}

	// metoda transpune vectorul pixels intr-un vector de bytes in care se afla
	// atat octetii culorilor (3 per pixel) cat si octetii de padding daca este cazul
	public byte[] getPixelsByte() {
		int padding = 0;
		if (3 * super.width % 4 != 0)
			// se determina numarul de octeti de padding necesari
			padding = 4 - (3 * super.width) % 4;
		// se aloca memorie pentru vectorul de octeti
		byte[] bytes = new byte[super.width * super.height * 3 + padding * super.height];
		int index = 0; // index folosit pentru parcurgerea vectorului de octeti
		for (int i = super.height - 1; i >= 0; i--) {
			int nrPadding = padding;
			for (int j = 0; j < super.width; j++) {

				// se extrag informatiile despre fiecare culoare din pixelul de
				// la linia i, coloana j
				// si se stocheaza in bytes in ordinea blue, green, red asa cum
				// impune formatul bmp.
				bytes[index++] = (byte) ((super.pixels[i * super.width + j] >> 16) & 0xFF);
				bytes[index++] = (byte) ((super.pixels[i * super.width + j] >> 8) & 0xFF);
				bytes[index++] = (byte) ((super.pixels[i * super.width + j]) & 0xFF);
			}
			// se introduc octetii de padding daca este cazul
			while (nrPadding > 0) {
				bytes[index++] = 0x00;
				nrPadding--;
			}
		}
		return bytes;
	}

	// Metoda creeaza fisierul bmp cu calea path folosind headerul de informatii
	// si octetii primiti
	public void createBMPImage(String path, byte[] byteArray) {
		if (super.isBMP == false) // se verifica daca info-headerul este cel
									// pentru formatul bmp
			System.out.println("Cannot create BMP file!");
		// Se deschide fisierul cu calea path (daca nu exista, acesta este
		// creat) si se scriu informatiile
		try (FileOutputStream fos = new FileOutputStream(path)) {
			fos.write(this.headerBytes); // se scrie info-headerul
			fos.write(byteArray); // se scriu octetii pixelilor
			fos.close(); // se inchide fisierul
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
