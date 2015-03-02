package com.monolc.fell.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class ResourceHandler {
	Map<String, Shader> shaders;
	Map<String, Texture> textures;
	public ResourceHandler() {
		shaders = new HashMap<String,Shader>();
		textures = new HashMap<String,Texture>();
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
	public Shader getShader(String name){
		if(shaders.get(name) == null){
			shaders.put(name, loadShader(name));
		}
		return shaders.get(name);
	}
	public Texture getTexture(String name){
		if(textures.get(name) == null){
			textures.put(name, loadTexture(name));
		}
		return textures.get(name);
	}
	private Shader loadShader(String name) {
		return new Shader(load("res/shader/" + name + "_vertex.glsl"), load("res/shader/" + name + "_fragment.glsl"));
	}
	private Texture loadTexture(String name) {
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
