package com.fdmgroup.spring.timothy_chai_ecommerce_project.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.spring.timothy_chai_ecommerce_project.model.Cart;
import com.fdmgroup.spring.timothy_chai_ecommerce_project.model.Customer;
import com.fdmgroup.spring.timothy_chai_ecommerce_project.model.Order;
import com.fdmgroup.spring.timothy_chai_ecommerce_project.repository.CartItemRepository;
import com.fdmgroup.spring.timothy_chai_ecommerce_project.repository.CustomerRepository;
import com.fdmgroup.spring.timothy_chai_ecommerce_project.repository.OrderRepository;

/**
 * This service class is used to create and manage orders.
 * 
 * @author timothy.chai
 * 
 * @see Order
 *
 */
@Service
public class OrderService {

	private static Logger logger = LogManager.getLogger(OrderService.class);

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private CartItemRepository cartItemRepo;

	@Autowired
	private CustomerRepository customerRepo;

	/**
	 * Saves the given order to the database.
	 * 
	 * @param order
	 * @return
	 */
	public Order save(Order order) {
		return orderRepo.save(order);
	}

	/**
	 * Creates a new order for the given customer.
	 * 
	 * @param customer
	 * @return
	 */
	public Order createOrder(Customer customer) {
		logger.debug("createOrder called for customer " + customer);

		Optional<Customer> optionalCustomer = customerRepo.findById(customer.getCustomerID());
		if (optionalCustomer.isEmpty()) {
			return new Order();
		}
		Customer currentCustomer = optionalCustomer.get();
		Cart cart = currentCustomer.getCart();

		// add items of cart to order
		Order order = new Order(cart, currentCustomer);
		order.updateOrderTotalPrice();

		orderRepo.save(order);
		return order;
	}

//	public List<Order> getOrdersFromCustomer(Customer customer) {
//		List<Order> orders = new ArrayList<>();
//		Optional<Customer> optionalCustomer = customerRepo.findById(customer.getCustomerID());
//		if (optionalCustomer.isEmpty()) {
//			return orders;
//		}
//		Customer currentCustomer = optionalCustomer.get();
//		return currentCustomer.getOrders();
//
//	}

}
