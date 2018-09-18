package com.fblaise.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fblaise.sms.model.SchoolYear;
import com.fblaise.sms.model.SchoolYearPart;

@Repository("schoolYearPartRepository")
public interface SchoolYearPartRepository extends JpaRepository<SchoolYearPart, Long> {

	SchoolYearPart findById(Long id);

	List<SchoolYearPart> findBySchoolYear(SchoolYear schoolYear);
}
