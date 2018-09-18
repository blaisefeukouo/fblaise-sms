package com.fblaise.sms.service;

import java.util.List;

import com.fblaise.sms.model.Student;

public interface StudentService {

	public Student findStudentById(Long id);

	public List<Student> findAllStudents();

	public Long saveStudent(Student Student);

	public Long updateStudent(Student StudentWithNewValues);

	public void deleteStudent(Long id);

}