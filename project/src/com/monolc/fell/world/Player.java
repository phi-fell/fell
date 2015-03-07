package com.monolc.fell.world;

import com.monolc.fell.resources.Shader;
import com.monolc.fell.resources.Sprite;

public class Player extends Entity {
	public Player(Sprite s, Location loc) {
		super(s, loc);
	}
	public void moveCameraTo(Shader s) {
		s.setUniformf("camx", posX * Tile.TILE_SIZE);
		s.setUniformf("camy", posY * Tile.TILE_SIZE);
	}
}
