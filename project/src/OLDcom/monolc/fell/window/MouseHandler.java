package OLDcom.monolc.fell.window;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseHandler extends GLFWMouseButtonCallback {
	Window wind;
	public MouseHandler(Window w) {
		wind = w;
	}
	@Override
	public void invoke(long window, int button, int action, int mods) {
		if (wind.id == window) {
			wind.mouseClick(button, action, mods);
		} else {
			System.out.println("WRONG WINDOW HANDLER!");
		}
	}
}
