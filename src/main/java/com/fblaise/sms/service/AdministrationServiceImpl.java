package com.fblaise.sms.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fblaise.sms.exception.DupplicateUserNameException;
import com.fblaise.sms.model.Employee;
import com.fblaise.sms.model.Role;
import com.fblaise.sms.model.SchoolYear;
import com.fblaise.sms.repository.EmployeeRepository;
import com.fblaise.sms.repository.RoleRepository;
import com.fblaise.sms.repository.SchoolYearRepository;

@Service("administrationService")
public class AdministrationServiceImpl implements AdministrationService {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private SchoolYearRepository productFamilyRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Long saveEmployee(Employee employee) {
		Employee existingEmployee = employeeRepository.findByUserName(employee.getUserName());
		if (existingEmployee != null) {
			if (employee.getId() != null) {
				if (employee.getId() != existingEmployee.getId()) {
					throw new DupplicateUserNameException();
				}
			} else {
				throw new DupplicateUserNameException();
			}
		}
		employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
		employee.setActive(true);
		employeeRepository.save(employee);
		return employee.getId();
	}

	@Override
	public Long updateEmployee(Employee employeeWithNewValues) {
		Employee existingEmployee = employeeRepository.findByUserName(employeeWithNewValues.getUserName());
		if (existingEmployee != null && employeeWithNewValues.getId() != existingEmployee.getId()) {
			throw new DupplicateUserNameException();
		}
		Employee employee = employeeRepository.findById(employeeWithNewValues.getId());
		employee.copyValuesFrom(employeeWithNewValues, bCryptPasswordEncoder);
		employeeRepository.save(employee);
		return employee.getId();
	}

	@Override
	public Employee findUserByUserName(String userName) {
		return employeeRepository.findByUserName(userName);
	}

	@Override
	public List<Employee> findAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee findEmployeeById(Long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public void deleteEmployee(Long id) {
		Employee employee = employeeRepository.findById(id);
		employee.setRoles(new HashSet<Role>());
		employeeRepository.save(employee);
		employeeRepository.delete(id);
	}

	@Override
	public Employee addRoleToEmployee(long employeeId, long roleId) {
		Employee employee = employeeRepository.findById(employeeId);
		Role role = roleRepository.findById(roleId);
		employee.addRole(role);
		return employeeRepository.save(employee);
	}

	@Override
	public Employee removeRoleToEmployee(long employeeId, long roleId) {
		Employee employee = employeeRepository.findById(employeeId);
		Role role = roleRepository.findById(roleId);
		employee.removeRole(role);
		employeeRepository.save(employee);
		return employee;
	}

	// ----------------------
	public Role findRoleByName(String name) {
		return roleRepository.findByRole(name);
	}

	@Override
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}

	public Long saveRole(Role role) {
		roleRepository.save(role);
		return role.getId();
	}

	@Override
	public SchoolYear findProductFamilyById(Long id) {
		return productFamilyRepository.findById(id);
	}

	@Override
	public List<SchoolYear> findAllProductFamilies() {
		return productFamilyRepository.findAll();
	}

	@Override
	public Long saveProductFamily(SchoolYear productFamily) {
		productFamilyRepository.save(productFamily);
		return productFamily.getId();
	}

	@Override
	public Long updateProductFamily(SchoolYear familyWithNewValues) {
		SchoolYear productFamily = productFamilyRepository.findById(familyWithNewValues.getId());
		// productFamily.copyValuesFrom(familyWithNewValues);
		productFamilyRepository.save(productFamily);
		return productFamily.getId();
	}

	@Override
	public void deleteProductFamily(Long id) {
		// if(ProductFamily.isActuallyUsed(id))
		productFamilyRepository.delete(id);
	}

}
