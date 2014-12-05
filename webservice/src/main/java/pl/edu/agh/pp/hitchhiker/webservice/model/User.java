package pl.edu.agh.pp.hitchhiker.webservice.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import pl.edu.agh.pp.hitchhiker.webservice.util.BCrypt;

@Entity
@Component
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(unique = true)
	@NotNull
	private String login;

	@NotNull
	private String password;

	@NotNull
	private String firstname;

	@NotNull
	private String lastname;

	private Date birthdate;
	
	private String email;
	
	private String phone;
	
	@OneToMany
	@JoinColumn(name = "user_id")
	private List<Hitchhiker> hitchhikers = null;
	
	@OneToMany
	@JoinColumn(name = "user_id")
	private List<Driver> drivers = null;

	public List<Hitchhiker> getHitchhikers() {
		return this.hitchhikers;
	}

	public void setHitchhikers(List<Hitchhiker> hitchhikers) {
		this.hitchhikers = hitchhikers;
	}

	public Integer getId() {
		return this.id;
	}

	public String getPassword() {
		return this.password;
	}

	public User(String login, String password) {
		setLogin(login);
		setPassword(password);
	}

	public User() {
		this("", "");
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void hashPassword(String password) {
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
		this.password = hashed;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return this.login;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean matches(String password) {
		return BCrypt.checkpw(password, this.password);
	}

	public Date getBirthdate() {
		Date result = null;
		if (birthdate != null) {
			result = new Date(birthdate.getTime());
		}
		return result;
	}

	public void setBirthdate(Date birthdate) {
		if (birthdate != null) {
			this.birthdate = new Date(birthdate.getTime());
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
