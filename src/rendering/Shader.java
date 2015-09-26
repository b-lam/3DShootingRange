package rendering;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;

import rendering.resourceManager.ResourceLoader;
import core.Matrix4f;
import core.Util;
import core.Vector3f;

public class Shader {

	private int program;	// pointer to program that will run on the graphics card
	private HashMap<String, Integer> uniforms;	// hashmap to map Strings to integer uniformLocations
	private String fileName;
	
	public Shader(String fileName)
	{
		this.fileName = fileName;
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		
		if(program == 0)	// failed to create shader program
		{
			System.err.println("Shader creation failed: Could not find valid memory location in constructor");
			System.exit(1);
		}
		
		addVertexShader(ResourceLoader.loadShader(fileName + ".vs"));
		addFragmentShader(ResourceLoader.loadShader(fileName + ".fs"));
		compileShader();
		
		// add all uniforms
		//addUniform("uniformFloat");
		addUniform("transform");
	}
	
	public void bind()
	{
		glUseProgram(program);
	}
	
	public void addUniform(String uniform)
	{
		int uniformLocation = glGetUniformLocation(program, uniform);	// get uniform location from program
		if(uniformLocation == -1)
		{
			System.err.println("ERROR: Could not find uniform " + uniform);
			System.exit(-1);
		}
		
		uniforms.put(uniform,  uniformLocation);	// makes it easier to access uniformLocation by name
		
	}
	
	public void addVertexShader(String text)
	{
		addProgram(text, GL_VERTEX_SHADER);
		
	}
	
	public void addFragmentShader(String text)
	{
		addProgram(text, GL_FRAGMENT_SHADER);
		
	}
	
	public void compileShader()
	{
		glLinkProgram(program);
		
		if(glGetProgram(program, GL_LINK_STATUS) == 0)
		{
			System.err.println(glGetShaderInfoLog(program, 1024));
			System.exit(1);
		}
		
		glValidateProgram(program);
		
		if(glGetProgram(program, GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(glGetShaderInfoLog(program, 1024));
			System.exit(1);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void addProgram(String text, int type)
	{
		int shader = glCreateShader(type);	// pointer to where the shader code is stored
		
		if(shader == 0)
		{
			System.err.println("Shader creation failed: Could not find valid memory location when adding shader");
			System.exit(1);
		}
		
		glShaderSource(shader, text);
		glCompileShader(shader);
		
		// check if shader compiled OK
		if(glGetShader(shader, GL_COMPILE_STATUS) == 0)
		{
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		// attach shader code to program
		glAttachShader(program, shader);
		
	}
	
	public void setUniformi(String uniformName, int value)
	{
		glUniform1i(uniforms.get(uniformName), value);
	}
	
	public void setUniformf(String uniformName, float value)
	{
		glUniform1f(uniforms.get(uniformName), value);
	}
	
	public void setUniform(String uniformName, Vector3f value)
	{
		glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	public void setUniform(String uniformName, Matrix4f value)
	{
		glUniformMatrix4(uniforms.get(uniformName), true, Util.createFlippedBuffer(value)); // true == row major form
	}
}
