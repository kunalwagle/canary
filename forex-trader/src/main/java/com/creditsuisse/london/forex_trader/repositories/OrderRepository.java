package com.creditsuisse.london.forex_trader.repositories;

import org.springframework.data.repository.CrudRepository;

import com.creditsuisse.london.forex_trader.orders.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
