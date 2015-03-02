package com.monolc.fell;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.lwjgl.Sys;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.monolc.fell.graphics.*;
import com.monolc.fell.resources.ResourceHandler;
import com.monolc.fell.resources.Shader;
import com.monolc.fell.resources.Texture;
import com.monolc.fell.version.*;
import com.monolc.fell.window.*;
import com.monolc.fell.world.Floor;

public class Program {
	public static void main(String[] args) {
		System.out.println("Running...");
		VersionData v = new VersionData();
		System.out.println("Fell " + v.getStage() + " V" + v.getVersion() + ", Build#" + v.getBuild());
		System.out.println("LWJGL Version " + Sys.getVersion() + " is working.");
		ResourceHandler res = new ResourceHandler();
		GLFWErrorCallback errorCallback = Callbacks.errorCallbackPrint(System.err);
		glfwSetErrorCallback(errorCallback);
		if (glfwInit() != GL11.GL_TRUE) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		int GLMajor = 4;
		int GLMinor = 1;
		Window w = new Window(800, 600, "Fell " + v.getStage() + " V" + v.getVersion() + ", Build#" + v.getBuild() + " OpenGL V" + GLMajor + "." + GLMinor, GLMajor, GLMinor);
		Shader s = res.loadShader("default");
		s.bind();
		s.setUniformi("width", 800);
		s.setUniformi("height", 600);
		s.setUniformf("z", 0.0f);
		s.setUniformf("x", 20);
		s.setUniformf("y", 20);
		Floor floor = new Floor(res.loadTexture("tiles"), 160, 160);
		while (w.shouldClose()) {
			w.update();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			floor.draw();
		}
		w.destroy();
		glfwTerminate();
		errorCallback.release();
	}
}
