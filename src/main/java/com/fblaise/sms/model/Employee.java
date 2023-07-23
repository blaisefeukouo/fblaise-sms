package com.fblaise.sms.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fblaise.sms.AttributesNames;

@Entity
@Table(name = "employee")
public class Employee extends Person {
	@Column(name = "user_name", unique=true)
	// @NotEmpty(message = "*Please provide your user name")
	private String userName;

	@Column(name = "password")
	//@NotEmpty(message = "*Please provide your password")
	private String password;
	@Temporal(TemporalType.DATE)
	@Column(name = "startdate")
	private Date startDate;
	@Temporal(TemporalType.DATE)
	@Column(name = "enddate")
	private Date endDate;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "employee_role", joinColumns = @JoinColumn(name = "employee_id") , inverseJoinColumns = @JoinColumn(name = "role_id") )
	private Set<Role> roles;

	public Employee(String firstName, String lastName, String email, String phoneNumber, String userName,
			String password, Date startDate, Date endDate, boolean isActive) {
		super(firstName, lastName, email, phoneNumber, isActive);
		this.userName = userName;
		this.password = password;
		this.startDate = new Date();
		this.endDate = endDate;
	}

	public Employee() {
		super();
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void copyValuesFrom(Employee employee, BCryptPasswordEncoder bCryptPasswordEncoder) {
		setFirstName(employee.getFirstName());
		setLastName(employee.getLastName());
		setEmail(employee.getEmail());
		setPhoneNumber(employee.getPhoneNumber());
		this.userName = employee.userName;
		this.password = bCryptPasswordEncoder.encode(employee.password);
		// this.isActive = user.isActive;
	}

	public boolean isAdmin() {
		for (Role role : roles) {
			if (role.getRole().equals(AttributesNames.Role_Admin)) {
				return true;
			}
		}
		return false;
	}

	public void removeRole(Role role) {
		if (roles.contains(role)) {
			roles.remove(role);
		}
	}

	public void addRole(Role role) {
		if (!roles.contains(role)) {
			roles.add(role);
		}
	}

}
