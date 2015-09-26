package core;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

import rendering.RenderingEngine;
import rendering.Window;

public class CoreEngine {
	private boolean isRunning;
	public int windowWidth = 800;
	public int windowHeight = 600;
	public String title = "OpenGL Intro";
	private double frameTime;	// time for one frame
	
	private RenderingEngine renderingEngine;
	private Game game;
	
	public CoreEngine(int width, int height, double frameRate, Game game)
	{
		isRunning = false;
		this.game = game;
		this.windowWidth = width;
		this.windowHeight = height;
		this.frameTime = 1/frameRate;
		game.setEngine(this);
	}
	
	public void createWindow(String title)
	{
		this.title = title;
		Window.createWindow(windowWidth,  windowHeight, title);
		this.renderingEngine = new RenderingEngine();
		System.out.println(glGetString(GL_VERSION));
	}
	
	private void run()
	{
		int frames = 0;							// counts the number of frames per second
		double startTime = 0;
		double previousTime = Time.getTime();
		double deltaTime = 0;
		double passedTime = 0;
		boolean render = false;
		double frameCounter = 0;
		
		game.init();
		
		while(isRunning)
		{
			render = false;
			
			startTime = Time.getTime();
			deltaTime = startTime - previousTime;
			previousTime = startTime;
			passedTime += deltaTime;	// reset every 1/frameRate (ie 1/60 seconds)
			frameCounter += deltaTime;	// reset every second
			
			if(passedTime > frameTime)
			{
				render = true;
				passedTime = 0;
				
				if(Window.isCloseRequested())
					stop();
				
				
				game.input((float)frameTime);
				Input.update();
				game.update((float)frameTime);
				
				if(frameCounter >= 1)
				{
					Display.setTitle(title + "\tFPS: " + frames);
					frames = 0;
					frameCounter = 0;
				}
			}

			if(render)
			{
				// draw here
				//RenderUtil.clearScreen();	// clears both the color and the depth buffer
				game.render(renderingEngine);
				Window.render();
				frames++;
			}
			else
			{
				try {
					Thread.sleep(1);		// sleep for 1 millisecond so that we don't waste processor time doing the while loop
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		cleanUp();
	}
	
	private void cleanUp() 
	{
		Window.dispose();
	}
	
	public void start()
	{
		if(!isRunning) {
			isRunning = true;
			run();
		}
	}
	
	public void stop()
	{
		if(isRunning)
			isRunning = false;
		
	}
	
	public RenderingEngine getRenderingEngine() {
		return renderingEngine;
	}

}
