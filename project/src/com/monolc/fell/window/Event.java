package com.monolc.fell.window;

import org.lwjgl.glfw.GLFW;

public class Event {
	public static final int KEY_PRESS = 0, KEY_RELEASE = 1, MOUSE_CLICK = 2, MOUSE_RELEASE = 3;
	int type;
	int key;
	public Event(int k, int action) {
		if (action == GLFW.GLFW_PRESS) {
			type = KEY_PRESS;
		} else if (action == GLFW.GLFW_RELEASE) {
			type = KEY_RELEASE;
		}
		key = k;
	}
	public int getType(){
		return type;
	}
	public int getKey(){
		return key;
	}
}
