package newworldorder.common.network.message;

import static org.mockito.BDDMockito.then;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Message;

import newworldorder.common.network.util.Serialization;

public class CommandListenerTest {
	@Mock private CommandExecutor executor;
	private CommandListener listener;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		listener = new CommandListener(executor);
	}

	@After
	public void tearDown() {
		executor = null;
		listener = null;
	}

	@Test
	public void testHandleMessage() throws IOException, ClassNotFoundException {
		LoginCommand command = new LoginCommand("username", "password");
		byte[] serialized = Serialization.command2bytes(command);
		Message message = new Message(serialized, null);

		listener.onMessage(message);

		then(executor).should().execute(command);
	}
}
