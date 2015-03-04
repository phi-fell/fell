package com.monolc.fell;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import com.monolc.fell.graphics.*;
import com.monolc.fell.resources.ResourceHandler;
import com.monolc.fell.resources.Sprite;
import com.monolc.fell.version.*;
import com.monolc.fell.window.*;
import com.monolc.fell.world.Entity;
import com.monolc.fell.world.Floor;
import com.monolc.fell.world.Location;
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
		Window w = new Window(800, 600, "Fell " + v.getStage() + " V" + v.getVersion() + ", Build#" + v.getBuild() + " OpenGL V" + GLMajor + "." + GLMinor, GLMajor, GLMinor);
		res.getShader("default").bind();
		res.getShader("default").setUniformi("width", 800);
		res.getShader("default").setUniformi("height", 600);
		Floor floor = new Floor(res.getTexture("tiles"), 50, 94);
		Player plr = new Player(res.getSprite("player"), new Location(floor, 2, 2));
		Entity[] ents = new Entity[10];
		for (int i = 0; i < 10; i++) {
			ents[i] = new Entity(res.getSprite("goblin"), new Location(floor, 3 + (i % 3) * 2, 7 + (i % 4) * 2));
		}
		int mu = 0; // number of times goblins have moved.
		Random rand = new Random();
		while (!w.shouldClose()) {
			w.update();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			while (w.eventsToQuery()) {
				Event e = w.queryEvent();
				if (e.getType() == Event.KEY_PRESS) {
					if (e.getKey() == GLFW.GLFW_KEY_W) {
						plr.moveUp(1);
					} else if (e.getKey() == GLFW.GLFW_KEY_A) {
						plr.moveLeft(1);
					} else if (e.getKey() == GLFW.GLFW_KEY_S) {
						plr.moveDown(1);
					} else if (e.getKey() == GLFW.GLFW_KEY_D) {
						plr.moveRight(1);
					}
				}
			}
			if (w.getSecondsSinceInitialization() > mu) {
				for (int i = 0; i < 10; i++) {
					int randi = rand.nextInt(4);
					if (randi == 0) {
						ents[i].moveUp(1);
					} else if (randi == 1) {
						ents[i].moveDown(1);
					} else if (randi == 2) {
						ents[i].moveLeft(1);
					} else if (randi == 3) {
						ents[i].moveRight(1);
					}
				}
				mu++;
			}
			plr.moveCameraTo(res.getShader("default"));
			floor.draw(res.getShader("default"));
		}
		w.destroy();
		glfwTerminate();
		errorCallback.release();
	}
}
