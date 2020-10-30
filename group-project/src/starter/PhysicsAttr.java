package starter;
import acm.graphics.*;

public class PhysicsAttr {
    public double xVel, yVel; // Velocities for X and Y axes
    public double xVelMax, yVelMax; // Velocity limits for the X and Y axes
    public double xAccel, xDecel, yAccel, yDecel; // Acceleration and deceleration for x and y axes, can be used to implement gravity
    public String xDirection, yDirection; // Direction in which the character is traveling. Used by the physicsengine to decide which way to move the character.
    public GObject associatedObject; // A pointer to the object that the attributes are associated with.
    public boolean constLinearMovement; // Constant linear movement, for moving enemies like goombas at a constant speed.
}

