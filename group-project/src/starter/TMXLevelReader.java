package starter;

import org.tiledreader.*;
import acm.program.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TMXLevelReader {

	public static void main(String[] args) {
		FileSystemTiledReader reader = new FileSystemTiledReader(); //reads in the TMX file 
		TiledMap map; //stores the TMX file
		List<TiledLayer> layers; 
		ArrayTileLayer ArrayTileLayer;
		HashTileLayer HashTileLayer;
		TiledObjectLayer TiledObjectLayer;
	
		ArrayList<HashTileLayer> arrayOfHashTile = new ArrayList<HashTileLayer>(); // stores HashTile (Collision stuff, details)
		ArrayList<TiledObjectLayer> arrayOfTiledObj = new ArrayList<TiledObjectLayer>(); // stores TiledObjectLayer (pipe,ground,door,pole)
		
		
		
	}

}
