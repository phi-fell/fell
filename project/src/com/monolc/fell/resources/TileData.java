package com.monolc.fell.resources;

import java.nio.FloatBuffer;

import com.monolc.fell.world.Tile;

public class TileData {
	int nID, oID, vID, hID, iID;
	public TileData(int none, int outer, int vert, int hor, int inner) {
		nID = none;
		oID = outer;
		vID = vert;
		hID = hor;
		iID = inner;
	}
	public void addToBuffer(int tID, int tx, int ty, FloatBuffer verts, Tile[][] tiles) {
		int id = getID(tID, tx, ty, tiles, 0);
		addQuarter(tx, ty, id, 0, 0, verts);
		id = getID(tID, tx, ty, tiles, 1);
		addQuarter(tx, ty, id, 1, 0, verts);
		id = getID(tID, tx, ty, tiles, 2);
		addQuarter(tx, ty, id, 1, 1, verts);
		id = getID(tID, tx, ty, tiles, 3);
		addQuarter(tx, ty, id, 0, 1, verts);
	}
	private void addQuarter(float ax, float ay, int id, int xmod, int ymod, FloatBuffer verts) {
		int x = (int) ((ax + xmod / 2.0) * Tile.TILE_SIZE);
		int y = (int) ((ay + (1 - ymod) / 2.0) * Tile.TILE_SIZE);
		float tx = (id % 10) / 10.0f;
		float ty = (id / 10) / 10.0f;
		tx += xmod / 20.0f;
		ty += ymod / 20.0f;
		float tmod = 1.0f / 20.0f;
		verts.put(x).put(y).put(1f).put(1f).put(1f).put(tx).put(ty + tmod);
		verts.put(x + Tile.TILE_SIZE / 2).put(y).put(1f).put(1f).put(1f).put(tx + tmod).put(ty + tmod);
		verts.put(x + Tile.TILE_SIZE / 2).put(y + Tile.TILE_SIZE / 2).put(1f).put(1f).put(1f).put(tx + tmod).put(ty);
		verts.put(x).put(y).put(1f).put(1f).put(1f).put(tx).put(ty + tmod);
		verts.put(x + Tile.TILE_SIZE / 2).put(y + Tile.TILE_SIZE / 2).put(1f).put(1f).put(1f).put(tx + tmod).put(ty);
		verts.put(x).put(y + Tile.TILE_SIZE / 2).put(1f).put(1f).put(1f).put(tx).put(ty);
	}
	private int getID(int id, int x, int y, Tile[][] tiles, int corner) {
		if (corner == 0) {
			if (isID(id, x, y + 1, tiles)) {
				if (isID(id, x - 1, y, tiles)) {
					if (isID(id, x - 1, y + 1, tiles)) {
						return nID;
					} else {
						return iID;
					}
				} else {
					return vID;
				}
			} else {
				if (isID(id, x - 1, y, tiles)) {
					return hID;
				} else {
					return oID;
				}
			}
		} else if (corner == 1) {
			if (isID(id, x, y + 1, tiles)) {
				if (isID(id, x + 1, y, tiles)) {
					if (isID(id, x + 1, y + 1, tiles)) {
						return nID;
					} else {
						return iID;
					}
				} else {
					return vID;
				}
			} else {
				if (isID(id, x + 1, y, tiles)) {
					return hID;
				} else {
					return oID;
				}
			}
		} else if (corner == 2) {
			if (isID(id, x, y - 1, tiles)) {
				if (isID(id, x + 1, y, tiles)) {
					if (isID(id, x + 1, y - 1, tiles)) {
						return nID;
					} else {
						return iID;
					}
				} else {
					return vID;
				}
			} else {
				if (isID(id, x + 1, y, tiles)) {
					return hID;
				} else {
					return oID;
				}
			}
		} else if (corner == 3) {
			if (isID(id, x, y - 1, tiles)) {
				if (isID(id, x - 1, y, tiles)) {
					if (isID(id, x - 1, y - 1, tiles)) {
						return nID;
					} else {
						return iID;
					}
				} else {
					return vID;
				}
			} else {
				if (isID(id, x - 1, y, tiles)) {
					return hID;
				} else {
					return oID;
				}
			}
		}
		return -1;
	}
	private boolean isID(int id, int x, int y, Tile[][] tiles) {
		return ((x >= 0 && y >= 0 && x < tiles.length && y < tiles[0].length && tiles[x][y] != null) ? tiles[x][y].getID() == id : true);
	}
}
