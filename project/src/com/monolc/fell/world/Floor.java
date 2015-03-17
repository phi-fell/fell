package com.monolc.fell.world;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.monolc.fell.graphics.VAO;
import com.monolc.fell.graphics.VBO;
import com.monolc.fell.resources.FMLTag;
import com.monolc.fell.resources.ResourceHandler;
import com.monolc.fell.resources.Shader;
import com.monolc.fell.resources.Texture;

public class Floor {
	ResourceHandler rh;
	Texture texture;
	VAO vao;
	VBO vbo;
	Tile[][] tiles;
	Entity[][] entities;
	int width;
	int height;
	int tilenum;
	public Floor(ResourceHandler res, Texture tex, FMLTag data) {
		tilenum = 0;
		rh = res;
		vao = null;
		vbo = null;
		texture = tex;
		width = data.getTag("width").getValueAsInt();
		height = data.getTag("height").getValueAsInt();
		tiles = new Tile[width][height];
		entities = new Entity[width][height];
		FMLTag tiles = data.getTag("tiles");
		for (int i = 0; i < width; i++) {
			// String
			// TODO
			for (int j = 0; j < height; j++) {
			}
		}
		generateModel();
	}
	public Floor(ResourceHandler res, Texture tex, int w, int h) {
		tilenum = 0;
		rh = res;
		vao = null;
		vbo = null;
		texture = tex;
		width = w;
		height = h;
		tiles = new Tile[width][height];
		Random rand = new Random();
		generate(rand);
		entities = new Entity[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (tiles[i][j] != null && tiles[i][j].isPassable() && rand.nextInt(100) == 3) {
					entities[i][j] = new Entity(rh.getSprite("goblin"), new Location(this, i, j), Entity.GOBLIN);
				} else {
					entities[i][j] = null;
				}
			}
		}
		generateModel();
	}
	public String toString() {
		return toString(0);
	}
	public String toString(int indent) {
		// TODO
		return null;
	}
	public Entity getEntity(int x, int y) {
		return entities[x][y];
	}
	public Entity getEntity(Location l) {
		return getEntity(l.getX(), l.getY());
	}
	public void setEntity(int x, int y, Entity e) {
		entities[x][y] = e;
	}
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	public void update(int dt) {
		Random r = new Random();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (entities[i][j] != null) {
					entities[i][j].update();
					if (entities[i][j].getType() == Entity.GOBLIN && dt > 0) {
						int d = r.nextInt(4);
						if (d == 0) {
							entities[i][j].moveUp();
						} else if (d == 1) {
							entities[i][j].moveRight();
						} else if (d == 2) {
							entities[i][j].moveDown();
						} else if (d == 3) {
							entities[i][j].moveLeft();
						}
					}
				}
			}
		}
	}
	public void draw(Shader s) {
		s.setUniformf("z", 0.0f);
		s.setUniformf("x", 0);
		s.setUniformf("y", 0);
		texture.bind();
		vao.bind();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, tilenum * 4 * 6);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (entities[i][j] != null) {
					entities[i][j].draw(s);
				}
			}
		}
	}
	public Location getOpenLocation() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (tiles[i][j] != null && tiles[i][j].isPassable() && entities[i][j] == null) {
					return new Location(this, i, j);
				}
			}
		}
		return null;
	}
	private void generateModel() {
		if (vao != null || vbo != null) {
			deleteModel();
		}
		vao = new VAO();
		vao.bind();
		tilenum = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (tiles[i][j] != null) {
					tilenum++;
				}
			}
		}
		FloatBuffer vertices = BufferUtils.createFloatBuffer(tilenum * 4 * 6 * 7);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (tiles[i][j] != null) {
					addTile(tiles[i][j].getID(), i, j, vertices);
				}
			}
		}
		vbo = new VBO(vertices);
		vbo.bind();
	}
	private void addTile(int id, int x, int y, FloatBuffer verts) {
		rh.getTileData(id).addToBuffer(id, x, y, verts, tiles);
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
	private void generate(Random rand) {
		int firstRoomID = -1;
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
					if (j < 1 || k < 1 || j >= width - 1 || k >= height - 1 || tiles[j][k] != null) {
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
				if (firstRoomID == -1) {
					firstRoomID = i;
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
								for (int a = 0; a < 2; a++) {
									locStack.remove(locStack.size() - 1);
									if (locStack.size() <= 0) {
										break outer;
									}
								}
								Location l = locStack.get(locStack.size() - 1);
								x = l.getX();
								y = l.getY();
								break;
							case 0:
								for (int a = 0; a < 2; a++) {
									y++;
									tiles[x][y] = new Tile((i * height) + j + 1000);
									locStack.add(new Location(this, x, y));
								}
								break;
							case 1:
								for (int a = 0; a < 2; a++) {
									x++;
									tiles[x][y] = new Tile((i * height) + j + 1000);
									locStack.add(new Location(this, x, y));
								}
								break;
							case 2:
								for (int a = 0; a < 2; a++) {
									y--;
									tiles[x][y] = new Tile((i * height) + j + 1000);
									locStack.add(new Location(this, x, y));
								}
								break;
							case 3:
								for (int a = 0; a < 2; a++) {
									x--;
									tiles[x][y] = new Tile((i * height) + j + 1000);
									locStack.add(new Location(this, x, y));
								}
								break;
						}
					}
				}
			}
		}
		connect(firstRoomID, rand);
		prune();
		boolean[][] walls = new boolean[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				walls[i][j] = false;
				if (tiles[i][j] != null) {
					tiles[i][j] = new Tile(0);
				} else if (nonNullSurrounded(i, j) > 0) {
					walls[i][j] = true;
				}
			}
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (walls[i][j]) {
					tiles[i][j] = new Tile(1);
				}
			}
		}
	}
	private boolean aboveNonNull(int x, int y) {
		return (y < height - 1 && tiles[x][y + 1] != null);
	}
	private boolean rightNonNull(int x, int y) {
		return (x < width - 1 && tiles[x + 1][y] != null);
	}
	private boolean belowNonNull(int x, int y) {
		return (y > 0 && tiles[x][y - 1] != null);
	}
	private boolean leftNonNull(int x, int y) {
		return (x > 0 && tiles[x - 1][y] != null);
	}
	private void prune() {
		boolean unpruned = true;
		while (unpruned) {
			unpruned = false;
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					if (tiles[i][j] != null && nonNullAdjacent(i, j) <= 1) {
						prune(i, j);
						unpruned = true;
					}
				}
			}
		}
	}
	private void prune(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height || tiles[x][y] == null) {
			return;
		}
		if (nonNullAdjacent(x, y) <= 1) {
			prune(x, y + 1);
			prune(x + 1, y);
			prune(x, y - 1);
			prune(x - 1, y);
			tiles[x][y] = null;
		}
	}
	private void connect(int id, Random rand) {
		while (!isOnlyID(id)) {
			int x = -1;
			int y = -1;
			int count = 0;
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					if (tiles[i][j] == null && hasIDandOther(i, j, id)) {
						count++;
					}
				}
			}
			if (count == 0) {
				break;
			}
			int num = rand.nextInt((count / 200) + 1) + 1;
			boolean[] shouldConnect = new boolean[count];
			for (int i = 0; i < count; i++) {
				shouldConnect[i] = false;
			}
			while (num > 0) {
				int p = rand.nextInt(shouldConnect.length);
				if (!shouldConnect[p]) {
					shouldConnect[p] = true;
					num--;
				}
			}
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					if (tiles[i][j] == null && hasIDandOther(i, j, id)) {
						count--;
						if (count >= 0 && shouldConnect[count]) {
							tiles[i][j] = new Tile(id);
							x = i;
							y = j;
						}
					}
				}
			}
			if (x != -1 && y != -1) {
				floodFill(x, y, tiles[x][y]);
			}
		}
	}
	private boolean isOnlyID(int id) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (tiles[i][j] != null && tiles[i][j].getID() != id) {
					return false;
				}
			}
		}
		return true;
	}
	private boolean hasIDandOther(int x, int y, int id) {
		boolean hasID = false;
		if (y + 1 < height && tiles[x][y + 1] != null && tiles[x][y + 1].getID() == id) {
			hasID = true;
		} else if (y > 0 && tiles[x][y - 1] != null && tiles[x][y - 1].getID() == id) {
			hasID = true;
		} else if (x + 1 < width && tiles[x + 1][y] != null && tiles[x + 1][y].getID() == id) {
			hasID = true;
		} else if (x > 0 && tiles[x - 1][y] != null && tiles[x - 1][y].getID() == id) {
			hasID = true;
		}
		boolean hasOther = false;
		if (y + 1 < height && tiles[x][y + 1] != null && tiles[x][y + 1].getID() != id) {
			hasOther = true;
		} else if (y > 0 && tiles[x][y - 1] != null && tiles[x][y - 1].getID() != id) {
			hasOther = true;
		} else if (x + 1 < width && tiles[x + 1][y] != null && tiles[x + 1][y].getID() != id) {
			hasOther = true;
		} else if (x > 0 && tiles[x - 1][y] != null && tiles[x - 1][y].getID() != id) {
			hasOther = true;
		}
		return hasID && hasOther && nonNullAdjacent(x, y) == 2;
	}
	private void floodFill(int x, int y, Tile t) {
		t.explore();
		if (y < height - 1 && tiles[x][y + 1] != null && !tiles[x][y + 1].isExplored()) {
			tiles[x][y + 1] = t;
			floodFill(x, y + 1, t);
		}
		if (x < width - 1 && tiles[x + 1][y] != null && !tiles[x + 1][y].isExplored()) {
			tiles[x + 1][y] = t;
			floodFill(x + 1, y, t);
		}
		if (y > 0 && tiles[x][y - 1] != null && !tiles[x][y - 1].isExplored()) {
			tiles[x][y - 1] = t;
			floodFill(x, y - 1, t);
		}
		if (x > 0 && tiles[x - 1][y] != null && !tiles[x - 1][y].isExplored()) {
			tiles[x - 1][y] = t;
			floodFill(x - 1, y, t);
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
				if (nullAbove(l.getX(), l.getY()) && l.getY() < height - 2) {
					return dir;
				} else {
					dirValid[dir] = false;
				}
			} else if (dir == 1) {
				if (nullRight(l.getX(), l.getY()) && l.getX() < width - 2) {
					return dir;
				} else {
					dirValid[dir] = false;
				}
			} else if (dir == 2) {
				if (nullBelow(l.getX(), l.getY()) && l.getY() > 1) {
					return dir;
				} else {
					dirValid[dir] = false;
				}
			} else if (dir == 3) {
				if (nullLeft(l.getX(), l.getY()) && l.getX() > 1) {
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
			for (int j = 1; j <= 3; j++) {
				if (i + x >= 0 && j + y >= 0 && i + x < width && j + y < height && tiles[i + x][j + y] != null) {
					return false;
				}
			}
		}
		return true;
	}
	private boolean nullBelow(int x, int y) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -3; j <= -1; j++) {
				if (i + x >= 0 && j + y >= 0 && i + x < width && j + y < height && tiles[i + x][j + y] != null) {
					return false;
				}
			}
		}
		return true;
	}
	private boolean nullLeft(int x, int y) {
		for (int i = -3; i <= -1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i + x >= 0 && j + y >= 0 && i + x < width && j + y < height && tiles[i + x][j + y] != null) {
					return false;
				}
			}
		}
		return true;
	}
	private boolean nullRight(int x, int y) {
		for (int i = 1; i <= 3; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i + x >= 0 && j + y >= 0 && i + x < width && j + y < height && tiles[i + x][j + y] != null) {
					return false;
				}
			}
		}
		return true;
	}
	private int nonNullSurrounded(int x, int y) {
		int ret = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if ((i != 0 || j != 0) && i + x >= 0 && j + y >= 0 && i + x < width && j + y < height && tiles[i + x][j + y] != null) {
					ret++;
				}
			}
		}
		return ret;
	}
	private int nonNullAdjacent(int x, int y) {
		int ret = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if ((i == 0 || j == 0) && (i != 0 || j != 0) && i + x >= 0 && j + y >= 0 && i + x < width && j + y < height && tiles[i + x][j + y] != null) {
					ret++;
				}
			}
		}
		return ret;
	}
}
