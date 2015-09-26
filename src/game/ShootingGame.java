package game;

/*
 * Brandon Lam
 * May 31, 2015
 * 
 * This is my first person shooting range game.
 * When players spawns they are given 6 clips of 12 bullets and their goal is to shoot 
 * as many monsters as they can with the given amount of bullets. (Maximum score is 216)
 * 
 * Hint: You can get double kills and triple kills if you line it up just right.
 * 
 * Controls:
 * WASD to move
 * LEFT and RIGHT arrow keys to look around
 * SPACE to shoot
 * R to reload
 * ESC to restart
 * F1 for instructions
 */

import components.Camera;
import components.FreeLook;
import components.FreeMove;
import core.BoundingSphere;
import core.Game;
import core.GameObject;
import core.Input;
import core.MP3;
import core.Point3f;
import core.Quaternion;
import core.Vector2f;
import core.Vector3f;
import rendering.Mesh;
import rendering.Vertex;
import rendering.Window;
import rendering.resourceManager.ResourceLoader;
import rendering.resourceManager.TextRenderer;

public class ShootingGame extends Game
{
	private Camera camera;
	private Mesh mesh;
	private Mesh mesh_range;
	private Mesh[] mesh_rogue;
	private GameObject mainCamera;
	private GameObject player;
	private GameObject[] rogue;
	private GameObject bulletsObject;
	private GameObject scoreObject;
	private GameObject clipObject;
	private GameObject text_GameOver;
	private GameObject text_Reload;
	private GameObject text_Instructions1;
	private GameObject text_Instructions2;
	private GameObject text_Instructions3;
	private GameObject text_Instructions4;
	private GameObject text_Instructions5;
	private GameObject text_Instructions6;
	private GameObject text_Instructions7;
	private GameObject text_Instructions8;
	private GameObject text_Instructions9;
	private GameObject text_Instructions10;
	private int bullets;
	private int score;
	private int clips;
	private float speed;
	private MP3 gunshot;
	private MP3 reload;
	private MP3 restart;


	public ShootingGame()
	{

	}

	public void init()
	{
		//Creates the main camera
		camera = new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 100);
		mainCamera = new GameObject().addComponent(camera);
		mainCamera.addComponent(new FreeMove(10));
		mainCamera.addComponent(new FreeLook());
		addObject(mainCamera);

		//Creates the player object (Made in blender)
		mesh = new Mesh("Arms05.obj");
		mesh.setTexture(ResourceLoader.loadTexture("Tex_0009_1.jpg"));
		player = new GameObject();
		player.addComponent(mesh);
		player.addComponent(new FreeMove(10));
		player.addComponent(new FreeLook());
		addObject(player);
		player.getTransform().setPos(new Vector3f(0,2.5f,-15));
		player.getTransform().setScale(new Vector3f(0.6f,0.6f,0.6f));

		//Sets main camera to the players initial position
		mainCamera.getTransform().setPos(player.getTransform().getPos().add(new Vector3f(0,0.5f,0)));

		//Creates the four wall and the floor
		int planeSize = 30;
		Vertex[] Plane_data = new Vertex[] { new Vertex(new Point3f(-planeSize,0,-planeSize), new Vector2f(0,0)),
				new Vertex(new Point3f(-planeSize,0,planeSize), new Vector2f(0,1)),
				new Vertex(new Point3f(planeSize,0,planeSize), new Vector2f(1,1)),
				new Vertex(new Point3f(planeSize,0,-planeSize), new Vector2f(1,0))
		};

		int[] Plane_indices = new int[] {0,1,2,
				0,2,3};

		Mesh Plane_mesh = new Mesh(ResourceLoader.loadTexture("trak_panel.jpg"));
		Plane_mesh.addVertices(Plane_data, Plane_indices);
		addObject(new GameObject().addComponent(Plane_mesh));

		Vertex[] wall1_data = new Vertex[] { new Vertex(new Point3f(0,-planeSize,-planeSize), new Vector2f(0,0)),
				new Vertex(new Point3f(0,-planeSize,planeSize), new Vector2f(0,1)),
				new Vertex(new Point3f(0,planeSize,planeSize), new Vector2f(1,1)),
				new Vertex(new Point3f(0,planeSize,-planeSize), new Vector2f(1,0))
		};

		int[] wall1_indices = new int[] {0,1,2,
				0,2,3};

		Mesh wall1_mesh = new Mesh(ResourceLoader.loadTexture("mural2.bmp"));
		wall1_mesh.addVertices(wall1_data, wall1_indices);
		addObject(new GameObject().addComponent(wall1_mesh));
		wall1_mesh.getTransform().setPos(new Vector3f(planeSize,planeSize,0));

		Vertex[] wall2_data = new Vertex[] { new Vertex(new Point3f(-planeSize,-planeSize,0), new Vector2f(0,0)),
				new Vertex(new Point3f(-planeSize,planeSize,0), new Vector2f(0,1)),
				new Vertex(new Point3f(planeSize,planeSize,0), new Vector2f(1,1)),
				new Vertex(new Point3f(planeSize,-planeSize,0), new Vector2f(1,0))
		};

		int[] wall2_indices = new int[] {0,1,2,
				0,2,3};

		Mesh wall2_mesh = new Mesh(ResourceLoader.loadTexture("mural2.bmp"));
		wall2_mesh.addVertices(wall2_data, wall2_indices);
		addObject(new GameObject().addComponent(wall2_mesh));
		wall2_mesh.getTransform().setPos(new Vector3f(0,planeSize,planeSize));

		Vertex[] wall3_data = new Vertex[] { new Vertex(new Point3f(0,-planeSize,-planeSize), new Vector2f(0,0)),
				new Vertex(new Point3f(0,-planeSize,planeSize), new Vector2f(0,1)),
				new Vertex(new Point3f(0,planeSize,planeSize), new Vector2f(1,1)),
				new Vertex(new Point3f(0,planeSize,-planeSize), new Vector2f(1,0))
		};

		int[] wall3_indices = new int[] {0,1,2,
				0,2,3};

		Mesh wall3_mesh = new Mesh(ResourceLoader.loadTexture("mural2.bmp"));
		wall3_mesh.addVertices(wall3_data, wall3_indices);
		addObject(new GameObject().addComponent(wall3_mesh));
		wall3_mesh.getTransform().setRot(new Quaternion(0,0,0,1));
		wall3_mesh.getTransform().setPos(new Vector3f(-planeSize,planeSize,0));

		Vertex[] wall4_data = new Vertex[] { new Vertex(new Point3f(-planeSize,-planeSize,0), new Vector2f(0,0)),
				new Vertex(new Point3f(-planeSize,planeSize,0), new Vector2f(0,1)),
				new Vertex(new Point3f(planeSize,planeSize,0), new Vector2f(1,1)),
				new Vertex(new Point3f(planeSize,-planeSize,0), new Vector2f(1,0))
		};

		int[] wall4_indices = new int[] {0,1,2,
				0,2,3};

		Mesh wall4_mesh = new Mesh(ResourceLoader.loadTexture("mural2.bmp"));
		wall4_mesh.addVertices(wall4_data, wall4_indices);
		addObject(new GameObject().addComponent(wall4_mesh));
		wall4_mesh.getTransform().setRot(new Quaternion(0,0,1,0));
		wall4_mesh.getTransform().setPos(new Vector3f(0,planeSize,-planeSize));

		//Creates the shooting range window (Made in blender)
		mesh_range = new Mesh("range.obj");
		mesh_range.setTexture(ResourceLoader.loadTexture("trak_panel.jpg"));
		GameObject range = new GameObject();
		range.addComponent(mesh_range);
		addObject(range);
		range.getTransform().setScale(new Vector3f(4,2,4));
		range.getTransform().setPos(new Vector3f(0,0,-15));

		//Loads the sound effects
		gunshot = new MP3("./res/audio/M4A1_Single.mp3");
		reload = new MP3("./res/audio/reload.mp3");
		restart = new MP3("./res/audio/1-up.mp3");

		//Creates the enemies/shooting targets
		mesh_rogue = new Mesh[3];
		rogue = new GameObject[3];
		for(int i = 0; i < rogue.length; i++){
			mesh_rogue[i] = new BoundingSphere("brawler_armoured.obj");
			mesh_rogue[i].setTexture(ResourceLoader.loadTexture("brawler_v2_colour.jpg"));
			rogue[i] = new GameObject();
			rogue[i].addComponent(mesh_rogue[i]);
			addObject(rogue[i]);
			rogue[i].getTransform().setScale(new Vector3f(0.1f,0.1f,0.1f));
			rogue[i].getTransform().setRot(new Quaternion(0,0,1,0));
			rogue[i].getTransform().setPos(new Vector3f((-50 + (int)(Math.random() * ((50 - (-50)) + 1))),0,(-5 + (int)(Math.random() * ((25 - (-5)) + 1)))));
		}

		//Creates the static HUD elements
		bullets = 12;
		mainCamera.addChild(new TextRenderer(new Vector2f(-420, 290), 16, camera).getString("AMMOX")); //X represents a colon

		score = 0;
		mainCamera.addChild(new TextRenderer(new Vector2f(300, 290), 16, camera).getString("SCOREX"));

		clips = 5;
		mainCamera.addChild(new TextRenderer(new Vector2f(-420, 260), 16, camera).getString("CLIPSZLEFTX"));

		text_Instructions9 = new TextRenderer(new Vector2f(-420, -290), 12, camera).getString("HOLDZFZZFORZINSTRUCTIONS"); //Z represents a space
		text_Instructions10 = new TextRenderer(new Vector2f(-355, -290), 12, camera).getNumber(1);
		mainCamera.addChild(text_Instructions9);
		mainCamera.addChild(text_Instructions10);

		//Creates the dynamic HUD elements
		updateStats();
		
		//Initializes the speed of the enemies
		speed = 0.3f;

	}

	public void input(float delta){
		super.input(delta);
		
		//Shoots a bullet/ray
		if(Input.getKeyDown(Input.KEY_SPACE) && bullets != 0 && clips >= 0){
			gunshot.play();	
			bullets--;

			//Checks if the ray intersects with an enemy
			for(int i = 0; i < rogue.length; i++){
				if(rayIntersect(rogue[i], mesh_rogue[i]) == true){
					speed = speed + 0.005f;
					score++;
				}
			}

			updateStats();

			//Tells player to reload
			if(bullets == 0 && clips != 0){
			text_Reload = new TextRenderer(new Vector2f(-55, 0), 24, camera).getString("RELOAD");
			mainCamera.addChild(text_Reload);
			}
		
			//If the player is out of bullets, the game is over
			if(clips == 0 && bullets == 0){
				speed = 0;
				text_GameOver = new TextRenderer(new Vector2f(-75, 0), 20, camera).getString("GAMEZOVER");
				mainCamera.addChild(text_GameOver);

			}
		}
		
		//Reloads the gun
		if(Input.getKeyDown(Input.KEY_R) && bullets != 12 && clips != 0){
			reload.play();
			bullets = 12;
			clips--;
			mainCamera.removeChild(text_Reload);
			updateStats();

		}

		//Restarts the game
		if(Input.getKey((Input.KEY_ESCAPE))){
			restart.play();
			speed = 0.3f;
			
			mainCamera.removeChild(text_GameOver);

			score = 0;
			bullets = 12;
			clips = 5;

			updateStats();

			player.getTransform().setPos(new Vector3f(0,2.5f,-15));
			mainCamera.getTransform().setPos(player.getTransform().getPos().add(new Vector3f(0,0.5f,0)));
			player.getTransform().lookAt(new Vector3f(0,2.5f,1), new Vector3f(0,1,0));
			mainCamera.getTransform().setRot(player.getTransform().getRot());
		}
		
		//Displays the instructions and controls for the game
		if(Input.getKeyDown(Input.KEY_F1)){
			text_Instructions1 = new TextRenderer(new Vector2f(-420, -185), 12, camera).getString("INSTRUCTIONSXSHOOTZASZMANYZENEMIESZASZYOUZCAN"); //Z represents a space and X represents a colon
			text_Instructions2 = new TextRenderer(new Vector2f(-420, -200), 12, camera).getString("HINTXYOUZCANZGETZDOUBLEZANDZTRIPLEZKILLS");
			text_Instructions3 = new TextRenderer(new Vector2f(-420, -215), 12, camera).getString("CONTROLSX");
			text_Instructions4 = new TextRenderer(new Vector2f(-420, -230), 12, camera).getString("WASDZTOZMOVE");
			text_Instructions5 = new TextRenderer(new Vector2f(-420, -245), 12, camera).getString("LEFTZANDZRIGHTZARROWSZTOZLOOKZAROUND");
			text_Instructions6 = new TextRenderer(new Vector2f(-420, -260), 12, camera).getString("SPACEZTOZSHOOT");
			text_Instructions7 = new TextRenderer(new Vector2f(-420, -275), 12, camera).getString("RZTOZRELOAD");
			text_Instructions8 = new TextRenderer(new Vector2f(-420, -290), 12, camera).getString("ESCZTOZRESTART");

			mainCamera.addChild(text_Instructions1);
			mainCamera.addChild(text_Instructions2);
			mainCamera.addChild(text_Instructions3);
			mainCamera.addChild(text_Instructions4);
			mainCamera.addChild(text_Instructions5);
			mainCamera.addChild(text_Instructions6);
			mainCamera.addChild(text_Instructions7);
			mainCamera.addChild(text_Instructions8);

			mainCamera.removeChild(text_Instructions9);
			mainCamera.removeChild(text_Instructions10);
		}
		
		//Hides the instructions and controls for the game
		if(Input.getKeyUp(Input.KEY_F1)){
			text_Instructions9 = new TextRenderer(new Vector2f(-420, -290), 12, camera).getString("HOLDZFZZFORZINSTRUCTIONS");
			text_Instructions10 = new TextRenderer(new Vector2f(-355, -290), 12, camera).getNumber(1);
			
			mainCamera.addChild(text_Instructions9);
			mainCamera.addChild(text_Instructions10);

			mainCamera.removeChild(text_Instructions1);
			mainCamera.removeChild(text_Instructions2);
			mainCamera.removeChild(text_Instructions3);
			mainCamera.removeChild(text_Instructions4);
			mainCamera.removeChild(text_Instructions5);
			mainCamera.removeChild(text_Instructions6);
			mainCamera.removeChild(text_Instructions7);
			mainCamera.removeChild(text_Instructions8);
		}

	}


	public void update(float delta)
	{
		//Enemy movement from left to right
		for(int i = 0; i < rogue.length/2; i++){
			if(rogue[i].getTransform().getPos().getX() >= 30){
				rogue[i].getTransform().setPos(new Vector3f((-50 + (int)(Math.random() * ((-25 - (-50)) + 1))),rogue[i].getTransform().getPos().getY(),(-5 + (int)(Math.random() * ((25 - (-5)) + 1)))));
			}
			rogue[i].getTransform().setPos(rogue[i].getTransform().getPos().add(new Vector3f(speed,0,0)));
		}
		//Enemy movement from right to left
		for(int i = rogue.length/2; i < rogue.length; i++){
			if(rogue[i].getTransform().getPos().getX() <= -30){
				rogue[i].getTransform().setPos(new Vector3f((25 + (int)(Math.random() * ((50 - (25)) + 1))),rogue[i].getTransform().getPos().getY(),(-5 + (int)(Math.random() * ((25 - (-5)) + 1)))));
			}
			rogue[i].getTransform().setPos(rogue[i].getTransform().getPos().add(new Vector3f(-speed,0,0)));
		}
	}

	
	public boolean rayIntersect(GameObject enemy, Mesh mesh_enemy){

//		float radius = ((BoundingSphere)mesh_rogue[0]).getMaxRadius();
		float radius = 2.7f;
		
		Vector3f centerMesh = ((BoundingSphere)mesh_enemy).getCenter(); //Finds the center of the enemy relative to the mesh
		Vector3f center = enemy.getTransform().getPos().add(new Vector3f(0,centerMesh.getY(),0)); //Finds the center of enemy relative to the environment
		Vector3f m = player.getTransform().getPos().sub((center)); //Calculates the vector between the players position to the center of the enemy object
		Vector3f d = (mainCamera.getTransform().getTransformedRot()).toRotationMatrix().transform(new Vector3f(0,0,1));	//Calculates the direction vector of the players looking direction

		//For testing
		//System.out.println(radius);
		//System.out.println(centerMesh.toString());
		//System.out.println(d.toString());

		//Based on the equation, t^2 + 2(m•d)t + (m•m)-r^2 for ray-sphere collisions
		//If the discriminant = 0, one intersection
		//If the discriminant >= 0, two intersections
		//If the discriminant < 0, no intersection

		float a = 1;
		float b = 2*(m.dot(d));
		float c = (m.dot(m))-(radius*radius);

		if(((b*b)-(4*a*c)) >= 0){
			//System.out.println((b*b)-(4*a*c));
			enemy.getTransform().setPos(new Vector3f((30 + (int)(Math.random() * ((60 - (30)) + 1))),enemy.getTransform().getPos().getY(),(-5 + (int)(Math.random() * ((25 - (-5)) + 1)))));
			return true;
		}

		//System.out.println((b*b)-(4*a*c));

		return false;
	}

	//Updates variable HUD elements
	public void updateStats(){
		mainCamera.removeChild(scoreObject);
		scoreObject = new TextRenderer(new Vector2f(390, 290), 16, camera).getNumber(score);
		mainCamera.addChild(scoreObject);

		mainCamera.removeChild(bulletsObject);
		bulletsObject = new TextRenderer(new Vector2f(-345, 290), 16, camera).getNumber(bullets);
		mainCamera.addChild(bulletsObject);

		mainCamera.removeChild(clipObject);
		clipObject = new TextRenderer(new Vector2f(-260, 260), 16, camera).getNumber(clips);
		mainCamera.addChild(clipObject);
	}
}
