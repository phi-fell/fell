package com.monolc.fell;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.lwjgl.Sys;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import com.monolc.fell.graphics.*;
import com.monolc.fell.resources.ResourceHandler;
import com.monolc.fell.version.*;
import com.monolc.fell.window.*;
import com.monolc.fell.world.Entity;
import com.monolc.fell.world.Floor;

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
		Window w = new Window(800, 600, "Fell " + v.getStage() + " V" + v.getVersion() + ", Build#" + v.getBuild() + " OpenGL V" + GLMajor + "." + GLMinor, GLMajor, GLMinor);
		res.getShader("default").bind();
		res.getShader("default").setUniformi("width", 800);
		res.getShader("default").setUniformi("height", 600);
		Floor floor = new Floor(res.getTexture("tiles"), 10, 10);
		Entity plr = new Entity(new Sprite(res.getTexture("player"), 32, 32));
		while (w.shouldClose()) {
			w.update();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			floor.draw(res.getShader("default"));
			plr.draw(res.getShader("default"));
			while(w.eventsToQuery()){
				Event e = w.queryEvent();
				if (e.getType() == Event.KEY_PRESS){
					if (e.getKey() == GLFW.GLFW_KEY_W){
						plr.y -= 1;
					}
					if (e.getKey() == GLFW.GLFW_KEY_A){
						plr.x -= 1;
					}
					if (e.getKey() == GLFW.GLFW_KEY_S){
						plr.y += 1;
					}
					if (e.getKey() == GLFW.GLFW_KEY_D){
						plr.x += 1;
					}
				}
			}
		}
		w.destroy();
		glfwTerminate();
		errorCallback.release();
	}
}
