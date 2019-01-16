package com.fblaise.sms.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = { "http://localhost:5000", "http://localhost:4200", "https://sms-vue-ui.herokuapp.com" })
@RequestMapping("/rest")
public class ClassroomRestController {
	@Autowired
	private ClassroomService classroomService;
	@Autowired
	private AdministrationService administrationService;
	@Autowired
	private SchoolYearService schoolYearService;
	@Autowired
	private StudentService studentService;

	@GetMapping("/classrooms")
	public Collection<Classroom> getClassroomList() {
		List<Classroom> listClassrooms = this.classroomService.findAllClassrooms();
		return listClassrooms;
	}

	@GetMapping(path = { "/classroom/{id}" })
	public Classroom getSchoolyearById(@PathVariable("id") Long id) {
		Classroom classroom = classroomService.findClassroomById(id);
		return classroom;
	}

	@PostMapping("/classroom/add")
	public SchoolYear createClassroom(@RequestBody Classroom classroom) {
		return classroomService.createNewSchoolYear(classroom);
	}

	@PutMapping("/classroom/update")
	public Classroom updateClassroom(@RequestBody Classroom classroom) {
		return classroomService.updateClassroom(classroom);
	}

	@DeleteMapping(path = { "classroom/{id}" })
	public Classroom deleteClassroom(@PathVariable("id") Long id) {
		return classroomService.deleteClassroom(id, schoolYearService.findOpenedSchoolYear());
	}

}