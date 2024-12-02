package com.apiTests.requests.mockRequests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.util.Arrays;

import static com.apiTests.constants.BaseUri.Mock_Uri;

public class MockBaseTest {

    // Define a static RequestSpecification object to be used by all tests extending this base class
    public static RequestSpecification spec;

    // Static block to add Allure report integration filter for RestAssured
    static {
        RestAssured.filters(new AllureRestAssured());
    }

    // Constructor of MockBaseTest class
    public MockBaseTest() {

        // Retrieve the base URI from system properties; default to Mock_Uri if not provided
        String baseUri = System.getProperty("baseUri", Mock_Uri);

        // Check if logging is enabled based on system properties; default to false if not provided
        boolean enableLogging = Boolean.parseBoolean(System.getProperty("enableLogging", "false"));

        // Initialize RequestSpecBuilder with the base URI
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUri);

        // If logging is enabled, add filters for logging requests and responses
        if (enableLogging) {
            builder.addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()));
        }

        // Build the RequestSpecification object
        spec = builder.build();
    }
}