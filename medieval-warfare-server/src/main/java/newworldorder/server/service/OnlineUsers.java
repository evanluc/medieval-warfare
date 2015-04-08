package newworldorder.server.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class OnlineUsers {
	private Set<String> onlineUsers;
	
	public OnlineUsers() {
		onlineUsers = new HashSet<>();
	}
	
	public void add(String username) {
		onlineUsers.add(username);
	}
	
	public void remove(String username) {
		onlineUsers.remove(username);
	}
	
	public Set<String> getAll() {
		return onlineUsers;
	}
	
	public boolean contains(String username) {
		return onlineUsers.contains(username);
	}
}
