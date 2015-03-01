package newworldorder.common.network.message;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

public class CommandTestBase {
	protected String sender;
	protected AbstractCommand command;

	@After
	public void tearDown() {
		sender = null;
		command = null;
	}

	@Test
	public void testSender() {
		assertEquals(sender, command.getSender());
	}
}
