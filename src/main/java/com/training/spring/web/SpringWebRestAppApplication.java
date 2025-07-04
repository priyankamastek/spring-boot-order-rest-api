package com.training.spring.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
public class SpringWebRestAppApplication {

    public static void main(String[] args) {
        // SpringApplication is class of Spring Boot
        // run() -> creates the spring container
        // container will get information for registering beans from @SpringBootApplication
        // By default @SpringBootApplciation will scan component from the base package
        // base package - com.training.spring.web
        WebApplicationContext context = (WebApplicationContext) SpringApplication.run(SpringWebRestAppApplication.class, args);

        String[] beanNames = context.getBeanDefinitionNames();
        for (String name : beanNames) {
            System.out.println("Bean = " + name);
        }
    }

}
