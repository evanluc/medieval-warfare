package newworldorder.server.matchmaking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.matchmaking.GameInfo;
import newworldorder.common.network.AmqpAdapter;
import newworldorder.common.network.command.ClientCommand;
import newworldorder.common.network.command.StartGameCommand;

@Component
public class GameInitializer {
	private final AmqpAdapter amqpAdapter;

	@Autowired
	public GameInitializer(AmqpAdapter adapter) {
		amqpAdapter = adapter;
	}

	public void initializeGame(List<String> players) {
		String gameExchangeName = String.valueOf(players.hashCode());

		GameInfo gameInfo = new GameInfo(players, gameExchangeName);
		ClientCommand command = new StartGameCommand("server", gameInfo);

		for (String player : players) {
			amqpAdapter.send(command, "notify-exchange", player);
		}
	}
}
