package com.fblaise.sms.controller;

import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.fblaise.sms.exception.AddingStudentInManyClassroomException;
import com.fblaise.sms.model.Classroom;
import com.fblaise.sms.model.ClassroomStudent;
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
public class ClassroomController {
	@Autowired
	private ClassroomService classroomService;
	@Autowired
	private AdministrationService administrationService;
	@Autowired
	private SchoolYearService schoolYearService;
	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "/classrooms", method = RequestMethod.GET)
	public String getClassroomList(Model model, HttpServletRequest request) {
		SchoolYear currentSchoolYear = (SchoolYear) request.getSession().getAttribute("currentSchoolYear");
		Set<Classroom> listClassrooms = schoolYearService.findSchoolYearById(currentSchoolYear.getId()).getClassrooms();
		model.addAttribute("listClassrooms", listClassrooms);
		SmsUiObject forDeleteAction = new SmsUiObject();
		model.addAttribute("forDeleteAction", forDeleteAction);
		getTheUserConnected(model, request);
		return "classroom/ClassroomsList";
	}

	@RequestMapping(value = "/classroom.add.htm", method = RequestMethod.GET)
	public String getClassroomAddPage(Model model, HttpServletRequest request) {
		model.addAttribute("classroom", new Classroom());
		getTheUserConnected(model, request);
		return "classroom/ClassroomAdd";
	}

	// For add and update Classroom both
	@RequestMapping(value = "/classroom/save", method = RequestMethod.POST)
	public String saveClassroom(@ModelAttribute("classroom") Classroom classroom, HttpServletRequest request) {
		Long classroomId;
		if (classroom.getId() == null) {
			// new person, add it
			SchoolYear selectedSchoolYear = (SchoolYear) request.getSession().getAttribute("currentSchoolYear");
			classroomId = this.classroomService.saveClassroom(classroom, selectedSchoolYear);
		} else {
			// existing person, call update
			classroomId = this.classroomService.updateClassroom(classroom);
		}

		return "redirect:/classroom.view.htm/" + classroomId;

	}

	@RequestMapping(value = "/classroom.remove")
	public String deleteClassroom(Model model, @ModelAttribute("forDeleteAction") SmsUiObject forDeleteAction,
			HttpServletRequest request) {
		SchoolYear currentSchoolYear = (SchoolYear) request.getSession().getAttribute("currentSchoolYear");
		this.classroomService.deleteClassroom(forDeleteAction.getId(), currentSchoolYear);
		return "redirect:/classrooms";

	}

	@RequestMapping("/classroom.edit.htm/{id}")
	public String editClassroom(@PathVariable("id") Long classroomId, Model model, HttpServletRequest request) {
		model.addAttribute("classroom", classroomService.findClassroomById(classroomId));
		getTheUserConnected(model, request);
		return "classroom/ClassroomEdit";
	}

	@RequestMapping("/classroom.view.htm/{id}")
	public String viewClassroom(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		SchoolYear currentSchoolYear = (SchoolYear) request.getSession().getAttribute("currentSchoolYear");
		Classroom classroom = this.classroomService.findClassroomById(id);
		model.addAttribute("classroom", classroom);
		List<ClassroomStudent> studentsList = classroomService.getStudentsOf(classroom, currentSchoolYear);
		List<Student> allStudents = studentService.findAllStudents();
		model.addAttribute("studentsList", studentsList);
		model.addAttribute("allStudents", allStudents);
		SmsUiObject forDeleteAction = new SmsUiObject();
		model.addAttribute("forDeleteAction", forDeleteAction);
		getTheUserConnected(model, request);
		return "classroom/ClassroomDetails";
	}

	@RequestMapping(value = "/classroom.student.pick", method = RequestMethod.GET)
	public String getStudentPickerForClassroomPage(@RequestParam("classroomId") Long classroomId, Model model,
			HttpServletRequest request) {
		SmsUiObject forStudentPick = new SmsUiObject();
		forStudentPick.setParentId(classroomId);
		model.addAttribute("forStudentPick", forStudentPick);
		List<Student> allStudents = studentService.findAllStudents();
		model.addAttribute("allStudents", allStudents);
		model.addAttribute("studentAlreadyHaveClassroom", false);
		getTheUserConnected(model, request);
		return "classroom/StudentPickerForClassroom";
	}

	@RequestMapping(value = "/classroom.student.pick.cancel", method = RequestMethod.GET)
	public String cancelStudentPickerForClassroomPage(@RequestParam("classroomId") Long classroomId, Model model) {
		return "redirect:/classroom.view.htm/" + classroomId;
	}

	@RequestMapping(value = "/classroom.student.add", method = RequestMethod.POST)
	public String addStudentToClassroom(@ModelAttribute("forStudentPick") SmsUiObject forStudentPick, Model model,
			HttpServletRequest request) {
		SchoolYear currentSchoolYear = (SchoolYear) request.getSession().getAttribute("currentSchoolYear");
		Long classroomId = forStudentPick.getParentId();
		Long studentId = forStudentPick.getId();
		Classroom classroom = null;
		try {
			classroom = classroomService.addStudentToClassroom(classroomId, studentId, currentSchoolYear.getId());
		} catch (AddingStudentInManyClassroomException e) {
			forStudentPick = new SmsUiObject();
			forStudentPick.setParentId(classroomId);
			model.addAttribute("forStudentPick", forStudentPick);
			List<Student> allStudents = studentService.findAllStudents();
			model.addAttribute("allStudents", allStudents);
			model.addAttribute("studentAlreadyHaveClassroom", true);
			model.addAttribute("errorMessage", e.getMessage());
			getTheUserConnected(model, request);
			return "classroom/StudentPickerForClassroom";
		}
		return "redirect:/classroom.view.htm/" + classroom.getId();
	}

	@RequestMapping(value = "/classroom.student.remove", method = RequestMethod.POST)
	public String removeStudentToClassroom(@ModelAttribute("forDeleteAction") SmsUiObject forDeleteAction, Model model,
			HttpServletRequest request) {
		SchoolYear currentSchoolYear = (SchoolYear) request.getSession().getAttribute("currentSchoolYear");
		Long classroomId = forDeleteAction.getParentId();
		Long studentId = forDeleteAction.getId();
		Classroom classroom = classroomService.removeStudentToClassroom(classroomId, studentId,
				currentSchoolYear.getId());
		return "redirect:/classroom.view.htm/" + classroom.getId();
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