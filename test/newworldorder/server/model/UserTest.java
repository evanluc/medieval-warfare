package newworldorder.server.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void testDifferentIdWithSameCredentials() {
		User user1 = new User(1, "username", "password");
		User user2 = new User(2, "username", "password");
		assertEquals(user1, user2);
	}

	@Test
	public void testDifferentUsername() {
		User user1 = new User(1, "username1", "password");
		User user2 = new User(2, "username2", "password");
		assertNotEquals(user1, user2);
	}

	@Test
	public void testDifferentPassword() {
		User user1 = new User(1, "username", "password1");
		User user2 = new User(2, "username", "password2");
		assertNotEquals(user1, user2);
	}
}
