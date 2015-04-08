package newworldorder.server.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import newworldorder.common.model.User;

class FileUserStore implements UserStore {
	private String fileStore;
	
	public FileUserStore(String filename) throws IOException {
		fileStore = filename;
		File store = new File(filename);
		if (!store.exists()) {
			store.createNewFile();
		}
	}
	
	@Override
	public boolean containsUsername(String username) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(fileStore));
		try {
			boolean res = reader.readAll().stream()
				.filter(sArr -> sArr[0].equals(username))
				.findAny()
				.isPresent();
			return res;
		}
		finally {
			reader.close();
		}
	}

	@Override
	public void insertUser(User user) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(fileStore));
		try {
			String[] entry = new String[] {user.getUsername(), user.getPassword()};
			writer.writeNext(entry);
		}
		finally {
			writer.close();
		}
		
	}

	@Override
	public User selectUser(String username) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(fileStore));
		try {
			String[] userInfo = reader.readAll().stream()
				.filter(sArr -> sArr[0].equals(username))
				.findFirst()
				.get();
			return new User(userInfo[0], userInfo[1]);
		}
		finally {
			reader.close();
		}
	}
}
