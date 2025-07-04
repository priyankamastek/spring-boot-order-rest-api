package com.training.spring.web.service;

import com.training.spring.web.custom.exceptions.ResourceNotFoundException;
import com.training.spring.web.rest.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Component and @Service  - no special features
 * Developer can use service to build the logics for the application
 * Add business methods
 */
@Service
public class OrderService {

    public final Logger log = LogManager.getLogger();
    List<Order> ordersList = new ArrayList<>();

    public OrderService() {
        // System.out.println("OrderService service bean created by the Spring container...");
        log.info("OrderService service bean created by the Spring container...");
        List<String> items = new ArrayList<>();
        items.add("Item1");
        items.add("Item2");
        ordersList.add(new Order(1001, LocalDate.now(), items));// List.of() method from List interface created list of items
        ordersList.add(new Order(1002, LocalDate.now(), List.of("A", "B", "C")));
        ordersList.add(new Order(1003, LocalDate.of(2025, 7, 1), List.of("A1", "B1", "C1", "D1")));
        //ordersList.add(null);
    }

    public List<Order> getOrderDetails() {
        //System.out.println("Within the getOrderDetails() called...");
        log.info("OrderService service bean created by the Spring container...");
        return ordersList;
    }

    // Add a new object of type Order in the list
    // void or List of orders or String or Order object
    public Order addOrderDetails(Order newOrder) throws ResourceNotFoundException {
        if (newOrder != null) {
            ordersList.add(newOrder);
            return newOrder;
        } else
            throw new ResourceNotFoundException("Order Resource not found");
    }


    public Order findOrderById(int orderId) throws ResourceNotFoundException {
        if (ordersList == null || ordersList.isEmpty()) {
            throw new ResourceNotFoundException("Order list is empty or not initialized.");
        }
        for (Order order : ordersList) {
            if (order != null && order.getOrderId() == orderId) {
                return order;
            }
        }
        throw new ResourceNotFoundException("Order with ID " + orderId + " not found.");
    }


// Upodate Existing order -> NewOrder
    // ArrayList.indexOf() -> int , ArrayList.set(index, E)
    public Order updateOrder(int id, Order order) throws ResourceNotFoundException {
        // you have method in your class to find order by id, call it here
        Order existingOrder = findOrderById(id);

        if (existingOrder == null) {
            throw new ResourceNotFoundException("Order Resource not found");
        }
        // Find the index of the existing order in the list
        int index = ordersList.indexOf(existingOrder);
        System.out.println("Index = " + index);

        // Update the order at the found index
        ordersList.set(index, order);
        System.out.println("List = " + ordersList);

        return order;
    }

    public void deleteOrder(int id) throws ResourceNotFoundException {
        Order existingOrder = findOrderById(id);
        if (existingOrder == null) {
            throw new ResourceNotFoundException("Order Resource not found");
        }
        int index = ordersList.indexOf(existingOrder);
       //  ordersList.remove(existingOrder);
       // ordersList.remove(findOrderById(id));
    }

    public int countOrders() {

        return this.ordersList.size();
    }

    /**
     * Implement method to find orders by Date
     *
     * @param orderDate
     * @return
     */
    public List<Order> findOrderByDate(LocalDate orderDate) {
        return null;
    }
}
