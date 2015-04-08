package newworldorder.server.persistence;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import newworldorder.common.model.User;

public class FileUserStoreTest {
	private String testFile = ".testStore.csv";
	private FileUserStore testFileStore;
	
	@Before
	public void setup() throws IOException {
		testFileStore = new FileUserStore(testFile);
	}
	
	@After
	public void tearDown() {
		File file = new File(testFile);
		file.delete();
		testFileStore = null;
	}
	
	@Test
	public void testEmptyStoreDoesNotContainAUsername() throws IOException {
		boolean expected = false;
		boolean actual = testFileStore.containsUsername("test-user-1");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testStoreContainsUsernameAfterInsert() throws IOException {
		boolean expected = true;
		testFileStore.insertUser(new User("test-user-1", "test-pass-1"));
		boolean actual = testFileStore.containsUsername("test-user-1");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testStoreContainsUserAfterInsert() throws IOException {
		User expected = new User("test-user-1", "test-pass-1");
		testFileStore.insertUser(expected);
		User actual = testFileStore.selectUser(expected.getUsername());
		assertEquals(expected, actual);
	}
}
