package newworldorder.game.driver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import newworldorder.game.model.Map;
import newworldorder.game.model.TerrainType;
import newworldorder.game.model.Tile;

/**
 * PresetMapGenerator is a utility class that generates a saved map file from a simple
 * text file that describes the terrain in a preset map. The text file must use the
 * following example format:
 * name-of-output-file 2 3
 * 0 t
 * 1 s
 * 2 g
 * 3 m
 * 4 s
 * 5 g
 * Where 2 is replaced by the width and 3 is replaced by the height. There must be one
 * line per tile and the line number must match the hashcode of the tile in order at
 * that position.
 * @author David
 *
 */
public class PresetMapGenerator {
	
	public static void generateMapFromDescFile(String inputFileName) {
		
		BufferedReader bufferedReader = null;
		String outputFileName = null;
		int width = 0, height = 0;
		
		try {
			bufferedReader = new BufferedReader( new FileReader(inputFileName) );
			
			String[] settings = bufferedReader.readLine().split("\\s");
			outputFileName = settings[0];
			width = Integer.parseInt(settings[1]);
			height = Integer.parseInt(settings[2]);
			
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + inputFileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + inputFileName + "'");
		} finally {
			
		}
		
		Map newMap = buildMap(width, height, bufferedReader);
		
		try {
			bufferedReader.close();
		} catch (IOException e) {
			System.out.println("Error closing file '" + inputFileName + "'");
		}
		
		outputMapFile(newMap, outputFileName);
	}
	
	private static void outputMapFile(Map newMap, String outputFileName) {
		try {
			ModelSerializer.saveMap(newMap, outputFileName + ".mwm");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private static Map buildMap(int width, int height, BufferedReader bufferedReader) {
		
		Map newMap = new Map(width, height);
		
		try {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					String[] line = bufferedReader.readLine().split("\\s");
					
					if (Integer.parseInt(line[0]) != (y * width + x)) {
						throw new Exception("Tile hashcode " + (y * width + x) + " did not match in file");
					} else {
						Tile t = newMap.getTile(x, y);
						switch (line[1].charAt(0)) {
						case 's':
							t.setTerrainType(TerrainType.SEA);
							break;
						case 'g':
							t.setTerrainType(TerrainType.GRASS);
							break;
						case 'm':
							t.setTerrainType(TerrainType.MEADOW);
							break;
						case 't':
							t.setTerrainType(TerrainType.TREE);
							break;
						default:
							t.setTerrainType(TerrainType.SEA);
							break;
						}
					}
				}
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.println("Map generation file is malformed");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		return newMap;
	}
	public static void main(String[] args) {
		// change the name below to generate from any text file you specify
		// when running from within eclipse, the root directory is the home
		// directory of the project (/medieval-warfare-game-core/test.txt)
		generateMapFromDescFile("testfiles/test.txt");
	}
}
