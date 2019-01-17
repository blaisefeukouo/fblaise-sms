package com.fblaise.sms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "classroom")
public class Classroom extends SmsDomain {

	private String name;

	private String description;

	private double fees;
	
	@JsonIgnore
	@OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ClassroomStudent> students = new ArrayList<>();

	public Classroom() {
	}

	public Classroom(String name, String description, double fees) {
		super();
		this.name = name;
		this.description = description;
		this.fees = fees;
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

	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}

	public List<ClassroomStudent> getStudents() {
		return students;
	}

	public void setStudents(List<ClassroomStudent> studens) {
		this.students = studens;
	}

	public void removeStudent(ClassroomStudent classroomStudent) {
		if (students.contains(classroomStudent)) {
			students.remove(classroomStudent);
		}
	}

	public void addStudent(Student student, SchoolYear schoolYear) {
		ClassroomStudent classroomStudent = new ClassroomStudent(this, student, schoolYear);
		for (ClassroomStudent pupil : students) {
			if (pupil.getStudent() == student && pupil.getSchoolYear() == schoolYear) {
				return;
			}
		}
		students.add(classroomStudent);
	}

	public void copyValuesFrom(Classroom classroom) {
		this.name = classroom.name;
		this.description = classroom.description;
		this.fees = classroom.fees;
	}

}
