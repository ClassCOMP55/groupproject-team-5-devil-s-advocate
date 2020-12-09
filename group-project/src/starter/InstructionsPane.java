package starter;
import java.awt.Color;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class InstructionsPane {
	private GImage background;
	private GImage ground;
	private GImage cloud1;
	private GImage cloud2;
	private GImage content;
	public GImage returnIcon;
	public GButton title;
	public GButton continueButton;
	private GParagraph para; // Part of starter code
	public ArrayList<GObject> objects = new ArrayList<GObject>();

	public InstructionsPane() {
		background = new GImage("Background/SolidBlueBackground.png", 0, 0);
		ground = new GImage("Background/Ground.png", 0,550);
		ground.setSize(800.0,75.0);
		cloud1 = new GImage("Background/Cloud.png",35,100);
		cloud1.setSize(125.0,75.0);
		cloud2 = new GImage("Background/Cloud.png",625,20);
		cloud2.setSize(125.0,75.0);
		content = new GImage("Background/Content.png",10,225);
		returnIcon = new GImage("Background/Return.png",5,5);
		returnIcon.setSize(45.0,45.0);
		title = new GButton("INSTRUCTIONS & CONTROLS",100,100,600,75);
		title.setFillColor(Color.RED);
		continueButton = new GButton("PRESS HERE TO CONTINUE\n",450,555,300,40);
		continueButton.setFillColor(Color.LIGHT_GRAY);

		para = new GParagraph("Back to Menu", 50, 35);//part of starter code
		para.setFont("Arial-24");//part of starter code

		objects.add(background);
		objects.add(ground);
		objects.add(cloud1);
		objects.add(cloud2);
		objects.add(content);
		objects.add(returnIcon);
		objects.add(title);
		objects.add(continueButton);
		objects.add(para);
	}
}
