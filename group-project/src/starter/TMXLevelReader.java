package starter;

import org.tiledreader.*;
import acm.graphics.*;
import java.awt.Point;
import java.util.ArrayList;

public class TMXLevelReader {
	private static FileSystemTiledReader reader = new FileSystemTiledReader(); //reads in the TMX file 
	private static TiledMap map; //stores the TMX file
	private static ArrayList<TiledLayer> allLayers = new ArrayList<TiledLayer>();
	private static SpriteSheet sheet;
	private static int tileWidth, tileHeight;
	private static double tileSizeOnScreen;
	private static Point[] arrayOfPoints;
	public static ArrayList<GImage> allGImages = new ArrayList<GImage>();
	private static final int TILESET_WIDTH_AND_HEIGHT = 18; // This is the value to determine the margins for the tilemap we are using. Only change when using a new tilemap
	
	/**
	 * Constructor for the TMXLevelReader class
	 * @param pathToTMX - Path to tmx files under levels folder
	 * @param pathToSpriteSheet - Path to sprite sheet, MUST BE UNDER /SpriteSheet/ FOLDER, because java is dumb when it comes to file paths
	 */
	TMXLevelReader(String pathToTMX, String pathToSpriteSheet, int windowHeight) {
		map = readFile(pathToTMX); //reads in the file
		tileWidth = map.getWidth(); // 50+ tiles long
		tileHeight = map.getHeight(); // 13 tiles high
		tileSizeOnScreen = windowHeight / tileHeight; //figures out the sizes of the tile
		System.out.println("Reading " + pathToTMX);
		System.out.println("Size of map: " + map.getWidth() + " wide, " + map.getHeight() + " high.");
		System.out.println("Size of tiles: " + map.getTileWidth() + " wide, " + map.getTileHeight() + " high.");
		System.out.println("Available layers: ");
		for (TiledLayer a : map.getTopLevelLayers()) { //gets all the layers + prints out the different layers
			allLayers.add(a);
			System.out.println("name: " + a.getName() + ", type: " + a.toString());
		}
		sheet = new SpriteSheet(pathToSpriteSheet); //creates a new SpiteSheet
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
		
		for (TiledLayer layer : allLayers) { 
			if (layer instanceof ArrayTileLayer) {
				tempATL = (ArrayTileLayer)layer; 
				arrayOfPoints = tempATL.getTileLocations().toArray(new Point[tempATL.getTileLocations().size()]);
				for (Point point : arrayOfPoints) { //gets the location of the tile layer
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
			}	
		}
		System.out.println("Conversion to GImages done");
		
		// TODO Add processing to return an ArrayList of hitboxes
	}
}
