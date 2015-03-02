package com.monolc.fell.resources;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

public class Texture {
	int id;
	int width, height;
	public Texture(ByteBuffer buffer, int w, int h) {
		width = w;
		height = h;
		id = GL11.glGenTextures();
		bind();
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
	}
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
}
