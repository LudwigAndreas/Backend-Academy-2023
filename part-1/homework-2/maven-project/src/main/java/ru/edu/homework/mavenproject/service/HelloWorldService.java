package ru.edu.homework.mavenproject.service;

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
		return "Если вам нужно быстро начать сборку проекта с минимальными усилиями по настройке и использовать " +
				"стандартные соглашения, то Maven может быть лучшим вариантом.\n" +
				"\n" +
				"Если у вас есть сложные требования к сборке, вы хотите большую гибкость в настройке, " +
				"или вам нужно автоматизировать множество различных задач, " +
				"то Gradle может быть более удобным и мощным выбором.";
	}

}
