package me.staek.config;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DBTest {

    @Test
    public void DB연결() {
        Connection connection = DB.getConnection();
        assertNotNull(connection);
    }
}