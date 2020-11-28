package com.example.security.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column
    String username;

	@Column
    String password;

	@Column
    Boolean enabled = true;

	@OneToMany(cascade=ALL, fetch=EAGER)
    List<UserAuthority> userAuthorities = new ArrayList<>();

	public User() {}

	public User(Long id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public User(User user) {
		this.id = user.id;
		this.username = user.username;
		this.password = user.password;
		this.enabled = user.enabled;
		this.userAuthorities = new ArrayList<>(user.userAuthorities);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<UserAuthority> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(List<UserAuthority> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	public void addAuthority(String authority) {
		this.userAuthorities.add(new UserAuthority(this, authority));
	}
}
