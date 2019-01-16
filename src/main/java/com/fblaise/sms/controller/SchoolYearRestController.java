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

import com.fblaise.sms.model.SchoolYear;
import com.fblaise.sms.service.SchoolYearService;

@RestController
@CrossOrigin(origins = {"http://localhost:5000","http://localhost:4200"})
@RequestMapping("/rest")
public class SchoolYearRestController {
	@Autowired
	private SchoolYearService schoolYearService;
	// @Autowired
	// private AdministrationService administrationService;

	@GetMapping("/schoolyears")
	public Collection<SchoolYear> getSchoolYearList() {
		List<SchoolYear> listSchoolYears = this.schoolYearService.findAllSchoolYears();
		return listSchoolYears;
	}

	@GetMapping(path = { "/schoolyear/{id}" })
	public SchoolYear getSchoolyearById(@PathVariable("id") Long id) {
		SchoolYear schoolYear = schoolYearService.findSchoolYearById(id);
		return schoolYear;
	}

	@PostMapping("/schoolyear/add")
	public SchoolYear createSchoolYear(@RequestBody SchoolYear schoolYear) {
		return schoolYearService.createNewSchoolYear(schoolYear);
	}

	@PutMapping("/schoolyear/update")
	public SchoolYear updateSchoolYear(@RequestBody SchoolYear schoolYear) {
		return schoolYearService.updateSchoolYear(schoolYear);
	}

	@DeleteMapping(path = { "schoolyear/{id}" })
	public SchoolYear deleteSchoolYear(@PathVariable("id") Long id) {
		return schoolYearService.deleteSchoolYear(id);
	}

	@PutMapping("/schoolyear/open/{id}")
	public SchoolYear openSchoolYear(@PathVariable("id") Long schoolYearId) {
		return schoolYearService.openSchoolYear(schoolYearId);
	}

	@PutMapping("/schoolyear/close/{id}")
	public SchoolYear closeSchoolYear(@PathVariable("id") Long schoolYearId) {
		return schoolYearService.closeSchoolYear(schoolYearId);
	}

	// @RequestMapping(value = "/schoolyear.part.save", method =
	// RequestMethod.POST)
	// public String saveSchoolYearPart(@ModelAttribute("schoolYearPart")
	// UiSchoolYearPart uiSchoolYearPart, Model model) {
	// SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
	// SchoolYear schoolYear =
	// schoolYearService.findSchoolYearById(uiSchoolYearPart.getSchoolYearId());
	// try {
	// if (uiSchoolYearPart.getId() != null) {
	// schoolYear =
	// schoolYearService.editSchoolYearPart(uiSchoolYearPart.getId(),
	// uiSchoolYearPart.getName(),
	// dfm.parse(uiSchoolYearPart.getStartDate()),
	// dfm.parse(uiSchoolYearPart.getEndDate()));
	// } else {
	// schoolYear = schoolYearService.addSchoolYearPart(schoolYear,
	// uiSchoolYearPart.getName(),
	// dfm.parse(uiSchoolYearPart.getStartDate()),
	// dfm.parse(uiSchoolYearPart.getEndDate()));
	// }
	// } catch (ParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return "redirect:/schoolyear.view.htm/" + schoolYear.getId();
	// }
	//
	// @RequestMapping(value = "/schoolyear.part.remove", method =
	// RequestMethod.POST)
	// public String removeSchoolYearPart(@ModelAttribute("forDeleteAction")
	// SmsUiObject forDeleteAction, Model model) {
	// SchoolYear schoolYear =
	// schoolYearService.removeSchoolYearPart(forDeleteAction.getParentId(),
	// forDeleteAction.getId());
	// return "redirect:/schoolyear.view.htm/" + schoolYear.getId();
	// }

	//
	// @RequestMapping(value = "/schoolyear.classroom.add", method =
	// RequestMethod.POST)
	// public String addClassroomToSchoolYear(@ModelAttribute("forDeleteAction")
	// SmsUiObject forDeleteAction,
	// Model model) {
	// SchoolYear schoolYear =
	// schoolYearService.addClassroomToSchoolYear(forDeleteAction.getParentId(),
	// forDeleteAction.getId());
	// return "redirect:/classroom.view.htm/" + schoolYear.getId();
	// }
	//
	// @RequestMapping(value = "/schoolyear.classroom.remove", method =
	// RequestMethod.POST)
	// public String
	// removeClassroomToSchoolYear(@ModelAttribute("forDeleteAction")
	// SmsUiObject forDeleteAction,
	// Model model) {
	// SchoolYear schoolYear =
	// schoolYearService.removeClassroomToSchoolYear(forDeleteAction.getParentId(),
	// forDeleteAction.getId());
	// return "redirect:/schoolyear.view.htm/" + schoolYear.getId();
	// }
	//
	// private void getTheUserConnected(Model model, HttpServletRequest request)
	// {
	// Authentication auth =
	// SecurityContextHolder.getContext().getAuthentication();
	// Employee userConnected =
	// administrationService.findUserByUserName(auth.getName());
	// model.addAttribute("currentUser", userConnected);
	// model.addAttribute("userName", userConnected.getFirstName() + " " +
	// userConnected.getLastName() + " ("
	// + userConnected.getUserName() + ")");
	// model.addAttribute("listSchoolYears",
	// schoolYearService.findAllSchoolYears());
	// SchoolYear selectedSchoolYear = (SchoolYear)
	// request.getSession().getAttribute("currentSchoolYear");
	// SmsConfigObject configObject = new SmsConfigObject(selectedSchoolYear);
	// model.addAttribute("smsConfigObject", configObject);
	// }

}