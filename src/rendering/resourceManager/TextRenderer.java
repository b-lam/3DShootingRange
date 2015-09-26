package rendering.resourceManager;

import components.Camera;
import rendering.Mesh;
import rendering.Window;
import core.GameObject;
import core.Point3f;
import core.Quaternion;
import core.Texture;
import core.Vector2f;
import core.Vector3f;

public class TextRenderer 
{
	private GameObject[] digits;
	private GameObject[] chars;
	private Vector2f start;	// the (x,y,z) starting location of the text
	private Vector3f scale;	// the size of the text
	private float spacing;	// spacing between letters/digits (default = 1.8)
	private Texture[] numMat;
	private Texture[] charMat;
	private Camera cam;
	
	private int lastNum;
	private String lastStr;
	private int numChars = 26;
	private float factor;
	
	public TextRenderer(Vector2f start, float fontSize, Camera cam)
	{
		digits = new GameObject[10];
		chars = new GameObject[numChars];
		factor = cam.getzNear()/cam.getzFar();
		this.start = start.mul(2.3f);
		//this.start = start.sub(new Vector2f(Window.getWidth()/2, -Window.getHeight()/2));
		scale = new Vector3f(fontSize, fontSize, 1).mul(factor);
		this.cam = cam;
		lastNum = -1;
		lastStr = "";
		spacing = 2.1f;
		
		
		numMat = new Texture[10];	// for 10 digits

		for(int i=0; i<digits.length; i++)
		{
			Texture texture = ResourceLoader.loadTexture("digits/" + i + ".png");
			//Texture texture = new Texture("digits/" + i + ".png");
			numMat[i] = texture;
			//numMat[i].setEmmissivity(new Vector3f(0.0f, 0.3f, 0.0f));
		}
		
		charMat = new Texture[numChars];	// for 26 digits

		for(int i=0; i<chars.length; i++)
		{
			Texture texture = ResourceLoader.loadTexture("alphabet/" + (char)(65+i) + ".png");
			//Texture texture = new Texture("alphabet/" + (char)(65+i) + ".png");
			charMat[i] = texture;
			//charMat[i].setEmmissivity(new Vector3f(0.0f, 0.3f, 0.0f));
		}
	}
	
	public GameObject getNumber(int num)	// returns a number as a GameObject
	{
		if(lastNum == num)
			return digits[0];
		
		String n = String.valueOf(num);
		int j=0;
		
		digits = new GameObject[n.length()];
		
		for(int i=0; i<n.length(); i++)
		{
			j = Integer.parseInt(""+n.charAt(i));
			Mesh mesh = new Mesh("plane.obj");
			mesh.setTexture(numMat[j]);
			digits[i] = new GameObject().addComponent(mesh);
			
			if(i==0)
			{
				digits[i].getTransform().setScale(scale);
				//digits[i].getTransform().setPos(start);
				digits[i].getTransform().setPos(start.mul(factor).getX(), start.mul(factor).getY(), 0.1f );
				digits[i].getTransform().rotate(new Vector3f(1,0,0),(float)Math.toRadians(-90));
				digits[i].getTransform().rotate(new Vector3f(0,0,1),(float)Math.toRadians(180));
			}else
			{
				digits[i].getTransform().setPos(new Vector3f(-spacing, 0f, 0));	
				digits[i-1].addChild(digits[i]);
			}
			
		}
		lastNum = num;
		return digits[0];
	}
	
	
	public GameObject getString(String str)		// returns a string as a GameObject
	{
		if(lastStr.equals(str))
			return chars[0];
		
		int j=0;
		
		chars = new GameObject[str.length()];
		
		for(int i=0; i<str.length(); i++)
		{
			j = (int)(str.charAt(i)) - 65;
			//chars[i] = new GameObject().addComponent(new Mesh("plane.obj", charMat[j]));
			
			Mesh mesh = new Mesh("plane.obj");
			mesh.setTexture(charMat[j]);
			chars[i] = new GameObject().addComponent(mesh);
			
			if(i==0)
			{
				chars[i].getTransform().setScale(scale);
				chars[i].getTransform().setPos(start.mul(factor).getX(), start.mul(factor).getY(), 0.1f);
				chars[i].getTransform().rotate(new Vector3f(1,0,0),(float)Math.toRadians(-90));
				chars[i].getTransform().rotate(new Vector3f(0,0,1),(float)Math.toRadians(180));
			}else
			{
				chars[i].getTransform().setPos(new Vector3f(-spacing, 0.0f, 0));
				chars[i-1].addChild(chars[i]);
			}
		}
		lastStr = str;
		return chars[0];
	}
	
	public Vector2f getStart() {
		return start;
	}

	public void setStart(Vector2f start) {
		this.start = start;
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

	public float getSpacing() {
		return spacing;
	}

	public void setSpacing(float spacing) {
		this.spacing = spacing;
	}
}
