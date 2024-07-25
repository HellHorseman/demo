package demo.god.controller;

import demo.god.model.Student;
import demo.god.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entities")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllEntities() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public void createEntity(@RequestBody Student student) {
        studentService.saveStudent(student);
    }
}