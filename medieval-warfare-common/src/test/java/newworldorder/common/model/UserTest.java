package newworldorder.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import newworldorder.common.model.User;

public class UserTest {

	@Test
	public void testSameCredentials() {
		User user1 = new User("username", "password");
		User user2 = new User("username", "password");
		assertEquals(user1, user2);
	}

	@Test
	public void testDifferentUsername() {
		User user1 = new User("username1", "password");
		User user2 = new User("username2", "password");
		assertNotEquals(user1, user2);
	}

	@Test
	public void testDifferentPassword() {
		User user1 = new User("username", "password1");
		User user2 = new User("username", "password2");
		assertNotEquals(user1, user2);
	}
}
