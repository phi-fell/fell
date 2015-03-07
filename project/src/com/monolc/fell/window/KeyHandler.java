package com.monolc.fell.window;

import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyHandler extends GLFWKeyCallback {
	Window wind;
	public KeyHandler(Window w) {
		wind = w;
	}
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if (wind.id == window) {
			wind.keyPress(key, scancode, action, mods);
		} else {
			System.out.println("WRONG WINDOW HANDLER!");
		}
	}
}
