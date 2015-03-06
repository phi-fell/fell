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
			if (rx % 2 == 1) {
				x++;
			}
			if (ry % 2 == 1) {
				y++;
			}
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
				outer: if (null3x3(i, j)) {
					int x = i;
					int y = j;
					tiles[x][y] = new Tile((i * height) + j + 1000);
					ArrayList<Location> locStack = new ArrayList<Location>();
					locStack.add(new Location(this, x, y));
					while (locStack.size() > 0) {
						int dir = branchPath(locStack.get(locStack.size() - 1), rand);
						switch (dir) {
							case -1:
								locStack.remove(locStack.size() - 1);
								if (locStack.size() <= 0) {
									break outer;
								}
								Location l = locStack.get(locStack.size() - 1);
								x = l.getX();
								y = l.getY();
								break;
							case 0:
								y++;
								tiles[x][y] = new Tile((i * height) + j + 1000);
								locStack.add(new Location(this, x, y));
								break;
							case 1:
								x++;
								tiles[x][y] = new Tile((i * height) + j + 1000);
								locStack.add(new Location(this, x, y));
								break;
							case 2:
								y--;
								tiles[x][y] = new Tile((i * height) + j + 1000);
								locStack.add(new Location(this, x, y));
								break;
							case 3:
								x--;
								tiles[x][y] = new Tile((i * height) + j + 1000);
								locStack.add(new Location(this, x, y));
								break;
						}
					}
				}
			}
		}
	}
	private int branchPath(Location l, Random rand) {
		boolean[] dirValid = { true, true, true, true };
		while (dirValid[0] || dirValid[1] || dirValid[2] || dirValid[3]) {
			int dir;
			do {
				dir = rand.nextInt(4);
			} while (!dirValid[dir]);
			if (dir == 0) {
				if (nullAbove(l.getX(), l.getY()) && l.getY() < height - 1) {
					return dir;
				} else {
					dirValid[dir] = false;
				}
			} else if (dir == 1) {
				if (nullRight(l.getX(), l.getY()) && l.getX() < width - 1) {
					return dir;
				} else {
					dirValid[dir] = false;
				}
			} else if (dir == 2) {
				if (nullBelow(l.getX(), l.getY()) && l.getY() > 0) {
					return dir;
				} else {
					dirValid[dir] = false;
				}
			} else if (dir == 3) {
				if (nullLeft(l.getX(), l.getY()) && l.getX() > 0) {
					return dir;
				} else {
					dirValid[dir] = false;
				}
			}
		}
		return -1;
	}
	private boolean null3x3(int x, int y) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i + x >= 0 && j + y >= 0 && i + x < width && j + y < height && tiles[i + x][j + y] != null) {
					return false;
				}
			}
		}
		return true;
	}
	private boolean nullAbove(int x, int y) {
		for (int i = -1; i <= 1; i++) {
			for (int j = 1; j <= 2; j++) {
				if (i + x >= 0 && j + y >= 0 && i + x < width && j + y < height && tiles[i + x][j + y] != null) {
					return false;
				}
			}
		}
		return true;
	}
	private boolean nullBelow(int x, int y) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -2; j <= -1; j++) {
				if (i + x >= 0 && j + y >= 0 && i + x < width && j + y < height && tiles[i + x][j + y] != null) {
					return false;
				}
			}
		}
		return true;
	}
	private boolean nullLeft(int x, int y) {
		for (int i = -2; i <= -1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i + x >= 0 && j + y >= 0 && i + x < width && j + y < height && tiles[i + x][j + y] != null) {
					return false;
				}
			}
		}
		return true;
	}
	private boolean nullRight(int x, int y) {
		for (int i = 1; i <= 2; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i + x >= 0 && j + y >= 0 && i + x < width && j + y < height && tiles[i + x][j + y] != null) {
					return false;
				}
			}
		}
		return true;
	}
	private boolean nullSurrounded(int x, int y) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if ((i != 0 || j != 0) && i + x >= 0 && j + y >= 0 && i + x < width && j + y < height && tiles[i + x][j + y] != null) {
					return false;
				}
			}
		}
		return true;
	}
	private boolean hasNullAdjacent(int x, int y) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if ((i == 0 || j == 0) && (i != 0 || j != 0) && i + x >= 0 && j + y >= 0 && i + x < width && j + y < height && tiles[i + x][j + y] != null) {
					return true;
				}
			}
		}
		return false;
	}
}
