package OLDcom.monolc.fell.graphics;

import org.lwjgl.opengl.*;

public class VAO {
	int id;
	public VAO() {
		id = GL30.glGenVertexArrays();
		this.bind();
	}
	public void bind() {
		GL30.glBindVertexArray(id);
	}
	public void delete(){
		GL30.glDeleteVertexArrays(id);
	}
}
