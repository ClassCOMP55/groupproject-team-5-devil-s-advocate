package starter;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class SomePane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	//------ Michelle Vu
	public static final String IMAGES_FOLDER = "images";
	private static final String[] IMAGES_MARIO_FILES = { "Mario_D_R.png", "Mario_S_R.png"};
	//------ Michelle Vu
	
	private GImage img;
	private GParagraph para;

	public SomePane(MainApplication app) {
		this.program = app;
		//img = new GImage("robot head.jpg", 100, 100);
		img = new GImage("Mario_S_R.png", 100, 100);
		//audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);

		para = new GParagraph("welcome\nto my\nsecret room!", 150, 300);
		para.setFont("Arial-24");
	}

	@Override
	public void showContents() {
		program.add(img);
		program.add(para);
	}

	@Override
	public void hideContents() {
		program.remove(img);
		program.remove(para);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		para.setText("you need\nto click\non the eyes\nto go back");
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == img) {
			program.switchToMenu();
		}
	}
}
