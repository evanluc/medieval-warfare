package newworldorder.server.matchmaking;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import newworldorder.common.matchmaking.GameInfo;
import newworldorder.common.network.AmqpAdapter;
import newworldorder.common.network.command.ClientCommand;
import newworldorder.common.network.command.StartGameCommand;
import newworldorder.common.service.IGameInitializer;

@Component
public class GameInitializer implements IGameInitializer {
	private final Logger logger = LoggerFactory.getLogger(GameInitializer.class);
	private final AmqpAdapter amqpAdapter;
	
	@Value("${rabbitmq.publishTo}")
	private String exchange;

	@Autowired
	public GameInitializer(AmqpAdapter adapter) {
		amqpAdapter = adapter;
	}

	@Override
	public void initializeGame(List<String> players) {
		String gameExchangeName = String.valueOf(players.hashCode());

		GameInfo gameInfo = new GameInfo(players, gameExchangeName);
		logger.info("Initializing game: " + gameInfo.toString());
		
		ClientCommand command = new StartGameCommand("server", gameInfo);

		for (String player : players) {
			amqpAdapter.send(command, exchange, player);
		}
	}
}
