package services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.creditsuisse.london.forex_trader.orders.ForexOrder;
import com.creditsuisse.london.forex_trader.orders.OrderError;
import com.creditsuisse.london.forex_trader.repositories.OrderRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class OrderRepositoryService {

	
	private final OrderRepository orderRepository;

	
	public OrderRepositoryService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@HystrixCommand(fallbackMethod = "orderFallback")
	public Object addOrder( ForexOrder order) 
	{
		OrderError errorType = generateDataErrorString(order);
		if (errorType != null) {
			return errorType;
		}
		this.orderRepository.save(order);
		return order;
	}
	
	
	@HystrixCommand(fallbackMethod = "orderFallback")
	public ForexOrder getOrderById( Long id) {
		return orderRepository.findOne(id);
	}
	
	
	@HystrixCommand(fallbackMethod = "deleteOrderFallback")
    public Object deleteOrderById(Long id) {
    	ForexOrder order = this.orderRepository.findOne(id);
    	if (order.isCompleted()) {
            return OrderError.CANNOT_DELETE_COMPLETED_ORDER;
        }   
        this.orderRepository.delete(id);
        if (!orderRepository.exists(id)) {
        	return HttpStatus.ACCEPTED;
        }
        return null;
    }
	
	public Object orderFallback(ForexOrder order) {
		return HttpStatus.SERVICE_UNAVAILABLE;
	}
	
	public Object deleteOrderFallback(Long id) {
		return HttpStatus.SERVICE_UNAVAILABLE;
	}

	private OrderError generateDataErrorString(ForexOrder order) {
		if (order.getQuantity() <= 0) {
			return OrderError.QUANTITY_ZERO;
		}
		
		if (order.getSource() == null || order.getDestination() == null) {
			return OrderError.CURRENCY_MISSING;
		}
		
		if (order.getSource() == order.getDestination()) {
			return OrderError.CURRENCY_IDENTICAL;
		}
		
		if (order.getPrice() <= 0) {
			return OrderError.PRICE_ZERO;
		}
		
		
		
		return null;
	}
}
