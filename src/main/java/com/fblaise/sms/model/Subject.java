package com.fblaise.sms.model;

import javax.persistence.Entity;

@Entity
public class Subject extends SmsDomain {
	private String name;
	private String description;

	public Subject(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Subject() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void copyValuesFrom(Subject subject) {
		this.name = subject.name;
		this.description = subject.description;
	}

}
