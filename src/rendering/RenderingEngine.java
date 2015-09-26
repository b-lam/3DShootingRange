package rendering;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashMap;

import components.Camera;
import core.*;

public class RenderingEngine
{
	private Camera mainCamera;
	private Shader shader;
	
	public RenderingEngine()
	{
		super();
		
		shader = new Shader("helloWorld");

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		
		glEnable(GL_TEXTURE_2D);		// enable texture mapping
	}
	
	public static void enableTextures(boolean enabled)
	{
		if(enabled)
			glEnable(GL_TEXTURE_2D);
		else
			glDisable(GL_TEXTURE_2D);
	}
	
	public void render(GameObject object)
	{
		// clear Screen
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		object.renderAll(shader, this);	// allow for multi-pass rendering
		
		// TODO: set up Opengl to add other blending effects to the ambient lighting
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);	// add the two colors together
		glDepthMask(false); // diasble writing to the depth buffer
		glDepthFunc(GL_EQUAL);	// only write the pixel if it has the exact same ...
		
		// any code in here will now be blended into the image
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}

	public static String getOpenGLVersion()
	{
		return glGetString(GL_VERSION);
	}
	
	public void addCamera(Camera camera)
	{
		mainCamera = camera;
	}

	public Camera getMainCamera() 
	{
		return mainCamera;
	}

	public void setMainCamera(Camera mainCamera) 
	{
		this.mainCamera = mainCamera;
	}
}