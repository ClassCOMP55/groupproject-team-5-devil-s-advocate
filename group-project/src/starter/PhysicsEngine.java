//package starter;
//import acm.graphics.*;
//import java.util.*;
//
//public class PhysicsEngine {
//	private entity mainObject; // Index to the main object, which will be controlled by keyboard input
//	//private ArrayList<GObject> objects; // Will be changed to Moveable class later, currently GObject for testing.
//	private boolean enableXDecel = false;
//	
//	private ArrayList<entity> entities = new ArrayList<entity>(); // testing only
//	
//	physicsEngine(entity mainObj) {
//		mainObject = mainObj;
//	}
//	
//	public void addEntity(entity entity) {
//		entities.add(entity);
//	}
//
//	public void removeEntity(entity entity) {
//		entities.remove(entity);
//	}
//	
//	/**
//	 * The update() function is where all the physics and movements will be calculated. This function must
//	 * be called by a game loop or a timer, which update the calculations every * seconds. The physics
//	 * engine is also responsible for the movement of the Movable class.
//	 */	
//	public void update(Boolean[] keysPressed) {
//		processKeys(keysPressed);
//		calculateXVelocity();
//		calculateGravity();
//		
//		mainObject.object.move(mainObject.attr.xVel, mainObject.attr.yVel);
//		for (entity e : entities) {
//			e.object.move(e.attr.xVel, e.attr.yVel);
//		}
//	}
//	
//	private void processKeys(Boolean[] keysPressed) {
//		boolean w = keysPressed[0];
//		boolean a = keysPressed[1];
//		boolean s = keysPressed[2];
//		boolean d = keysPressed[3];
//		
//		// These if statements are used to stop left and right keys being pressed simultaneously
//		if (a == true) {
//			moveLeft();
//		} else if (d == true) {
//			moveRight();
//		} else {
//			moveStop();
//		}
//		if (a == true && d == true) {
//			moveStop();
//		}
//		if (w == true) {
//			moveJump();
//		}
//	}
//	
//	// This function calculates the horizontal acceleration and deceleration of the controlled character
//	private void calculateXVelocity() {
//		// This part calculates the acceleration and deceleration of the controlled character
//		if (mainObject.attr.xDirection == "left") {
//			if (mainObject.attr.xVel > -mainObject.attr.xVelMax) mainObject.attr.xVel--;
//		} else if (mainObject.attr.xDirection == "right" ) {
//			if (mainObject.attr.xVel < mainObject.attr.xVelMax) mainObject.attr.xVel++;
//		} else if (enableXDecel) {
//			mainObject.attr.xVel *= 0.85;
//		}
//		
//		if (mainObject.attr.xVel < 0.9 && mainObject.attr.xVel > 0.1 || mainObject.attr.xVel < -0.1 && mainObject.attr.xVel > -0.9) {
//			mainObject.attr.xVel = 0;
//			enableXDecel = false;
//		}
//	}
//	
//	private void calculateGravity() {
//		
//	}
//	
//	private Boolean[] detectCollision() {
//		// Four sides of a hit-BOX
//		boolean top = true;
//		boolean bottom = true;
//		boolean left = true;
//		boolean right = true;
//		
//		return null;
//	}
//	
//	public void moveLeft() {
//		mainObject.attr.xDirection = "left";
//		enableXDecel = true;
//	}
//	
//	public void moveRight() {
//		mainObject.attr.xDirection = "right";
//		enableXDecel = true;
//	}
//	
//	public void moveStop() {
//		mainObject.attr.xDirection = "stop";
//	}
//	
//	public void moveJump() {
//		
//	}
//}