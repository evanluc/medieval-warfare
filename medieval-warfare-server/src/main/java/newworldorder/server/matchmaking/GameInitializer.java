package newworldorder.server.matchmaking;

import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.common.network.command.ClientCommand;
import newworldorder.common.network.command.StartGameCommand;

import newworldorder.common.matchmaking.GameInfo;

@Component
public class GameInitializer {
	private final AmqpTemplate amqpTemplate;

	@Autowired
	public GameInitializer(AmqpTemplate template) {
		amqpTemplate = template;
	}

	public void initializeGame(List<String> players) {
		String gameExchangeName = String.valueOf(players.hashCode());

		GameInfo gameInfo = new GameInfo(players, gameExchangeName);
		ClientCommand command = new StartGameCommand("server", gameInfo);

		for (String player : players) {
			amqpTemplate.convertAndSend(player, command);
		}
	}
}
