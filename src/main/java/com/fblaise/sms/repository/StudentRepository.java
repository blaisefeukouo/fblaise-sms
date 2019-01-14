package com.fblaise.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fblaise.sms.model.Student;

@Repository("studentRepository")
@CrossOrigin(origins = "http://localhost:4200")
public interface StudentRepository extends JpaRepository<Student, Long> {

	Student findByMatricule(String matricule);

	Student findById(Long id);
}