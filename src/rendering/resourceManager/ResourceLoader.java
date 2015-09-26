package rendering.resourceManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.opengl.TextureLoader;

import core.Point3f;
import core.Texture;
import core.Util;
import core.Vector2f;
import core.Vector3f;
import rendering.Mesh;
import rendering.Vertex;

public class ResourceLoader {
	
	public static Texture loadTexture(String fileName)
	{
		String[] splitArray = fileName.split("\\.");	// split by period to get the file extension
		String ext = splitArray[splitArray.length - 1];
		
		try {
			int id = TextureLoader.getTexture(ext, new FileInputStream(new File("./res/textures/" + fileName))).getTextureID();
			return new Texture(id);
			//return new Texture("./res/textures/" + fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String loadShader(String fileName)
	{
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		String line;
		try {
			shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));
			
			while((line = shaderReader.readLine()) != null)
			{
				shaderSource.append(line).append("\n");
			}
			
			shaderReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return shaderSource.toString();
	}
//	
//	public static Mesh loadMesh(String fileName)
//	{
//
//		
//		String[] splitArray = fileName.split("\\.");	// split by period to get the file extension
//		String ext = splitArray[splitArray.length - 1];
//		
//		if(!ext.equals("obj"))
//		{
//			System.err.println("Error: File format not supported for mesh data: " + ext);
//			new Exception().printStackTrace();
//			System.exit(1);
//		}
//		
//		ArrayList<Point3f> positions = new ArrayList<Point3f>();
//		ArrayList<OBJIndex> indices = new ArrayList<OBJIndex>();
//		ArrayList<Vector2f> texCoords = new ArrayList<Vector2f>();
//		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
//		
//		
//		BufferedReader meshReader = null;
//		
//		try {
//			meshReader = new BufferedReader(new FileReader("./res/models/" + fileName));
//			String line;
//			
//			while((line = meshReader.readLine()) != null)
//			{
//				String[] tokens = line.split(" ");
//				tokens = Util.removeEmptyStrings(tokens);
//				
//				if(tokens.length == 0 || tokens[0].equals("#"))
//					continue;
//				else if(tokens[0].equals("v"))
//				{
//					positions.add(new Point3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));
//				}
//				else if(tokens[0].equals("vt"))
//				{
//					texCoords.add(new Vector2f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2])));
//				}
//				else if(tokens[0].equals("vn"))
//				{
//					normals.add(new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));
//				}
//				else if(tokens[0].equals("f"))	// we need indices for textures and normals as well
//				{
//					for(int i=0; i<tokens.length - 3; i++)	// triangulate convex polygons
//					{
//						indices.add(parseOBJIndex(tokens[1]));
//						indices.add(parseOBJIndex(tokens[2+i]));
//						indices.add(parseOBJIndex(tokens[3+i]));
//					}
//					
////					indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
////					indices.add(Integer.parseInt(tokens[2].split("/")[0]) - 1);
////					indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);
////					
////					if(tokens.length > 4)	// if we have quads, then triangularize them
////					{
////						indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
////						indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);
////						indices.add(Integer.parseInt(tokens[4].split("/")[0]) - 1);
////					}
//				}
//			}
//			
//			meshReader.close();
//			
//			IndexedModel model = 
////			
////			Mesh res = new Mesh();
////			Vertex[] vertexData = new Vertex[vertices.size()];
////			vertices.toArray(vertexData);
////			
////			Integer[] indexData = new Integer[indices.size()];
////			indices.toArray(indexData);
////			
////			res.addVertices(vertexData, Util.toIntArray(indexData));
////			
////			return res;
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.exit(1);
//		}
//		
//		return null;
//	}
	
}
