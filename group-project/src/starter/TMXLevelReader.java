package starter;

import org.tiledreader.*;
import acm.program.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TMXLevelReader {
	public static FileSystemTiledReader reader = new FileSystemTiledReader(); //reads in the TMX file 
	public static TiledMap map; //stores the TMX file
	public static List<TiledLayer> layers; // Stores the layers of the TMX File
	public static ArrayTileLayer ArrayTileLayer; // Stores layers that are of ArrayTileLayer type
	public static HashTileLayer HashTileLayer; // Stores layers that are of HashTileLayer type
	public static TiledObjectLayer TiledObjectLayer; // Stores layers that are of TiledObjectLayer type

	public static ArrayList<HashTileLayer> arrayOfHashTile = new ArrayList<HashTileLayer>(); // stores HashTile (Collision stuff, details)
	public static ArrayList<TiledObjectLayer> arrayOfTiledObj = new ArrayList<TiledObjectLayer>(); // stores TiledObjectLayer (pipe,ground,door,pole)
	
	// For the path put in folder name and name of .TMX file e.g. test/Test_Level.tmx
	private static TiledMap readFile(String path) {
		String actualPath = reader.getCanonicalPath("levels/" + path);
		System.out.println(actualPath);
		TiledMap temp = reader.getMap(actualPath);
		return temp;
	}
	
	// Main function to test things out
	public static void main(String[] args) {
		map = readFile("test/Test_Level.tmx");
		System.out.println("Size of map: " + map.getWidth() + " wide, " + map.getHeight() + " high.");
		System.out.println("Size of tiles: " + map.getTileWidth() + " wide, " + map.getTileHeight() + " high.");
	}
}
