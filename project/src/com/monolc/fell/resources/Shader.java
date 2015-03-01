package com.monolc.fell.resources;

import org.lwjgl.opengl.*;

public class Shader {
	int id;
	int vert;
	int frag;
	public Shader(String vertFile, String fragFile) {
		vert = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vert, vertFile);
		GL20.glCompileShader(vert);
		int status = GL20.glGetShaderi(vert, GL20.GL_COMPILE_STATUS);
		if (status != GL11.GL_TRUE) {
			throw new RuntimeException(GL20.glGetShaderInfoLog(vert));
		}
		frag = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(frag, fragFile);
		GL20.glCompileShader(frag);
		status = GL20.glGetShaderi(frag, GL20.GL_COMPILE_STATUS);
		if (status != GL11.GL_TRUE) {
			throw new RuntimeException(GL20.glGetShaderInfoLog(frag));
		}
		id = GL20.glCreateProgram();
		GL20.glAttachShader(id, vert);
		GL20.glAttachShader(id, frag);
		GL30.glBindFragDataLocation(id, 0, "fragColor");
		GL20.glLinkProgram(id);
		status = GL20.glGetProgrami(id, GL20.GL_LINK_STATUS);
		if (status != GL11.GL_TRUE) {
		    throw new RuntimeException(GL20.glGetProgramInfoLog(id));
		}
	}
	public void bind(){
		GL20.glUseProgram(id);
	}
	public void setUniformi(String name, int value){
		bind();
		int pos = GL20.glGetUniformLocation(id, name);
	    GL20.glUniform1i(pos, value);
	}
	public void setUniformf(String name, float value){
		bind();
		int pos = GL20.glGetUniformLocation(id, name);
	    GL20.glUniform1f(pos, value);
	}
}
