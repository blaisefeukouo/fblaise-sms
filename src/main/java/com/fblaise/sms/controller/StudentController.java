package com.fblaise.sms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fblaise.sms.model.Classroom;
import com.fblaise.sms.model.Employee;
import com.fblaise.sms.model.SchoolYear;
import com.fblaise.sms.model.SmsConfigObject;
import com.fblaise.sms.model.SmsUiObject;
import com.fblaise.sms.model.Student;
import com.fblaise.sms.service.AdministrationService;
import com.fblaise.sms.service.ClassroomService;
import com.fblaise.sms.service.SchoolYearService;
import com.fblaise.sms.service.StudentService;

@Controller
public class StudentController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private AdministrationService administrationService;
	@Autowired
	private SchoolYearService schoolYearService;
	@Autowired
	private ClassroomService classroomService;

	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public String getStudentList(Model model, HttpServletRequest request) {
		List<Student> listStudents = this.studentService.findAllStudents();
		model.addAttribute("listStudents", listStudents);
		SmsUiObject forDeleteAction = new SmsUiObject();
		model.addAttribute("forDeleteAction", forDeleteAction);
		getTheUserConnected(model, request);
		return "student/StudentsList";
	}

	@RequestMapping(value = "/student.add.htm", method = RequestMethod.GET)
	public String getStudentAddPage(Model model, HttpServletRequest request) {
		model.addAttribute("student", new Student());
		getTheUserConnected(model, request);
		return "student/StudentAdd";
	}

	@RequestMapping(value = "/student/save", method = RequestMethod.POST)
	public String saveStudent(@ModelAttribute("Student") Student student) {
		Student savedStudent = null;
		if (student.getId() == null) {
			savedStudent = this.studentService.saveStudent(student);
		} else {
			savedStudent = this.studentService.updateStudent(student);
		}

		return "redirect:/student.view.htm/" + savedStudent.getId();

	}

	@RequestMapping(value = "/student.remove")
	public String deleteStudent(Model model, @ModelAttribute("forDeleteAction") SmsUiObject forDeleteAction) {
		this.studentService.deleteStudent(forDeleteAction.getId());
		return "redirect:/students";

	}

	@RequestMapping("/student.edit.htm/{id}")
	public String getEditStudentPage(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		Student student = studentService.findStudentById(id);
		model.addAttribute("student", student);
		getTheUserConnected(model, request);
		return "student/StudentEdit";
	}

	@RequestMapping("/student.view.htm/{id}")
	public String viewStudent(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		Student student = studentService.findStudentById(id);
		model.addAttribute("student", student);
		SchoolYear currentSchoolYear = (SchoolYear) request.getSession().getAttribute("currentSchoolYear");
		Classroom claassroom = classroomService.findClassroomOf(student, currentSchoolYear);
		model.addAttribute("classroom", claassroom);
		getTheUserConnected(model, request);
		return "student/StudentDetails";
	}

	private void getTheUserConnected(Model model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee userConnected = administrationService.findUserByUserName(auth.getName());
		model.addAttribute("currentUser", userConnected);
		model.addAttribute("userName", userConnected.getFirstName() + " " + userConnected.getLastName() + " ("
				+ userConnected.getUserName() + ")");
		model.addAttribute("listSchoolYears", schoolYearService.findAllSchoolYears());
		SchoolYear selectedSchoolYear = (SchoolYear) request.getSession().getAttribute("currentSchoolYear");
		SmsConfigObject configObject = new SmsConfigObject(selectedSchoolYear);
		model.addAttribute("smsConfigObject", configObject);
	}

}
