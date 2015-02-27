package com.monolc.fell;

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

import com.monolc.fell.graphics.Window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Program {
	public static void main(String[] args) {
		System.out.println("LWJGL Version " + Sys.getVersion() + " is working.");
		GLFWErrorCallback errorCallback = Callbacks.errorCallbackPrint(System.err);
		glfwSetErrorCallback(errorCallback);
		if (glfwInit() != GL11.GL_TRUE) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		Window w = new Window(800, 600, "Trolling around... The Christmas tree.");
		while (w.shouldClose()){
			w.update();
		}
		w.destroy();
		glfwTerminate();
		errorCallback.release();
	}
}
