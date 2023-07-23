package com.fblaise.sms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fblaise.sms.exception.AddingStudentInManyClassroomException;
import com.fblaise.sms.model.Classroom;
import com.fblaise.sms.model.ClassroomStudent;
import com.fblaise.sms.model.SchoolYear;
import com.fblaise.sms.model.Student;
import com.fblaise.sms.repository.ClassroomRepository;
import com.fblaise.sms.repository.ClassroomStudentRepository;
import com.fblaise.sms.repository.SchoolYearRepository;
import com.fblaise.sms.repository.StudentRepository;

@Service("classroomService")
public class ClassroomServiceImpl implements ClassroomService {

	@Autowired
	private ClassroomRepository classroomRepository;
	@Autowired
	private SchoolYearRepository schoolYearRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ClassroomStudentRepository classroomStudentRepository;

	@Override
	public Classroom findClassroomById(Long id) {
		return classroomRepository.findById(id).get();
	}

	@Override
	public List<Classroom> findAllClassrooms() {
		return classroomRepository.findAll();
	}

	@Override
	public Long saveClassroom(Classroom classroom, SchoolYear schoolYear) {
		classroomRepository.save(classroom);
		schoolYear = schoolYearRepository.findById(schoolYear.getId()).get();
		schoolYear.addClassroom(classroom);
		schoolYearRepository.save(schoolYear);
		return classroom.getId();
	}

	@Override
	public Classroom createNewClassroom(Classroom classroom, String schoolYearName) {
		classroomRepository.save(classroom);
		SchoolYear schoolYear = schoolYearRepository.findByName(schoolYearName);
		schoolYear.addClassroom(classroom);
		schoolYearRepository.save(schoolYear);
		return classroom;
	}

	@Override
	public Classroom updateClassroom(Classroom classroomWithNewValues) {
		Classroom Classroom = classroomRepository.findById(classroomWithNewValues.getId()).get();
		Classroom.copyValuesFrom(classroomWithNewValues);
		return classroomRepository.save(Classroom);
	}

	@Override
	public Classroom deleteClassroom(Long id, SchoolYear currentSchoolYear) {
		currentSchoolYear = schoolYearRepository.findById(currentSchoolYear.getId()).get();
		Classroom classroom = classroomRepository.findById(id).get();
		currentSchoolYear.removeClassroom(classroom);
		schoolYearRepository.save(currentSchoolYear);
		classroomRepository.deleteById(id);
		return classroom;
	}

	@Override
	public Classroom removeStudentToClassroom(Long classroomId, Long studentId, Long schoolYearId) {
		Classroom classroom = classroomRepository.findById(classroomId).get();
		Student student = studentRepository.findById(studentId).get();
		SchoolYear schoolYear = schoolYearRepository.findById(schoolYearId).get();
		ClassroomStudent classroomStudent = classroomStudentRepository.findByStudentAndClassroomAndSchoolYear(student,
				classroom, schoolYear);
		classroom.removeStudent(classroomStudent);
		classroomRepository.save(classroom);
		classroomStudentRepository.deleteByStudentClassroomAndSchoolYear(studentId, classroomId, schoolYearId);
		if (classroom.equals(student.getCurrentClassroom())) {
			student.setCurrentClassroom(null);
			studentRepository.save(student);
		}
		return classroom;
	}

	@Override
	public Classroom addStudentToClassroom(Long classroomId, Long studentId, Long schoolYearId) {
		Classroom classroom = classroomRepository.findById(classroomId).get();
		Student student = studentRepository.findById(studentId).get();
		SchoolYear schoolYear = schoolYearRepository.findById(schoolYearId).get();
		ClassroomStudent classroomStudent = classroomStudentRepository.findByStudentAndSchoolYear(student, schoolYear);
		if (classroomStudent != null) {
			throw new AddingStudentInManyClassroomException();
		}
		student.setCurrentClassroom(classroom);
		classroom.addStudent(student, schoolYear);
		return classroomRepository.save(classroom);

	}

	@Override
	public List<ClassroomStudent> getStudentsOf(Classroom classroom, SchoolYear schoolYear) {
		return classroomStudentRepository.findByClassroomAndSchoolYear(classroom, schoolYear);
	}

	@Override
	public List<Student> getStudentsOf(Classroom classroom, String schoolYearName) {
		SchoolYear schoolYear = schoolYearRepository.findByName(schoolYearName);
		List<Student> students = new ArrayList<>();
		for (ClassroomStudent val : classroomStudentRepository.findByClassroomAndSchoolYear(classroom, schoolYear)) {
			students.add(val.getStudent());
		}
		return students;
	}

	@Override
	public Classroom findClassroomOf(Student student, SchoolYear schoolYear) {
		ClassroomStudent classroomStudent = classroomStudentRepository.findByStudentAndSchoolYear(student, schoolYear);
		return classroomStudent != null ? classroomStudent.getClassroom() : null;
	}

}
