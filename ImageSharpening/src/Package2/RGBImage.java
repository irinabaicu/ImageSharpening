package Package2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class RGBImage extends ImageHeader {
	boolean isTrue;
	int[] pixels;
	int height;
	int width;
	int row;
	int col;
	
	public RGBImage(){
		super();
		
	}
	
	public void readImageInfo(InputStream input){
		
			
//		    input.read();
//		    input.read();
//		    input.read();
//		    input.read();
//		    input.read();
//		    input.read();
//		    input.read();
//		    input.read();
//		    
//		    input.read(super.offsetByte,0,4);
//		    		    
//		    input.read();
//		    input.read();
//		    input.read();
//		    input.read();
//		    
//		    input.read(super.width,0,4);
//		    input.read(super.height,0,4);
//		    	    
//		    input.read();
//		    input.read();
//		    
//		    input.read(super.noOfBitsPerPixel,0,2);
//		    if((convertLittleToBigEndian(super.noOfBitsPerPixel)) != 24){
//		    	System.out.println("The input file is not a 24bit BMP file.");
//		    	System.exit(0);
//		    }
//		    
//		    this.width = convertLittleToBigEndian(super.width);
//		    this.height = convertLittleToBigEndian(super.height);
			

		try{
			byte[] headerBytes = new byte[54];
			//input.read(headerBytes);
				
				super.imageType = Arrays.copyOfRange(headerBytes, 0, 2);
				isTrue = isBMP(super.imageType);
				if(isTrue == false){
					System.out.println("The input file is not a BMP file.");
					System.exit(0);
				}
				super.noOfBitsPerPixel = Arrays.copyOfRange(headerBytes, 28, 30);
				if((convertLittleToBigEndian(super.noOfBitsPerPixel)) != 24){
			    	System.out.println("The input file is not a 24bit BMP file.");
			    	System.exit(0);
			    }
			
			super.widthBytes = Arrays.copyOfRange(headerBytes, 18, 22);
			super.heightBytes = Arrays.copyOfRange(headerBytes, 22, 26);
			this.width = convertLittleToBigEndian(super.widthBytes);
			this.height = convertLittleToBigEndian(super.heightBytes);
			
		    this.row = this.height - 1;
		    this.col = 0;
		    
		    
		}catch (IOException e) {
	        e.printStackTrace();
	    }
						
	}
	
	

	
	@Override 
	protected int readImage(InputStream input, int dimension, int[] pixels) throws IOException{
		
		this.pixels = new int[this.width * this.height];
		int noOfPaddingBytes = 4 - (3*this.width) % 4;
	    for(int i = this.row, counter = 0; i >= 0; i--, counter ++){
	    	int index = i * this.width;
	    	if(counter == dimension && i != 0){
    			this.row = i;
    			return this.row + 1;
    		}
	    	for(int j = 0; j < this.width; j++){
	    		
	    		int red = input.read();
	    		int green = input.read();
	    		int blue = input.read();
	    		
	    		pixels[index++] = 0xff000000 + (red << 16) + (green << 8) + blue;
	    		this.pixels[index-1] = 0xff000000 + (red << 16) + (green << 8) + blue;

	    	}
	    	
	    	while(noOfPaddingBytes == 0){
	    		input.read();
	    		noOfPaddingBytes --;
	    	}
	    }
	    this.row = this.height - 1;
	    return 0;
	}
	
	public int getImageDimension(){
		return this.width * this.height;
	}
		
	public int getHeight() {
		return this.height;
	}
	public int getWidth() {
		return this.width;
	}
}


































































































