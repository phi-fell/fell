package com.monolc.fell.world;

import com.monolc.fell.resources.Shader;
import com.monolc.fell.resources.Sprite;

public class Entity {
	Sprite sprite;
	Location location;
	boolean inMove;
	float posX, posY;
	public Entity(Sprite s, Location loc) {
		location = loc;
		sprite = s;
		location.getFloor().setEntity(location.getX(), location.getY(), this);
		posX = loc.getX();
		posY = loc.getY();
		inMove = false;
	}
	public void update() {
		float c = 0.01f;
		if (inMove) {
			if (Math.abs(posX - location.getX()) < c && Math.abs(posY - location.getY()) < c) {
				posX = location.getX();
				posY = location.getY();
				inMove = false;
			}
			if (posX < location.getX()) {
				posX += c;
			}
			if (posY < location.getY()) {
				posY += c;
			}
			if (posX > location.getX()) {
				posX -= c;
			}
			if (posY > location.getY()) {
				posY -= c;
			}
		}
	}
	public void draw(Shader s) {
		s.setUniformf("z", 0.9f);
		s.setUniformf("x", posX * Tile.TILE_SIZE);
		s.setUniformf("y", posY * Tile.TILE_SIZE);
		sprite.draw();
	}
	public Location getLocation() {
		return location;
	}
	public boolean moveUp(int amount) {
		return moveTo((new Location(location)).modY(amount));
	}
	public boolean moveDown(int amount) {
		return moveTo((new Location(location)).modY(-amount));
	}
	public boolean moveLeft(int amount) {
		return moveTo((new Location(location)).modX(-amount));
	}
	public boolean moveRight(int amount) {
		return moveTo((new Location(location)).modX(amount));
	}
	public boolean moveTo(Location newLoc) {
		if (inMove) {
			return false;
		}
		if (newLoc.isPassable()) {
			location.clear();
			location = newLoc;
			location.getFloor().setEntity(location.getX(), location.getY(), this);
			inMove = true;
			return true;
		}
		return false;
	}
}
