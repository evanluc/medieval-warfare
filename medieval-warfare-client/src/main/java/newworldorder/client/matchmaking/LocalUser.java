package newworldorder.client.matchmaking;

public class LocalUser {
	private Party inParty; 
	private String username; 
	private String pendingPartyRequest; //Uname of party requester
	private static LocalUser instance = null;
	
	private LocalUser() {
		super();
	}
	
	public static LocalUser getInstance() {
		if (instance == null) {
			instance = new LocalUser();
		}
		return instance;
	}

	public Party getInParty() {
		return inParty;
	}

	public void setInParty(Party inParty) {
		this.inParty = inParty;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPendingPartyRequest() {
		return pendingPartyRequest;
	}

	public void setPendingPartyRequest(String pendingPartyRequest) {
		this.pendingPartyRequest = pendingPartyRequest;
	}
}
