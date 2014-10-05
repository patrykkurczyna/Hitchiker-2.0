package main.java.pl.edu.agh.pp.hitchhiker.webservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import pl.edu.agh.pp.hitchhiker.webservice.util.BCrypt;

@Entity
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
    private Integer id;
	
	private String login;
	
	private String password;
	
	private String deviceId;
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getDeviceId() {
		return this.deviceId;
	}
	
	public User(String login, String password) {
		setLogin(login);
		setPassword(login, password);
	}
	
	public void setPassword(String login, String password) {
		String logpass = login + password;
		String hashed = BCrypt.hashpw(logpass, BCrypt.gensalt());
		this.password = hashed;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public boolean matches(String login, String password) {
		String logpass = login + password;
		return BCrypt.checkpw(logpass, this.password);
	}
}
