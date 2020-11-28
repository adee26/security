package com.example.security.entities;

import javax.persistence.*;

@Entity
public class Resolution {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String text;

	@Column
	private Long owner;

	@Column(nullable=false)
	private Boolean completed = false;

	public Resolution() {
	}

	public Resolution(String text, Long owner) {
		this.text = text;
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
}
