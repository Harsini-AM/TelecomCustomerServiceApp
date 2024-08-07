package com.example.Customer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;

import org.springframework.boot.builder.SpringApplicationBuilder;
 
import static org.junit.jupiter.api.Assertions.*;
 
class ServletInitializerTest {
 
    @Test
    void testConfigure() {
        // Arrange
        ServletInitializer servletInitializer = new ServletInitializer();
 
        // Act
        SpringApplicationBuilder builder = servletInitializer.configure(new SpringApplicationBuilder());
 
        // Assert
        assertNotNull(builder);
        assertNotNull(builder.sources(CustomerApplication.class));
    }
}

