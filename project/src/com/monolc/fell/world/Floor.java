package com.monolc.fell.world;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.monolc.fell.graphics.VAO;
import com.monolc.fell.graphics.VBO;
import com.monolc.fell.resources.Shader;
import com.monolc.fell.resources.Texture;

public class Floor {
	// tile, entity, items, structure
	// bool explored, bool passable, effect(ground on fire), are in tile class
	Texture texture;
	VAO vao;
	VBO vbo;
	Tile[][] tiles;
	Entity[][] entities;
	ArrayList<Entity> entityList;//note that entities and entityList include the same entities, but in different formats
	int width;
	int height;
	public Floor(Texture tex, int w, int h) {
		vao = null;
		vbo = null;
		texture = tex;
		width = w;
		height = h;
		tiles = new Tile[width][height];
		entities = new Entity[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tiles[i][j] = new Tile((i + (j*10)) % 4);
			}
		}
		generateModel();
	}
	public void generateModel() {
		if (vao != null || vbo != null) {
			deleteModel();
		}
		vao = new VAO();
		vao.bind();
		FloatBuffer vertices = BufferUtils.createFloatBuffer(width * height * 6 * 7);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				addTile(tiles[i][j].getID(), i * Tile.TILE_SIZE, j * Tile.TILE_SIZE, vertices);
			}
		}
		vbo = new VBO(vertices);
		vbo.bind();
	}
	private void addTile(int id, int x, int y, FloatBuffer verts) {
		verts.put(x).put(y).put(1f).put(1f).put(1f).put((id % 10) / 10.0f).put(((int) (id / 10)) / 10.0f);
		verts.put(x + Tile.TILE_SIZE).put(y).put(1f).put(1f).put(1f).put(((id % 10) + 1) / 10.0f).put(((int) (id / 10)) / 10.0f);
		verts.put(x + Tile.TILE_SIZE).put(y + Tile.TILE_SIZE).put(1f).put(1f).put(1f).put(((id % 10) + 1) / 10.0f).put((((int) (id / 10)) + 1) / 10.0f);
		verts.put(x).put(y).put(1f).put(1f).put(1f).put((id % 10) / 10.0f).put(((int) (id / 10)) / 10.0f);
		verts.put(x + Tile.TILE_SIZE).put(y + Tile.TILE_SIZE).put(1f).put(1f).put(1f).put(((id % 10) + 1) / 10.0f).put((((int) (id / 10)) + 1) / 10.0f);
		verts.put(x).put(y + Tile.TILE_SIZE).put(1f).put(1f).put(1f).put((id % 10) / 10.0f).put((((int) (id / 10)) + 1) / 10.0f);
	}
	private void deleteModel() {
		if (vao != null) {
			vao.bind();
		}
		if (vbo != null) {
			vbo.bind();
			vbo.delete();
		}
		if (vao != null) {
			vao.delete();
		}
		vbo = null;
		vao = null;
	}
	public void draw(Shader s) {
		s.setUniformf("z", 0.0f);
		s.setUniformf("x", 32);
		s.setUniformf("y", 32);
		texture.bind();
		vao.bind();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, width * height * 6);
	}
}
