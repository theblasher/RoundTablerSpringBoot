package com.RoundTabler.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationTest {

    @Test
    public void setTypeWorks() {

        Configuration config = new Configuration();

        config.setScanType("all");

        assertEquals("all", config.getScanType());

    }

    @Test
    public void setDBTypeWorks() {

        Configuration config = new Configuration();

        config.setDbType("mariadb");

        assertEquals("mariadb", config.getDbType());

    }

    @Test
    public void setServerWorks() {

        Configuration config = new Configuration();

        config.setServer("127.0.0.1");

        assertEquals("127.0.0.1", config.getServer());

    }

    @Test
    public void setUserWorks() {

        Configuration config = new Configuration();

        config.setUser("user");

        assertEquals("user", config.getUser());

    }

    @Test
    public void setPasswordWorks() {

        Configuration config = new Configuration();

        config.setPassword("1234");

        assertEquals("1234", config.getPassword());

    }

    @Test
    public void setDatabaseWorks() {

        Configuration config = new Configuration();

        config.setDatabase("DBToTest");

        assertEquals("DBToTest", config.getDatabase());

    }

    @Test
    public void setTableWorks() {

        Configuration config = new Configuration();

        config.setTableName("table");

        assertEquals("table", config.getTableName());

    }

    @Test
    public void getPort() {

        Configuration config = new Configuration();

        config.setServer("127.0.0.1:3306");

        assertEquals("3306", config.getPort());
    }

    @Test
    public void allParamsFilledTrue() throws IllegalAccessException {

        Configuration config = new Configuration();

        config.setScanType("all");
        config.setDbType("mariadb");
        config.setServer("127.0.0.1");
        config.setUser("user");
        config.setPassword("1234");
        config.setDatabase("DBToTest");

        assertTrue(config.allFilled());

    }

    @Test
    public void allParamsFilledFalse() throws IllegalAccessException {

        Configuration config = new Configuration();

        config.setScanType("all");
        config.setDbType("mariadb");
        config.setServer("127.0.0.1");
        config.setUser("user");
        config.setPassword("1234");

        assertFalse(config.allFilled());

    }

    @Test
    public void validScanTypeReturnsTrue() {

        Configuration config = new Configuration();

        config.setScanType("all");

        assertTrue(config.validateScanType());

    }

    @Test
    public void validScanTypeReturnsFalse() {

        Configuration config = new Configuration();

        config.setScanType("invalid");

        assertFalse(config.validateScanType());

    }

    @Test
    public void validDBTypeReturnsTrue() {

        Configuration config = new Configuration();

        config.setDbType("mariadb");

        assertTrue(config.validateDbType());

    }

    @Test
    public void validDBTypeReturnsFalse() {

        Configuration config = new Configuration();

        config.setDbType("invalid");

        assertFalse(config.validateDbType());

    }

    @Test
    public void doesNotThrow() {

        Configuration config = new Configuration();

        config.setScanType("all");
        config.setDbType("mariadb");
        config.setServer("127.0.0.1");
        config.setUser("user");
        config.setPassword("1234");
        config.setDatabase("DBToTest");

        assertDoesNotThrow(config::allFilled);

    }

}
