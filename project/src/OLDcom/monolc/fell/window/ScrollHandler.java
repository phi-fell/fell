package OLDcom.monolc.fell.window;

import org.lwjgl.glfw.GLFWScrollCallback;

public class ScrollHandler extends GLFWScrollCallback {
	Window wind;
	public ScrollHandler(Window w) {
		wind = w;
	}
	@Override
	public void invoke(long window, double xoffset, double yoffset) {
		if (wind.id == window) {
			wind.mouseScroll(xoffset, yoffset);
		} else {
			System.out.println("WRONG WINDOW HANDLER!");
		}
	}
}
