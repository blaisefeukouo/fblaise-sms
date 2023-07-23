package com.fblaise.sms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fblaise.sms.model.Classroom;
import com.fblaise.sms.model.ClassroomStudent;
import com.fblaise.sms.model.SchoolYear;
import com.fblaise.sms.model.Student;

@Repository("classroomStudentRepository")
@CrossOrigin(origins = "http://localhost:4200")
public interface ClassroomStudentRepository extends JpaRepository<ClassroomStudent, Long> {

	List<ClassroomStudent> findByClassroomAndSchoolYear(Classroom classroom, SchoolYear schoolYear);

	ClassroomStudent findByStudentAndSchoolYear(Student student, SchoolYear schoolYear);

	ClassroomStudent findByStudentAndClassroom(Student student, Classroom classroom);

	ClassroomStudent findByStudentAndClassroomAndSchoolYear(Student student, Classroom classroom,
			SchoolYear schoolYear);

	Optional<ClassroomStudent> findById(Long id);

	@Transactional
	@Modifying
	@Query("DELETE FROM ClassroomStudent c where c.student.id=:studentId AND c.classroom.id=:classroomId AND c.schoolYear.id=:schoolYearId")
	void deleteByStudentClassroomAndSchoolYear(@Param("studentId") Long studentId,
			@Param("classroomId") Long classroomId, @Param("schoolYearId") Long schoolYearId);
}