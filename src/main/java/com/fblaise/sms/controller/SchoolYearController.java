package com.fblaise.sms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.fblaise.sms.model.Classroom;
import com.fblaise.sms.model.Employee;
import com.fblaise.sms.model.SchoolYear;
import com.fblaise.sms.model.SchoolYearPart;
import com.fblaise.sms.model.SmsConfigObject;
import com.fblaise.sms.model.SmsUiObject;
import com.fblaise.sms.model.UiSchoolYear;
import com.fblaise.sms.model.UiSchoolYearPart;
import com.fblaise.sms.service.AdministrationService;
import com.fblaise.sms.service.SchoolYearService;

@Controller
public class SchoolYearController {
	@Autowired
	private SchoolYearService schoolYearService;
	@Autowired
	private AdministrationService administrationService;

	@RequestMapping(value = "/schoolyears", method = RequestMethod.GET)
	public String getSchoolYearList(Model model, HttpServletRequest request) {
		List<SchoolYear> listSchoolYears = this.schoolYearService.findAllSchoolYears();
		model.addAttribute("listSchoolYears", listSchoolYears);
		SmsUiObject forDeleteAction = new SmsUiObject();
		model.addAttribute("forDeleteAction", forDeleteAction);
		getTheUserConnected(model, request);
		return "schoolyear/SchoolYearsList";
	}

	@RequestMapping(value = "/schoolyear.add.htm", method = RequestMethod.GET)
	public String getSchoolYearAddPage(Model model, HttpServletRequest request) {
		model.addAttribute("schoolYear", new UiSchoolYear());
		getTheUserConnected(model, request);
		return "schoolyear/SchoolYearAdd";
	}

	@RequestMapping(value = "/schoolyear/save", method = RequestMethod.POST)
	public String saveSchoolYear(@ModelAttribute("schoolYear") UiSchoolYear uiSchoolYear) {
		Long schoolYearId = null;
		SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
		try {
			SchoolYear schoolYear = new SchoolYear(uiSchoolYear.getName(), dfm.parse(uiSchoolYear.getStartDate()),
					dfm.parse(uiSchoolYear.getEndDate()));
			if (uiSchoolYear.getId() == null) {
				schoolYearId = this.schoolYearService.saveSchoolYear(schoolYear);
			} else {
				schoolYear.setId(uiSchoolYear.getId());
				schoolYearId = this.schoolYearService.updateSchoolYear(schoolYear);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "redirect:/schoolyear.view.htm/" + schoolYearId;

	}

	@RequestMapping(value = "/schoolyear.remove")
	public String deleteSchoolYear(Model model, @ModelAttribute("forDeleteAction") SmsUiObject forDeleteAction) {
		this.schoolYearService.deleteSchoolYear(forDeleteAction.getId());
		return "redirect:/schoolyears";

	}

	@RequestMapping("/schoolyear.edit.htm/{id}")
	public String getEditSchoolYearPage(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		SchoolYear schoolYear = schoolYearService.findSchoolYearById(id);
		SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
		UiSchoolYear uiSchoolYear = new UiSchoolYear(schoolYear.getId(), schoolYear.getName(),
				dfm.format(schoolYear.getStartDate()), dfm.format(schoolYear.getEndDate()), schoolYear.getState());
		uiSchoolYear.setRealStartDate(schoolYear.getStartDate());
		uiSchoolYear.setRealEndDate(schoolYear.getEndDate());
		model.addAttribute("schoolYear", uiSchoolYear);
		getTheUserConnected(model, request);
		return "schoolyear/SchoolYearEdit";
	}

	@RequestMapping("/schoolyear.view.htm/{id}")
	public String viewSchoolYear(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		SchoolYear schoolYear = schoolYearService.findSchoolYearById(id);
		model.addAttribute("schoolYear", schoolYear);
		model.addAttribute("forDeleteAction", new SmsUiObject());
		UiSchoolYearPart uiSchoolYearPart = new UiSchoolYearPart(schoolYear.getId());
		model.addAttribute("schoolYearPart", uiSchoolYearPart);
		List<SchoolYearPart> schoolYearPartsList = schoolYear.getSchoolYearParts();
		Set<Classroom> classrooms = schoolYear.getClassrooms();
		model.addAttribute("schoolYearPartsList", schoolYearPartsList);
		model.addAttribute("classrooms", classrooms);
		getTheUserConnected(model, request);
		return "schoolyear/SchoolYearDetails";
	}

	@RequestMapping(value = "/schoolyear.part.save", method = RequestMethod.POST)
	public String saveSchoolYearPart(@ModelAttribute("schoolYearPart") UiSchoolYearPart uiSchoolYearPart, Model model) {
		SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
		SchoolYear schoolYear = schoolYearService.findSchoolYearById(uiSchoolYearPart.getSchoolYearId());
		try {
			if (uiSchoolYearPart.getId() != null) {
				schoolYear = schoolYearService.editSchoolYearPart(uiSchoolYearPart.getId(), uiSchoolYearPart.getName(),
						dfm.parse(uiSchoolYearPart.getStartDate()), dfm.parse(uiSchoolYearPart.getEndDate()));
			} else {
				schoolYear = schoolYearService.addSchoolYearPart(schoolYear, uiSchoolYearPart.getName(),
						dfm.parse(uiSchoolYearPart.getStartDate()), dfm.parse(uiSchoolYearPart.getEndDate()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/schoolyear.view.htm/" + schoolYear.getId();
	}

	@RequestMapping(value = "/schoolyear.part.remove", method = RequestMethod.POST)
	public String removeSchoolYearPart(@ModelAttribute("forDeleteAction") SmsUiObject forDeleteAction, Model model) {
		SchoolYear schoolYear = schoolYearService.removeSchoolYearPart(forDeleteAction.getParentId(),
				forDeleteAction.getId());
		return "redirect:/schoolyear.view.htm/" + schoolYear.getId();
	}

	@RequestMapping("/schoolyear/open/{id}")
	public String openSchoolYear(@PathVariable("id") Long schoolYearId, Model model, HttpServletRequest request) {
		schoolYearService.openSchoolYear(schoolYearId);
		return "redirect:/schoolyear.view.htm/" + schoolYearId;
	}

	@RequestMapping("/schoolyear/close/{id}")
	public String closeSchoolYear(@PathVariable("id") Long schoolYearId, Model model, HttpServletRequest request) {
		schoolYearService.closeSchoolYear(schoolYearId);
		return "redirect:/schoolyear.view.htm/" + schoolYearId;
	}

	@RequestMapping(value = "/schoolyear.classroom.add", method = RequestMethod.POST)
	public String addClassroomToSchoolYear(@ModelAttribute("forDeleteAction") SmsUiObject forDeleteAction,
			Model model) {
		SchoolYear schoolYear = schoolYearService.addClassroomToSchoolYear(forDeleteAction.getParentId(),
				forDeleteAction.getId());
		return "redirect:/classroom.view.htm/" + schoolYear.getId();
	}

	@RequestMapping(value = "/schoolyear.classroom.remove", method = RequestMethod.POST)
	public String removeClassroomToSchoolYear(@ModelAttribute("forDeleteAction") SmsUiObject forDeleteAction,
			Model model) {
		SchoolYear schoolYear = schoolYearService.removeClassroomToSchoolYear(forDeleteAction.getParentId(),
				forDeleteAction.getId());
		return "redirect:/schoolyear.view.htm/" + schoolYear.getId();
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