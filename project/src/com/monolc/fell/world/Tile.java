package com.monolc.fell.world;

public class Tile {
	public static final int TILE_SIZE = 32;
	boolean explored;
	boolean passable;
	int id;
	public Tile(int idNum) {
		id = idNum;
	}
	public int getID(){
		return id;
	}
}
