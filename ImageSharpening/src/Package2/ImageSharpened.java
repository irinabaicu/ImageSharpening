package Package2;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageSharpened extends SharpenedImage{
	Image image = null;
	public ImageSharpened(){
		super();
	}
	
	private Image getImageFromArray() {
        BufferedImage image = new BufferedImage(super.width, super.height, BufferedImage.TYPE_INT_);
        WritableRaster raster = (WritableRaster) image.getData();
        raster.setPixels(0,0,super.width,super.height,super.pixels);
        return image;
    }
	
	public void writeImage(){
		this.image = getImageFromArray();
		
		File f = null;
		
		try{
			f = new File("D:\\Java\\ImageSharpening\\Output.bmp");  //output file path
		    ImageIO.write((RenderedImage) image, "bmp", f);
		    System.out.println("Writing complete.");
		}catch(IOException e){
		    System.out.println("Error: "+e);
		}
	}
}
