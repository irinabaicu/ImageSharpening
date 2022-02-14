package Package2;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.io.*;
public class Consumer extends Thread {
	private Buffer buffer;
	private String imagePath; // image destination
	
	public Consumer(Buffer buffer, String path){
		this.buffer = buffer;
		this.imagePath = path;
	}
	
	public void run(){
		int[] value = {};
		int ct = 0;
		byte[] imageHeader = buffer.getImageHeader();
		
		
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
		
		SharpenedImage img = new SharpenedImage(imageHeader, value);
		img.sharpImage();
		
		
		//Path path = Paths.get(imagePath);
		try (FileOutputStream fos = new FileOutputStream(imagePath)) {
			   fos.write(imageHeader);
			   fos.write(img.getPixelsByte());
			   //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//        try {
//            Files.write(path,imageHeader);    // Java 7+ only
//            Files.write(path, img.getPixelsByte());
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
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
