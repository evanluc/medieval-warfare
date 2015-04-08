package newworldorder.server.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import newworldorder.common.model.User;
import newworldorder.common.persistence.PersistenceException;

public class UserTransactionTest {
	private String testFilename = ".testStore.csv";
	private UserTransaction transaction;
	
	@Before
	public void setup() throws IOException {
		UserStore store = new FileUserStore(testFilename);
		transaction = new UserTransaction(store);
	}
	
	@After
	public void tearDown() {
		transaction = null;
		new File(testFilename).delete();
	}
	
	@Test
	public void testCreateNewUserNoException() throws PersistenceException {
		transaction.createUser(new User("test-user-1", "test-pass-1"));
	}
	
	@Test(expected=PersistenceException.class)
	public void testCreateExistingUserThrowsException() throws PersistenceException {
		User user = new User("test-user-1", "test-pass-1");
		try {
			transaction.createUser(user);	// This one should be ok
		}
		catch (PersistenceException e) {
			fail();
		}
		
		User user2 = new User("test-user-1", "test-pass-2");
		transaction.createUser(user2);
	}
	
	@Test
	public void testLoginWithoutCreating() throws PersistenceException {
		boolean expected = false;
		boolean actual = transaction.loginUser(new User("test-user-1", "test-pass-1"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void testLoginAfterCreate() throws PersistenceException {
		boolean expected = true;
		User user = new User("test-user-1", "test-pass-1");
		transaction.createUser(user);
		boolean actual = transaction.loginUser(user);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testLoginAfterCreateWithWrongPassword() throws PersistenceException {
		boolean expected = false;
		User user = new User("test-user-1", "test-pass-1");
		transaction.createUser(user);
		boolean actual = transaction.loginUser(new User("test-user-1", "test-pass-2"));
		assertEquals(expected, actual);
	}
}
