package newworldorder.common.network.message;

import static org.mockito.BDDMockito.then;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import newworldorder.common.network.util.Serialization;

public class CommandHandlerTest {
	@Mock private CommandExecutor executor;
	private CommandHandler handler;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		handler = new CommandHandler(executor);
	}
	
	@After
	public void tearDown() {
		executor = null;
		handler = null;
	}
	
	@Test
	public void testHandleMessage() throws IOException, ClassNotFoundException {
		LoginCommand command = new LoginCommand("username", "password");
		byte[] serialized = Serialization.command2bytes(command);
		
		handler.handle(serialized);
		
		then(executor).should().execute(command);
	}
}
