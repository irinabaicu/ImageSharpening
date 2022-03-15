package Package2;

public class Consumer extends Thread {
	private Buffer buffer;
	private String imagePath;

	public Consumer(Buffer buffer, String path) {
		this.buffer = buffer;
		this.imagePath = path;
	}

	public void run() {
		long startTimer0 = System.currentTimeMillis();
		int[] value = {};
		int ct = 0;
		// se extrage info-headeru; imaginii atunci cand acesta este pus la
		// dispozitie de catre producer
		byte[] imageHeader = buffer.getImageHeader();

		// La fiecare iteratie se citesc 1/4 date puse la dispozitie de catre
		// producer
		while (ct < 4) {
			long startTimerLoop = System.currentTimeMillis();
			int[] array = new int[2];
			// pentru a stii de unde sa porneasca citirea si cat sa citeasca,
			// producer
			// trimite prin intermediul obiectului Buffer indexul de pornire si
			// numarul de
			// pixeli cititi.
			value = buffer.get(array); // se extrag pixelii cand acestia sunt
										// pusi in buffer de catre producer
			//System.out.println(array[0]);
			//System.out.println(array[1]);

			System.out.println("-----------------------------------------------------------------------------");
			
			for (int i = array[0]; i < array[0] + array[1]; i++) {
				System.out.print("Consumatorul a citit:\t");
				System.out.println(String.format("0x%08X", value[i]));
			}

			System.out.println("------------------------------------------------------------------------------");
			long elapsedTimeLoop = System.currentTimeMillis() - startTimerLoop;
			System.out.println("=======================Citirea datelor de la iteratia " + (ct + 1) +
					"a avut loc in " + elapsedTimeLoop + "ms==========================");
			ct++;
		}
		
		// se creeaza un obiect de tip SharpenedImage pentru procesarea datelor primite
		SharpenedImage img = new SharpenedImage(imageHeader, value);
		// Se aplica filtrul de tip sharpen peste imaginea citita
		long sharpenTimer = System.currentTimeMillis();
		img.sharpImage();
		long elapsedSharpenTime = System.currentTimeMillis() - sharpenTimer;
		System.out.println("=======================Prelucrarea datelor a avut loc in " 
		+ elapsedSharpenTime + "ms==========================");
		int[] processedImage = img.getSharpenedImage();
		
		long writeTimer = System.currentTimeMillis();
		//Imaginea procesata se transpune intr-un vector de octeti si se scrie in fisierul dorit
		ImageWriter writer = new ImageWriter(img.getHeader(), processedImage);
		writer.createBMPImage(imagePath, writer.getPixelsByte());
		long elapsedWriteTime = System.currentTimeMillis() - writeTimer;
		System.out.println("=======================Scrierea datelor a avut loc in " 
		+ elapsedWriteTime + "ms==========================");
		
		long elapsedTime0 = System.currentTimeMillis() - startTimer0;
		System.out.println("=======================Munca depusa de Consumer a avut loc in " 
		+ elapsedTime0 + "ms==========================");
	}

}
