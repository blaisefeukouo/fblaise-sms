package com.fblaise.sms.service;

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
		return classroomRepository.findById(id);
	}

	@Override
	public List<Classroom> findAllClassrooms() {
		return classroomRepository.findAll();
	}

	@Override
	public Long saveClassroom(Classroom classroom, SchoolYear schoolYear) {
		classroomRepository.save(classroom);
		schoolYear = schoolYearRepository.findById(schoolYear.getId());
		schoolYear.addClassroom(classroom);
		schoolYearRepository.save(schoolYear);
		return classroom.getId();
	}

	@Override
	public Classroom createNewClassroom(Classroom classroom) {
		classroomRepository.save(classroom);
		SchoolYear schoolYear = schoolYearRepository.findById(1L);
		schoolYear.addClassroom(classroom);
		schoolYearRepository.save(schoolYear);
		return classroom;
	}

	@Override
	public Classroom updateClassroom(Classroom classroomWithNewValues) {
		Classroom Classroom = classroomRepository.findById(classroomWithNewValues.getId());
		Classroom.copyValuesFrom(classroomWithNewValues);
		return classroomRepository.save(Classroom);
	}

	@Override
	public Classroom deleteClassroom(Long id, SchoolYear currentSchoolYear) {
		currentSchoolYear = schoolYearRepository.findById(currentSchoolYear.getId());
		Classroom classroom = classroomRepository.findById(id);
		currentSchoolYear.removeClassroom(classroom);
		schoolYearRepository.save(currentSchoolYear);
		classroomRepository.delete(id);
		return classroom;
	}

	@Override
	public Classroom removeStudentToClassroom(Long classroomId, Long studentId, Long schoolYearId) {
		Classroom classroom = classroomRepository.findById(classroomId);
		Student student = studentRepository.findById(studentId);
		SchoolYear schoolYear = schoolYearRepository.findById(schoolYearId);
		ClassroomStudent classroomStudent = classroomStudentRepository.findByStudentAndClassroomAndSchoolYear(student,
				classroom, schoolYear);
		classroom.removeStudent(classroomStudent);
		classroomRepository.save(classroom);
		classroomStudentRepository.deleteByStudentClassroomAndSchoolYear(studentId, classroomId, schoolYearId);
		return classroom;
	}

	@Override
	public Classroom addStudentToClassroom(Long classroomId, Long studentId, Long schoolYearId) {
		Classroom classroom = classroomRepository.findById(classroomId);
		Student student = studentRepository.findById(studentId);
		SchoolYear schoolYear = schoolYearRepository.findById(schoolYearId);
		ClassroomStudent classroomStudent = classroomStudentRepository.findByStudentAndSchoolYear(student, schoolYear);
		if (classroomStudent != null) {
			throw new AddingStudentInManyClassroomException();
		}
		classroom.addStudent(student, schoolYear);
		return classroomRepository.save(classroom);

	}

	@Override
	public List<ClassroomStudent> getStudentsOf(Classroom classroom, SchoolYear schoolYear) {
		return classroomStudentRepository.findByClassroomAndSchoolYear(classroom, schoolYear);
	}

	@Override
	public Classroom findClassroomOf(Student student, SchoolYear schoolYear) {
		ClassroomStudent classroomStudent = classroomStudentRepository.findByStudentAndSchoolYear(student, schoolYear);
		return classroomStudent != null ? classroomStudent.getClassroom() : null;
	}


}
