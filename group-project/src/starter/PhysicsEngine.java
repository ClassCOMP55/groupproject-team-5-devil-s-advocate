package starter;
import acm.graphics.*;
import java.util.*;

public class PhysicsEngine {
	private Entity mainEntity; // Entity to be controlled with keyboard aka main character
	private ArrayList<Entity> winningSpace; // If player is in this region, the player wins and the game is over
	private boolean enableXDecel = false;
	private static final String JUMP = "in/jumpShort.mp3";
	private static final String STOMP = "in/stomp.mp3";
	private static final String GOOMBA = "in/goomba.mp3";
	public static final String MUSIC_FOLDER = "sound";
	public int windowWidth;
	private int jumpTime = 13; 					// Used to jump how long you are allowed to press the jump key
	private static int i = 0; 					// Used to track how long the jump key has been pressed
	
	/**
	 * ArrayLists to store objects that are movable and immovable
	 * movable - stores movable objects like enemies
	 * immovable - stores immovable objects like walls and platforms
	 */
	public ArrayList<Entity> movable = new ArrayList<Entity>();
	private ArrayList<Entity> immovable = new ArrayList<Entity>();
	
	public void playGoombaSound() {				//function to play the GOOMBA sound...
			AudioPlayer audio = AudioPlayer.getInstance();
			audio.playSound(MUSIC_FOLDER, GOOMBA);
	}

	public void playStompSound() {				//function to play the STOMP sound...
			AudioPlayer audio = AudioPlayer.getInstance();
			audio.playSound(MUSIC_FOLDER, STOMP);
	}

	/**
	 * Constructor
	 * @param mainEnt = Entity to be controlled with keyboard aka main character 
	 */
	PhysicsEngine(Entity mainEnt) {
		mainEntity = mainEnt;
	}
	
	public static int returnI() {
		return i;
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

	public void setWinningSpace(ArrayList<Entity> ent) {
		winningSpace = ent;
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
			if (e.dead) {
				e.setLocation(1000, 1000);
			} else {
				if (e.getX() < windowWidth) e.move(-e.xVel, e.yVel); //Remove the minus to have object go to the right	
			}

		}
		detectCollision();
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

		/**
		 * These if statements are used to stop left and right keys being pressed simultaneously
		 * Otherwise just moves according to keys pressed
		 */
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
			i++;
			if (i < jumpTime) {
				AudioPlayer audio = AudioPlayer.getInstance();
				audio.stopSound(MUSIC_FOLDER, JUMP);
				audio.playSound(MUSIC_FOLDER, JUMP);
				mainEntity.yVel = -12;
			}
		}

		if (mainEntity.yVel < mainEntity.yVelMax) {
			mainEntity.yVel++;
		}

		for (Entity e : movable) {
			if (e.yVel < e.yVelMax) {
				e.yVel++;
			}
		}
	}

	// Detects collision for movement blocking, enemies and 
	private void detectCollision() {
		for (Entity e : immovable) {
			// Collision between the left side of mainEntity and the right side of immovable objects
			if (getLeftHitbox(mainEntity).intersects(getRightHitbox(e))) {
				mainEntity.setLocation(e.getX() + e.getWidth() + 1, mainEntity.getY());
				mainEntity.xVel = 0;
			}

			// Collision between the right side of mainEntity and the left side of immovable objects
			if (getRightHitbox(mainEntity).intersects(getLeftHitbox(e))) {
				mainEntity.setLocation(e.getX() - mainEntity.getWidth() - 1, mainEntity.getY());
				mainEntity.xVel = 0;
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

			if (mainEntity.getX() < 0) {
				mainEntity.setLocation(0, mainEntity.getY());
			}

			// Logic for Enemies and immovable
			for (Entity m : movable) {
				// Collision between the left side of movable and the right side of immovable objects
				// Currently set up to change directions on collision with immovable objects
				if (getLeftHitbox(m).intersects(getRightHitbox(e))) {
					m.setLocation(e.getX() + e.getWidth() + 1, m.getY());
					m.xVel = -m.xVel;
					m.xDirection = "right";
				}

				// Collision between the right side of movable and the left side of immovable objects
				// Currently set up to change directions on collision with immovable objects
				if (getRightHitbox(m).intersects(getLeftHitbox(e))) {
					m.setLocation(e.getX() - m.getWidth() - 1, m.getY());
					m.xVel = -m.xVel;
					m.xDirection = "left";
				}

				// Collision between the bottom side of movable and the top side of immovable objects
				if (getBottomHitbox(m).intersects(getTopHitbox(e))) {
					m.yDirection = "stop";
					m.setLocation(m.getX(), e.getY() - m.getHeight() - 1);
				}

				if (getBottomHitbox(mainEntity).intersects(getTopHitbox(m))) {
					playStompSound();			//play Stomp sound when MArio kills Goomba...
					System.out.println("Kill current goomba");
					mainEntity.yVel = -mainEntity.yVel;
					m.dead = true;
				} else if (getHitbox(mainEntity).intersects(getHitbox(m))) {
					System.out.println("Mario dies");
					playGoombaSound();			//play Goomba kill effect...
					mainEntity.dead = true;
				}
			}
		}
	}

	// Checks whether mainEntity is colliding with winningSpace
	public Boolean won() {
		for (Entity e : winningSpace) {
			if (getHitbox(mainEntity).intersects(getHitbox(e))) return true;
		}
		return false;
	}

	// Used in conjunction with processCamera() in main
	public void moveHitboxes(double x, double y) {
		for (Entity e : immovable) {
			e.move(x, y);
		}
		for (Entity w : winningSpace) {
			w.move(x, y);
		}
	}

	// Used in conjunction with processCamera() in main
	public void moveEnemies(double x, double y) {
		for (Entity e : movable) {
			if (e.dead) {
				e.EntImage.move(x,y);
			}
			e.move(x, y);
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