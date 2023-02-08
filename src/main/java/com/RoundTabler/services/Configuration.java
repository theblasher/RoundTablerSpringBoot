package com.RoundTabler.services;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/*
 * Class/structure that stores and manages arguments and configuration
 * Performs validation on input
 */

public class Configuration {

    private final String[] validScanTypes = {"all", "nacha", "pci"};
    private final String[] validDbTypes = {"mysql", "mariadb", "maria", "postgres", "postgresql", "mongo", "mongodb"};
    public String missingParameter;
    private String type;
    private String dbType;
    private String server;
    private String user;
    private String password;
    private String database;
    private String table;
    private String port;

    public Configuration() {
        this.type = "";
        this.dbType = "";
        this.server = "";
        this.port = "";
        this.user = "";
        this.password = "";
        this.database = "";
        this.table = "";
    }

    //All getters and setters for config class

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean validateScanType() {
        List<String> types = Arrays.asList(this.validScanTypes);

        return types.contains(this.type.toLowerCase());
    }

    public String getDbType() {
        return this.dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getServer() {
        return this.server;
    }

    public void setServer(String server) {
        this.server = server.split(":")[0]; // If ":" is not present, this is always the entire string

        if (server.contains(":")) {
            this.port = server.split(":")[1];
        }
    }

    public String getPort() {
        return this.port;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public boolean validateDbType() {
        List<String> types = Arrays.asList(this.validDbTypes);

        return types.contains(this.dbType.toLowerCase());
    }

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }


    // Ensure all required parameters have been filled out
    // Non-required fields are "table" and "port" (port is a part of the server argument)
    public boolean allFilled() throws IllegalAccessException {

        for (Field f : getClass().getDeclaredFields()) {
            if (f.get(this) == "" && !f.getName().equals("table") && !f.getName().equals("port")) {
                this.missingParameter = f.getName();
                return false;
            }
        }

        return true;
    }
}
