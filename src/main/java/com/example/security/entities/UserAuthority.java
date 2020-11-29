package com.example.security.entities;

import com.example.security.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name="userAuthorities")
@Table(name = "userAuthorities")
public class UserAuthority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	User user;

	@Column
    public String authority;

	UserAuthority() {}

	public UserAuthority(User user, String authority) {

		this.user = user;
		this.authority = authority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
