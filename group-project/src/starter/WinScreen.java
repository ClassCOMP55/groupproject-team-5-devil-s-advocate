package starter;

import java.awt.event.MouseEvent;
import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class WinScreen extends GraphicsPane {
    private MainApplication program; // you will use program to get access to
    //we will change these images                                    // all of the GraphicsProgram calls
    private GImage WinScreen;
    private GLabel Mario_Win;
    private GImage Mario; 
    
    private GButton playAgainButton;// keep buttons
    private GButton quitButton;


    
    public WinScreen(MainApplication app) {
        super();
        program = app;
        
        WinScreen = new GImage("background/WinScreen.png", 0, 0);
        Mario = new GImage("Mario/Mario_S_R.png", 150, 30);
        
        Mario_Win = new GLabel("You have passed the level!",150,50);
        Mario_Win.setColor(Color.RED);
        
        playAgainButton = new GButton("Play Again", 200, 375, 300, 75);
        playAgainButton.setFillColor(Color.RED);
        
        quitButton = new GButton("Quit", 250, 475, 300, 75);
        quitButton.setFillColor(Color.RED);
        
    }
        @Override
        public void showContents() {
            // TODO Auto-generated method stub
            //Change images 
            program.add(WinScreen);
            program.add(Mario_Win);
            program.add(Mario); 
            
            
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