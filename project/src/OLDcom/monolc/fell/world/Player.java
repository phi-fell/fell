package OLDcom.monolc.fell.world;

import OLDcom.monolc.fell.resources.Shader;
import OLDcom.monolc.fell.resources.Sprite;

public class Player extends Entity {
	public Player(Sprite s, Location loc) {
		super(s, loc, Entity.PLAYER);
	}
	public void moveCameraTo(Shader s) {
		s.setUniformf("camx", (posX + 0.5f) * Tile.TILE_SIZE);
		s.setUniformf("camy", (posY + 0.5f) * Tile.TILE_SIZE);
	}
}
