package com.fblaise.sms.controller;

import com.fblaise.sms.model.*;
import com.fblaise.sms.service.AdministrationService;
import com.fblaise.sms.service.ClassroomService;
import com.fblaise.sms.service.SchoolYearService;
import com.fblaise.sms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5000", "http://localhost:4200", "https://sms-vue-ui.herokuapp.com", "https://sms-angular-ui.herokuapp.com"})
@RequestMapping("/rest/students")
public class StudentRestController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AdministrationService administrationService;
    @Autowired
    private SchoolYearService schoolYearService;
    @Autowired
    private ClassroomService classroomService;

    @GetMapping(value = "/list")
    public Collection<StudentDto> getStudentList() {
        List<Student> listStudents = this.studentService.findAllStudents();
        List<StudentDto> students = new ArrayList<>();
        for (Student student : listStudents) {
            students.add(convertToDto(student));
        }
        return students;
    }

    @RequestMapping("/view.htm/{id}")
    public StudentDto viewStudent(@PathVariable("id") Long id) {
        Student student = studentService.findStudentById(id);
        return convertToDto(student);
    }

    @PostMapping(value = "/save")
    public StudentDto saveStudent(@RequestBody Student student) {
        Student savedStudent = this.studentService.saveStudent(student);
        return convertToDto(savedStudent);
    }

    @PutMapping(value = "/update")
    public StudentDto updateStudent(@RequestBody Student student) {
        Student updatedStudent = this.studentService.updateStudent(student);
        return convertToDto(updatedStudent);

    }

    @DeleteMapping(value = "/remove/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        this.studentService.deleteStudent(id);
    }

    public StudentDto convertToDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setActive(student.isActive());
        dto.setMatricule(student.getMatricule());
        dto.setMotherPhoneNumber(student.getMotherPhoneNumber());
        dto.setEntranceDate(student.getEntranceDate());

        Classroom currentClassroom = student.getCurrentClassroom();
        if(currentClassroom!=null){
            ClassroomDto classroomDto = new ClassroomDto();
            classroomDto.setId(currentClassroom.getId());
            classroomDto.setName(currentClassroom.getName());
            classroomDto.setDescription(currentClassroom.getDescription());
            classroomDto.setFees(currentClassroom.getFees());
            dto.setCurrentClassroom(classroomDto);
        }
        return dto;
    }

}
