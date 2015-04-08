package newworldorder.client.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import newworldorder.common.matchmaking.GameRequest;
import newworldorder.common.model.PartyInvitation;
import newworldorder.common.network.AmqpAdapter;
import newworldorder.common.network.CommandConsumer;
import newworldorder.common.network.command.CheckPartyCommand;
import newworldorder.common.network.command.ClientCommand;
import newworldorder.common.network.command.CreateAccountCommand;
import newworldorder.common.network.command.JoinGameCommand;
import newworldorder.common.network.command.LoginCommand;
import newworldorder.common.network.command.LogoutCommand;
import newworldorder.common.network.command.RemoveFromPartyCommand;
import newworldorder.common.network.command.StartPartyGameCommand;
import newworldorder.common.network.command.SynchronizePartyCommand;

@Component
public class ClientController implements IController {
	private final AmqpAdapter adapter;
	private final Session session;
	private final CommandConsumer consumer;

	@Value("${rabbitmq.consumeFrom}")
	private String notifyExchange;

	@Value("${rabbitmq.publishTo}")
	private String commandExchange;
	
	@Value("${rabbitmq.p2pexchange}")
	private String p2pExchange;

	@Value("${rabbitmq.routingKey}")
	private String routingKey;

	@Autowired
	public ClientController(AmqpAdapter adapter, Session session, CommandConsumer consumer) {
		super();
		this.adapter = adapter;
		this.session = session;
		this.consumer = consumer;
	}

	@Override
	public boolean login(String username, String password) {
		if (username.equals("") || username == null) {
			return false;
		}
		else {
			LoginCommand loginCommand = new LoginCommand(username, password);
			boolean result = (Boolean) adapter.sendAndReceive(loginCommand, commandExchange, routingKey);
			if (result) {
				session.setUsername(username);
				consumer.startConsumingFromDirectExchange(notifyExchange, username);
			}
			return result;
		}
	}

	@Override
	public void logout() {
		consumer.stop();
		if (session.getUsername() != null) {
			LogoutCommand command = new LogoutCommand(session.getUsername());
			adapter.send(command, commandExchange, routingKey);
			session.reset();
		}
	}

	@Override
	public void requestGame(int numPlayers) {
		String username = session.getUsername();
		GameRequest curRequest = new GameRequest(username, numPlayers);
		JoinGameCommand joinGameCommand = new JoinGameCommand(username, curRequest);
		adapter.send(joinGameCommand, commandExchange, routingKey);
	}

	@Override
	public boolean newAccount(String username, String password) {
		CreateAccountCommand command = new CreateAccountCommand(username, password);
		boolean result = (Boolean) adapter.sendAndReceive(command, commandExchange, routingKey);
		return result;
	}

	@Override
	public void invitePlayer(String username, String toInvite) {
		boolean canInvite = (Boolean) adapter.sendAndReceive(new CheckPartyCommand(username), p2pExchange, toInvite);
		if (canInvite) {
			session.addToParty(toInvite);
			SynchronizePartyCommand command = new SynchronizePartyCommand(username, session.getParty());
			sendToAllParty(command);
		}
	}

	@Override
	public void acceptInvite() {
		if (!session.getParty().isEmpty() && !acceptedPartyInvite()) {
			session.acceptPartyInvitation();
			SynchronizePartyCommand command = new SynchronizePartyCommand(session.getUsername(), session.getParty());
			sendToAllParty(command);
		}
	}

	@Override
	public void startPartyGame() {
		for (String player : this.getNotAcceptedPlayersInParty()) {
			RemoveFromPartyCommand command = new RemoveFromPartyCommand(session.getUsername(), player);
			this.sendToAllParty(command);
		}
		StartPartyGameCommand command = new StartPartyGameCommand(session.getUsername(), this.getAcceptedPlayersInParty());
		adapter.send(command, commandExchange, routingKey);
	}

	@Override
	public List<String> getPlayersInParty() {
		return session.getParty().stream()
				.map(p -> p.getUsername())
				.collect(Collectors.toList());
				
	}

	@Override
	public String getLeaderOfParty() {
		return getAcceptedPlayersInParty().get(0);
	}

	@Override
	public void leaveParty(String username) {
		RemoveFromPartyCommand command = new RemoveFromPartyCommand(session.getUsername(), session.getUsername());
		sendToAllParty(command);
	}

	@Override
	public void kickFromParty(String toKick) {
		if (getLeaderOfParty().equals(session.getUsername())) {
			RemoveFromPartyCommand command = new RemoveFromPartyCommand(session.getUsername(), toKick);
			sendToAllParty(command);
		}
	}
	
	private void sendToAllParty(ClientCommand command) {
		for (String player : getPlayersInParty()) {
			adapter.send(command, p2pExchange, player);
		}
	}
	
	private boolean acceptedPartyInvite() {
		for (PartyInvitation p : session.getParty()) {
			if (p.getUsername().equals(session.getUsername())) {
				return p.isAccepted();
			}
		}
		
		return false;
	}
	
	private List<String> getAcceptedPlayersInParty() {
		return session.getParty().stream()
				.filter(p -> p.isAccepted())
				.map(p -> p.getUsername())
				.collect(Collectors.toList());
	}
	
	private List<String> getNotAcceptedPlayersInParty() {
		return session.getParty().stream()
				.filter(p -> !p.isAccepted())
				.map(p -> p.getUsername())
				.collect(Collectors.toList());
	}
}
