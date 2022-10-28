package tester;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import chazzvader.lib.helper.other.Window;
import chazzvader.lib.helper.render.Shader;
import chazzvader.lib.helper.render.Texture;
import chazzvader.lib.helper.render.VertexArray;

/**
 * Simple class to test library
 * Do not use.
 */
class TestMain implements Runnable {
	
	private Thread thread;
	
	private long windowPtr;
	
	private Window windowObject;
	
	public static void main(String[] args) {
		new TestMain();
	}
	
	public TestMain() {
		thread = new Thread(this, "Render Thread");
		thread.start();
	}

	@Override
	public void run() {
		init();
		while(!GLFW.glfwWindowShouldClose(windowPtr)) {
			update();
			render();
		}
	}

	public static Shader TEST; 
	
	public static void loadAll() {
		TEST = new Shader("src/tester/test.vert", "src/tester/test.frag");
	}
	
	private void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		
		windowObject = Window.create(1920, 1080, "0x001");
		windowPtr = windowObject.id;
		
		testScene();
		
		GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		
		loadAll();
		
		System.out.println(GL11.glGetString(GL11.GL_VERSION));

		TEST.enable();
		
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		
		
		Matrix4f pr_matrix = new Matrix4f().setOrtho(-16.0f, 16.0f, -9.0f, 9.0f, -1.0f, 1.0f);
		TEST.setUniformMat4f("pr_matrix", pr_matrix);
		TEST.setUniform1i("tex", 1);
	}
	
	private VertexArray test;
	private Texture tex;
	
	private void testScene() {
		float[] vertices = new float[] {
			-16.0f, -9.0f, 0.0f,
			 16.0f, -9.0f, 0.0f,
			-16.0f,  9.0f, 0.0f,
			 16.0f,  9.0f, 0.0f
		};
		int[] indices = new int[] {
			0, 1, 2,
			1, 2, 3
		};
		float[] textureCoordinates = new float[] {
			0, 1,
			1, 1,
			0, 0,
			1, 0
		};
		
		test = new VertexArray(vertices, indices, textureCoordinates);
		
		tex = new Texture("tst.jpg");
	}
	
	private void render() {
		GLFW.glfwMakeContextCurrent(windowPtr);
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		tex.bind();
		TEST.enable();
		test.render();
		TEST.disable();
		tex.unbind();
		
		GLFW.glfwSwapBuffers(windowPtr);
	}

	private void update() {
		GLFW.glfwPollEvents();
		windowObject.update();
	}

}
