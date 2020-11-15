package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;
import org.tiledreader.*;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class GameScreen extends GraphicsPane {
	//private TmxMapLoader maploader;
    //private TiledMap map;
    //private OrthogonalTiledMapRenderer renderer;
	private MainApplication program; 
	
	
	public GameScreen (MainApplication app) {
		program = app; 
	}
	//maploader = new TmxMapLoader();
    //map = maploader.load("level1.tmx");
    //renderer = new OrthogonalTiledMapRenderer(map, 1  / MarioBros.PPM);

    //initially set our gamcam to be centered correctly at the start of of map
    //gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

	//public void update(float dt){
	//	handleInput(dt);
	//	
	//	gamecam.update();
	//	renderer.setView(gamecam);
	//}
	
	//public void render(float delta){
	//update(delta);
	//
	//Gdx.gl.glClearColor(0,0,0,1);
	//Gdx.gl.glClearColor(GL20.GL_COLOR_BUFFER_BIT);
	//
	//renderer.render();
	


	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
	}
}

