package OLDcom.monolc.fell.window;

import org.lwjgl.glfw.GLFW;

public class Event {
	public static final int KEY_PRESS = 0, KEY_REPEAT = 1, KEY_RELEASE = 2, MOUSE_CLICK = 3, MOUSE_RELEASE = 4, MOUSE_SCROLL = 5;
	public static final int KEYBOARD = 0, MOUSE_BUTTON = 1, SCROLL_WHEEL = 2;
	int type;
	int key;
	double v1, v2;
	public Event(int k, int action, int source) {
		if (source == KEYBOARD) {
			if (action == GLFW.GLFW_PRESS) {
				type = KEY_PRESS;
			} else if (action == GLFW.GLFW_RELEASE) {
				type = KEY_RELEASE;
			} else if (action == GLFW.GLFW_REPEAT){
				type = KEY_REPEAT;
			}
		} else if (source == MOUSE_BUTTON) {
			if (action == GLFW.GLFW_PRESS) {
				type = MOUSE_CLICK;
			} else if (action == GLFW.GLFW_RELEASE) {
				type = MOUSE_RELEASE;
			}
		}
		key = k;
	}
	public Event(double sx, double sy) {
		v1 = sx;
		v2 = sy;
		type = MOUSE_SCROLL;
	}
	public int getType() {
		return type;
	}
	public int getKey() {
		return key;
	}
	public double getX() {
		return v1;
	}
	public double getY() {
		return v2;
	}
}
