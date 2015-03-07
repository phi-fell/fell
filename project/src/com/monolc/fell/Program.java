package com.monolc.fell;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.lwjgl.Sys;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import com.monolc.fell.resources.ResourceHandler;
import com.monolc.fell.version.*;
import com.monolc.fell.window.*;
import com.monolc.fell.world.Floor;
import com.monolc.fell.world.Player;

public class Program {
	public static void main(String[] args) {
		System.out.println("Running...");
		VersionData v = new VersionData();
		System.out.println("Fell " + v.getStage() + " V" + v.getVersion() + ", Build#" + v.getBuild());
		System.out.println("LWJGL V" + Sys.getVersion());
		ResourceHandler res = new ResourceHandler();
		GLFWErrorCallback errorCallback = Callbacks.errorCallbackPrint(System.err);
		glfwSetErrorCallback(errorCallback);
		if (glfwInit() != GL11.GL_TRUE) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		int GLMajor = 4;
		int GLMinor = 1;
		Window w = new Window(1600, 900, "Fell " + v.getStage() + " V" + v.getVersion() + ", Build#" + v.getBuild() + " OpenGL V" + GLMajor + "." + GLMinor, GLMajor, GLMinor);
		w.setPosition(10, 30);
		res.getShader("default").bind();
		res.getShader("default").setUniformi("width", 1600);
		res.getShader("default").setUniformi("height", 900);
		res.getShader("default").setUniformf("zoom", 1.0f);
		Floor floor = new Floor(res, res.getTexture("tiles"), 121, 101);
		Player plr = new Player(res.getSprite("player"), floor.getOpenLocation());
		int zoom = 0;
		double startTime = w.getSecondsSinceInitialization();
		int updates = 0;
		while (!w.shouldClose()) {
			w.update();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			while (w.eventsToQuery()) {
				Event e = w.queryEvent();
				if (e.getType() == Event.KEY_PRESS || e.getType() == Event.KEY_REPEAT) {
					if (e.getKey() == GLFW.GLFW_KEY_W) {
						plr.moveUp();
					} else if (e.getKey() == GLFW.GLFW_KEY_A) {
						plr.moveLeft();
					} else if (e.getKey() == GLFW.GLFW_KEY_S) {
						plr.moveDown();
					} else if (e.getKey() == GLFW.GLFW_KEY_D) {
						plr.moveRight();
					}
				} else if (e.getType() == Event.MOUSE_SCROLL) {
					zoom += e.getY();
					if (zoom > 7) {
						zoom = 7;
					}
					if (zoom < -2) {
						zoom = -2;
					}
					res.getShader("default").setUniformf("zoom", ((int)(Math.pow(1.4, zoom) * 10)) / 10.0f);
				}
			}
			int n = (int) (w.getSecondsSinceInitialization() - startTime) - updates;
			if (n > 0) {
				updates++;
			}
			floor.update(n > 0 ? 1 : 0);
			plr.moveCameraTo(res.getShader("default"));
			floor.draw(res.getShader("default"));
		}
		w.destroy();
		glfwTerminate();
		errorCallback.release();
	}
}
