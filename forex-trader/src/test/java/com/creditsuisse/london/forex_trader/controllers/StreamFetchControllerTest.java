package com.creditsuisse.london.forex_trader.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.creditsuisse.london.forex_trader.orders.StreamOrder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamFetchControllerTest {

	@Test
	public void returnsAllData() {
		StreamOrder[] orders = RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.get("/emulatedstream")
				.as(StreamOrder[].class);
		Assert.assertEquals(orders.length, 1000, 0);
	}
	
}
