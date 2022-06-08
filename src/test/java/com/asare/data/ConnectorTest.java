package com.asare.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConnectorTest {

    private Connector testConnector;
    private User testUser;
    private List<Materials> materials;
    private List<Services> services;
    private List<Aproximation> aproximations;
    private DateTimeFormatter formatter;

    @BeforeEach
    void setUp(){
        testConnector = new Connector("juca", "g*$0Pe$h18cyiyJC");
        testUser = new User("adipiscing.mauris@yahoo.org", "BNL84PNT7HH");
        materials = new LinkedList<>();
        services = new LinkedList<>();
        aproximations = new LinkedList<>();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @AfterEach
    void tearDown(){

    }

    @Test
    void validateUser() {

        try {
            assertTrue(testConnector.validateUser(testUser.getEmail(), testUser.getPassword()));

            assertFalse(testConnector.validateUser(testUser.getEmail(), ""));

            assertFalse(testConnector.validateUser("l1k2j21ljk", testUser.getPassword()));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Test
    void getUserMaterials() {

        try {
            materials = testConnector.getUserMaterials(testUser.getEmail());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        assertEquals(1, materials.size());
        assertEquals(new Materials("Madera", new BigDecimal("82482.49"),
                "scelerisque mollis. Phasellus libero mauris, aliquam eu, accumsan sed, facilisis", 0), materials.get(0));

    }

    @Test
    void getUserServices() {

        try {
            services = testConnector.getUserServices(testUser.getEmail());

            assertEquals(1, services.size());
            assertEquals(new Services("Transporte", new BigDecimal("21476.98"),
                    "lorem vitae odio sagittis semper. Nam tempor diam dictum sapien.", 0), services.get(0));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void getUserAproximations() {

        try {
            aproximations = testConnector.getUserAproximations(testUser.getEmail());

            assertEquals(1, aproximations.size());

            assertEquals(new Aproximation("Mexico", new BigDecimal("79835.68"), 4, 8,
                    LocalDateTime.parse("2019-10-03 00:00:00", formatter)), aproximations.get(0));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}