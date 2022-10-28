package chazzvader.lib.helper.render;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

public class Shader {

	public static final int VERTEX_ATTRIB = 0;
	public static final int TEX_COORD_ATTRIB = 1;
	
	private final int id;
	private Map<String, Integer> locationCache = new HashMap<>();
	
	public Shader(String vertPath, String fragPath) {
		id = ShaderUtils.load(vertPath, fragPath);
		if(id == -1) {
			System.err.println("Shader failed to load");
		}
	}
	
	public void enable() {
		GL20.glUseProgram(id);
	}
	
	public void disable() {
		GL20.glUseProgram(0);
	}
	
	public int getUniform(String name) {
		if(locationCache.containsKey(name)) {
			return locationCache.get(name);
		}
		int ret = GL20.glGetUniformLocation(id, name);
		if(ret == -1) {
			System.err.println("Unable to find uniform");
		} else {
			locationCache.put(name, ret);
		}
		return ret;
	}
	
	public void setUniform1i(String name, int value) {
		GL20.glUniform1i(getUniform(name), value);
	}
	
	public void setUniform1f(String name, float value) {
		GL20.glUniform1f(getUniform(name), value);
	}
	
	public void setUniform3f(String name, Vector3f vec) {
		GL20.glUniform3f(getUniform(name), vec.x, vec.y, vec.z);
	}
	
	public void setUniformMat4f(String name, Matrix4f matrix) {
		GL20.glUniformMatrix4fv(getUniform(name), false, matrix.get(new float[16]));
	}

	@Override
	public String toString() {
		return "Shader [id=" + id + ", locationCache=" + locationCache + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((locationCache == null) ? 0 : locationCache.hashCode());
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
		Shader other = (Shader) obj;
		if (id != other.id)
			return false;
		if (locationCache == null) {
			if (other.locationCache != null)
				return false;
		} else if (!locationCache.equals(other.locationCache))
			return false;
		return true;
	}
	
}
