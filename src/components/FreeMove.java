package components;

import core.Input;
import core.MP3;
import core.Time;
import core.Vector3f;


public class FreeMove extends GameComponent
{
	public static final Vector3f zAxis = new Vector3f(0,0,1);
	private float speed;
	private int   forwardKey;
	private int   backKey;
	private int   leftKey;
	private int   rightKey;
	
	public FreeMove(float speed) {
		this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
	}
	
	public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey) {
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
		
		
		if(Input.getKey(Input.KEY_W)){
			move(getTransform().getRot().getForward(), movAmt);
			inBox();
		}
		if(Input.getKey(Input.KEY_S)){
			move(getTransform().getRot().getForward(), -movAmt);
			inBox();
		}
		if(Input.getKey(Input.KEY_A)){
			move(getTransform().getRot().getLeft(), movAmt);
			inBox();
		}
		if(Input.getKey(Input.KEY_D)){
			move(getTransform().getRot().getRight(), movAmt);
			inBox();
		}
	
	}
	
	public void move(Vector3f dir, float amt)
	{
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
		
	}
	
	//Moves player back into the room if they try to go out
	public void inBox(){
		if(getTransform().getPos().getX() > -28)
			getTransform().setPos(new Vector3f(getTransform().getPos().getX()-0.17f,getTransform().getPos().getY(),getTransform().getPos().getZ()));
		
		if(getTransform().getPos().getX() < 28)
			getTransform().setPos(new Vector3f(getTransform().getPos().getX()+0.17f,getTransform().getPos().getY(),getTransform().getPos().getZ()));

		if(getTransform().getPos().getZ() > -28)
			getTransform().setPos(new Vector3f(getTransform().getPos().getX(),getTransform().getPos().getY(),getTransform().getPos().getZ()-0.17f));

		if(getTransform().getPos().getZ() < -10)
			getTransform().setPos(new Vector3f(getTransform().getPos().getX(),getTransform().getPos().getY(),getTransform().getPos().getZ()+0.17f));
		
	}
}