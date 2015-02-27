package com.monolc.fell.graphics;

import org.lwjgl.Sys;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.glfw.GLFWvidmode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	long id;
	KeyHandler kh;

	public Window(int w, int h, String t) {
		id = glfwCreateWindow(w, h, t, NULL, NULL);
		if (id == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		kh = new KeyHandler(this);
		glfwSetKeyCallback(id, kh);
		glfwMakeContextCurrent(id);
		GLContext.createFromCurrent();
	}

	public void keyPress(int key, int scancode, int action, int mods) {
		if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
			glfwSetWindowShouldClose(id, GL_TRUE);
		} else {
			System.out.println(key);
		}
	}

	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(id) != GL_TRUE;
	}

	public void update() {
		double time = glfwGetTime();
		glfwSwapBuffers(id);
		glfwPollEvents();
	}

	public void destroy() {
		glfwDestroyWindow(id);
		kh.release();
	}
}
