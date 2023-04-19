package com.asare.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
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

    private User testUser2;

    @BeforeEach
    void setUp(){
        testConnector = new Connector();
        testUser = new User("Pamela","Quách", "Praesent Interdum Foundation","adipiscing.mauris@yahoo.org", "BNL84PNT7HH");
        materials = new LinkedList<>();
        services = new LinkedList<>();
        aproximations = new LinkedList<>();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        testUser2 = new User("Barrett", "Burton", "Ut Consulting", "tempor.lorem.eget@yahoo.couk", "KRC46MQL6FB");
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
        finally {
            testConnector.closeConnection();
        }

    }

    @Test
    void getUserMaterials() {

        try {
            materials = testConnector.getUserMaterials(testUser.getEmail());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            testConnector.closeConnection();
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
        finally {
            testConnector.closeConnection();
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
        finally {
            testConnector.closeConnection();
        }

    }

    @Test
    void disableRecord(){
        Materials tmpMaterial = new Materials("TestDisableM", new BigDecimal("8.00"), "Test Disable Material");
        Services tmpService = new Services("TestDisable", new BigDecimal("6.00"), "Test Disable");

        try {
            assertTrue(testConnector.deleteRecord(tmpMaterial));
            assertTrue(testConnector.deleteRecord(tmpService));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            testConnector.closeConnection();
        }
    }

    @Test
    void saveAproximation(){
        Aproximation aproximation = new Aproximation("TestTestInsert", new BigDecimal("100.50"), 3,
                1, LocalDateTime.now());
        /*
        Collections.addAll(aproximation.getRecords(),
                new Materials("Some Test Material", new BigDecimal("0.50"), "goes brrr", 1),
                new Materials("Some Test extra Material", new BigDecimal("0.76"), "makes brrr", 3),
                new Materials("Some Test extra extra Material", new BigDecimal("0.89"), "sounds brrr", 4));

        aproximation.getRecords().add(new Services("Some Test Service", new BigDecimal("100.00"), " goes rrr", 1));

        assertTrue(testConnector.saveAproximation(aproximation, testUser.getEmail()));

         */

        /*
        Aproximation aproximation2 = new Aproximation("TestServicesInsert",
                new BigDecimal("500.50"), 1, 3, LocalDateTime.now());

        Collections.addAll(aproximation2.getRecords(),
                new Services("Another Test Service", new BigDecimal("10.76"), "flkfldkf", 3),
                new Services("Another Test Test Service", new BigDecimal("11.76"), "flkfldf", 4),
                new Services("Another Test TEST Service", new BigDecimal("12.76"), "flkfldkf", 3),
                new Materials("ANOTHER TEST MATERIAL", new BigDecimal("5.75"), "qeweqweqw", 5));

        assertTrue(testConnector.saveAproximation(aproximation2, testUser));


         */
        Aproximation aproximation3 = new Aproximation("Espejo", new BigDecimal("10000"),
                2, 2, LocalDateTime.now());

        Collections.addAll(aproximation3.getRecords(),
                new Services("Shipping", new BigDecimal("10.76"), "flkfldkf", 3),
                new Services("AWS", new BigDecimal("11.76"), "flkfldf", 4),
                new Materials("Tabla", new BigDecimal("5.75"), "qeweqweqw", 5),
                new Materials("Cristal", new BigDecimal("5.75"), "qeweqweqw", 5)
                );

        testConnector.saveAproximation(aproximation3, testUser2);

    }

}