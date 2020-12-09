package starter;

import org.tiledreader.*;
import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class Level {
	private static FileSystemTiledReader reader = new FileSystemTiledReader(); // Reader to read TMX files
	private static TiledMap map; //stores the TMX file
	private static ArrayList<TiledLayer> allLayers = new ArrayList<TiledLayer>(); // Stores all the layers from the map file
	private static SpriteSheet sheet; // Sprite sheet to reference
	private static final int TILESET_WIDTH_AND_HEIGHT = 18; // This is the value to determine the margins for the tilemap we are using. Only change when using a new tilemap
	private static int tileWidth, tileHeight;
	private static double tileSizeOnScreen; // Size of the tiles on the screen, calculated by using window height
	private static Point[] arrayOfPoints; // Location of the tiles on screen
	
	public static ArrayList<GImage> allGImages = new ArrayList<GImage>();
	public static ArrayList<Entity> hitboxes = new ArrayList<Entity>();
	public static ArrayList<Entity> winningSpace = new ArrayList<Entity>();
	public static ArrayList<GRect> hitboxes_debug = new ArrayList<GRect>();
	public static ArrayList<GPoint> goomba_points = new ArrayList<GPoint>();
	
	String pathToTMX;
	String pathToSpriteSheet;
	int windowHeight;

	/**
	 * Constructor for the TMXLevelReader class
	 * @param pathToTMX - Path to tmx files under levels folder
	 * @param pathToSpriteSheet - Path to sprite sheet, MUST BE UNDER /SpriteSheet/ FOLDER, because java is dumb when it comes to file paths
	 */

	Level(String pathToTMX, String pathToSpriteSheet, int windowHeight) {
		this.pathToTMX = pathToTMX;
		this.pathToSpriteSheet = pathToSpriteSheet;
		this.windowHeight = windowHeight;
		reset();
	}

	// Reloads the TMX and resets everything
	public void reset() {
		allLayers.clear();
		allGImages.clear();
		hitboxes.clear();
		hitboxes_debug.clear();
		goomba_points.clear();
		winningSpace.clear();
		map = readFile(pathToTMX);
		tileWidth = map.getWidth();
		tileHeight = map.getHeight();
		tileSizeOnScreen = windowHeight / tileHeight;
		System.out.println("Reading " + pathToTMX);
		System.out.println("Size of map: " + map.getWidth() + " wide, " + map.getHeight() + " high.");
		System.out.println("Size of tiles: " + map.getTileWidth() + " wide, " + map.getTileHeight() + " high.");
		System.out.println("Available layers: ");

		for (TiledLayer a : map.getTopLevelLayers()) {
			allLayers.add(a);
			System.out.println("name: " + a.getName() + ", type: " + a.toString());
		}
		
		sheet = new SpriteSheet(pathToSpriteSheet);
		processLayers();
	}

	// For the path put in folder name and name of .TMX file e.g. test/Test_Level.tmx
	private static TiledMap readFile(String path) {
		String actualPath = reader.getCanonicalPath("../media/" + path);
		System.out.println(actualPath);
		TiledMap temp = reader.getMap(actualPath);
		return temp;
	}

	public GImage getSubGImage(int x, int y, int w, int h) {
		return new GImage(sheet.getSprite(x, y, w, h));
	}

	private static void processLayers() {
		int tilesetX, tilesetY;
		ArrayTileLayer tempATL;
		HashTileLayer tempHTL;
		TiledObjectLayer tempTOL;

		for (TiledLayer layer : allLayers) { 
			if (layer instanceof ArrayTileLayer) {
				tempATL = (ArrayTileLayer)layer;
				arrayOfPoints = tempATL.getTileLocations().toArray(new Point[tempATL.getTileLocations().size()]);
				for (Point point : arrayOfPoints) {
					if (tempATL.getTile(point.x, point.y) != null) {
						tilesetX = tempATL.getTile(point.x, point.y).getTilesetX();
						tilesetY = tempATL.getTile(point.x, point.y).getTilesetY();
						GImage temp = new GImage(sheet.getSprite(tilesetX, tilesetY, TILESET_WIDTH_AND_HEIGHT, TILESET_WIDTH_AND_HEIGHT), point.x * tileSizeOnScreen, point.y * tileSizeOnScreen);
						temp.setSize(tileSizeOnScreen, tileSizeOnScreen);

						allGImages.add(temp);
					}
				}
			} else if (layer instanceof HashTileLayer) {
				tempHTL = (HashTileLayer)layer;
				arrayOfPoints = tempHTL.getTileLocations().toArray(new Point[tempHTL.getTileLocations().size()]);
				for (Point point : arrayOfPoints) {
					if (tempHTL.getTile(point.x, point.y) != null) {
						tilesetX = tempHTL.getTile(point.x, point.y).getTilesetX();
						tilesetY = tempHTL.getTile(point.x, point.y).getTilesetY();
						GImage temp = new GImage(sheet.getSprite(tilesetX, tilesetY, TILESET_WIDTH_AND_HEIGHT, TILESET_WIDTH_AND_HEIGHT), point.x * tileSizeOnScreen, point.y * tileSizeOnScreen);
						temp.setSize(tileSizeOnScreen, tileSizeOnScreen);
						allGImages.add(temp);
					}
				}
			} else if (layer instanceof TiledObjectLayer) {
				tempTOL = (TiledObjectLayer)layer;
				ArrayList<TiledObject> tileObjs = new ArrayList<TiledObject>();
				for (TiledObject e : tempTOL.getObjects()) {
					tileObjs.add(e);
				}
				for (TiledObject e : tileObjs) {
					Entity temp = new Entity((e.getX() / 16) * tileSizeOnScreen, (e.getY() / 16) * tileSizeOnScreen, (e.getWidth() / 16) * tileSizeOnScreen, (e.getHeight() / 16) * tileSizeOnScreen, Id.immovable);
					System.out.println("X: " + (e.getX() / 16) * tileSizeOnScreen + " Y: " + (e.getY() / 16) * tileSizeOnScreen + " Width: " + (e.getWidth() / 16) * tileSizeOnScreen + " Height: " + (e.getHeight() / 16) * tileSizeOnScreen);
					if (layer.getName().equals("pole")) { // == operator for strings do not work cuz java
						winningSpace.add(temp);
						System.out.println(winningSpace);
					} else if (layer.getName().equals("goombas")) {
						System.out.println("Goomba at X: " + (e.getX() / 16) * tileSizeOnScreen + ", Y: " + (e.getY() / 16) * tileSizeOnScreen);
						goomba_points.add(new GPoint((e.getX() / 16) * tileSizeOnScreen, (e.getY() / 16) * tileSizeOnScreen));
					} else {
						hitboxes.add(temp);
					}
				}
			}
		}
		System.out.println("Conversion to GImages done");
		generateHitboxesDebug();
	}
	public double returnTileSizeOnScreen() {
		return tileSizeOnScreen;
	}

	public static void generateHitboxesDebug() {
		GRect temp;
		for (Entity e : hitboxes) {
			temp = new GRect(e.getX(), e.getY(), e.getWidth(), e.getHeight());
			temp.setFilled(true);
			temp.setFillColor(new Color(240, 224, 46, 162));
			hitboxes_debug.add(temp);
		}

		for (Entity e : winningSpace) {
			temp = new GRect(e.getX(), e.getY(), e.getWidth(), e.getHeight());
			temp.setFilled(true);
			temp.setFillColor(new Color(45, 204, 20, 162));
			hitboxes_debug.add(temp);
		}
		System.out.println("Debug hitbox overlays generated");
	}
}
