package org.edupoll.model.dto.request;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDTO {

	String id;
	String password;
	String nick;

	public UserDTO() {
	}

	public UserDTO(String id, String password, String nick) {
		this.id = id;

		PasswordEncoder encoder = new BCryptPasswordEncoder();
		password = encoder.encode(password);
		this.password = "{bcrypt}" + password;
		
		this.nick = nick;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

}
