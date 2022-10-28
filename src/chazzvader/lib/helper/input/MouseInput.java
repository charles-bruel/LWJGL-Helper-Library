package chazzvader.lib.helper.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseInput extends GLFWMouseButtonCallback {

	public boolean[] buttons = new boolean[3];
	
	@Override
	public void invoke(long window, int button, int action, int mods) {
		if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			buttons[0] = (action == GLFW.GLFW_RELEASE ? false : true);
		}
		if(button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
			buttons[1] = (action == GLFW.GLFW_RELEASE ? false : true);
		}
		if(button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE) {
			buttons[2] = (action == GLFW.GLFW_RELEASE ? false : true);
		}
	}

}
