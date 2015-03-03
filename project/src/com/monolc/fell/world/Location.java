package com.monolc.fell.world;

public class Location {
	private int x, y;
	Floor floor;
	public Location(Floor f, int x, int y) {
		floor = f;
		this.x = x;
		this.y = y;
	}
	public Location(Location l) {
		x = l.getX();
		y = l.getY();
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Location setX(int nx) {
		x = nx;
		return this;
	}
	public Location setY(int ny) {
		y = ny;
		return this;
	}
	public Location modX(int mx) {
		x += mx;
		return this;
	}
	public Location modY(int my) {
		y += my;
		return this;
	}
}
