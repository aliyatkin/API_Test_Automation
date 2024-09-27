package com.apiTests.serviceSteps.serviceSteps;

import com.apiTests.models.user_controller.login.LoginResponse;
import com.apiTests.requests.serviceRequests.user_controller.LoginTests;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;

import static com.apiTests.constants.Data_Path.*;
import static com.apiTests.constants.StatusCode.*;
import static com.apiTests.requests.HelperMethod.requestBodyLoader;

public class LoginSteps {

    ObjectMapper objectMapper = new ObjectMapper();

    private LoginResponse loginResponse;
    private String accessToken;
    private LoginTests loginTests;
    private static final Logger logger = LogManager.getLogger(LoginSteps.class);

    @Given("User logs into the system with a valid username and invalid password")
    public void validUsernameAndInvalidPassword(){

        loginTests = new LoginTests();  // Create the LoginTests class for Login
        loginResponse = loginTests.Login(validUsernameInvalidPassword,NOT_OK);  // Save the response in loginResponse
        logger.info("The system has not been logged in with a valid username and password");
    }

    @Given("User logs into the system with a valid username and empty password")
    public void validUsernameAndEmptyPassword(){

        loginTests = new LoginTests();  // Create the LoginTests class for Login
        loginResponse = loginTests.Login(validUsernameAndEmptyPassword,NOT_OK);  // Save the response in loginResponse
        logger.info("The system has not been logged in with a valid username and empty password");
    }

    @Given("User logs into the system with a valid username and password")
    public void validUsernameAndPassword() {

        // Read the data from JSON file and save it in requestBody
        loginTests = new LoginTests();  // Create the LoginTests class for Login
        loginResponse = loginTests.Login(validUsernameAndPassword,OK);  // Save the response in loginResponse

        logger.info("The system has been logged in with a valid username and password");
    }

    @When("Verifying the required response parameters")
    public void checkParameter() throws JsonProcessingException {

        String username = loginResponse.getUser().getUsername();
        String requestBody = requestBodyLoader(validUsernameAndPassword);
        JsonNode jsonNode = objectMapper.readTree(requestBody);

        // Username check
        if (jsonNode.get("username").asText().equals(username)) {
            logger.info("The username in the response matches the one in the request");
            Allure.addAttachment("username check","The username in the response matches the one in the request");
        } else {
            logger.error("The username in the response does not match the one in the request");
            Allure.addAttachment("username check","The username in the response does not matches the one in the request");
            Assertions.fail("The username in the response does not match the one in the request");
        }
    }

    @Then("Save the access token")
    public void saveAccessToken() {
        // Save access token
        accessToken = loginResponse.getTokenDetails().getAccessToken();
        logger.info("The Access Token has been saved: " + accessToken);
        Allure.addAttachment("Access Token", accessToken);
    }

}