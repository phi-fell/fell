package com.monolc.fell.world;

import com.monolc.fell.resources.Shader;
import com.monolc.fell.resources.Sprite;

public class Entity {
	Sprite sprite;
	Location location;
	public Entity(Sprite s, Location loc) {
		location = loc;
		sprite = s;
	}
	public void draw(Shader s) {
		s.setUniformf("z", 0.9f);
		s.setUniformf("x", location.getX() * Tile.TILE_SIZE);
		s.setUniformf("y", location.getY() * Tile.TILE_SIZE);
		sprite.draw();
	}
	public boolean moveUp(int amount){
		location.modY(amount);
		return true;
	}
	public boolean moveDown(int amount){
		location.modY(-amount);
		return true;
	}
	public boolean moveLeft(int amount){
		location.modX(-amount);
		return true;
	}
	public boolean moveRight(int amount){
		location.modX(amount);
		return true;
	}
	public boolean moveTo(Location newLoc){
		return false;
	}
}
