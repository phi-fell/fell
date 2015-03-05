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
		floor = l.getFloor();
		x = l.getX();
		y = l.getY();
	}
	public void clear() {
		floor.setEntity(x, y, null);
	}
	public Floor getFloor() {
		return floor;
	}
	public Location setFloor(Floor f) {
		floor = f;
		return this;
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
	public boolean isPassable() {
		if (floor.getEntity(x, y) == null && floor.getTile(x, y).isPassable()) {
			return true;
		}
		return false;
	}
}
