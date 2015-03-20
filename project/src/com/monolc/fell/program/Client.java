package com.monolc.fell.program;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.lwjgl.Sys;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import com.monolc.fell.net.ClientIO;
import com.monolc.fell.resources.FMLTag;
import com.monolc.fell.resources.ResourceHandler;
import com.monolc.fell.version.VersionData;
import com.monolc.fell.window.Event;
import com.monolc.fell.window.Window;
import com.monolc.fell.world.Floor;
import com.monolc.fell.world.Player;

public class Client {
	ResourceHandler resources;
	ClientIO server;
	GLFWErrorCallback errorCallback;
	Floor currentFloor;
	Window window;
	VersionData version;
	Player plr;
	int zoom;
	double startTime;
	int updates;
	public Client(VersionData v) {
		version = v;
	}
	public void initialize() {
		System.out.println("Initializing Client...");
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Server location: ");
		String line = null;
		try {
			line = console.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		server = new ClientIO(version, line);
		while (!server.hasMessage()) {
		}
		if (server.recieve().equals("acknowledged")) {
			System.out.println("Connected to server.");
		} else {
			System.out.println("Unknown Connection Error!");
		}
		System.out.println("LWJGL V" + Sys.getVersion());
		resources = new ResourceHandler();
		errorCallback = Callbacks.errorCallbackPrint(System.err);
		glfwSetErrorCallback(errorCallback);
		if (glfwInit() != GL11.GL_TRUE) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		int GLMajor = 4;
		int GLMinor = 0;
		window = new Window(800, 600, "Fell " + version.getStage() + " V" + version.getVersion() + ", Build#" + version.getBuild() + " OpenGL V" + GLMajor + "." + GLMinor, GLMajor, GLMinor);
		// window.setPosition(10, 30);
		resources.getShader("default").bind();
		resources.getShader("default").setUniformi("width", 800);
		resources.getShader("default").setUniformi("height", 600);
		resources.getShader("default").setUniformf("zoom", 1.0f);
		while (!server.hasMessage()) {
		}
		currentFloor = new Floor(resources, resources.getTexture("tiles"), (new FMLTag(server.recieve())).getTag("floor"));
		plr = new Player(resources.getSprite("player"), currentFloor.getOpenLocation());
		zoom = 0;
		startTime = window.getSecondsSinceInitialization();
		updates = 0;
	}
	public void destroy() {
		window.destroy();
		glfwTerminate();
		errorCallback.release();
	}
	public void run() {
		initialize();
		while (!window.shouldClose()) {
			window.update();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			while (window.eventsToQuery()) {
				Event e = window.queryEvent();
				if (e.getType() == Event.KEY_PRESS || e.getType() == Event.KEY_REPEAT) {
					if (e.getKey() == GLFW.GLFW_KEY_ESCAPE) {
						window.destroy();
						//return;
					} else if (e.getKey() == GLFW.GLFW_KEY_W) {
						plr.moveUp();
					} else if (e.getKey() == GLFW.GLFW_KEY_A) {
						plr.moveLeft();
					} else if (e.getKey() == GLFW.GLFW_KEY_S) {
						plr.moveDown();
					} else if (e.getKey() == GLFW.GLFW_KEY_D) {
						plr.moveRight();
					}
					server.send("action:\n\tmove:up");
				} else if (e.getType() == Event.MOUSE_SCROLL) {
					zoom += e.getY();
					if (zoom > 7) {
						zoom = 7;
					}
					if (zoom < -2) {
						zoom = -2;
					}
					resources.getShader("default").setUniformf("zoom", ((int) (Math.pow(1.4, zoom) * 10)) / 10.0f);
				}
			}
			int n = (int) (window.getSecondsSinceInitialization() - startTime) - updates;
			if (n > 0) {
				updates++;
			}
			currentFloor.update(n > 0 ? 1 : 0);
			plr.moveCameraTo(resources.getShader("default"));
			currentFloor.draw(resources.getShader("default"));
		}
		destroy();
	}
}
