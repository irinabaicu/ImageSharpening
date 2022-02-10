package Package2;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class Consumer extends Thread {
	private Buffer buffer;
	private String imagePath; // image destination
	
	public Consumer(Buffer buffer, String path){
		this.buffer = buffer;
		this.imagePath = path;
	}
	
	public void run(){
		int[] value;
		int ct = 0;
		while(ct < 4){
			int[] array = new int[2];
			value = buffer.get(array);
			System.out.println(array[0]);
			System.out.println(array[1]);
			
			System.out.println("----------------------------------------------------------------");
			for(int i = array[0]; i < array[0] + array[1]; i++){
				System.out.print("Consumer a pus:\t");	
				System.out.println(String.format("%x", value[i]));
			}
			System.out.println("----------------------------------------------------------------");
			ct++;
		}
		
		// asigura-te ca la producer trimiti inclusiv bytes de header
		
		//InputStream ifs = new ByteArrayInputStream(buffer.pixels);
		// RGBImage image = new RGBImage();
		// image.readImageInfo();
		
		// process image
		// sharpener = new ImageSharpener(imageBytes);  //sau buffer bytes
		// byte[] sharpenedImage = sharpener.sharpen();
		
		
		// write sharpened image to destination path
		// file = new File (imagePath) ; // creezi fisier destinatie
		// file.write (sharpenedImage) ; // scrii bytes ale imaginii sharpened in fisier
	}
}
