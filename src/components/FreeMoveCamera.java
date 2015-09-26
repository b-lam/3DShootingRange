package components;

import core.Input;
import core.Vector3f;


public class FreeMoveCamera extends GameComponent
{
	public static final Vector3f zAxis = new Vector3f(0,0,1);
	private float speed;
	private int   forwardKey;
	private int   backKey;
	private int   leftKey;
	private int   rightKey;
	
	public FreeMoveCamera(float speed) {
		this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
	}
	
	public FreeMoveCamera(float speed, int forwardKey, int backKey, int leftKey, int rightKey) {
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backKey = backKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
	}
	
	@Override
	public void input(float delta) // testing camera
	{
		float movAmt = speed * delta;
		double b;
		if(Input.getKey(Input.KEY_W))
			move(getTransform().getRot().getForward(), movAmt);
		if(Input.getKey(Input.KEY_S))
			move(getTransform().getRot().getForward(), -movAmt);
		if(Input.getKey(Input.KEY_A))
			move(getTransform().getRot().getLeft(), movAmt);
		if(Input.getKey(Input.KEY_D))
			move(getTransform().getRot().getRight(), movAmt);
		
	}
	
	public void move(Vector3f dir, float amt)
	{
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
		
	}
}