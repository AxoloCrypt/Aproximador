package com.aproximador.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RecordTest {

    private Materials materials;
    private Services services;

    @BeforeEach
    void setUp() {
        materials = new Materials();

        materials.addRecord(new Materials("Table 1ft x 1 ft", new BigDecimal("5.99"), "A table"));
        materials.addRecord(new Materials("White Paint", new BigDecimal("21.55"), "Paint for certain mirrors"));
        materials.addRecord(new Materials("Glass", new BigDecimal("15.00"), "Glass for mirrors"));

        services = new Services();

        services.addRecord(new Services("Shipping", new BigDecimal("50.99"), "Shipping with amazon"));
        services.addRecord(new Services("FB Adds", new BigDecimal("6.99"), "Facebook Adds weekly"));
        services.addRecord(new Services("Google Analytics", new BigDecimal("10.00"), "Google Analytics monthly"));
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");

        materials = null;
        services = null;

        assertNull(materials);
        assertNull(services);
    }

    @Test
    void addRecord() {
        assertTrue(materials.addRecord(new Materials("Huevos", new BigDecimal("1.00"),
                "Huevos Frescos")));

        assertTrue(services.addRecord(new Services("Package", new BigDecimal("2.0"),
                "Pa")));

        assertThrows(NullPointerException.class, () -> materials.addRecord(null));

        assertThrows(NullPointerException.class, () -> services.addRecord(null));

    }

    @Test
    void deleteRecord() {
        assertTrue(materials.deleteRecord(new Materials("Glass", new BigDecimal("15.00"), "Glass for mirrors")));
        assertTrue(services.deleteRecord(new Services("Google Analytics", new BigDecimal("10.00"), "Google Analytics monthly")));

        assertFalse(materials.deleteRecord(new Materials("Gla", new BigDecimal("15.00"), "Glass for mirrors")));
        assertFalse(services.deleteRecord(new Services("Googlalytics", new BigDecimal("10.00"), "Google Analytics monthly")));

        assertFalse(materials.deleteRecord(null));
        assertFalse(services.deleteRecord(null));
    }

    @Test
    void editRecord() {
        materials.editRecord("White Paint", new BigDecimal("21.55"), "Paint for certain mirrors",
                "Black paint", new BigDecimal("22.00"), "Black");

        assertEquals(new Materials("Black paint", new BigDecimal("22.00"), "Black"), materials.getRecords().get(1));

        services.editRecord("Shipping", new BigDecimal("50.99"), "Shipping with amazon",
                "Shipping", new BigDecimal("40.50"), "Shipping with Mercado Libre");

        assertEquals(services.getRecords().get(0), new Services("Shipping", new BigDecimal("40.50"),
                "Shipping with Mercado Libre"));

    }
}