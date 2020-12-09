package starter;

import java.util.ArrayList;
import java.awt.Color;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class WinScreen {
    private GImage background;
    private GImage Mario;
    private GLabel Mario_Win; 
    public GButton playAgainButton;
    public GButton quitButton;
    public ArrayList<GObject> objects = new ArrayList<GObject>();

    public WinScreen() {
        background = new GImage("background/WinScreen.png", 0, 0);
        Mario = new GImage("Mario/Mario_S_R.png", 365, 250);
        Mario.setSize(75.0,125.0);
        Mario_Win = new GLabel("You have passed the level!", 100, 100);
        Mario_Win.setFont("Arial-48");
        Mario_Win.setColor(Color.RED);
        playAgainButton = new GButton("Play Again!", 250, 375, 300, 75);
		playAgainButton.setFillColor(Color.RED);
		quitButton = new GButton("Quit", 250, 475, 300, 75);
		quitButton.setFillColor(Color.RED);
		objects.add(background);
	    objects.add(Mario_Win);
	    objects.add(Mario);
	    objects.add(playAgainButton);
	    objects.add(quitButton);
    }
}