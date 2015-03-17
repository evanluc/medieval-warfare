package newworldorder.client.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ModelSerializer utility class for loading and saving maps and game states
 */
public class ModelSerializer {

	public static Map loadMap(String savedMapPath) throws Exception {
		Map map = null;

		try {
			map = (Map) readSerializedObject(savedMapPath);
		}
		catch (ClassNotFoundException e) {
			throw new Exception("File is either corrupt or out of date when loading map");
		}
		catch (FileNotFoundException e) {
			throw new Exception("Invalid path when loading map");
		}
		catch (IOException e) {
			throw new Exception("File system IO error when loading map");
		}

		return map;
	}

	public static void saveMap(Map mapToSave, String savedMapPath) throws Exception {
		try {
			writeSerializedObject(mapToSave, savedMapPath);
		}
		catch (FileNotFoundException e) {
			throw new Exception("Invalid path when saving map");
		}
		catch (IOException e) {
			throw new Exception("File system IO error when saving map");
		}
	}

	public static Game loadGameState(String savedGameStatePath) throws Exception {
		Game gameState = null;

		try {
			gameState = (Game) readSerializedObject(savedGameStatePath);
		}
		catch (ClassNotFoundException e) {
			throw new Exception("File is either corrupt or out of date when loading saved game");
		}
		catch (FileNotFoundException e) {
			throw new Exception("Invalid path when loading saved game");
		}
		catch (IOException e) {
			throw new Exception("File system IO error when loading saved game");
		}

		return gameState;
	}

	public static void saveGameState(Game gameToSave, String savedGameStatePath) throws Exception {
		try {
			writeSerializedObject(gameToSave, savedGameStatePath);
		}
		catch (FileNotFoundException e) {
			throw new Exception("Invalid path when saving game");
		}
		catch (IOException e) {
			throw new Exception("File system IO error when saving game");
		}
	}

	private synchronized static void writeSerializedObject(Object o, String path) throws FileNotFoundException, IOException {
		// The "ObjectOutputStream" class have the default
		// definition to serialize an object.
		// By using "FileOutputStream" we will
		// Write it to a File in the file system
		// It could have been a Socket to another
		// machine, a database, an in memory array, etc.
		// - stackoverflow.com
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(path)));
			oos.writeObject(o);
		}
		catch (Exception ex) {
			throw ex;
		}
		finally {
			oos.close();
		}
	}

	private synchronized static Object readSerializedObject(String path) throws FileNotFoundException, IOException, ClassNotFoundException {
		// The idea from the above method also applies here,
		// we can easily chance the FileInputStream to a Socket
		// or another machine or anything really.
		ObjectInputStream ois = null;
		Object o;
		try {
			ois = new ObjectInputStream(new FileInputStream(new File(path)));
			o = ois.readObject();
		}
		catch (Exception ex) {
			throw ex;
		}
		finally {
			ois.close();
		}
		return o;
	}

}
