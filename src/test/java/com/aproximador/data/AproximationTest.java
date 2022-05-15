package com.aproximador.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AproximationTest {

    private Aproximation aproximation;
    private Aproximation aproximation2;

    @BeforeEach
    void setUp() {

        aproximation = new Aproximation();

        aproximation.getRecords().add(new Materials("Blue Paint", new BigDecimal("249.50"), ""));
        aproximation.getRecords().add(new Materials("Glass", new BigDecimal("1300.00"), ""));
        aproximation.getRecords().add(new Materials("Tables 1m x 5m", new BigDecimal("150.00"), ""));
        aproximation.getRecords().add(new Services("Shipping", new BigDecimal("300.00"), ""));

        aproximation2 = new Aproximation();

        aproximation2.getRecords().add(new Materials("Blue Paint", new BigDecimal("249.50"), ""));
        aproximation2.getRecords().add(new Materials("Blue Paint", new BigDecimal("249.50"), ""));
        aproximation2.getRecords().add(new Materials("Glass", new BigDecimal("2700.00"), ""));
        aproximation2.getRecords().add(new Materials("Tables 1.75m x 8m", new BigDecimal("300.00"), ""));
        aproximation2.getRecords().add(new Materials("Tables 1.75m x 8m", new BigDecimal("300.00"), ""));
        aproximation2.getRecords().add(new Materials("Tables 1.75m x 8m", new BigDecimal("300.00"), ""));
        aproximation2.getRecords().add(new Materials("Tables 1.75m x 8m", new BigDecimal("300.00"), ""));
        aproximation2.getRecords().add(new Materials("Tables 1.75m x 8m", new BigDecimal("300.00"), ""));
        aproximation2.getRecords().add(new Materials("Tables 1.75m x 8m", new BigDecimal("300.00"), ""));
        aproximation2.getRecords().add(new Services("Shipping", new BigDecimal("600.00"), ""));



    }

    @AfterEach
    void tearDown() {
        aproximation = null;
        assertNull(aproximation);
    }

    @Test
    void calculateTotal() {

        assertEquals(new BigDecimal("1999.50"), aproximation.calculateTotal());
        assertEquals(new BigDecimal("5599.00"), aproximation2.calculateTotal());
    }
}