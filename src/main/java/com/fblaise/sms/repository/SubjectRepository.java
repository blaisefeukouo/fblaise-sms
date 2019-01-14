package com.fblaise.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fblaise.sms.model.Subject;

@Repository("subjectRepository")
@CrossOrigin(origins = "http://localhost:4200")
public interface SubjectRepository extends JpaRepository<Subject, Long> {

	Subject findById(Long id);

}
