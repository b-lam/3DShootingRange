package core;

public class Point3f {
	private float x, y, z;
	
	public Point3f()
	{
		x=0;
		y=0;
		z=0;
	}
	
	public Point3f(float x, float y, float z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public Vector3f sub(Point3f p)
	{
		return new Vector3f(x-p.getX(), y-p.getY(), z-p.getZ());
	}
	public Point3f add(Vector3f v)
	{
		return new Point3f(x + v.getX(), y + v.getY(), z + v.getZ());
	}
	
	public Point3f mul(float s)
	{
		return new Point3f(s*x, s*y, s*z);
	}
	
	public float getX()
	{
		return x;
	}
	public float getY()
	{
		return y;
	}
	public float getZ()
	{
		return z;
	}
	public void setX(float x)
	{
		this.x=x;
	}
	public void setY(float y)
	{
		this.y=y;
	}
	
	public void setZ(float z)
	{
		this.z=z;
	}
	public void set(float x, float y, float z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
}
