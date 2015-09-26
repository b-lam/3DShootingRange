package core;

import rendering.Mesh;

public class BoundingSphere extends Mesh {

	protected float maxRadius;
	protected Vector3f center;

	public BoundingSphere(String filename){
		super(filename);
		findBoundingSphere();
	}

	//Finds the center and radius of the largest sphere surrounding a mesh
	public float findBoundingSphere(){
		float maxX = points[0].getPos().getX(), maxY = points[0].getPos().getY(), maxZ = points[0].getPos().getZ(), minX = points[0].getPos().getX(), minY = points[0].getPos().getY(), minZ = points[0].getPos().getZ();
		for(int i = 0; i < points.length; i++){
			if(points[i].getPos().getX() > maxX){
				maxX = points[i].getPos().getX();
			}
			if(points[i].getPos().getY() > maxY){
				maxY = points[i].getPos().getY();
			}
			if(points[i].getPos().getZ() > maxZ){
				maxZ = points[i].getPos().getZ();
			}
			if(points[i].getPos().getX() < minX){
				minX = points[i].getPos().getX();
			}
			if(points[i].getPos().getY() < minY){
				minY = points[i].getPos().getY();
			}
			if(points[i].getPos().getZ() < minZ){
				minZ = points[i].getPos().getZ();
			}
		}

		float avgX = 0.1f*((maxX-minX)/2);
		float avgY = 0.1f*((maxY-minY)/2);
		float avgZ = 0.1f*((maxZ-minZ)/2);
		
		center = new Vector3f(avgX,avgY,avgZ);

		maxRadius = 0;
		float radius = 0;

		for(int i = 0; i < points.length; i++){
			double x = (0.1f*points[i].getPos().getX())-avgX;
			double y = (0.1f*points[i].getPos().getY())-avgY;
			double z = (0.1f*points[i].getPos().getZ())-avgZ;

			radius = (float) Math.sqrt((x*x)+(y*y)+(z*z));

			if(radius > maxRadius)
				maxRadius = radius;			
		}

		return maxRadius;

	}
	
	public Vector3f getCenter() {
		return center;
	}

	public void setCenter(Vector3f center) {
		this.center = center;
	}

	public float getMaxRadius() {
		return maxRadius;
	}


}
