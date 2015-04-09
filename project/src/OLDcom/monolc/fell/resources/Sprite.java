package OLDcom.monolc.fell.resources;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import OLDcom.monolc.fell.graphics.VAO;
import OLDcom.monolc.fell.graphics.VBO;

public class Sprite {
	String name;
	Texture texture;
	VAO vao;
	VBO vbo;
	int width;
	int height;
	public Sprite(String id, Texture tex, int w, int h) {
		name = id;
		texture = tex;
		width = w;
		height = h;
		vao = new VAO();
		vao.bind();
		FloatBuffer vertices = BufferUtils.createFloatBuffer(6 * 7);
		vertices.put(0).put(0).put(1f).put(1f).put(1f).put(0f).put(1f);
		vertices.put(width).put(0).put(1f).put(1f).put(1f).put(1f).put(1f);
		vertices.put(width).put(height).put(1f).put(1f).put(1f).put(1f).put(0f);
		vertices.put(0).put(0).put(1f).put(1f).put(1f).put(0f).put(1f);
		vertices.put(width).put(height).put(1f).put(1f).put(1f).put(1f).put(0f);
		vertices.put(0).put(height).put(1f).put(1f).put(1f).put(0f).put(0f);
		vbo = new VBO(vertices);
		vbo.bind();
	}
	public Sprite(String id) {
		name = id;
	}
	public void draw() {
		texture.bind();
		vao.bind();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
	}
	public String getName() {
		return name;
	}
}
