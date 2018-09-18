package com.fblaise.sms.model;

import java.util.Date;

public class UiEmployee {
	private Long id;
	private String userName;
	private String password;
	private String startDate;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private Date realStartDate;

	public UiEmployee(String firstName, String lastName, String email, String phoneNumber, String userName,
			String password, String startDate, boolean isActive) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.password = password;
		this.startDate = startDate;
	}

	public UiEmployee(Long id, String firstName, String lastName, String email, String phoneNumber, String userName,
			String password, String startDate, boolean isActive) {
		this(firstName, lastName, email, phoneNumber, userName, password, startDate, isActive);
		this.id = id;
	}

	public UiEmployee() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getRealStartDate() {
		return realStartDate;
	}

	public void setRealStartDate(Date realStartDate) {
		this.realStartDate = realStartDate;
	}

}
