package Package2;


public class Buffer {
    private int[] pixels;
    private byte[] imageHeader;
    private int start;
    private int amount;
    private boolean headerAvailable = false;
    private boolean available = false;

    public synchronized int[] get(int[] array) {
        while (!available) {
            try {
                wait();
                // Asteapta producatorul sa puna o valoare
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        array[0] = this.start;
        array[1] = this.amount;
        available = false;
        notifyAll();
		return pixels;
    }

    public synchronized void put(int[] pixels, int start, int amount) {
        while (available) {
            try {
                wait();
                // Asteapta consumatorul sa preia valoarea
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.pixels = pixels;
        this.start = start;
        this.amount = amount;
        available = true;
        notifyAll();
    }
    
    public synchronized byte[] getImageHeader() {
        while (!headerAvailable) {
            try {
                wait();
                // Asteapta producatorul sa puna o valoare
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         
        headerAvailable = false;
        notifyAll();
        return imageHeader;
    }
    
    public synchronized void putImageHeader(byte[] imageHeader){
    	while (headerAvailable) {
            try {
                wait();
                // Asteapta consumatorul sa preia valoarea
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.imageHeader = imageHeader;
        headerAvailable = true;
        notifyAll();
    }
}
