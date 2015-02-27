package newworldorder.common.network.util;

import static newworldorder.common.network.util.Serialization.bytes2command;
import static newworldorder.common.network.util.Serialization.command2bytes;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import newworldorder.common.matchmaking.GameInfo;
import newworldorder.common.matchmaking.GameRequest;
import newworldorder.common.network.message.AbstractCommand;
import newworldorder.common.network.message.JoinGameCommand;
import newworldorder.common.network.message.LoginCommand;
import newworldorder.common.network.message.StartGameCommand;

public class SerializationTest {
	@Test
	public void testLoginCommandSerialization() throws ClassNotFoundException, IOException {
		LoginCommand command = new LoginCommand("username", "password");
		AbstractCommand restored = bytes2command(command2bytes(command));
		assertEquals(command, restored);
	}
	
	@Test
	public void testJoinGameCommandSerialization() throws ClassNotFoundException, IOException {
		JoinGameCommand command = new JoinGameCommand("sender", new GameRequest("player1", 3));
		AbstractCommand restored = bytes2command(command2bytes(command));
		assertEquals(command, restored);
	}
	
	@Test
	public void testStartGameCommandSerialization() throws ClassNotFoundException, IOException {
		List<String> players = new ArrayList<>();
		players.add("player1");
		players.add("player2");
		players.add("player3");
		GameInfo info = new GameInfo(players, "test-exchange-1");
		StartGameCommand command = new StartGameCommand("sender", info);
		AbstractCommand restored = bytes2command(command2bytes(command));
		assertEquals(command, restored);
	}
}
