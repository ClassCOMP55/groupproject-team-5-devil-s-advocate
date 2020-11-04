package starter;
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class InstructionsPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	
	private GImage background;
	private GImage ground;
	private GImage cloud1;
	private GImage cloud2;
	private GImage content;
	private GImage returnIcon;
	
	private GButton title;
	private GButton continueButton;

	
	private GParagraph para;//part of starter code

	public InstructionsPane(MainApplication app) {
		this.program = app;
		
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
		continueButton = new GButton("Press here to continue\n",450,555,300,40);
		continueButton.setFillColor(Color.RED);
		

		para = new GParagraph("Back to Menu", 50, 35);//part of starter code
		para.setFont("Arial-24");//part of starter code
	}

	@Override
	public void showContents() {
		program.add(background);

		program.add(ground);
		program.add(cloud1);
		program.add(cloud2);
		program.add(content);
		program.add(returnIcon);

		program.add(title);
		program.add(continueButton);

		program.add(para);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(ground);
		program.remove(cloud1);
		program.remove(cloud2);
		program.remove(content);
		program.remove(returnIcon);

		program.remove(title);
		program.remove(continueButton);

		program.remove(para);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//para.setText("you need\nto click\non the eyes\nto go back");
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == returnIcon) {
			program.switchToMenu();
			program.stopRandomSound(); 		//stop the ongoing sound...
		}
		if(obj == continueButton) {
			program.switchToDead();
			program.stopRandomSound();     //stop the ongoing sound...
		}
		
	}
}
