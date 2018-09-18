package com.fblaise.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fblaise.sms.model.Subject;

@Repository("subjectRepository")
public interface SubjectRepository extends JpaRepository<Subject, Long> {

	Subject findById(Long id);

}
