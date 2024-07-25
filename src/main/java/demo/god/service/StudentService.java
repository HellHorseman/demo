package demo.god.service;

import demo.god.model.Student;

import java.util.List;

public interface StudentService {

    void saveStudent(Student student);

    List<Student> getAllStudents();
}
