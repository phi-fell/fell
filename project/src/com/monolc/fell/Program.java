package com.monolc.fell;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.lwjgl.Sys;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import com.monolc.fell.program.Client;
import com.monolc.fell.program.Server;
import com.monolc.fell.resources.ResourceHandler;
import com.monolc.fell.version.*;
import com.monolc.fell.window.*;
import com.monolc.fell.world.Floor;
import com.monolc.fell.world.Player;

public class Program {
	public static void main(String args[]) {
		System.out.println("Running...");
		VersionData v = new VersionData();
		System.out.println("Fell " + v.getStage() + " V" + v.getVersion() + ", Build#" + v.getBuild());
		if (args.length < 1){
			System.out.println("NOT ENOUGH ARGUMENTS!");
		} else if (args[0].equals("client")){
			Client c = new Client(v);
			c.run();
		}else if (args[0].equals("server")){
			Server s = new Server(v);
			s.run();
		}
	}
}
