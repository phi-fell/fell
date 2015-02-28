package com.monolc.fell;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.lwjgl.Sys;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import com.monolc.fell.graphics.Window;
import com.monolc.fell.version.VersionData;


public class Program {
	public static void main(String[] args) {
		VersionData v = new VersionData();
		System.out.println("LWJGL Version " + Sys.getVersion() + " is working.");
		GLFWErrorCallback errorCallback = Callbacks.errorCallbackPrint(System.err);
		glfwSetErrorCallback(errorCallback);
		if (glfwInit() != GL11.GL_TRUE) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		Window w2 = new Window(100, 10, "", true, false, true, false);
		w2.setPosition(10, 10);
		Window w = new Window(800, 600, "Fell  V" + v.getVersion() + ", Build#" + v.getBuild());
		while (w.shouldClose()) {
			w.update();
			w2.update();
		}
		w.destroy();
		glfwTerminate();
		errorCallback.release();
	}
}
