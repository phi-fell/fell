package com.monolc.fell.resources;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class ResourceHandler {
	public ResourceHandler() {
	}
	public String load(String name) {
		String ret = "";
		if (this.getClass().getResourceAsStream(name) == null) {
			System.out.println(name + " is NULL!");
		}
		Scanner file = new Scanner(this.getClass().getResourceAsStream(name));
		while (file.hasNextLine()) {
			ret += file.nextLine() + "\n";
		}
		file.close();
		return ret;
	}
	public Shader loadShader(String name) {
		return new Shader(load("res/shader/" + name + "_vertex.glsl"), load("res/shader/" + name + "_fragment.glsl"));
	}
	public Texture loadTexture(String name) {
		InputStream in = this.getClass().getResourceAsStream("res/texture/" + name + ".png");
		BufferedImage image = null;
		try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int width = image.getWidth();
		int height = image.getHeight();
		int[] pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = pixels[y * width + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}
		buffer.flip();
		return new Texture(buffer, width, height);
	}
}
