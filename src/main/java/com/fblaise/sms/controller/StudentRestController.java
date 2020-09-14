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
    public Collection<Student> getStudentList() {
        List<Student> listStudents = this.studentService.findAllStudents();
        return listStudents;
    }

    @RequestMapping("/view.htm/{id}")
    public Student viewStudent(@PathVariable("id") Long id) {
        Student student = studentService.findStudentById(id);
        return student;
    }

    @PostMapping(value = "/save")
    public Student saveStudent(@RequestBody Student student) {
        return this.studentService.saveStudent(student);

    }

    @PutMapping(value = "/update")
    public Student updateStudent(@RequestBody Student student) {
        return this.studentService.updateStudent(student);

    }

    @DeleteMapping(value = "/remove/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        this.studentService.deleteStudent(id);
    }

}
