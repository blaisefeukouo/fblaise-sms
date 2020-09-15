package com.fblaise.sms.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fblaise.sms.model.SchoolYear;
import com.fblaise.sms.service.SchoolYearService;

@RestController
@CrossOrigin(origins = {"http://localhost:5000", "http://localhost:4200", "https://sms-vue-ui.herokuapp.com", "https://sms-angular-ui.herokuapp.com"})
@RequestMapping("/rest/schoolyears")
public class SchoolYearRestController {
    @Autowired
    private SchoolYearService schoolYearService;
    // @Autowired
    // private AdministrationService administrationService;

    @GetMapping("/list")
    public Collection<SchoolYear> getSchoolYearList() {
        List<SchoolYear> listSchoolYears = this.schoolYearService.findAllSchoolYears();
        return listSchoolYears;
    }

    @GetMapping("/view.htm/{id}")
    public SchoolYear getSchoolyearById(@PathVariable("id") Long id) {
        SchoolYear schoolYear = schoolYearService.findSchoolYearById(id);
        return schoolYear;
    }

    @PostMapping("/save")
    public SchoolYear createSchoolYear(@RequestBody SchoolYear schoolYear) {
        return schoolYearService.createNewSchoolYear(schoolYear);
    }

    @PutMapping("/update")
    public SchoolYear updateSchoolYear(@RequestBody SchoolYear schoolYear) {
        return schoolYearService.updateSchoolYear(schoolYear);
    }

    @DeleteMapping(path = {"remove/{id}"})
    public SchoolYear deleteSchoolYear(@PathVariable("id") Long id) {
        return schoolYearService.deleteSchoolYear(id);
    }

    @PutMapping("/open/{id}")
    public SchoolYear openSchoolYear(@PathVariable("id") Long schoolYearId) {
        return schoolYearService.openSchoolYear(schoolYearId);
    }

    @PutMapping("/close/{id}")
    public SchoolYear closeSchoolYear(@PathVariable("id") Long schoolYearId) {
        return schoolYearService.closeSchoolYear(schoolYearId);
    }
}
