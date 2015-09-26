package components;

import rendering.Window;
import core.Input;
import core.Vector2f;
import core.Vector3f;


public class FreeLookCamera extends GameComponent
{
	public static final Vector3f yAxis = new Vector3f(0,1,0);	// world up vector
	
	private boolean mouseLocked = false;
	private float   sensitivity;
	private int     unlockMouseKey;
	
	public FreeLookCamera(float sensitivity) 
	{
		this(sensitivity, Input.KEY_ESCAPE);
	}

	public FreeLookCamera(float sensitivity, int unlockMouseKey) 
	{
		this.sensitivity = sensitivity;
		this.unlockMouseKey = unlockMouseKey;
	}
	
	@Override
	public void input(float delta) // testing camera
	{
		Vector2f centerPosition = new Vector2f(Window.getWidth()/2, Window.getHeight()/2);
		
		//float sensitivity = 0.09f;
		
		if(Input.getKey(unlockMouseKey))
		{
			Input.setCursor(true);
			mouseLocked = false;
		}
		
		if(Input.getMouse(0))
		{
			Input.setMousePosition(centerPosition);
			Input.setCursor(false);
			mouseLocked = true;
		}
		
		if(mouseLocked)
		{
			Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);
			
			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;
			
			if(rotY)
				getTransform().rotate(yAxis, (float)Math.toRadians(deltaPos.getX() * sensitivity));
			if(rotX) {
				getTransform().rotate(getTransform().getRot().getRight(), (float)Math.toRadians(-deltaPos.getY() * sensitivity));
			}
				
			if(rotY || rotX)
				Input.setMousePosition(new Vector2f(Window.getWidth()/2, Window.getHeight()/2));
			
		}
	
	}
}