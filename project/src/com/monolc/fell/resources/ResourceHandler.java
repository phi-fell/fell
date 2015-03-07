package com.monolc.fell.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class ResourceHandler {
	Map<String, Shader> shaders;
	Map<String, Texture> textures;
	Map<String, Sprite> sprites;
	Map<String, TileData> tiledatas;
	public ResourceHandler() {
		shaders = new HashMap<String, Shader>();
		textures = new HashMap<String, Texture>();
		sprites = new HashMap<String, Sprite>();
		tiledatas = new HashMap<String, TileData>();
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
	public String getFMLValue(String fmlFile, String key) {
		// FML stands for "fell markup language", even though it's not actually
		// a markup language, this was too good of an opportunity to miss.
		return fmlFile.substring(fmlFile.indexOf(key + ":") + key.length() + 1, fmlFile.indexOf("\n", fmlFile.indexOf(key + ":")));
	}
	public Shader getShader(String name) {
		if (shaders.get(name) == null) {
			shaders.put(name, loadShader(name));
		}
		return shaders.get(name);
	}
	public Texture getTexture(String name) {
		if (textures.get(name) == null) {
			textures.put(name, loadTexture(name));
		}
		return textures.get(name);
	}
	public Sprite getSprite(String name) {
		if (sprites.get(name) == null) {
			sprites.put(name, loadSprite(name));
		}
		return sprites.get(name);
	}
	public TileData getTileData(int id) {
		if (id >= tiledatas.size() || tiledatas.get(id) == null) {
			tiledatas.put(id + "", loadTileData(id));
		}
		return tiledatas.get(id + "");
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
	private Sprite loadSprite(String name) {
		String spriteFile = load("res/sprite/" + name + ".spt");
		return new Sprite(getTexture(getFMLValue(spriteFile, "texture")), Integer.parseInt(getFMLValue(spriteFile, "width")), Integer.parseInt(getFMLValue(spriteFile, "height")));
	}
	private TileData loadTileData(int id) {
		String tileFile = load("res/tile/" + id + ".til");
		return new TileData(Integer.parseInt(getFMLValue(tileFile, "noneID")), Integer.parseInt(getFMLValue(tileFile, "outerID")), Integer.parseInt(getFMLValue(tileFile, "verticalID")),
				Integer.parseInt(getFMLValue(tileFile, "horizontalID")), Integer.parseInt(getFMLValue(tileFile, "innerID")));
	}
}
