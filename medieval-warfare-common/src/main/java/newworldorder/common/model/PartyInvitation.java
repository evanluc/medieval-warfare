package newworldorder.common.model;

import java.io.Serializable;

public class PartyInvitation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8167590583960159425L;
	private String username;
	private boolean accepted = false;
	
	public PartyInvitation(String username) {
		this.username = username;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartyInvitation other = (PartyInvitation) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
