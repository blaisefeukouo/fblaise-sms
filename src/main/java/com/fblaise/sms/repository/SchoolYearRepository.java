package com.fblaise.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fblaise.sms.model.ElementState;
import com.fblaise.sms.model.SchoolYear;

@Repository("schoolYearRepository")
public interface SchoolYearRepository extends JpaRepository<SchoolYear, Long> {

	SchoolYear findById(Long id);

	SchoolYear findByState(ElementState state);

}
