package com.training.spring.web.rest;

import com.training.spring.web.custom.exceptions.ResourceNotFoundException;
import com.training.spring.web.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Version to the REST API @RequestMapping
 */
@RestController
@RequestMapping("/api/v1")
public class OrderController {

    //public final Logger log = LogManager.getLogger();
    // You can also use SLF4j to set logging in your application
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);


    // private OrderService service = new OrderService();
    private OrderService service;


    public OrderController(@Autowired OrderService service) {
        //  System.out.println("OrderRestController created in the Spring container..");
        logger.info("OrderRestController created in the Spring container.");
        this.service = service;
    }

    /**
     * REST API ENDPOINT - http://localhost:9000/api/v1/orders
     *
     * @return GET /  - http://localhost:9000/api/v1
     */
    @GetMapping
    public ResponseEntity<String> message() {
        return new ResponseEntity<String>("Hello from OrderController", HttpStatus.OK);
    }

   @GetMapping("/welcome7")
   public ResponseEntity<String> welcome(){
       logger.info("GET: /welcome url added");
        return ResponseEntity.status(HttpStatus.OK).body("Testing Devtools");
   }


    /**
     * Retrieves a list of sample {@link Order} objects.
     *
     * <p>This endpoint handles HTTP GET requests to {@code /orders/all} and returns a hardcoded list
     * of orders for demonstration or testing purposes. Each order includes an ID, a date, and a list of items.</p>
     *
     * <p>The method uses both {@code ArrayList} and {@code List.of()} to create item lists for the orders.
     * The returned list is not fetched from a database or external source, and is intended for mock or static use.</p>
     *
     * @return a {@link List} of {@link Order} objects containing sample data
     */

    @RequestMapping(method = RequestMethod.GET, value = "/orders/all", produces = "application/json")
    // old annotation to map URLs with Enum Requestmethod
    public List<Order> getOrdersList() {
        List<Order> ordersList = new ArrayList<>();
        // List of Strings created for the Order object
        List<String> items = new ArrayList<>();
        items.add("Item1");
        items.add("Item2");
        ordersList.add(new Order(1001, LocalDate.now(), items));
        // List.of() method from List interface created list of items
        ordersList.add(new Order(1002, LocalDate.now(), List.of("A", "B", "C")));
        ordersList.add(new Order(1003, LocalDate.of(2025, 7, 1), List.of("A1", "B1", "C1", "D1")));
        return ordersList;
    }

    /**
     * This method returns a list of orders in the response body
     * http request packet - request header
     * http response packet - response body [ArrayList of Orders is converted into JSON]
     *
     * @return List<Order>
     */
    @GetMapping("/orders")
    public List<Order> getOrders() throws ResourceNotFoundException {
        List<Order> list = this.service.getOrderDetails();
        if (list == null) {
            logger.error("Order List is empty or null");
            throw new ResourceNotFoundException("Resource not found.");
        }
        logger.info("RestController Sends List of Orders in the Response Body");
        return list;
    }

    /**
     * POST method of REST API creates new resource (object)
     * Sends HTTP response status code - 201 , status message - Created
     *@return ResponseEntity<Order> - ResponseEntity: A generic wrapper for HTTP responses in Spring.
     * It allows you to customize the status code, headers, and body of the response.</Order>
     * @Param order
     * @RequestBody - Binds the request body data sent from client to the Order object
     */
    @PostMapping("/orders/create")
    public ResponseEntity<Order> createNewOrder(@RequestBody Order order) {
        logger.info("Creating new Order: {}", order);

        try {
            Order createdOrder = service.addOrderDetails(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (ResourceNotFoundException e) {
            logger.error("Resource not found while creating order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Unexpected error while creating order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * Retrieves an {@Link Order} by its unique identifier.
     *
     * <p>This endpoint handles HTTP GET requests to fetch order details based on the provided ID.
     * If the order is found, it returns a 200 OK response with the order data.
     * If the order is not found, it returns a 404 Not Found response.
     * In case of unexpected errors, it returns a 500 Internal Server Error response.</p>
     *
     * @param id the unique identifier of the order to retrieve
     * @return a {@link ResponseEntity} containing the {@link Order} object if found,
     * or an appropriate HTTP status code if not found or if an error occurs
     */

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> findOrderByID(@PathVariable int id) {
        logger.info("Fetching order with ID: {}", id);
        try {
            Order order = service.findOrderById(id);
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException e) {
            logger.error("Order not found with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Unexpected error while fetching order with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * Updates an existing {@link Order} resource identified by its unique ID.
     *
     * <p>This endpoint handles HTTP PUT requests to update the details of an existing order.
     * The updated order data is provided in the request body. If the order exists, it is updated
     * and returned with a 200 OK status. If the order is not found, a 404 Not Found status is returned.
     * In case of unexpected errors, a 500 Internal Server Error status is returned.</p>
     *
     * @param id    the unique identifier of the order to be updated
     * @param order the {@link Order} object containing updated order details
     * @return a {@link ResponseEntity} containing the updated {@link Order} object if successful,
     * or an appropriate HTTP status code if the order is not found or an error occurs
     */

    @PutMapping("/orders/update/{id}")
    public ResponseEntity<Order> updateOrderById(@PathVariable int id, @RequestBody Order order) {

        logger.info("Updating order with ID: {}", id);
        try {
            Order updatedOrder = service.updateOrder(id, order);
            return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
            // return ResponseEntity.ok(updatedOrder);
        } catch (ResourceNotFoundException e) {
            logger.error("Order not found with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Unexpected error while updating order with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * Deletes an existing {@link Order} resource identified by its unique ID.
     *
     * <p>This endpoint handles HTTP DELETE requests to remove an order from the system.
     * If the order exists, it is deleted and a 204 No Content response is returned.
     * If the order is not found, a 404 Not Found response is returned.
     * In case of unexpected errors, a 500 Internal Server Error response is returned.</p>
     *
     * @param id the unique identifier of the order to be deleted
     * @return a {@link ResponseEntity} with appropriate HTTP status code based on the outcome
     */

    public ResponseEntity<Void> deleteOrderById(int id) {
        logger.info("Deleting order with ID: {}", id);
        try {
            service.deleteOrder(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (ResourceNotFoundException e) {
            logger.error("Order not found with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        } catch (Exception e) {
            logger.error("Unexpected error while deleting order with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }


    /**
     * Retrieves the total number of orders in the system.
     *
     * <p>This endpoint handles HTTP GET requests to count all existing orders.
     * It returns a JSON response containing a key-value pair where the key is
     * {@code "totalOrders"} and the value is the count of orders.</p>
     *
     * <p>If the operation is successful, it returns a 200 OK response with the count.
     * In case of unexpected errors, it returns a 500 Internal Server Error response.</p>
     *
     * @return a {@link ResponseEntity} containing a {@link Map} with the total order count,
     * or an appropriate HTTP status code if an error occurs
     */

    @GetMapping("/orders/count")
    public ResponseEntity<Map<String, Integer>> countOrders() {
        int count = service.countOrders();
        try {
            Map<String, Integer> response = new HashMap<>();
            response.put("totalOrders", count);
            return ResponseEntity.ok(response); // 200 OK with map in body
        } catch (Exception e) {
            logger.error("Error while counting orders: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }


}
