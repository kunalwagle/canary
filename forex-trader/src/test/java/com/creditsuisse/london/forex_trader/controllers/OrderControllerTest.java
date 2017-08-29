package com.creditsuisse.london.forex_trader.controllers;

import org.apache.http.HttpStatus;
import org.apache.http.impl.bootstrap.HttpServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.creditsuisse.london.forex_trader.App;
import com.creditsuisse.london.forex_trader.orders.BuySell;
import com.creditsuisse.london.forex_trader.orders.Currency;
import com.creditsuisse.london.forex_trader.orders.Order;
import com.creditsuisse.london.forex_trader.orders.Type;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={App.class})
public class OrderControllerTest {
    
    private Order happyOrder;
 
    @Before
    public void initialise() {
    	RestAssured.port = 8080;
    	happyOrder = new Order(3, 10, "2015-10-04_18:00:00.050", Currency.USD, Currency.GBP, Type.MARKET, BuySell.BUY);
    }
	
	@Test
	public void randomTestToSeeIfWeCanWriteATest() {
		RestAssured.given()
		.contentType("application/json")
		.body(happyOrder)
		.when()
		.post("/addorder")
		.then()
		.statusCode(HttpStatus.SC_OK);
		
		
//		Order order = orderController.addOrder(happyOrder);
//		Assert.assertEquals(order.getPrice(), happyOrder.getPrice(), 0.0);
//		Assert.assertEquals(order.getQuantity(), happyOrder.getQuantity(), 0);
//		Assert.assertEquals(order.getDate(), happyOrder.getDate());
//		Assert.assertEquals(order.getBuySell(), happyOrder.getBuySell());
//		Assert.assertEquals(order.getDestination(), happyOrder.getDestination());
//		Assert.assertEquals(order.getSource(), happyOrder.getSource());
//		Assert.assertEquals(order.getType(), happyOrder.getType());
	}
	
	@Test
	public void quantityFailsWhenZero() {
		happyOrder.setQuantity(0);
		RestAssured.given().
		contentType("application/json")
		.body(happyOrder)
		.when()
		.post("/addorder")
		.then()
		.statusCode(HttpStatus.SC_BAD_REQUEST);
	}

}
