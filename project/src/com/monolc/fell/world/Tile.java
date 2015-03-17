package com.monolc.fell.world;

public class Tile {
	public static final int TILE_SIZE = 32;
	static final boolean[] passable = { true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false };
	boolean explored;
	int id;
	public Tile(int idNum) {
		id = idNum;
		explored = false;
	}
	public Tile(String data) {
		id = Integer.parseInt(data.substring(0, data.indexOf("|")));
		explored = data.substring(data.indexOf("|") + 1).equals("t");
	}
	public String toString() {
		return id + "|" + (explored ? "t" : "f");
	}
	public int getID() {
		return id;
	}
	public boolean isPassable() {
		return passable[id % 100];
	}
	public boolean isExplored() {
		return explored;
	}
	public void explore() {
		explored = true;
	}
}
