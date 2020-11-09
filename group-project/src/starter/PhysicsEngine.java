package starter;
import acm.graphics.*;
import java.awt.Rectangle;
import java.util.*;
public class PhysicsEngine {
	private entity mainEntity; // Entity to be controlled with keyboard aka main character
	private boolean enableXDecel = false;
	int jumpTime = 15; // Used to jump how long you are allowed to press the jump key
	
	// TODO Replace entity with Entity class and redo needed stuff
	private ArrayList<entity> movable = new ArrayList<entity>(); // data type "entity" testing only
	private ArrayList<entity> immovable = new ArrayList<entity>(); // data type "entity" testing only
	

	/**
	 * Constructor
	 * @param mainEnt = Entity to be controlled with keyboard aka main character 
	 */
	physTest(entity mainEnt) {
		mainEntity = mainEnt;
	}
	
	public void addMovable(entity... ent) {
		for (entity e : ent) {
			movable.add(e);
		}
	}
	
	public void addImmovable(entity... ent) {
		for (entity e : ent) {
			immovable.add(e);
		}
	}

	public void removeMovable(entity entity) {
		movable.remove(entity);
	}
	
	public void removeImmovable(entity entity) {
		immovable.remove(entity);
	}
	/**
	 * The update() function is where all the physics and movements will be calculated. This function must
	 * be called by a game loop or a timer, which update the calculations every * seconds. The physics
	 * engine is also responsible for the movement of the Movable class.
	 */	
	public void update(Boolean[] keysPressed) {
		processKeys(keysPressed);
		calculateXVelocity();
		calculateGravity();
		mainEntity.object.move(mainEntity.attr.xVel, mainEntity.attr.yVel);
		detectCollision();
		for (entity e : movable) {
			e.object.move(e.attr.xVel, e.attr.yVel);
		}

	}
	
	private void processKeys(Boolean[] keysPressed) {
		boolean w = keysPressed[0];
		boolean a = keysPressed[1];
		boolean s = keysPressed[2];
		boolean d = keysPressed[3];
		
		// These if statements are used to stop left and right keys being pressed simultaneously
		if (a == true) {
			moveLeft();
		} else if (d == true) {
			moveRight();
		} else {
			moveStop();
		}
		if (a == true && d == true) {
			moveStop();
		}
		if (w == true) {
			moveJump();
		} else {
			moveJumpStop();
		}
	}
	
	// This function calculates the horizontal acceleration and deceleration of the controlled character
	private void calculateXVelocity() {
		if (mainEntity.attr.xDirection == "left") {
			if (mainEntity.attr.xVel > -mainEntity.attr.xVelMax) mainEntity.attr.xVel--;
		} else if (mainEntity.attr.xDirection == "right" ) {
			if (mainEntity.attr.xVel < mainEntity.attr.xVelMax) mainEntity.attr.xVel++;
		} else if (enableXDecel) {
			mainEntity.attr.xVel *= 0.85;
		}
		if (mainEntity.attr.xVel < 0.9 && mainEntity.attr.xVel > 0.1 || mainEntity.attr.xVel < -0.1 && mainEntity.attr.xVel > -0.9) {
			mainEntity.attr.xVel = 0;
			enableXDecel = false;
		}
	}
	
	int i = 0;
	
	private void calculateGravity() {
		if (mainEntity.attr.yDirection == "jump") {
			i++;
			if (i < jumpTime) {
				mainEntity.attr.yVel = -12;
			}
		}
		
		if (mainEntity.attr.enableGravity) {
			if (mainEntity.attr.yVel < mainEntity.attr.yVelMax) {
				mainEntity.attr.yVel++;
			}
		} else {
			mainEntity.attr.yVel = 0;
		}
	}
	
	private void detectCollision() {
		for (entity e : immovable) {
			// Interaction between main and immovable
			if (getLeftHitbox(mainEntity).intersects(getHitbox(e))) {
				mainEntity.object.setLocation(e.object.getX() + e.object.getWidth() + 1, mainEntity.object.getY());
				System.out.println("Left contact");
			}
			
			if (getRightHitbox(mainEntity).intersects(getHitbox(e))) {
				mainEntity.object.setLocation(e.object.getX() - e.object.getWidth() - 1, mainEntity.object.getY());
				System.out.println("Right contact");
			}
			
			if (getBottomHitbox(mainEntity).intersects(getHitbox(e))) {
				mainEntity.attr.enableGravity = false;
				mainEntity.attr.yDirection = "stop";
				mainEntity.object.setLocation(mainEntity.object.getX(), e.object.getY() - mainEntity.object.getHeight() - 1);
				i = 0;
				System.out.println("Bottom contact");
			}
			
			// Interaction between movable and immovable
			
		}	
	}
	
	public void moveLeft() {
		mainEntity.attr.xDirection = "left";
		enableXDecel = true;
	}
	
	public void moveRight() {
		mainEntity.attr.xDirection = "right";
		enableXDecel = true;
	}
	
	public void moveStop() {
		mainEntity.attr.xDirection = "stop";
	}
	
	public void moveJump() {
		mainEntity.attr.yDirection = "jump";
	}
	
	public void moveJumpStop() {
		mainEntity.attr.yDirection = "stop";
		i = jumpTime;
	}
	
	//Rectangle(int x, int y, int width, int height)
	
	public Rectangle getHitbox(entity ent) {
		GObject obj = ent.object;
		return new Rectangle((int)obj.getX(), (int)obj.getY(), (int)obj.getWidth(), (int)obj.getHeight());
	}
	
	public Rectangle getTopHitbox(entity ent) {
		GObject obj = ent.object;
		return new Rectangle((int)obj.getX() + 2, (int)obj.getY(), (int)obj.getWidth() - 4, 2);
	}
	
	public Rectangle getBottomHitbox(entity ent) {
		GObject obj = ent.object;
		return new Rectangle((int)obj.getX() + 2, (int)obj.getY() + (int)obj.getHeight() + 2, (int)obj.getWidth() - 4, 2);
	}
	
	// WHY THE FUCK IS THE LEFT HITBOX TOUCHING THE GROUND, THIS ABSOLUTELY MAKES NO SENSE
	public Rectangle getLeftHitbox(entity ent) {
		GObject obj = ent.object;
		return new Rectangle((int)obj.getX(), (int)obj.getY() + 7, 2, (int)obj.getHeight() - 14); // height default 4
	}
	
	public Rectangle getRightHitbox(entity ent) {
		GObject obj = ent.object;
		return new Rectangle((int)obj.getX() + (int)obj.getWidth() - 2, (int)obj.getY() + 7, 2, (int)obj.getHeight() - 14);
	}
	
	public GRect debugReturnHitBoxes() {
		
		return null;
	}
}