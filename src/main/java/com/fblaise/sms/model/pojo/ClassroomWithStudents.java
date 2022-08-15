package com.fblaise.sms.model.pojo;

import com.fblaise.sms.model.Classroom;
import com.fblaise.sms.model.Student;

import java.util.List;

public class ClassroomWithStudents {
    private Classroom classroom;
    private List<Student> students;

    public ClassroomWithStudents(Classroom classroom, List<Student> students) {
        this.classroom = classroom;
        this.students = students;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
