package com.fblaise.sms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fblaise.sms.model.SchoolYear;
import com.fblaise.sms.model.SchoolYearPart;

@Repository("schoolYearPartRepository")
@CrossOrigin(origins = "http://localhost:4200")
public interface SchoolYearPartRepository extends JpaRepository<SchoolYearPart, Long> {

	Optional<SchoolYearPart> findById(Long id);

	List<SchoolYearPart> findBySchoolYear(SchoolYear schoolYear);
}
