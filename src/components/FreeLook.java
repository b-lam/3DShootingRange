package components;

import core.Input;
import core.Vector3f;


public class FreeLook extends GameComponent
{
	public static final Vector3f yAxis = new Vector3f(0,1,0);	// world up vector

	private int   leftKey;
	private int   rightKey;
	
	public FreeLook() {
		this(Input.KEY_LEFT, Input.KEY_RIGHT);
	}
	
	public FreeLook(int leftKey, int rightKey) {
		this.leftKey = leftKey;
		this.rightKey = rightKey;
	}
	
	@Override
	public void input(float delta) // testing camera
	{
		//Player and camera rotation
		if(Input.getKey(Input.KEY_LEFT))
			getTransform().rotate(yAxis, (float)Math.toRadians(-2));
		if(Input.getKey(Input.KEY_RIGHT))
			getTransform().rotate(yAxis, (float)Math.toRadians(2));
		
		}
	
	
}