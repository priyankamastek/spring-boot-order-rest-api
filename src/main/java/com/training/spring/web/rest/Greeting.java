package com.training.spring.web.rest;

public class Greeting {
     int value;

    public Greeting(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
