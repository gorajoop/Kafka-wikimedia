package joop.kafka.stream.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DatabasePingService {

    @Autowired
    private DataSource dataSource;

    /**
     * Ping the PostgreSQL database to check if it is reachable.
     *
     * @return A message indicating the status of the database connection.
     */
    public String pingDatabase() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) { // 2 seconds timeout
                return "Database is reachable!";
            } else {
                return "Database is unreachable!";
            }
        } catch (SQLException e) {
            return "Failed to ping database: " + e.getMessage();
        }
    }
}
