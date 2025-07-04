package com.training.spring.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom Spring API classes based on application requirements.
 * @RestController will define class as a REST API
 */
//@RestController("greetcontroller")
public class GreetController {

    public GreetController() {
        System.out.println("GreetController Bean Created by Spring Container");
    }

    /**
     * REST API endpoint - http://localhost:9000/message
     *
     * @return String
     */
    @GetMapping("message")
    public String getMessage() {
        return "Hello from Spring Web Controller";
    }

    /**
     * REST API End POINT with Query Parameter,
     * query parameters are optional for user to pass
     * In the response body - we are sending greeting object
     */

    @GetMapping("/greet")
    public Greeting sendGreeting(@RequestParam(value = "name", defaultValue = "ABC") String name,
                                 @RequestParam(value = "age", defaultValue = "0") int age) {
        System.out.println("The query parameter passed name & age - " + name + " " + age);
        Greeting greeting = new Greeting(10);
        return greeting;
    }

    /**
     * REST API End Point with Path Parameter
     * URL : http://localhost:9000/greet/10
     */
    @GetMapping("/greet/{id}")
    public Greeting getGreetingBasedonID(@PathVariable("id") int x) {
        // creating list of Greeting objects
        List<Greeting> list = new ArrayList<>();
        list.add(new Greeting(10));
        list.add(new Greeting(20));
        list.add(new Greeting(30));
        list.add(new Greeting(40));
        list.add(new Greeting(50));

        for (Greeting obj : list) {
            if (obj.getValue() == x) {
                return obj;
            }
        }
       return null;
   }

}
