package com.fblaise.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fblaise.sms.model.Student;

@Repository("studentRepository")
public interface StudentRepository extends JpaRepository<Student, Long> {

	Student findByMatricule(String matricule);

	Student findById(Long id);
}