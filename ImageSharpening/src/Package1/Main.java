package Package1;

import java.util.Scanner;

import javax.swing.JOptionPane;
import Package2.Buffer;
import Package2.Consumer;
import Package2.Producer;

public class Main {

	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		JOptionPane.showMessageDialog(null, "Acest program aplica asupra unei imagini un filtru de sharpening\n\n"
				+ "Introduceti in consola calea catre imaginea asupra careia doriti sa aplicati transformarea:\n");

		// path imagine sursa - citit tasttatura
		System.out.println("Intodu cale fisier sursa.");
		// String source = scanner.nextLine(); // Read user input
		// path imagine destinatie - citit tastatura
		System.out.println("Intodu cale fisier destinatie.");
		// String dest = scanner.nextLine(); // Read user input

		Buffer b = new Buffer();
		Producer p1 = new Producer(b, "D:\\Java\\ImageSharpening\\book.bmp");
		Consumer c1 = new Consumer(b, "D:\\Java\\ImageSharpening\\book_after.bmp");

		p1.start();
		c1.start();

	}
}
