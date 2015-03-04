package com.monolc.fell.world;

import com.monolc.fell.resources.Shader;
import com.monolc.fell.resources.Sprite;

public class Entity {
	Sprite sprite;
	Location location;
	public Entity(Sprite s, Location loc) {
		location = loc;
		sprite = s;
		location.getFloor().setEntity(location.getX(), location.getY(), this);
	}
	public void draw(Shader s) {
		s.setUniformf("z", 0.9f);
		s.setUniformf("x", location.getX() * Tile.TILE_SIZE);
		s.setUniformf("y", location.getY() * Tile.TILE_SIZE);
		sprite.draw();
	}
	public Location getLocation() {
		return location;
	}
	public boolean moveUp(int amount) {
		moveTo((new Location(location)).modY(amount));
		return true;
	}
	public boolean moveDown(int amount) {
		moveTo((new Location(location)).modY(-amount));
		return true;
	}
	public boolean moveLeft(int amount) {
		moveTo((new Location(location)).modX(-amount));
		return true;
	}
	public boolean moveRight(int amount) {
		moveTo((new Location(location)).modX(amount));
		return true;
	}
	public boolean moveTo(Location newLoc) {
		if (newLoc.isPassable()) {
			location.clear();
			location = newLoc;
			location.getFloor().setEntity(location.getX(), location.getY(), this);
			return true;
		}
		return false;
	}
}
