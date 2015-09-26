package rendering.resourceManager;

import java.util.ArrayList;

import core.Point3f;
import core.Vector2f;
import core.Vector3f;

public class IndexedModel 
{
	private ArrayList<Point3f> positions;
	private ArrayList<Vector2f> texCoords;
	private ArrayList<Vector3f> normals;
	private ArrayList<Integer> indices;
	
	public IndexedModel()
	{
		positions = new ArrayList<Point3f>();
		texCoords = new ArrayList<Vector2f>();
		normals = new ArrayList<Vector3f>();
		indices = new ArrayList<Integer>();
	}
	
	public ArrayList<Point3f> getPositions()
	{
		return positions;
	}
	
	public ArrayList<Vector2f> getTexCoords()
	{
		return texCoords;
	}

	public ArrayList<Vector3f> getNormals() 
	{
		return normals;
	}

	public ArrayList<Integer> getIndices() 
	{
		return indices;
	}
}
