package newworldorder.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import newworldorder.common.model.PartyInvitation;
import newworldorder.common.service.ISession;

@Component
class Session implements ISession {
	private String username;
	private List<PartyInvitation> party;
	
	public Session() {
		party = new ArrayList<>();
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public List<PartyInvitation> getParty() {
		return this.party;
	}
	
	@Override
	public void addToParty(String username) {
		party.add(new PartyInvitation(username));
	}
	
	@Override
	public void clearParty() {
		party.clear();
	}
	
	@Override
	public void removePlayer(String username) {
		party.remove(username);
	}
	
	public void reset() {
		username = null;
		party.clear();
	}

	@Override
	public void setParty(List<PartyInvitation> party) {
		this.party = party;
	}
	
	public void acceptPartyInvitation() {
		for (PartyInvitation p : party) {
			if (p.getUsername().equals(username)) {
				p.setAccepted(true);
			}
		}
	}
}
