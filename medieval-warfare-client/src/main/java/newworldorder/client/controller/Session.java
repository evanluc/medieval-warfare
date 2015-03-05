package newworldorder.client.controller;

import org.springframework.stereotype.Component;

@Component
class Session implements ISession {
	private String username;

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
