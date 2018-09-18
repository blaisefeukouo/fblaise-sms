package com.fblaise.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fblaise.sms.model.Classroom;

@Repository("classroomRepository")
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

	Classroom findById(Long id);

}
