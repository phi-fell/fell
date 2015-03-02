package com.monolc.fell.world;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.monolc.fell.graphics.VAO;
import com.monolc.fell.graphics.VBO;
import com.monolc.fell.resources.Texture;

public class Floor {
	//tile, entity, items, structure
	//bool explored, bool passable, effect(ground on fire), are in tile class
	Texture texture;
	VAO vao;
	VBO vbo;
	int width;
	int height;
	public Floor(Texture tex, int w, int h) {
		texture = tex;
		width = w;
		height = h;
		vao = new VAO();
		vao.bind();
		FloatBuffer vertices = BufferUtils.createFloatBuffer(6 * 7);
		vertices.put(0).put(0).put(1f).put(1f).put(1f).put(0f).put(0f);
		vertices.put(width).put(0).put(1f).put(1f).put(1f).put(1f).put(0f);
		vertices.put(width).put(height).put(1f).put(1f).put(1f).put(1f).put(1f);
		vertices.put(0).put(0).put(1f).put(1f).put(1f).put(0f).put(0f);
		vertices.put(width).put(height).put(1f).put(1f).put(1f).put(1f).put(1f);
		vertices.put(0).put(height).put(1f).put(1f).put(1f).put(0f).put(1f);
		vbo = new VBO(vertices);
		vbo.bind();
	}
	public void draw() {
		vao.bind();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
	}
}
