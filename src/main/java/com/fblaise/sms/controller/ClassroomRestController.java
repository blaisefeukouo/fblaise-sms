package com.fblaise.sms.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fblaise.sms.model.ClassroomStudent;
import com.fblaise.sms.model.Student;
import com.fblaise.sms.model.pojo.ClassroomWithStudents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fblaise.sms.model.Classroom;
import com.fblaise.sms.model.SchoolYear;
import com.fblaise.sms.service.AdministrationService;
import com.fblaise.sms.service.ClassroomService;
import com.fblaise.sms.service.SchoolYearService;
import com.fblaise.sms.service.StudentService;

@RestController
@CrossOrigin(origins = {"http://localhost:5000", "http://localhost:4200", "https://sms-vue-ui.herokuapp.com", "https://sms-angular-ui.herokuapp.com"})
@RequestMapping("/rest/classrooms")
public class ClassroomRestController {
	@Autowired
	private ClassroomService classroomService;
	@Autowired
	private AdministrationService administrationService;
	@Autowired
	private SchoolYearService schoolYearService;
	@Autowired
	private StudentService studentService;

	@GetMapping("/list")
	public Collection<Classroom> getClassroomList() {
		List<Classroom> listClassrooms = this.classroomService.findAllClassrooms();
		return listClassrooms;
	}

	@GetMapping("/view.htm/{classroomId}/{currentSchoolYearName}")
	public ClassroomWithStudents getClassroomById(@PathVariable("classroomId") Long classroomId, @PathVariable("currentSchoolYearName") String currentSchoolYearName) {
		Classroom classroom = classroomService.findClassroomById(classroomId);
        List<Student> studentsList = classroomService.getStudentsOf(classroom, currentSchoolYearName);
		return new ClassroomWithStudents(classroom, studentsList);
	}

	@PostMapping("/save/{schoolYearName}")
	public Classroom createClassroom(@RequestBody Classroom classroom, @PathVariable("schoolYearName") String schoolYearName) {
		return classroomService.createNewClassroom(classroom, schoolYearName);
	}

	@PutMapping("/update")
	public Classroom updateClassroom(@RequestBody Classroom classroom) {
		return classroomService.updateClassroom(classroom);
	}

	@DeleteMapping(path = { "/remove/{id}" })
	public Classroom deleteClassroom(@PathVariable("id") Long id) {
		return classroomService.deleteClassroom(id, schoolYearService.findOpenedSchoolYear());
	}

}
