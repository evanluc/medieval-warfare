package newworldorder.client.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import newworldorder.common.matchmaking.GameRequest;
import newworldorder.common.model.PartyInvitation;
import newworldorder.common.model.Stats;
import newworldorder.common.network.AmqpAdapter;
import newworldorder.common.network.CommandConsumer;
import newworldorder.common.network.command.CheckPartyCommand;
import newworldorder.common.network.command.ClientCommand;
import newworldorder.common.network.command.CreateAccountCommand;
import newworldorder.common.network.command.GetOnlinePlayersCommand;
import newworldorder.common.network.command.GetPlayerStatsCommand;
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
	private static ClientController instance;
	public String username;
	public String password;

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
		if (instance == null) {
			instance = this;
		}
	}
	
	public static ClientController getInstance() {
		if (instance == null) {
			System.out.println("Halp");
		}
		return instance;
	}
	public String getUsername(){
		return this.session.getUsername();
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
				this.username = username;
				this.password = password;
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
		if (username.equals("") || username == null|| password.equals("") || password == null) {
			return false;
		}
		CreateAccountCommand command = new CreateAccountCommand(username, password);
		boolean result = (Boolean) adapter.sendAndReceive(command, commandExchange, routingKey);
		return result;
	}

	@Override
	public void invitePlayer(String username, String toInvite) {
		Object o = adapter.sendAndReceive(new CheckPartyCommand(username), p2pExchange, toInvite);
		boolean canInvite = false;
		if(o != null){
			canInvite = (boolean) o;
		}else{
			canInvite = true;
			//requestHandler currently not handling sendAndREceive so CheckPartyCommand is breaking
		}
		if (canInvite) {
			if(session.getParty().isEmpty()){
				session.addToParty(session.getUsername());
				session.acceptPartyInvitation();
			}
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
	public List<String> getOnlinePlayers(){
		GetOnlinePlayersCommand command = new GetOnlinePlayersCommand(session.getUsername());
		 Set<String> result = (Set<String>) adapter.sendAndReceive(command, commandExchange, routingKey);
		 return new ArrayList<String>(result);
	
	}
	
	private void sendToAllParty(ClientCommand command) {
		for (String player : getPlayersInParty()) {
			adapter.send(command, p2pExchange, player);
		}
	}
	
	public boolean acceptedPartyInvite() {
		for (PartyInvitation p : session.getParty()) {
			if (p.getUsername().equals(session.getUsername())) {
				return p.isAccepted();
			}
		}
		
		return false;
	}
	
	public List<String> getAcceptedPlayersInParty() {
		return session.getParty().stream()
				.filter(p -> p.isAccepted())
				.map(p -> p.getUsername())
				.collect(Collectors.toList());
	}
	
	public List<String> getNotAcceptedPlayersInParty() {
		return session.getParty().stream()
				.filter(p -> !p.isAccepted())
				.map(p -> p.getUsername())
				.collect(Collectors.toList());
	}
	
	public String getLocalUsername() {
		return session.getUsername();
	}
	
	public void endGame(List<String> losers, String winner) {
		
	}
	
	public Stats getStatsForPlayer(String username) {
		GetPlayerStatsCommand command = new GetPlayerStatsCommand(username);
		Stats result = (Stats) adapter.sendAndReceive(command, commandExchange, routingKey);
		return result;
	}
}
