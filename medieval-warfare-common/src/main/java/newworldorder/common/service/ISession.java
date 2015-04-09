package newworldorder.common.service;

import java.util.List;

import newworldorder.common.model.PartyInvitation;

public interface ISession {
	public String getUsername();
	public List<PartyInvitation> getParty();
	public void removePlayer(String username);
	public void clearParty();
	public void addToParty(String username);
	public void setParty(List<PartyInvitation> party);
}
