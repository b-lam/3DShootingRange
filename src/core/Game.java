package core;

import rendering.RenderingEngine;


public abstract class Game 
{
	private GameObject root;
	
	public abstract void init();
	
	public void input(float delta)
	{
		getRootObject().inputAll(delta);
	}
	
	public void update(float delta)
	{
		getRootObject().updateAll(delta);
	}
	
	public void addObject(GameObject object)
	{
		getRootObject().addChild(object);
	}
	
	public void render(RenderingEngine renderingEngine)
	{
		renderingEngine.render(getRootObject());
	}
	
	private GameObject getRootObject()
	{
		if(root == null)
			root = new GameObject();
		return root;
	}
	
	public void setEngine(CoreEngine engine)
	{
		getRootObject().setEngine(engine);
	}
	
}