package core;

import java.util.ArrayList;
import java.util.Iterator;

import rendering.*;
import components.*;

public class GameObject 
{
	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	private Transform transform;
	private CoreEngine engine;
	
	public GameObject()
	{
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
		engine = null;
	}
	
	public void addChild(GameObject child)
	{
		children.add(child);
		child.setEngine(engine);
		child.getTransform().setParent(transform);
	}
	
	public void removeChild(GameObject child)	// remove indicated child and all of its children
	{
		Iterator<GameObject> iter = children.iterator();

		while (iter.hasNext()) {
		    GameObject obj = iter.next();

		    if (obj == child)
		        iter.remove();
		}
	}
	
	public GameObject addComponent(GameComponent component)
	{
		components.add(component);
		component.setParent(this);
		
		return this;
	}
	
	public void inputAll(float delta)
	{
		input(delta);
		
		for(GameObject child : children)
			child.inputAll(delta);
	}
	
	public void updateAll(float delta)
	{
		update(delta);
		
		for(GameObject child : children)
			child.updateAll(delta);
	}
	
	public void renderAll(Shader shader, RenderingEngine renderingEngine)
	{
		render(shader, renderingEngine);
		
		for(GameObject child : children)
			child.renderAll(shader, renderingEngine);
	}
	
	public void input(float delta)
	{
		transform.update();
		
		for(GameComponent component : components)
			component.input(delta);
	}
	
	public void update(float delta)
	{
		for(GameComponent component : components)
			component.update(delta);
	}
	
	public void render(Shader shader, RenderingEngine renderingEngine)
	{
		for(GameComponent component : components)
			component.render(shader, renderingEngine);
	}
	
	/*
	 * returns a flat list of all the children GameObject and itself
	 */
	public ArrayList<GameObject> getAllAttached()
	{
		ArrayList<GameObject> result = new ArrayList<GameObject>();
		for(GameObject child : children)
			result.addAll(child.getAllAttached());
		
		result.add(this);
		return result;
	}

	public Transform getTransform()
	{
		return transform;
	}
	
	public void setEngine(CoreEngine engine)
	{
		if(this.engine != engine)
		{
			this.engine = engine;
			
			for(GameComponent component : components)
				component.addToEngine(engine);
			
			for(GameObject child : children)
				child.setEngine(engine);
			
		}
	}
}