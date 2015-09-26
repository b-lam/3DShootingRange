package core;

public class Vector3f {
	
	private float x;
	private float y;
	private float z;
	
	public Vector3f(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float max()
	{
		return Math.max(x,  Math.max(y, z));
	}
	
	public Vector3f set(float x, float y, float z) 
	{ 
		this.x = x; this.y = y; this.z = z; return this;
	}
	
	public Vector3f set(Vector3f v)
	{
		set(v.getX(), v.getY(), v.getZ()); return this;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public float length()
	{
		return (float)Math.sqrt(x*x + y*y + z*z);
	}
	
	public float dot(Vector3f v)
	{
		return x*v.getX() + y*v.getY() + z*v.getZ();
	}
	
	public Vector3f cross(Vector3f v)
	{
		float _x = y*v.getZ() - z*v.getY();
		float _y = z*v.getX() - x*v.getZ();
		float _z = x*v.getY() - y*v.getX();
		
		return new Vector3f(_x, _y, _z);
	}
	
	public Vector3f normalize()		// this is the only mutable method! 
	{
		float length = length();
		
		x /= length;
		y /= length;
		z /= length;
		
		return this;
	}
	
	public Vector3f normalized()		
	{
		float length = length();
		
		return new Vector3f(x/length, y/length, z/length);
	}
	
	public Vector3f rotate(Vector3f axis, float angle)	// angle is in radians
	{
		//Quaternion q = new Quaternion(1, this);
		//Quaternion q_rot = q.rotate(new Quaternion().initRotation(axis, angle), (float)Math.toRadians(angle));
		return this.rotate(new Quaternion(axis, angle));
		
		//Quaternion q_rot = q.rotate(new Quaternion(0, this), (float)(Math.toRadians(angle)));
		//return q_rot.getV();
		
//		float sinHalfAngle = (float)Math.sin(Math.toRadians(angle / 2));
//		float cosHalfAngle = (float)Math.cos(Math.toRadians(angle / 2));
//		
//		float rX = axis.getX() * sinHalfAngle;
//		float rY = axis.getY() * sinHalfAngle;
//		float rZ = axis.getZ() * sinHalfAngle;
//		float rW =  cosHalfAngle;
//		
//		Quaternion rotation = new Quaternion(rW, rX, rY, rZ);
//		Quaternion conjugate = rotation.conjugate();
//		Quaternion w = rotation.mul(this).mul(conjugate);
		
//		x = q_rot.getX();
//		y = q_rot.getY();
//		z = q_rot.getZ();
		
		//return this;
	}
	
	public Vector3f rotate(Quaternion rotation)		// rotate this vector using the specified quaternion
	{
		//return rotation.rotate(this);
		
		Quaternion q = new Quaternion(this);		// transform this Vector to quaternion

		Quaternion zq = rotation.mul(q);
		Quaternion zqz = zq.mul(rotation.conjugate()).mul(1.0f/rotation.quadrance());
		return zqz.getV();
	}
	
//	public Vector3f rotate(Quaternion rotation)
//	{
//		Quaternion conjugate = rotation.conjugate();
//
//		Quaternion w = rotation.mul(new Quaternion(this)).mul(conjugate);
//
//		return new Vector3f(w.getX(), w.getY(), w.getZ());
//	}
	
	public Vector3f lerp(Vector3f dest, float lerpFactor)
	{
		return dest.sub(this).mul(lerpFactor).add(this);
	}
	
	public Vector3f add(Vector3f v)
	{
		return new Vector3f(x+v.getX(), y+v.getY(), z+v.getZ());
	}
	
	public Vector3f add(float r)
	{
		return new Vector3f(x+r, y+r, z+r);
	}
	
	public Vector3f sub(Vector3f v)
	{
		return new Vector3f(x-v.getX(), y-v.getY(), z-v.getZ());
	}
	
	public Vector3f mul(float s)
	{
		return new Vector3f(s*x, s*y, s*z);
	}
	
	public Vector3f div(float s)
	{
		return new Vector3f(x/s, y/s, z/s);
	}
	
	public Vector3f abs()
	{
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}
	
	public String toString()
	{
		return "[ " + x + ", " + y + ", " + z + " ]";
	}
	
	public Vector2f getXY() { return new Vector2f(x, y);}
	public Vector2f getYZ() { return new Vector2f(y, z);}
	public Vector2f getZX() { return new Vector2f(z, x);}
	
	public Vector2f getYX() { return new Vector2f(y, x);}
	public Vector2f getZY() { return new Vector2f(z, y);}
	public Vector2f getXZ() { return new Vector2f(x, z);}
	
	public boolean equals(Vector3f v)
	{
		return x == v.getX() && y == v.getY() && z == v.getZ();
	}

}



