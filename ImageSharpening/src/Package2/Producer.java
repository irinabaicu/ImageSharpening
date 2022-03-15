package Package2;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Producer extends Thread {
	private Buffer buffer;
	private RawImage image;
	private String imagePath;

	public Producer(Buffer buffer, String path) {
		this.buffer = buffer;
		this.image = new RawImage();
		this.imagePath = path;
	}

	public void run() {
		InputStream input = null;

		try {
			// deschid fisierul dat
			input = new BufferedInputStream(new FileInputStream(imagePath));
			// apelez metoda readImageInfo pentru a prelua informatiile necesare
			// din fisier
			image.readImageInfo(input);
			// incarca in buffer heder-ul imaginii
			buffer.putImageHeader(image.getHeader());
			// portion reprezinta ce dimeniune are o parte din 4 din imagine in
			// functie de inaltimea acestea
			int portion = image.getHeight() / 4;

			int ct = 0; // contor ce ajuta la citirea in producer
			// variabila ce pastraza restul de linii (daca imaginea nu se poate
			// imparti in 4 parti egale
			int left = image.getHeight() % 4;
			// ajuta la distribuirea aproximativ uniforma a informatiei
			long startTimer0 = System.currentTimeMillis();
			while (ct != 4) {
				int start;
				long startTimerLoop = System.currentTimeMillis();
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				// daca exista linii in plus le sitribui in primele parti
				if (left > 0) {
					// se apeleaza metoda pentru a prelua 1/4 din pixelii din
					// imaginea sursa
					start = image.readImage(input, portion + 1);
					// se afiseaza pixelii stocati
					for (int i = start * image.getWidth(); i < start * image.getWidth() + portion + 1; i++) {
						System.out.print("Producatorul a pus:\t");
						System.out.println(String.format("0x%08X", image.getPixel(i)));
					}
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

					long elapsedTimeLoop = System.currentTimeMillis() - startTimerLoop;
					System.out.println("=======================Extragerea datelor de la iteratia " + (ct + 1)
							+ "a avut loc in " + elapsedTimeLoop + "ms==========================");

					// se stocheaza pixelii in buffer
					buffer.put(image.getPixels(), start * image.getWidth(), portion + 1);

				}
				// daca nu mai exista linii in plus continui stocarea
				// urmatoarelor parti
				else {
					// se apeleaza metoda pentru a prelua 1/4 din pixelii din
					// imaginea sursa
					start = image.readImage(input, portion);
					// se afiseaza pixelii stocati
					for (int i = start * image.getWidth(); i < start * image.getWidth() + portion; i++) {
						System.out.print("Producatorul a pus:\t");
						System.out.println(String.format("0x%08X", image.getPixel(i)));
					}
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

					long elapsedTimeLoop = System.currentTimeMillis() - startTimerLoop;
					System.out.println("=======================Extragerea datelor de la iteratia " + (ct + 1)
							+ "a avut loc in " + elapsedTimeLoop + "ms==========================");

					// se stocheaza pixelii in buffer
					buffer.put(image.getPixels(), start * image.getWidth(), portion);

				}
				left--;

				try {
					sleep(1000); // se adauga delay de 1 secunda
				} catch (InterruptedException e) {
				}
				ct++; // se incrementeaza contorul

			}
			long elapsedTime0 = System.currentTimeMillis() - startTimer0;
			System.out.println("=======================Munca depusa de Producer a avut loc in " + elapsedTime0
					+ "ms============================");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close(); // inchid fisier sursa
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
