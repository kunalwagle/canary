package com.creditsuisse.london.forex_trader.controllers;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.creditsuisse.london.forex_trader.orders.StreamOrder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamControllerTest {
	
	@Test
	public void returnsAllDataForLargeDateRange() {
		StreamOrder[] orders = RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.get("/stream/2015-01-01/2018-01-01")
				.as(StreamOrder[].class);
		Assert.assertEquals(orders.length, 1000, 0);
	}
	
	@Test
	public void filtersDataForSmallDataRange() {
		StreamOrder[] orders = RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.get("/stream/2017-01-03/2017-01-04")
				.as(StreamOrder[].class);
		Assert.assertEquals(orders.length, 7, 0);
	}
	
	@Test
	public void returnsNothingForPoorDateRange() {
		StreamOrder[] orders = RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.get("/stream/2017-01-05/2017-01-04")
				.as(StreamOrder[].class);
		Assert.assertEquals(orders.length, 0, 0);
	}
	
	

}