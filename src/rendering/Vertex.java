package rendering;

import core.Point3f;
import core.Vector2f;
import core.Vector3f;

public class Vertex 
{
	public static final int SIZE = 5;	// 3 floats for pos + 2 floats for texture coords  = 5
	
	private Point3f pos;
	private Vector2f texCoord;
	private Vector3f normal;
	
	public Vertex(Point3f pos)
	{
		this(pos, new Vector2f(0, 0));
	}
	
	public Vertex(Point3f pos, Vector2f texCoord)
	{
		this.pos = pos;
		this.texCoord = texCoord;
	}
	
	public Vertex(Point3f pos, Vector2f texCoord, Vector3f normal)
	{
		this.pos = pos;
		this.texCoord = texCoord;
		this.normal = normal;
	}

	
	public Point3f getPos() {
		return pos;
	}
	public void setPos(Point3f pos) {
		this.pos = pos;
	}

	public Vector2f getTexCoord() {
		return texCoord;
	}

	public void setTexCoord(Vector2f texCoord) {
		this.texCoord = texCoord;
	}

}
