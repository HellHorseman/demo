package demo.god.service.impl;

import demo.god.model.Student;
import demo.god.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM demo";
        return jdbcTemplate.query(sql, new StudentRowMapper());
    }

    public void saveStudent(Student student) {
        String sql = "INSERT INTO demo (id, name, surname, age) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, student.getId(), student.getName(), student.getSurname(), student.getAge());
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new StudentRowMapper());
    }

    public Student updateStudent(int id, Student student) {
        Student existing = getStudentById(id);
        if (existing != null) {
            String sql = "UPDATE students SET age = ?, name = ?, surname = ? WHERE id = ?";
            jdbcTemplate.update(sql, student.getAge(), student.getName(), student.getSurname(), id);
            existing.setAge(student.getAge());
            existing.setName(student.getName());
            existing.setSurname(student.getSurname());
        }
        return existing;
    }

    public void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getInt("id"));
            student.setName(rs.getString("name"));
            student.setSurname(rs.getString("surname"));
            student.setAge(rs.getInt("age"));
            return student;
        }
    }
}
