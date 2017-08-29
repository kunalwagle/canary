package com.creditsuisse.london.forex_trader.orders;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;


@Component
public class HistoricalOrderRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	public HistoricalOrderRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Transactional
	public void insertHistoricalOrder(Order order){
		
		jdbcTemplate.update("insert into ORDERS(SOURCE_CURRENCY, DEST_CURRRENCY, QUANTITY, PRICE, DATE_TIME, SIDE, TOTAL, TYPE_OF_TRADE)",
			order.getSourceCurrency(), order.getDestinationCurrency(), order.getQuantity(), order.getPrice(), order.getDate(), order.getBuySell(), order.getTotal(), order.getTypeOfTrade());
	}

}
