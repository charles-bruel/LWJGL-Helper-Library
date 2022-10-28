package chazzvader.lib.helper.other;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import chazzvader.lib.helper.input.MousePosInput;

public class Window {

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public final long id;
	
	private int width, height, pWidth, pHeight;
	
	private Window(long id) {
		this.id = id;
	}
	
	
	
	public static Window create(int width, int height, String name) {
		if(!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initilize GLFW");
		}
		
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		
		long id = GLFW.glfwCreateWindow(width, height, name, MemoryUtil.NULL, MemoryUtil.NULL);
		
		Window window = new Window(id);
		
		if(id == MemoryUtil.NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		
		try ( MemoryStack stack = MemoryStack.stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);

			GLFW.glfwGetWindowSize(id, pWidth, pHeight);

			GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

			GLFW.glfwSetWindowPos(
				id,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		}
		
		GLFW.glfwSetCursorPosCallback(id, new MousePosInput());
		GLFW.glfwSetWindowSizeCallback(id, window.new WindowResizeListener());

		GLFW.glfwMakeContextCurrent(id);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(id);
		
		
		GL.createCapabilities();
		
		GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		
		return window;
	}
	
	public void update() {
		if(width != pWidth || height != pHeight) {
			GL11.glViewport(0, 0, width, height);
			pWidth = width;
			pHeight = height;
		}
	}
	
	private class WindowResizeListener extends GLFWWindowSizeCallback {

		@Override
		public void invoke(long windo, int w, int h) {
			width = w;
			height = h;
		}
		
	}
	
}
