package com.RoundTabler.utility.database;

import com.RoundTabler.services.Configuration;

import java.sql.SQLException;

/*
 * MariaDB database reader
 * Uses the MariaDB JDBC driver, everything else is the same for our uses as the MySQLReader
 */

public class MariaReader extends JDBCUser {
    public MariaReader(Configuration config) throws ClassNotFoundException, SQLException {
        super(config);

        // Check for the relevant JDBC Driver
        Class.forName("org.mariadb.jdbc.Driver");

        // Use args to establish database connection
        String jdbcUri = String.format("jdbc:%s://%s:%s?user=%s&password=%s",
                "mariadb",
                config.getServer(), !config.getPort().isBlank() ? config.getPort() : "3306",
                config.getUser(), config.getPassword());

        startConnection(jdbcUri);
        buildSchemaQuery();
    }
}