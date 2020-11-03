package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class DeadScreen extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
	//we will change these images									// all of the GraphicsProgram calls
	private GImage DeadScreen;
	
	private GLabel Mario_Dead; 
	
	private GButton playAgainButton;// keep buttons
	private GButton quitButton;


	
	public DeadScreen(MainApplication app) {
		super();
		program = app;
		//change images
		
		DeadScreen = new GImage("background/DeadScreen.png", 0, 0);
		

		
		playAgainButton = new GButton("Play Again", 250, 325, 300, 75);
		playAgainButton.setFillColor(Color.RED);
		
		quitButton = new GButton("Quit", 250, 425, 300, 75);
		quitButton.setFillColor(Color.RED);
		
	}
		@Override
		public void showContents() {
			// TODO Auto-generated method stub
			//Change images 
			program.add(DeadScreen);
			
			
			program.add(playAgainButton);
			program.add(quitButton);
		}
		@Override
		public void hideContents() {
			// TODO Auto-generated method stub
			

			program.remove(playAgainButton);
			program.remove(quitButton);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			GObject obj = program.getElementAt(e.getX(), e.getY());
			if (obj == quitButton) {
				program.switchToInstructions();
			}
		}
	}
