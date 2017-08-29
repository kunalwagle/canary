package com.creditsuisse.london.forex_trader.orders;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;


@Component
public class HistoricalOrderRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	public HistoricalOrderRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Transactional
	public void insertHistoricalOrder(Order order){
		jdbcTemplate.update("insert into HISTORICAL_ORDERS(")
	}

}
