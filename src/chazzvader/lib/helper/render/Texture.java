package chazzvader.lib.helper.render;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import chazzvader.lib.helper.other.BufferUtils;

public class Texture {
	
	private int width, height;
	private int id;
	
	public Texture(String path) {
		id = load(path);
	}

	private int load(String path) {
		int[] pixels = null;
		int totalPixels = 0;
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			width = image.getWidth();
			height = image.getHeight();
			totalPixels = width*height;
			pixels = new int[totalPixels];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		int[] data = new int[totalPixels];
		for(int i = 0;i < totalPixels;i ++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		
		int result = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, result);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return result;
	}
	
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

}
