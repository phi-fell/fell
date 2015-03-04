package com.monolc.fell.world;

public class Tile {
	public static final int TILE_SIZE = 32;
	static final boolean[] passable = { true, false };
	boolean explored;
	int id;
	public Tile(int idNum) {
		id = idNum;
	}
	public int getID() {
		return id;
	}
	public boolean isPassable() {
		return passable[id];
	}
	public boolean isExplored() {
		return explored;
	}
}
