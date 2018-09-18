package com.fblaise.sms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends SmsDomain {
	@Column(name = "role", unique = true)
	private String role;

	public Role() {
		super();
	}

	public Role(String role) {
		super();
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}