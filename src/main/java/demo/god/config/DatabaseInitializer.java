package demo.god.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS demo (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "surname VARCHAR(100), " +
                "age INT)";
        jdbcTemplate.execute(sql);
    }
}
