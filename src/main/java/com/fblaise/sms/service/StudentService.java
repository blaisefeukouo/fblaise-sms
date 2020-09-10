package com.fblaise.sms.service;

import java.util.List;

import com.fblaise.sms.model.Student;

public interface StudentService {

	public Student findStudentById(Long id);

	public List<Student> findAllStudents();

	public Student saveStudent(Student Student);

	public Student updateStudent(Student StudentWithNewValues);

	public void deleteStudent(Long id);

}
