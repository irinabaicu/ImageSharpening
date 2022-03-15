package Package1;

import java.util.Scanner;

import Package2.Buffer;
import Package2.Consumer;
import Package2.Producer;

public class Main {

	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		//String source = args[0];
		//String dest = args[1];

		 //path imagine sursa
		System.out.println("Intodu cale fisier sursa.");
		String source = scanner.nextLine();
		 //path imagine destinatie
		System.out.println("Intodu cale fisier destinatie.");
		String dest = scanner.nextLine();

		Buffer b = new Buffer();
		Producer p1 = new Producer(b, source);
		Consumer c1 = new Consumer(b, dest);

		p1.start();
		c1.start();

	}
}
