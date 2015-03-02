package com.monolc.fell.graphics;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.*;

public class VBO {
	int id;
	public VBO(FloatBuffer vertices) {
		id = GL15.glGenBuffers();
		vertices.flip();
		bind();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
		int floatSize = 4;
		GL20.glEnableVertexAttribArray(0);
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 7 * floatSize, 0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 7 * floatSize, 2 * floatSize);
		GL20.glEnableVertexAttribArray(2);
		GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, 7 * floatSize, 5 * floatSize);
	}
	public void bind() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
	}
	public void delete(){
		GL15.glDeleteBuffers(id);
	}
}
