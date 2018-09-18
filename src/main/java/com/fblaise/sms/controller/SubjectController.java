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

import com.fblaise.sms.model.Employee;
import com.fblaise.sms.model.SchoolYear;
import com.fblaise.sms.model.SmsConfigObject;
import com.fblaise.sms.model.SmsUiObject;
import com.fblaise.sms.model.Subject;
import com.fblaise.sms.service.AdministrationService;
import com.fblaise.sms.service.SchoolYearService;
import com.fblaise.sms.service.SubjectService;

@Controller
public class SubjectController {
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private AdministrationService administrationService;
	@Autowired
	private SchoolYearService schoolYearService;

	@RequestMapping(value = "/subjects", method = RequestMethod.GET)
	public String getSubjectList(Model model, HttpServletRequest request) {
		List<Subject> listSubjects = this.subjectService.findAllSubjects();
		model.addAttribute("listSubjects", listSubjects);
		SmsUiObject forDeleteAction = new SmsUiObject();
		model.addAttribute("forDeleteAction", forDeleteAction);
		getTheUserConnected(model, request);
		return "subject/SubjectsList";
	}

	@RequestMapping(value = "/subject.add.htm", method = RequestMethod.GET)
	public String getSubjectAddPage(Model model, HttpServletRequest request) {
		model.addAttribute("subject", new Subject());
		getTheUserConnected(model, request);
		return "subject/SubjectAdd";
	}

	@RequestMapping(value = "/subject/save", method = RequestMethod.POST)
	public String saveSubject(@ModelAttribute("subject") Subject Subject) {
		Long subjectId = null;
		if (Subject.getId() == null) {
			subjectId = this.subjectService.saveSubject(Subject);
		} else {
			subjectId = this.subjectService.updateSubject(Subject);
		}

		return "redirect:/subject.view.htm/" + subjectId;

	}

	@RequestMapping(value = "/subject.remove")
	public String deleteSubject(Model model, @ModelAttribute("forDeleteAction") SmsUiObject forDeleteAction) {
		this.subjectService.deleteSubject(forDeleteAction.getId());
		return "redirect:/subjects";

	}

	@RequestMapping("/subject.edit.htm/{id}")
	public String getEditSubjectPage(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		Subject Subject = subjectService.findSubjectById(id);
		model.addAttribute("subject", Subject);
		getTheUserConnected(model, request);
		return "subject/SubjectEdit";
	}

	@RequestMapping("/subject.view.htm/{id}")
	public String viewSubject(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		Subject Subject = subjectService.findSubjectById(id);
		model.addAttribute("subject", Subject);
		getTheUserConnected(model, request);
		return "subject/SubjectDetails";
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