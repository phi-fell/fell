package com.monolc.fell.resources;

import java.util.Scanner;

public class ResourceHandler {
	public ResourceHandler() {
	}
	public String load(String name) {
		String ret = "";
		if (this.getClass().getResourceAsStream(name) == null) {
			System.out.println(name + " is NULL!");
		}
		Scanner file = new Scanner(this.getClass().getResourceAsStream(name));
		while (file.hasNextLine()) {
			ret += file.nextLine() + "\n";
		}
		file.close();
		return ret;
	}
	public Shader loadShader(String name) {
		return new Shader(load("res/shader/" + name + "_vertex.glsl"), load("res/shader/" + name + "_fragment.glsl"));
	}
}
