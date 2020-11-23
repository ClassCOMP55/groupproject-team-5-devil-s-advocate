package starter;
import acm.graphics.*;
import java.util.*;

public class PhysicsEngine {
	private Entity mainEntity; // Entity to be controlled with keyboard aka main character
	private boolean enableXDecel = false;
	private int jumpTime = 15; // Used to jump how long you are allowed to press the jump key
	private int i = 0; // Used to track how long the jump key has been pressed
	
	// TODO Create an Entity/GRect/GRectangle for winningSpace, to see if player has passed the level
	
	/**
	 * ArrayLists to store objects that are movable and immovable
	 * movable - stores movable objects like enemies
	 * immovable - stores immovable objects like walls and platforms
	 */
	private ArrayList<Entity> movable = new ArrayList<Entity>();
	private ArrayList<Entity> immovable = new ArrayList<Entity>();
	
	/**
	 * Constructor
	 * @param mainEnt = Entity to be controlled with keyboard aka main character 
	 */
	PhysicsEngine(Entity mainEnt) {
		mainEntity = mainEnt;
	}
	
	public void addMovable(Entity... ent) {
		for (Entity e : ent) {
			movable.add(e);
		}
	}
	public void addImmovable(Entity... ent) {
		for (Entity e : ent) {
			immovable.add(e);
		}
	}
	public void removeMovable(Entity Entity) {
		movable.remove(Entity);
	}
	public void removeImmovable(Entity Entity) {
		immovable.remove(Entity);
	}
	
	/**
	 * The update() function is where all the physics and movements will be calculated. This function must
	 * be called by a game loop or a timer, which update the calculations every * seconds. The physics
	 * engine is also responsible for the movement.
	 */	
	public void update(Boolean[] keysPressed) {
		processKeys(keysPressed);
		calculateXVelocity();
		calculateGravity();
		detectCollision();
		mainEntity.move(mainEntity.xVel, mainEntity.yVel);
		for (Entity e : movable) {
			e.move(e.xVel, e.yVel);
		}
		detectCollision();
		System.out.println(mainEntity.yVel);

	}
	
	/**
	 * Processes keys to determine which keys are pressed on the keyboard
	 * @param keysPressed - pass an array of Boolean values of which keys are pressed
	 * [0] = W, [1] = A, [2] = S, [3] = D
	 */
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
		if (mainEntity.xDirection == "left") {
			if (mainEntity.xVel > -mainEntity.xVelMax) mainEntity.xVel--;
		} else if (mainEntity.xDirection == "right" ) {
			if (mainEntity.xVel < mainEntity.xVelMax) mainEntity.xVel++;
		} else if (enableXDecel) {
			mainEntity.xVel *= 0.85;
		}
		if (mainEntity.xVel < 0.9 && mainEntity.xVel > 0.1 || mainEntity.xVel < -0.1 && mainEntity.xVel > -0.9) {
			mainEntity.xVel = 0;
			enableXDecel = false;
		}
	}

	// Calculates gravity and jumping
	private void calculateGravity() {
		if (mainEntity.yDirection == "jump") {
			System.out.println("Jump");
			i++;
			if (i < jumpTime) {
				mainEntity.yVel = -12;
			}
		}
		
		if (mainEntity.yVel < mainEntity.yVelMax) {
			mainEntity.yVel++;
		}

	}
	
	// Detects collision for movement blocking, enemies and 
	private void detectCollision() {
		for (Entity e : immovable) {
			// Collision between the left side of mainEntity and the right side of immovable objects
			if (getLeftHitbox(mainEntity).intersects(getRightHitbox(e))) {
				mainEntity.setLocation(e.getX() + e.getWidth() + 1, mainEntity.getY());
			}
			
			// Collision between the right side of mainEntity and the left side of immovable objects
			if (getRightHitbox(mainEntity).intersects(getLeftHitbox(e))) {
				mainEntity.setLocation(e.getX() - mainEntity.getWidth() - 1, mainEntity.getY());
			}
			
			// Collision between the bottom side of mainEntity and the top side of immovable objects
			if (getBottomHitbox(mainEntity).intersects(getTopHitbox(e))) {
				mainEntity.yDirection = "stop";
				i = 0;
				mainEntity.setLocation(mainEntity.getX(), e.getY() - mainEntity.getHeight() - 1);
			}
			
			// Collision between the top side of mainEntity and the bottom side of immovable objects
			if (getTopHitbox(mainEntity).intersects(getBottomHitbox(e))) {
				i = 15;
				mainEntity.setLocation(mainEntity.getX(), e.getY() + e.getHeight() + 1);
				mainEntity.yVel = 0;
			}
			
			// TODO Add logic for collision detection for enemies and immovable, enemies and mainEntity
			// 		and mainEntity and winningSpace
		}	
	}
	
	// Used to set movement directions for the mainEntity
	public void moveLeft() {
		mainEntity.lastDirection = "left";
		mainEntity.xDirection = "left";
		enableXDecel = true;
	}
	public void moveRight() {
		mainEntity.lastDirection = "right";
		mainEntity.xDirection = "right";
		enableXDecel = true;
	}
	public void moveStop() {
		mainEntity.xDirection = "stop";
	}
	public void moveJump() {
		mainEntity.yDirection = "jump";
	}
	public void moveJumpStop() {
		mainEntity.yDirection = "stop";
		i = jumpTime;
	}
	
	// Returns the hitboxes of the Entity passed to it
	public GRectangle getHitbox(Entity ent) {
		return new GRectangle(ent.getX(), ent.getY(), ent.getWidth(), ent.getHeight());
	}
	public GRectangle getTopHitbox(Entity ent) {
		return new GRectangle(ent.getX(), ent.getY(), ent.getWidth(), 5);
	}
	public GRectangle getBottomHitbox(Entity ent) {
		return new GRectangle(ent.getX(), ent.getY() + ent.getHeight() - 5, ent.getWidth(), 5);
	}
	public GRectangle getLeftHitbox(Entity ent) {
		return new GRectangle(ent.getX(), ent.getY(), 5, ent.getHeight());
	}
	public GRectangle getRightHitbox(Entity ent) {
		return new GRectangle(ent.getX() + ent.getWidth() - 5, ent.getY(), 5, ent.getHeight());
	}
}