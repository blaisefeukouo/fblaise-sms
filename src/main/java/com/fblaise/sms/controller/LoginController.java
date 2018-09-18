package com.fblaise.sms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fblaise.sms.model.Employee;
import com.fblaise.sms.model.SchoolYear;
import com.fblaise.sms.model.SmsConfigObject;
import com.fblaise.sms.service.AdministrationService;
import com.fblaise.sms.service.SchoolYearService;

@Controller
public class LoginController {

	@Autowired
	private AdministrationService administrationService;
	@Autowired
	private SchoolYearService schoolYearService;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Employee employee = new Employee();
		modelAndView.addObject("user", employee);
		modelAndView.setViewName("login");
		SchoolYear openedSchoolYear = schoolYearService.findOpenedSchoolYear();
		request.getSession().setAttribute("currentSchoolYear", openedSchoolYear);
		return modelAndView;
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee userConnected = administrationService.findUserByUserName(auth.getName());
		modelAndView.addObject("currentUser", userConnected);
		modelAndView.addObject("userName", userConnected.getFirstName() + " " + userConnected.getLastName() + " ("
				+ userConnected.getUserName() + ")");
		modelAndView.addObject("adminMessage", "Content Available Only for employees with Admin Role");
		modelAndView.addObject("listSchoolYears", schoolYearService.findAllSchoolYears());
		SchoolYear currentSchoolYear = (SchoolYear) request.getSession().getAttribute("currentSchoolYear");
		SmsConfigObject smsConfigObject = new SmsConfigObject(currentSchoolYear);
		modelAndView.addObject("smsConfigObject", smsConfigObject);
		modelAndView.setViewName("home");
		return modelAndView;
	}

	@RequestMapping(value = "/config/selectedSchooYear")
	public String setTheSelectedSchoolYear(HttpServletRequest request, Model model,
			@ModelAttribute("smsConfigObject") SmsConfigObject smsConfigObject) {
		request.getSession().setAttribute("currentSchoolYear", smsConfigObject.getCurrentSchoolYear());
		return "redirect:/home";
	}

	@GetMapping(value = { "/access-denied" })
	public String accessDenied() {
		return "error/AccessDenied";
	}

}
