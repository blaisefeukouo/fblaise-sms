package com.fblaise.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fblaise.sms.model.ElementState;
import com.fblaise.sms.model.SchoolYear;

@Repository("schoolYearRepository")
@CrossOrigin(origins = {"http://localhost:5000","http://localhost:4200"})
public interface SchoolYearRepository extends JpaRepository<SchoolYear, Long> {

	SchoolYear findById(Long id);

	SchoolYear findByState(ElementState state);

	SchoolYear findByName(String name);

}
