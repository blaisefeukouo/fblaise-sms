package com.fblaise.sms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fblaise.sms.AttributesNames;
import com.fblaise.sms.model.Employee;
import com.fblaise.sms.model.Role;
import com.fblaise.sms.model.SchoolYear;
import com.fblaise.sms.model.SmsConfigObject;
import com.fblaise.sms.model.SmsUiObject;
import com.fblaise.sms.model.UiEmployee;
import com.fblaise.sms.service.AdministrationService;
import com.fblaise.sms.service.SchoolYearService;

@Controller
public class AdministrationController {
	@Autowired
	private AdministrationService administrationService;
	@Autowired
	private SchoolYearService schoolYearService;

	@RequestMapping(value = "/admin/employees", method = RequestMethod.GET)
	public String getEmployeeList(Model model, HttpServletRequest request) {
		model.addAttribute("employee", new Employee());
		List<Employee> listEmployee = administrationService.findAllEmployees();
		model.addAttribute("listEmployee", listEmployee);
		SmsUiObject forDeleteAction = new SmsUiObject();
		model.addAttribute("forDeleteAction", forDeleteAction);
		getTheUserConnected(model, request);
		return "admin/employees/EmployeesList";
	}

	@RequestMapping(value = "/admin/employee.add.htm", method = RequestMethod.GET)
	public String getEmployeeAddPage(Model model, HttpServletRequest request) {
		model.addAttribute("employee", new UiEmployee());
		getTheUserConnected(model, request);
		return "admin/employees/EmployeeAdd";
	}

	@RequestMapping(value = "/admin/employee/add", method = RequestMethod.POST)
	public String addEmployee(@ModelAttribute("Employee") UiEmployee uiEmployee, Model model,
			HttpServletRequest request) {
		Employee EmployeeExists = administrationService.findUserByUserName(uiEmployee.getUserName());
		String viewToReturn;
		Long employeeId;
		if (EmployeeExists != null) {
			model.addAttribute("userNameError", "There is already a Employee registered with the user name provided");
			viewToReturn = "admin/employees/EmployeeAdd";
		} else {
			SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
			Role employeeRole = administrationService.findRoleByName(AttributesNames.Role_Teacher);
			Employee employee = null;
			try {
				employee = new Employee(uiEmployee.getFirstName(), uiEmployee.getLastName(), uiEmployee.getEmail(),
						uiEmployee.getPhoneNumber(), uiEmployee.getPhoneNumber(), uiEmployee.getPassword(),
						dfm.parse(uiEmployee.getStartDate()), null, true);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			employee.setRoles(new HashSet<Role>(Arrays.asList(employeeRole)));
			employeeId = administrationService.saveEmployee(employee);
			model.addAttribute("successMessage", "employee has been registered successfully");
			model.addAttribute("employee", new Employee());
			viewToReturn = "redirect:/admin/employee.view.htm/" + employeeId;
		}
		getTheUserConnected(model, request);
		return viewToReturn;
	}

	@RequestMapping("/admin/employee.edit.htm/{id}")
	public String getEditEmployeePage(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		Employee employee = this.administrationService.findEmployeeById(id);
		SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
		UiEmployee uiEmployee = new UiEmployee(employee.getId(), employee.getFirstName(), employee.getLastName(),
				employee.getEmail(), employee.getPhoneNumber(), employee.getUserName(), employee.getPassword(),
				dfm.format(employee.getStartDate()), employee.isActive());
		uiEmployee.setRealStartDate(employee.getStartDate());
		model.addAttribute("employee", uiEmployee);
		getTheUserConnected(model, request);
		return "admin/employees/EmployeeEdit";
	}

	@RequestMapping(value = "/admin/employee/edit", method = RequestMethod.POST)
	public String editEmployee(@ModelAttribute("employee") UiEmployee uiEmployee, BindingResult bindingResult,
			Model model, HttpServletRequest request) {
		String viewToReturn;
		Long employeeId;
		if (uiEmployee.getId() == null) {
			bindingResult.rejectValue("id", "error.user", "The Employee you are trying to update does not exist");
		}
		if (bindingResult.hasErrors()) {
			viewToReturn = "admin/employees/EmployeeEdit";
		} else {
			SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
			Employee employee = null;
			try {
				employee = new Employee(uiEmployee.getFirstName(), uiEmployee.getLastName(), uiEmployee.getEmail(),
						uiEmployee.getPhoneNumber(), uiEmployee.getUserName(), uiEmployee.getPassword(),
						dfm.parse(uiEmployee.getStartDate()), null, true);
				employee.setId(uiEmployee.getId());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			employeeId = administrationService.updateEmployee(employee);
			model.addAttribute("successMessage", "Employee has been updated successfully");
			model.addAttribute("employee", new Employee());
			viewToReturn = "redirect:/admin/employee.view.htm/" + employeeId;

		}
		getTheUserConnected(model, request);
		return viewToReturn;
	}

	@RequestMapping("/admin/employee.remove")
	public String deleteEmployee(Model model, @ModelAttribute("forDeleteAction") SmsUiObject forDeleteAction) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee userConnected = administrationService.findUserByUserName(auth.getName());
		Long userId = forDeleteAction.getId();
		if (userId == userConnected.getId()) {
			throw new RuntimeException("Cannot delete connected user");
		}
		this.administrationService.deleteEmployee(userId);
		return "redirect:/admin/employees";
	}

	@RequestMapping("/admin/employee.view.htm/{id}")
	public String viewEmployee(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		Employee employee = this.administrationService.findEmployeeById(id);
		model.addAttribute("employee", employee);
		model.addAttribute("rolesList", employee.getRoles());
		model.addAttribute("roles", administrationService.findAllRoles());
		SmsUiObject forDeleteAction = new SmsUiObject();
		model.addAttribute("forDeleteAction", forDeleteAction);
		getTheUserConnected(model, request);
		return "admin/employees/EmployeeDetails";
	}

	@RequestMapping(value = "/admin/employee.role.add", method = RequestMethod.POST)
	public String addRoleToEmployee(@ModelAttribute("forDeleteAction") SmsUiObject uiObject, Model model,
			HttpServletRequest request) {

		Employee employee = administrationService.addRoleToEmployee(uiObject.getParentId(), uiObject.getId());
		model.addAttribute("employee", employee);
		model.addAttribute("rolesList", employee.getRoles());
		model.addAttribute("roles", administrationService.findAllRoles());
		SmsUiObject forDeleteAction = new SmsUiObject();
		model.addAttribute("forDeleteAction", forDeleteAction);
		getTheUserConnected(model, request);
		return "admin/employees/EmployeeDetails";
	}

	@RequestMapping(value = "/admin/employee.role.remove", method = RequestMethod.POST)
	public String removeRoleToEmployee(@ModelAttribute("forDeleteAction") SmsUiObject forDeleteAction, Model model,
			HttpServletRequest request) {
		Employee employee = administrationService.removeRoleToEmployee(forDeleteAction.getParentId(),
				forDeleteAction.getId());
		model.addAttribute("employee", employee);
		model.addAttribute("rolesList", employee.getRoles());
		model.addAttribute("roles", administrationService.findAllRoles());
		model.addAttribute("forDeleteAction", forDeleteAction);
		getTheUserConnected(model, request);
		return "admin/employees/EmployeeDetails";
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