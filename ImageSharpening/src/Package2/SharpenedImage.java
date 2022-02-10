package Package2;

public class SharpenedImage extends RGBImage implements ImageFiltering{
	protected int[] pixels;
	private int[] sharpImage;
	
	public SharpenedImage(){
		
		
		
		
		this.pixels = super.pixels;
		this.sharpImage = new int[super.width * super.height];
	}
	
	@Override
	public void sharpImage(){
		for(int i = 0; i < super.height; i++){
			for(int j = 0; j < super.width; j++){
				
				//calculate the up-left corner
				if(i == 0 && j == 0){
					sharpImage[i*super.width + j] = 5 * pixels[i*super.width + j]
													- pixels[(i+1)*super.width + j]
													- pixels[i*super.width + j+1]
												    - 2*pixels[i*super.width + j];
				}
				
				//calculate the up-right corner
				if(i == 0 && j == super.width){
					sharpImage[i*super.width + j] = 5 * pixels[i*super.width + j]
													- pixels[i*super.width + j-1]
													- pixels[(i+1)*super.width + j]
													- 2*pixels[i*super.width + j];		
				}
				
				//calculate the down-right corner
				if(j == super.width && i == super.height){
					sharpImage[i*super.width + j] = 5 * pixels[i*super.width + j]
													- pixels[(i-1)*super.width + j]
													- pixels[i*super.width + j-1]
													- 2*pixels[i*super.width + j];
				}
				
				//calculate the down-left corner
				if(i == super.height && j == 0){
					sharpImage[i*super.width + j] = 5 * pixels[i*super.width + j]
													-  pixels[i*super.width + j+1]
													-  pixels[(i-1)*super.width + j]
													- 2*pixels[i*super.width + j];		
				}
				
				//calculate upper edge
				if(i == 0 && j > 0 && j < super.width){
					sharpImage[i*super.width + j] = 5 * pixels[i*super.width + j]
													- pixels[(i+1)*super.width + j]
													- pixels[i*super.width + j-1]
												    - pixels[i*super.width + j+1]
												    - pixels[i*super.width + j];
				}
				
				//calculate bottom edge
				if(i == super.height &&  j > 0 && j < super.width){
					sharpImage[i*super.width + j] = 5 * pixels[i*super.width + j]
													- pixels[i*super.width + j-1]
													- pixels[i*super.width + j+1]
													- pixels[(i-1)*super.width + j];
													
				}
				
				//calculate left edge
				if( j == 0 && i > 0 && i < super.height){
					sharpImage[i*super.width + j] = 5 * pixels[i*super.width + j]
													- pixels[(i+1)*super.width + j]
													- pixels[(i-1)*super.width + j]
													- pixels[i*super.width + j+1]
													- pixels[i*super.width + j];
				}
				
				//calculate right edge
				if( j == super.width && i > 0 && i < super.height){
					sharpImage[i*super.width + j] = 5 * pixels[i*super.width + j]
													- pixels[(i+1)*super.width + j]
													- pixels[(i-1)*super.width + j]
													- pixels[i*super.width + j-1]
													- pixels[i*super.width + j];
				}
				
				//calculate the middle pixels 
				if((i > 0) && (i < super.width) && (j > 0) && (j < super.height)){
					sharpImage[i*super.width + j] = 5 * pixels[i*super.width + j]
							                        - pixels[(i-1)*super.width + j]
							                        - pixels[(i+1)*super.width + j]
							                        - pixels[i*super.width + j-1]
							                        - pixels[i*super.width + j+1];
				}
				
				
				
				
			}
		}
	}
	
	
	
}
