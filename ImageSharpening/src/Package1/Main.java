package Package1;

//import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


import javax.swing.JOptionPane;

import Package2.RGBImage;
import Package2.SharpenedImage;
import Package2.Buffer;
import Package2.Consumer;
import Package2.ImageSharpened;
import Package2.Producer;

public class Main {
	
	public static void main(String[] args){
	
		JOptionPane.showMessageDialog(null,
				   "Acest program aplica asupra unei imagini un filtru de sharpening\n\n" +
	               "Introduceti in consola calea catre imaginea asupra careia doriti sa aplicati transformarea:\n");
		
		// path imagine sursa - citit tasttatura
		String source = "";
		
		// path imagine destinatie - citit tastatura
		String dest = "";
		
		
		Buffer b = new Buffer();
		Producer p1 = new Producer(b, source);
		Consumer c1 = new Consumer(b, dest);
		
		p1.start();
		c1.start();
		
	}	
}
