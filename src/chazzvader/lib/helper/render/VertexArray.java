package chazzvader.lib.helper.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import chazzvader.lib.helper.other.BufferUtils;

public class VertexArray {

	private int count, vertexArrayObject, vertexBufferObject, indexBufferObject, textureBufferObject;
	
	public VertexArray(float[] vertices, int[] indices, float[] textureCoordinates) {
		count = indices.length;
		
		vertexArrayObject = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vertexArrayObject);
		
		vertexBufferObject = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferObject);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(Shader.VERTEX_ATTRIB, 3, GL11.GL_FLOAT, false, 0, 0);
		GL20.glEnableVertexAttribArray(Shader.VERTEX_ATTRIB);
		
		textureBufferObject = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureBufferObject);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(textureCoordinates), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(Shader.TEX_COORD_ATTRIB, 2, GL11.GL_FLOAT, false, 0, 0);
		GL20.glEnableVertexAttribArray(Shader.TEX_COORD_ATTRIB);
		
		indexBufferObject = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBufferObject);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices), GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void bind() {
		GL30.glBindVertexArray(vertexArrayObject);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBufferObject);
	}
	
	public void unbind() {
		GL30.glBindVertexArray(0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void draw() {
		GL11.glDrawElements(GL11.GL_TRIANGLES, count, GL11.GL_UNSIGNED_INT, 0);
	}
	
	public void render() {
		bind();
		draw();
	}

	@Override
	public String toString() {
		return "VertexArray [count=" + count + ", vertexArrayObject=" + vertexArrayObject + ", vertexBufferObject="
				+ vertexBufferObject + ", indexBufferObject=" + indexBufferObject + ", textureBufferObject="
				+ textureBufferObject + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + indexBufferObject;
		result = prime * result + textureBufferObject;
		result = prime * result + vertexArrayObject;
		result = prime * result + vertexBufferObject;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VertexArray other = (VertexArray) obj;
		if (count != other.count)
			return false;
		if (indexBufferObject != other.indexBufferObject)
			return false;
		if (textureBufferObject != other.textureBufferObject)
			return false;
		if (vertexArrayObject != other.vertexArrayObject)
			return false;
		if (vertexBufferObject != other.vertexBufferObject)
			return false;
		return true;
	}
	
}
