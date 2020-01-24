package com.lunatech;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class MainResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/times")
          .then()
             .statusCode(200);
    }

    @Test
    public void testQuteTemplate() {
        given()
                .when().get("/hello/Nicolas")
                .then()
                .statusCode(200);
    }

}