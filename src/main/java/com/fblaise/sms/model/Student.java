package com.fblaise.sms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Student extends Person {
	// @NotEmpty(message = "*Please provide your user name")
	@Column(name = "matricule", unique = true)
	private String matricule;
	@Column(name = "fatherphonenumber")
	private String fatherPhoneNumber;
	@Column(name = "motherphonenumber")
	private String motherPhoneNumber;
	@Column(name = "entrancedate")
	private Date entranceDate;

	@JsonIgnore
	@OneToMany(mappedBy = "student", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private List<ClassroomStudent> classrooms = new ArrayList<>();

	public Student(String firstName, String lastName, String email, String phoneNumber, String matricule,
			String fatherPhoneNumber, String motherPhoneNumber, boolean isActive) {
		super(firstName, lastName, email, phoneNumber, isActive);
		this.matricule = matricule;
		this.fatherPhoneNumber = fatherPhoneNumber;
		this.motherPhoneNumber = motherPhoneNumber;
		this.entranceDate = new Date();
	}

	public Student() {
		super();
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getFatherPhoneNumber() {
		return fatherPhoneNumber;
	}

	public void setFatherPhoneNumber(String fatherPhoneNumber) {
		this.fatherPhoneNumber = fatherPhoneNumber;
	}

	public String getMotherPhoneNumber() {
		return motherPhoneNumber;
	}

	public void setMotherPhoneNumber(String motherPhoneNumber) {
		this.motherPhoneNumber = motherPhoneNumber;
	}

	public Date getEntranceDate() {
		return entranceDate;
	}

	public void setEntranceDate(Date entranceDate) {
		this.entranceDate = entranceDate;
	}

	public List<ClassroomStudent> getClassrooms() {
		return classrooms;
	}

	public void setClassrooms(List<ClassroomStudent> classrooms) {
		this.classrooms = classrooms;
	}
	//
	// public void removeClassroom(Classroom classroom, SchoolYear schoolYear) {
	// ClassroomStudent classroomStudent = new ClassroomStudent(classroom, this,
	// schoolYear);
	// if (classrooms.contains(classroomStudent)) {
	// classrooms.remove(classroomStudent);
	// }
	// }
	//
	// public void addClassroom(Classroom classroom, SchoolYear schoolYear) {
	// ClassroomStudent classroomStudent = new ClassroomStudent(classroom, this,
	// schoolYear);
	// if (!classrooms.contains(classroomStudent)) {
	// classrooms.add(classroomStudent);
	// }
	// }

	public void copyValuesFrom(Student student) {
		setFirstName(student.getFirstName());
		setLastName(student.getLastName());
		setEmail(student.getEmail());
		setPhoneNumber(student.getPhoneNumber());
		this.matricule = student.matricule;
		this.fatherPhoneNumber = student.fatherPhoneNumber;
		this.motherPhoneNumber = student.motherPhoneNumber;
	}

}
