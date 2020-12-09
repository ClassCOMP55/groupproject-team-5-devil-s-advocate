package starter;

import java.awt.Color;
import java.util.ArrayList;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class DeadScreen {
	private GImage DeadScreen;
	private GImage Mario_Dead_Rotate;
	private static final double object_WIDTH = MainApplication.WINDOW_WIDTH;
	private static final double object_HEIGHT = MainApplication.WINDOW_HEIGHT;
	private GLabel Mario_Dead; 
	public GButton playAgainButton;
	public GButton quitButton;
	public ArrayList<GObject> objects = new ArrayList<GObject>();
	
	public DeadScreen() {
		DeadScreen = new GImage("background/DeadScreen.png", 0, 0);
		Mario_Dead_Rotate = new GImage("Mario/Mario_Dead_Rotate.gif",0,225);
		Mario_Dead_Rotate.setSize(100.0,140.0);
		Mario_Dead_Rotate.move(object_HEIGHT-255, -50);

		Mario_Dead = new GLabel("Oh no! You just died!",175,100);
		Mario_Dead.setColor(Color.RED);
        Mario_Dead.setFont("Arial-48");
		
		playAgainButton = new GButton("Play Again!", 250, 375, 300, 75);
		playAgainButton.setFillColor(Color.RED);
		
		quitButton = new GButton("Exit", 250, 475, 300, 75);
		quitButton.setFillColor(Color.RED);
		
		objects.add(DeadScreen);
		objects.add(Mario_Dead_Rotate);
		objects.add(Mario_Dead);
		objects.add(playAgainButton);
		objects.add(quitButton);
	}
}