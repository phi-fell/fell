package com.monolc.fell.world;

import com.monolc.fell.graphics.Sprite;
import com.monolc.fell.resources.Shader;

public class Entity {
	Sprite sprite;
	public int x;
	public int y;
	public Entity(Sprite s) {
		x = 0;
		y = 0;
		sprite = s;
	}
	public void draw(Shader s) {
		s.setUniformf("x", x * Tile.TILE_SIZE);
		s.setUniformf("y", y * Tile.TILE_SIZE);
		sprite.draw(s);
	}
}
