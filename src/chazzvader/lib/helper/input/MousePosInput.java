package chazzvader.lib.helper.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MousePosInput extends GLFWCursorPosCallback {

	public static double xpos, ypos;
	
	@Override
	public void invoke(long window, double xpos, double ypos) {
		MousePosInput.xpos = xpos;
		MousePosInput.ypos = ypos;
	}

}
