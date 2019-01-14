package com.fblaise.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fblaise.sms.model.Role;

@Repository("roleRepository")
@CrossOrigin(origins = "http://localhost:4200")
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByRole(String role);

	Role findById(long roleId);

}
