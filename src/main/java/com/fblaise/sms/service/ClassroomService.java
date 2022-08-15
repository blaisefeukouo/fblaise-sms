package com.fblaise.sms.service;

import java.util.List;

import com.fblaise.sms.model.Classroom;
import com.fblaise.sms.model.ClassroomStudent;
import com.fblaise.sms.model.SchoolYear;
import com.fblaise.sms.model.Student;

public interface ClassroomService {

	public Classroom findClassroomById(Long id);

	public List<Classroom> findAllClassrooms();

	public Long saveClassroom(Classroom classroom, SchoolYear schoolYear);

	public Classroom updateClassroom(Classroom classroomWithNewValues);

	public Classroom deleteClassroom(Long id, SchoolYear currentSchoolYear);

	public Classroom removeStudentToClassroom(Long classroomId, Long studentId, Long schoolYearId);

	public Classroom addStudentToClassroom(Long classroomId, Long studentId, Long schoolYearId);

	public List<ClassroomStudent> getStudentsOf(Classroom classroom, SchoolYear schoolYear);

	public List<Student> getStudentsOf(Classroom classroom, String schoolYearName);

	public Classroom findClassroomOf(Student student, SchoolYear schoolYear);

	public Classroom createNewClassroom(Classroom classroom, String schoolYearName);

}
