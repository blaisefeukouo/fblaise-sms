package com.fblaise.sms.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "classroom_student")
public class ClassroomStudent extends SmsDomain {
	@ManyToOne()
	@JoinColumn(name = "classroom_id", nullable = false)
	private Classroom classroom;
	@ManyToOne()
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;
	@ManyToOne()
	@JoinColumn(name = "schoolyear_id", nullable = false)
	private SchoolYear schoolYear;

	public ClassroomStudent() {
		super();
	}

	public ClassroomStudent(Classroom classroom, Student student, SchoolYear schoolYear) {
		super();
		this.classroom = classroom;
		this.student = student;
		this.schoolYear = schoolYear;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public SchoolYear getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(SchoolYear schoolYear) {
		this.schoolYear = schoolYear;
	}

}
