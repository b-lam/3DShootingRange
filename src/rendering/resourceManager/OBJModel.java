package rendering.resourceManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import core.Point3f;
import core.Util;
import core.Vector2f;
import core.Vector3f;

public class OBJModel {
	private ArrayList<Point3f> positions;
	private ArrayList<Vector2f> texCoords;
	private ArrayList<Vector3f> normals;
	private ArrayList<OBJIndex> indices;
	
	private boolean hasTexCoords;
	private boolean hasNormals;
	
	public OBJModel(String fileName) {
		positions = new ArrayList<Point3f>();
		texCoords = new ArrayList<Vector2f>();
		normals = new ArrayList<Vector3f>();
		indices = new ArrayList<OBJIndex>();
		hasTexCoords = false;
		hasNormals = false;
		
		BufferedReader meshReader = null;
		
		try {
			meshReader = new BufferedReader(new FileReader(fileName));
			String line;
			
			while((line = meshReader.readLine()) != null)
			{
				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyStrings(tokens);
				
				if(tokens.length == 0 || tokens[0].equals("#"))
					continue;
				else if(tokens[0].equals("v"))
				{
					positions.add(new Point3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));
				}
				else if(tokens[0].equals("vt"))
				{
					texCoords.add(new Vector2f(Float.valueOf(tokens[1]), 1.0f - Float.valueOf(tokens[2])));
				}
				else if(tokens[0].equals("vn"))
				{
					normals.add(new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));
				}
				else if(tokens[0].equals("f"))
				{
					for(int i=0; i<tokens.length-3; i++)	// triangulate convex polygons
					{
						indices.add(parseOBJIndex(tokens[1]));
						indices.add(parseOBJIndex(tokens[2+i]));
						indices.add(parseOBJIndex(tokens[3+i]));
					}
				}
			}
			
			meshReader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Takes in a single String of type v/t/n and returns an OBJIndex of the 3 indices
	 * @param token representing potentially 3 indices of the form v/t/n
	 * @return OBJIndex of the indices
	 */
	private OBJIndex parseOBJIndex(String token)
	{	
		String[] indexValues = token.split("/");
		
		OBJIndex result = new OBJIndex();
		result.vertexIndex = Integer.parseInt(indexValues[0]) - 1;
		
		if(indexValues.length > 1)
		{
			hasTexCoords = true;
			result.texCoordIndex = Integer.parseInt(indexValues[1]) - 1;
			
			if(indexValues.length > 2)
			{
				hasNormals = true;
				result.normalIndex = Integer.parseInt(indexValues[2]) - 1;
			}
		}
		
		return result;
		
	}
	
	public IndexedModel toIndexedModel()
	{
		IndexedModel result = new IndexedModel();
		
		for(int i=0; i<indices.size(); i++)
		{
			OBJIndex currentIndex = indices.get(i);
			
			Point3f currentPosition = positions.get(currentIndex.vertexIndex);
			Vector2f currentTexCoord;
			Vector3f currentNormal;
			
			if(hasTexCoords)
				currentTexCoord = texCoords.get(currentIndex.texCoordIndex);
			else
				currentTexCoord = new Vector2f(0,0);
			
			if(hasNormals)
				currentNormal = normals.get(currentIndex.normalIndex);
			else
				currentNormal = new Vector3f(0,0,0);
			
			result.getPositions().add(currentPosition);
			result.getTexCoords().add(currentTexCoord);
			result.getNormals().add(currentNormal);
			result.getIndices().add(i);
		}
		
		return result;
	}
}
