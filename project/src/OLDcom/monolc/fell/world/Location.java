package OLDcom.monolc.fell.world;

import OLDcom.monolc.fell.resources.FMLTag;

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
	public Location(FMLTag data, Floor f) {
		floor = f;
		x = data.getTag("x").getValueAsInt();
		y = data.getTag("y").getValueAsInt();
	}
	public String toString() {
		return toString(0);
	}
	public String toString(int indent) {
		String ind = "";
		for (int i = 0; i < indent; i++) {
			ind += "\t";
		}
		String ret = ind + "loc:\n";
		ret += ind + "\tx: " + x + "\n";
		ret += ind + "\ty: " + y + "\n";
		return ret;
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
