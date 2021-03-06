package Package2;

import java.io.IOException;
import java.io.InputStream;

public abstract class ImageHeader {

	protected byte[] imageType = new byte[2];
	protected byte[] widthBytes = new byte[4];
	protected byte[] heightBytes = new byte[4];
	protected byte[] noOfBitsPerPixel = new byte[2];

	// Metoda verifica daca formatul fisierului este cel folosit pentru bmp
	public boolean isBMP(byte... b) {
		if (b[0] == 'B' && b[1] == 'M')
			return true;
		return false;
	}

	protected abstract int readImage(InputStream input, int dimension) throws IOException;
	
	// Pentru datele fisierului este folosita reprezentarea Little Endian, iar in Java
	// se foloseste Big Endian. De aceea este nevoie de o metoda ce converteste 
	// datele stocate cu Little Endian in date Big Endian.
	protected int convertLittleToBigEndian(byte[] b) {

		int value = 0;
		for (int i = b.length - 1; i >= 0; i--) {
			value = (value << 8) + (b[i] & 0xFF);
		}
		return value;
	}

	// Metode de tip setter
	protected void setImageType(byte[] imageType) {
		this.imageType = imageType;
	}

	protected void setWidthBytes(byte[] widthBytes) {
		this.widthBytes = widthBytes;
	}

	protected void setHeightBytes(byte[] heightBytes) {
		this.heightBytes = heightBytes;
	}

	protected void setNoOfBitsPerPixel(byte[] noOfBitsPerPixel) {
		this.noOfBitsPerPixel = noOfBitsPerPixel;
	}

}
