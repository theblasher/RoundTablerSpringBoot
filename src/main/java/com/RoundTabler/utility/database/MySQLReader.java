package com.RoundTabler.utility.database;

import com.RoundTabler.services.Configuration;

import java.sql.SQLException;

/*
 * MySQL and MariaDB database readers
 * Requires the MySQL JDBC Driver
 */

public class MySQLReader extends JDBCUser {
    public MySQLReader(Configuration config) throws ClassNotFoundException, SQLException {
        super(config);

        // Check for JDBC driver; if fails, throw ClassNotFoundException
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Use args to establish database connection
        String jdbcUri = String.format("jdbc:%s://%s:%s?user=%s&password=%s",
                "mysql",
                config.getServer(), !config.getPort().isBlank() ? config.getPort() : "3306",
                config.getUser(), config.getPassword());


        startConnection(jdbcUri);
        buildSchemaQuery();
    }
}