package com.monolc.fell.world;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.monolc.fell.graphics.VAO;
import com.monolc.fell.graphics.VBO;
import com.monolc.fell.resources.Shader;
import com.monolc.fell.resources.Texture;

public class Floor {
	Texture texture;
	VAO vao;
	VBO vbo;
	Tile[][] tiles;
	Entity[][] entities;
	int width;
	int height;
	public Floor(Texture tex, int w, int h) {
		vao = null;
		vbo = null;
		texture = tex;
		width = w;
		height = h;
		tiles = new Tile[width][height];
		generate();
		entities = new Entity[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				entities[i][j] = null;
			}
		}
		generateModel();
	}
	public Entity getEntity(int x, int y) {
		return entities[x][y];
	}
	public void setEntity(int x, int y, Entity e) {
		entities[x][y] = e;
	}
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	public void draw(Shader s) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (entities[i][j] != null) {
					entities[i][j].draw(s);
				}
			}
		}
		s.setUniformf("z", 0.0f);
		s.setUniformf("x", 0);
		s.setUniformf("y", 0);
		texture.bind();
		vao.bind();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, width * height * 6);
	}
	public Location getOpenLocation() {
		return new Location(this, 50, 50);
	}
	private void generateModel() {
		if (vao != null || vbo != null) {
			deleteModel();
		}
		vao = new VAO();
		vao.bind();
		int tilenum = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (tiles[i][j] != null) {
					tilenum++;
				}
			}
		}
		FloatBuffer vertices = BufferUtils.createFloatBuffer(tilenum * 6 * 7);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (tiles[i][j] != null) {
					addTile(tiles[i][j].getID(), i * Tile.TILE_SIZE, j * Tile.TILE_SIZE, vertices);
				}
			}
		}
		vbo = new VBO(vertices);
		vbo.bind();
	}
	private void addTile(int id, int x, int y, FloatBuffer verts) {
		verts.put(x).put(y).put(1f).put(1f).put(1f).put((id % 10) / 10.0f).put((((int) (id / 10)) + 1) / 10.0f);
		verts.put(x + Tile.TILE_SIZE).put(y).put(1f).put(1f).put(1f).put(((id % 10) + 1) / 10.0f).put((((int) (id / 10)) + 1) / 10.0f);
		verts.put(x + Tile.TILE_SIZE).put(y + Tile.TILE_SIZE).put(1f).put(1f).put(1f).put(((id % 10) + 1) / 10.0f).put(((int) (id / 10)) / 10.0f);
		verts.put(x).put(y).put(1f).put(1f).put(1f).put((id % 10) / 10.0f).put((((int) (id / 10)) + 1) / 10.0f);
		verts.put(x + Tile.TILE_SIZE).put(y + Tile.TILE_SIZE).put(1f).put(1f).put(1f).put(((id % 10) + 1) / 10.0f).put(((int) (id / 10)) / 10.0f);
		verts.put(x).put(y + Tile.TILE_SIZE).put(1f).put(1f).put(1f).put((id % 10) / 10.0f).put(((int) (id / 10)) / 10.0f);
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
	private void generate() {
		Random rand = new Random();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tiles[i][j] = null;
			}
		}
		for (int i = 0; i < 50; i++) {
			int x = (rand.nextInt((width / 2) - 1) * 2) + 1;
			int y = (rand.nextInt((height / 2) - 1) * 2) + 1;
			int rx = rand.nextInt(5) + 2;
			int ry = rand.nextInt(5) + 2;
			boolean valid = true;
			for (int j = x - rx; j <= x + rx; j++) {
				for (int k = y - ry; k <= y + ry; k++) {
					if (j < 0 || k < 0 || j >= width || k >= height || tiles[j][k] != null) {
						valid = false;
					}
				}
			}
			for (int j = x - rx - 1; j <= x + rx + 1; j++) {
				for (int k = y - ry - 1; k <= y + ry + 1; k++) {
					if (j >= 0 && k >= 0 && j < width && k < height && tiles[j][k] != null) {
						valid = false;
					}
				}
			}
			if (valid) {
				for (int j = x - rx; j <= x + rx; j++) {
					for (int k = y - ry; k <= y + ry; k++) {
						tiles[j][k] = new Tile(i);
					}
				}
			}
		}
		for (int i = 1; i < width; i += 2) {
			for (int j = 1; j < height; j += 2) {
				if (null3x3(i, j)) {
					int x = i;
					int y = j;
					tiles[i][j] = new Tile((i * height) + j + 1000);
				}
			}
		}
	}
	private boolean null3x3(int x, int y) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i >= 0 && j >= 0 && i < width && j < height && tiles[i][j] != null) {
					return false;
				}
			}
		}
		return true;
	}
}
