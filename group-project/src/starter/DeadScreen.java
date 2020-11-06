package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;



public class DeadScreen extends GraphicsPane {	
	public static final int BREAK_MS = 30;
	public static final int INIT_X_VELOCITY = 5;
	
	private MainApplication program; // you will use program to get access to
	private GImage DeadScreen;
	private GImage FlyingMario;
	private GImage Mario_Dead_Rotate;
	private int xVelocity; 
	
	private GLabel Mario_Dead; 
	
	private GButton playAgainButton;// keep buttons
	private GButton quitButton;
	
	
	public DeadScreen(MainApplication app) {
		super();
		program = app;
		//change images
		
		DeadScreen = new GImage("background/DeadScreen.png", 0, 0);
		Mario_Dead_Rotate = new GImage("Mario/Mario_Dead_Rotate.gif",0,225);
		Mario_Dead_Rotate.setSize(75.0,125.0);
		
		Mario_Dead = new GLabel("Oh no! you just died!",175,100);
		Mario_Dead.setColor(Color.RED);
        Mario_Dead.setFont("Arial-48");
		
		playAgainButton = new GButton("Play Again", 250, 375, 300, 75);
		playAgainButton.setFillColor(Color.RED);
		
		quitButton = new GButton("Quit", 250, 475, 300, 75);
		quitButton.setFillColor(Color.RED);
		
	}
		@Override
		public void showContents() {
			// TODO Auto-generated method stub
			program.add(DeadScreen);
			program.add(Mario_Dead_Rotate);

			program.add(Mario_Dead);

			program.add(playAgainButton);
			program.add(quitButton);
		}
		@Override
		public void hideContents() {
			// TODO Auto-generated method stub
			program.remove(DeadScreen);
			program.remove(Mario_Dead_Rotate);
			
			program.remove(Mario_Dead);

			program.remove(playAgainButton);
			program.remove(quitButton);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			GObject obj = program.getElementAt(e.getX(), e.getY());
			if (obj == quitButton) { 
				program.switchToMenu();          // Back to menu after clicking quit...
				program.stopRandomSound();  	//stop the ongoing sound...
			}
			if (obj== playAgainButton) {
				program.switchToMenu();
				program.stopRandomSound();
			}
		}
		private void animateMarioDeadRotate() {
			while(true) {
				Mario_Dead_Rotate.move(xVelocity, 0);
				if(outOfBounds()) {
					xVelocity *= -1;
				}
				pause(BREAK_MS);
			}
		}
		
		private void pause(int breakMs) {
			// TODO Auto-generated method stub
			
		}
		private boolean outOfBounds() {
			double x = Mario_Dead_Rotate.getX();
			return (x < 0 && xVelocity < 0 || x > WINDOW_WIDTH && xVelocity > 0);
		}

	}