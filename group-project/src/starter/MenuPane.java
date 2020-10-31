package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GButton rect;
	
	private GImage background;
	private GImage MarioLogo;
	private GImage Mario;

	
	public MenuPane(MainApplication app) {
		super();
		program = app;
		
		
		background = new GImage("Background/SolidBlueBackground.png", 0, 0);
		MarioLogo = new GImage("Background/MarioLogo.png", 150, 100);
		Mario = new GImage("Mario/Mario_S_L.png", 400, 300);
		
		rect = new GButton("Next", 200, 350, 200, 200);
		rect.setFillColor(Color.RED);
		
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(MarioLogo);
		program.add(Mario);
		program.add(rect);


	}

	@Override
	public void hideContents() {
		program.remove(rect);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == rect) {
			program.switchToSome();
		}
	}
}
