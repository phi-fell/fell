package com.monolc.fell.graphics;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.monolc.fell.resources.Shader;
import com.monolc.fell.resources.Texture;

public class Sprite {
	Texture texture;
	VAO vao;
	VBO vbo;
	int width;
	int height;
	public Sprite(Texture tex, int w, int h) {
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
	public void draw(Shader s) {
		s.setUniformf("z", 0.9f);
		texture.bind();
		vao.bind();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
	}
}
