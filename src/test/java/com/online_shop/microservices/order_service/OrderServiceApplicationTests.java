package com.online_shop.microservices.order_service;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class OrderServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDBContainer.start();
	}

	@Test
	void shouldPlaceOrder() {
		String requestBody = """
                {
                    "skuCode": "iphone_15",
                    "price": 1000,
                    "quantity": 1
                }
                """;

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/orders")
				.then()
				.statusCode(201)
				.body(equalTo("Order placed successfully")); // Testet den String-Response
	}
}
