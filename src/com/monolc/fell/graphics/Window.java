package com.monolc.fell.graphics;

import org.lwjgl.glfw.GLFW;

import org.lwjgl.opengl.GLContext;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	long id;
	KeyHandler kh;

	public Window(int w, int h, String t) {
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		id = glfwCreateWindow(w, h, t, NULL, NULL);
		if (id == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		kh = new KeyHandler(this);
		glfwSetKeyCallback(id, kh);
		glfwMakeContextCurrent(id);
		GLContext.createFromCurrent();
	}

	public Window(int w, int h, String t, boolean visible, boolean resizable, boolean floating, boolean decorated) {
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, visible ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_FLOATING, floating ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_DECORATED, decorated ? GL_TRUE : GL_FALSE);
		id = glfwCreateWindow(w, h, t, NULL, NULL);
		if (id == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		kh = new KeyHandler(this);
		glfwSetKeyCallback(id, kh);
		glfwMakeContextCurrent(id);
		GLContext.createFromCurrent();
	}

	public void setPosition(int x, int y) {
		GLFW.glfwSetWindowPos(id, x, y);
	}

	public void keyPress(int key, int scancode, int action, int mods) {
		if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
			glfwSetWindowShouldClose(id, GL_TRUE);
		} else {
			if (action == GLFW_PRESS) {
				System.out.println("Key #" + key + " was pressed");
			}
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
