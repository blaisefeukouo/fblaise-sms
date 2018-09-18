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
	private StudentRepository StudentRepository;

	@Override
	public Student findStudentById(Long id) {
		return StudentRepository.findById(id);
	}

	@Override
	public List<Student> findAllStudents() {
		return StudentRepository.findAll();
	}

	@Override
	public Long saveStudent(Student student) {
		student.setEntranceDate(new Date());
		student = StudentRepository.save(student);
		return student.getId();
	}

	@Override
	public Long updateStudent(Student StudentWithNewValues) {
		Student Student = StudentRepository.findById(StudentWithNewValues.getId());
		Student.copyValuesFrom(StudentWithNewValues);
		StudentRepository.save(Student);
		return Student.getId();
	}

	@Override
	public void deleteStudent(Long id) {
		StudentRepository.delete(id);
	}

}
