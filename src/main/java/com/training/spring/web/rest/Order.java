package com.training.spring.web.rest;

import java.time.LocalDate;
import java.util.List;


/**
 * Represents an order placed by a customer.
 *
 * <p>The {@code Order} class contains details such as the order ID, the date the order was placed,
 * and a list of items included in the order.</p>
 *
 * <p>This class provides a parameterized constructor for initializing all fields, along with
 * standard getter and setter methods for accessing and modifying the order details.</p>
 */

public class Order {

    private long orderId;
    private LocalDate orderDate;

    private List<String> items;


    // parameterized constructor
    public Order(long orderId, LocalDate orderDate, List<String> items) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.items = items;
    }

    // getter and setters

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
