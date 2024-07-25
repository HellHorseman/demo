package demo.god.service;

import demo.god.model.Student;
import demo.god.service.impl.StudentRowMapper;
import demo.god.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllStudents() {
        Student student1 = new Student();
        student1.setId(1);
        student1.setName("John");
        student1.setSurname("Doe");
        student1.setAge(20);

        Student student2 = new Student();
        student2.setId(2);
        student2.setName("Jane");
        student2.setSurname("Smith");
        student2.setAge(22);

        List<Student> expectedStudents = Arrays.asList(student1, student2);

        when(jdbcTemplate.query(anyString(), any(StudentRowMapper.class)))
                .thenReturn(expectedStudents);

        List<Student> actualStudents = studentService.getAllStudents();

        assertEquals(expectedStudents.size(), actualStudents.size());
        assertEquals(expectedStudents.get(0).getName(), actualStudents.get(0).getName());
        assertEquals(expectedStudents.get(1).getName(), actualStudents.get(1).getName());
    }

    @Test
    public void testSaveStudent() {
        Student student = new Student();
        student.setName("John");
        student.setSurname("Doe");
        student.setAge(20);

        when(jdbcTemplate.update(anyString(), any(), any(), any())).thenReturn(1);

        studentService.saveStudent(student);

        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any());
    }

    @Test
    public void testGetStudentById() {
        Student expectedStudent = new Student();
        expectedStudent.setId(1);
        expectedStudent.setName("John");
        expectedStudent.setSurname("Doe");
        expectedStudent.setAge(20);

        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(StudentRowMapper.class)))
                .thenReturn(expectedStudent);

        Student actualStudent = studentService.getStudentById(1);

        assertEquals(expectedStudent.getId(), actualStudent.getId());
        assertEquals(expectedStudent.getName(), actualStudent.getName());
    }

    @Test
    public void testUpdateStudent() {
        Student existingStudent = new Student();
        existingStudent.setId(1);
        existingStudent.setName("John");
        existingStudent.setSurname("Doe");
        existingStudent.setAge(20);

        Student updatedStudent = new Student();
        updatedStudent.setName("Johnny");
        updatedStudent.setSurname("Doey");
        updatedStudent.setAge(21);

        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(StudentRowMapper.class)))
                .thenReturn(existingStudent);

        when(jdbcTemplate.update(anyString(), any(), any(), any(), anyInt())).thenReturn(1);

        Student actualStudent = studentService.updateStudent(1, updatedStudent);

        assertNotNull(actualStudent);
        assertEquals(updatedStudent.getName(), actualStudent.getName());
        assertEquals(updatedStudent.getSurname(), actualStudent.getSurname());
        assertEquals(updatedStudent.getAge(), actualStudent.getAge());
    }

    @Test
    public void testDeleteStudent() {
        when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(1);

        studentService.deleteStudent(1);

        verify(jdbcTemplate, times(1)).update(anyString(), anyInt());
    }
}
