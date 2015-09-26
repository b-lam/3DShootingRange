package rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;

import rendering.resourceManager.IndexedModel;
import rendering.resourceManager.OBJModel;
import components.GameComponent;
import core.Texture;
import core.Util;
import core.Vector3f;

public class Mesh extends GameComponent
{
	private int vbo;
	private int ibo;
	private int size;
	private Texture texture;
	protected Vertex[] points;
	
	public Mesh()
	{
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		size = 0;
	}
	
	public Mesh(Texture tex)
	{
		this();
		texture = tex;
	}
	
	public Mesh(String fileName)
	{
		this();
		loadMesh("./res/models/" + fileName);
	}
	
	public Mesh loadMesh(String fileName)
	{
		String[] splitArray = fileName.split("\\.");	// split by period to get the file extension
		String ext = splitArray[splitArray.length - 1];
		
		if(!ext.equals("obj"))
		{
			System.err.println("Error: File format not supported for mesh data: " + ext);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		OBJModel test = new OBJModel(fileName);
		IndexedModel model = test.toIndexedModel();
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		
		for(int i=0; i < model.getPositions().size(); i++)
		{
			vertices.add(new Vertex(model.getPositions().get(i), model.getTexCoords().get(i), model.getNormals().get(i)));
		}
		
		//convert ArrayLists to arrays
		Vertex[] vertexData = new Vertex[vertices.size()];
		vertices.toArray(vertexData);
		
		//saves the vertices to an array for bounding spheres
		points = vertexData;
		
		Integer[] indexData = new Integer[model.getIndices().size()];
		model.getIndices().toArray(indexData);
		
		// and finally add the data to the Vertex
		addVertices(vertexData, Util.toIntArray(indexData));
		
		return this;
	}
	
	public void setTexture(Texture tex)
	{
		texture = tex;
	}
	
	public void addVertices(Vertex[] vertices, int[] indices)
	{
		size = indices.length;
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
	}
	
	float temp = 0.0f;
	
	public void draw()
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);	// for tangent
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);	// normals start at 12 + 2 floats x 4 bytes/float = 20 bytes
		glVertexAttribPointer(3, 3, GL_FLOAT, false, Vertex.SIZE * 4, 32);	// tangents start at 12 + 2 floats x 4 bytes/float + 3 floats x 4 bytes/float= 32 bytes
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
	}
	
	@Override
	public void update(float delta)
	{
		//temp = (float)Time.getTime();
		temp += 1;
		
		//this.getTransform().rotate(new Vector3f(0,1,0), 0.01f);
		//getTransform().setPos(new Vector3f(0, 0, 2));
		//transform.setPos(new Vector3f(2*(float)Math.sin(Math.toRadians(temp)), 0, 5));
		//transform.setScale(0.5f, 0.5f, 0.5f);
		
		
	}
	
	@Override
	public void render(Shader shader, RenderingEngine renderingEngine)
	{
		shader.bind();
		//shader.setUniformf("uniformFloat", 1);
		shader.setUniform("transform", renderingEngine.getMainCamera().getViewProjection().mul(getTransform().getTransformation()));
		if(texture != null)
			texture.bind();
		draw();
	}
}
