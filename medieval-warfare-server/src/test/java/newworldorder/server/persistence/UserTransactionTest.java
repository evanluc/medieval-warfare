package newworldorder.server.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import newworldorder.common.model.User;
import newworldorder.common.persistence.PersistenceException;
import newworldorder.server.service.OnlineUsers;

public class UserTransactionTest {
	private String testFilename = ".testStore.csv";
	private UserTransaction transaction;
	private OnlineUsers onlineUsers;
	
	@Before
	public void setup() throws IOException {
		UserStore store = new FileUserStore(testFilename);
		onlineUsers = new OnlineUsers();
		transaction = new UserTransaction(store, onlineUsers);
	}
	
	@After
	public void tearDown() {
		transaction = null;
		onlineUsers = null;
		new File(testFilename).delete();
	}
	
	@Test
	public void testCreateNewUserNoException() throws PersistenceException {
		transaction.createUser(new User("test-user-1", "test-pass-1"));
	}
	
	@Test
	public void testCreateExistingUserThrowsException() throws PersistenceException {
		User user = new User("test-user-1", "test-pass-1");
		try {
			boolean first = transaction.createUser(user);	// This one should be ok
			assertTrue(first);
		}
		catch (PersistenceException e) {
			fail();
		}
		
		User user2 = new User("test-user-1", "test-pass-2");
		boolean repeatUser = transaction.createUser(user2);
		assertFalse(repeatUser);
	}
	
	@Test
	public void testLoginWithoutCreating() throws PersistenceException {
		boolean result = transaction.loginUser(new User("test-user-1", "test-pass-1"));
		assertFalse(result);
		assertFalse(onlineUsers.contains("test-user-1"));
	}
	
	@Test
	public void testLoginAfterCreate() throws PersistenceException {
		User user = new User("test-user-1", "test-pass-1");
		transaction.createUser(user);
		boolean result = transaction.loginUser(user);
		assertTrue(result);
		assertTrue(onlineUsers.contains("test-user-1"));
	}
	
	@Test
	public void testLoginAfterCreateWithWrongPassword() throws PersistenceException {
		User user = new User("test-user-1", "test-pass-1");
		transaction.createUser(user);
		boolean result = transaction.loginUser(new User("test-user-1", "test-pass-2"));
		assertFalse(result);
		assertFalse(onlineUsers.contains("test-user-1"));
	}
}
