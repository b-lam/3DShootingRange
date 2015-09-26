package rendering;

import static org.lwjgl.opengl.GL11.glViewport;
//import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
//import static org.lwjgl.opengl.GL30.glBindFramebuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import core.Vector2f;

public class Window {

	public static void createWindow(int width, int height, String title)
	{
		Display.setTitle(title);
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void render()
	{
		Display.update();
	}
	
	public static void dispose() 
	{
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}
	
	public static void bindAsRenderTarget()	// once this method is called, then any OpenGL drawing function is going to be drawing inside the window
	{
//		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);	// 0 is the default screen buffer ... ie it is the window
		glViewport(0,0,getWidth(), getHeight());
	}
	
	public static boolean isCloseRequested() 
	{
		return Display.isCloseRequested();
	}
	
	public static int getWidth() 
	{
		return Display.getWidth();
	}
	
	public static int getHeight()
	{
		return Display.getHeight();
	}
	
	public static String getTitle()
	{
		return Display.getTitle();
	}

	public Vector2f getCenter()
	{
		return new Vector2f(getWidth()/2, getHeight()/2);
	}

}
