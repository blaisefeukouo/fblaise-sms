package com.fblaise.sms.service;

import java.util.List;

import com.fblaise.sms.model.Employee;
import com.fblaise.sms.model.Role;
import com.fblaise.sms.model.SchoolYear;

public interface AdministrationService {

	public Employee findEmployeeById(Long id);

	public Employee findUserByUserName(String EmployeeName);

	public List<Employee> findAllEmployees();

	public Long saveEmployee(Employee Employee);

	public Long updateEmployee(Employee EmployeeWithNewValues);

	public void deleteEmployee(Long id);

	public Long saveRole(Role role);

	public SchoolYear findProductFamilyById(Long id);

	public List<SchoolYear> findAllProductFamilies();

	public Long saveProductFamily(SchoolYear family);

	public Long updateProductFamily(SchoolYear familyWithNewValues);

	public void deleteProductFamily(Long id);

	public Role findRoleByName(String name);

	public Employee removeRoleToEmployee(long employeeId, long roleId);

	public Employee addRoleToEmployee(long employeeId, long roleId);

	public List<Role> findAllRoles();
}