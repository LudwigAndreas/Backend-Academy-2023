package ru.tinkoff.edu.seminar.lesson1.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldService {

	public HelloWorldService(
			@Value("${name:World}") String name) {
		this.name = name;
	}
	
	private final String name;

	public String getHelloMessage() {
		return "Hello " + this.name;
	}

}
