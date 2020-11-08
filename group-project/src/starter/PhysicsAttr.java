package starter;

public class PhysicsAttr {
	/**
	 * There should be three main types of Entities in our game: 
	 *  - main character 
	 *  - enemies that can move
	 *  - immovable objects such as the floor and obstacles such as pipes. 
	 *  This comment will explain the functions of each variable and how each variable should
	 *  be initialized for each type of Entity.
	 *  
	 *  double xVel, yVel
	 *  These variables will be used to track the X velocity and the Y velocity of its parent
	 *  object.
	 *  ==
	 *  Main Character: Initialize to 0
	 *  Enemies + other movable Entities: Initialize to 0
	 *  Immovable Entities: Do not initialize
	 *  
	 *  double xVelMax, yVelMax
	 *  These variables will be used to limit the X and Y velocities of its parent object.
	 *  ==
	 *  Main Character: Initialize to 0 or any positive number
	 *  Enemies + other movable Entities: Initialize to any positive number past 0
	 *  Immovable Entities: Do not initialize
	 *  
	 *  String xDirection, yDirection
	 *  These variables will be used to tell the PhysicsEngine the direction to move the 
	 *  Entity. These variables DO NOT reflect which direction the Entity is traveling.
	 *  ==
	 *  Main Character: Initialize to empty string (xDirection = "")
	 *  Enemies + other movable Entities: Initialize to (xDirection = "")
	 *  Immovable Entities: Do not initialize
	 *  
	 *  boolean immovable
	 *  This variable will be used to tell PhysicsEngine whether the object is Immovable or
	 *  not. Immovable objects will be skipped in the movement loop.
	 *  ==
	 *  Main Character: Initialize to false
	 *  Enemies + other movable Entities: Initialize to false
	 *  Immovable Entities: Initialize to true
	 *  
	 *  boolean hitTop, hitBottom, hitLeft, hitRight
	 *  These variables will be used to track the statuses for the hitboxes on the four sides
	 *  of an Entity.
	 *  ==
	 *  Main Character: Initialize to false
	 *  Enemies + other movable Entities: Initialize to false
	 *  Immovable Entities: Initialize to false
	 */
	
	public double xVel, yVel;
    public double xVelMax, yVelMax;
    public String xDirection, yDirection;
    public boolean immovable;
    public boolean hitTop, hitBottom, hitLeft, hitRight;
}

