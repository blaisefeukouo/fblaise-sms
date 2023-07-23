package com.fblaise.sms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fblaise.sms.model.Student;
import com.fblaise.sms.repository.StudentRepository;

@Service("studentServiceImpl")
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public Student findStudentById(Long id) {
		return studentRepository.findById(id).get();
	}

	@Override
	public List<Student> findAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Student saveStudent(Student student) {
		student.setEntranceDate(new Date());
		student = studentRepository.save(student);
		return student;
	}

	@Override
	public Student updateStudent(Student StudentWithNewValues) {
		Student student = studentRepository.findById(StudentWithNewValues.getId()).get();
		student.copyValuesFrom(StudentWithNewValues);
		studentRepository.save(student);
		return student;
	}

	@Override
	public void deleteStudent(Long id) {
		studentRepository.deleteById(id);
	}

}
