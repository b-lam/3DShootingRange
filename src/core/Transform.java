package core;

public class Transform
{
	private Transform parent;
	private Matrix4f parentMatrix;
	
	private Vector3f pos;
	private Quaternion rot;		// using quaternions for all rotations
	private Vector3f scale;
	
	private Vector3f oldPos;
	private Quaternion oldRot;		// using quaternions for all rotations
	private Vector3f oldScale;
	
	public Transform()
	{
		pos = new Vector3f(0,0,0);
		rot = new Quaternion(1,0,0,0);	// null rotation
		scale = new Vector3f(1, 1, 1);
		
		parentMatrix = new Matrix4f().setIdentity();
	}
	
	public void update()
	{
		if(oldPos != null)
		{
			oldPos.set(pos);
			oldRot.set(rot);
			oldScale.set(scale);
		}else
		{
			oldPos = new Vector3f(0,0,0).set(pos).add(1.0f);
			oldRot = new Quaternion(0,0,0,0).set(rot).mul(0.5f);
			oldScale = new Vector3f(0,0,0).set(scale).add(1.0f);
		}
	}
	
	public void rotate(Vector3f axis, float angle)
	{
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}
	
	public void lookAt(Vector3f point, Vector3f up)
	{
		rot = getLookAtDirection(point, up);
	}
	
	public Quaternion getLookAtDirection(Vector3f point, Vector3f up)
	{
		return	new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}
	
	public boolean hasChanged()
	{
		if(parent != null && parent.hasChanged())
			return true;	
		
		if(!pos.equals(oldPos))
			return true;
		
		if(!pos.equals(oldRot))
			return true;
			
		if(!pos.equals(oldScale))
			return true;
		
		return false;
	}
	
	public Quaternion getRot() {
		return rot;
	}
	
	public void setRot(Quaternion rot) {
		this.rot = rot;
	}

	public Matrix4f getTransformation()
	{
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());	
		
		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}
	
	private Matrix4f getParentMatrix()
	{
		if(parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();
		
		return parentMatrix;
	}
	
	public void setParent(Transform parent)
	{
		this.parent = parent;
	}
	
	public Matrix4f getProjectedTransformation()
	{
		return null;
	}
	
	public Vector3f getTransformedPos()
	{
		return getParentMatrix().transform(pos);
	}
	
	public Quaternion getTransformedRot()
	{
		Quaternion parentRotation = new Quaternion(1,0,0,0);	// quaternion that represents no rotation
		
		if(parent != null)
			parentRotation = parent.getTransformedRot();
		
		return parentRotation.mul(rot);
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}
	
	public void setPos(float x, float y, float z) {
		pos.set(x, y, z);
	}


	public Vector3f getScale() {
		return scale;
	}
	
	public void setScale(float x, float y, float z) {
		this.scale = new Vector3f(x, y, z);
	}
	
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
}