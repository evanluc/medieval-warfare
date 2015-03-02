package newworldorder.common.network.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import newworldorder.common.network.command.LoginCommand;

public class LoginCommandTest extends CommandTestBase {
	@Before
	public void setup() {
		sender = "username";
		command = new LoginCommand("username", "password");
	}

	@Test
	public void testExecute() {
		// TODO when we actually have a real execute method
	}

	@Test
	public void testEquals() {
		LoginCommand other = new LoginCommand("username", "password");
		assertEquals(command, other);
	}
}
