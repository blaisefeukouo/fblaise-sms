package com.fblaise.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fblaise.sms.model.Classroom;

@Repository("classroomRepository")
@CrossOrigin(origins = {"http://localhost:5000","http://localhost:4200","https://sms-vue-ui.herokuapp.com"})
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

	Classroom findById(Long id);

}
