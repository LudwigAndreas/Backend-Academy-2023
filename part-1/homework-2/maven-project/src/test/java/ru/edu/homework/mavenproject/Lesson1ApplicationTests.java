package ru.edu.homework.mavenproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.edu.homework.mavenproject.controller.SampleController;
import ru.edu.homework.mavenproject.service.HelloWorldService;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class Lesson1ApplicationTests {
	private final SampleController controller;
	private final HelloWorldService service;

	private final SampleController contextController;

	@Autowired
	public Lesson1ApplicationTests(SampleController contextController) {
		service = new HelloWorldService("world");
		controller = new SampleController(service);
		this.contextController = contextController;
	}

	@Test
	private void contextLoads() {
		assertThat(contextController)
				.as("Контроллер должен быть доступен из контекста спринга")
				.isNotNull();
	}

	@Test
	void controllerTest() {
		assertThat(controller.helloWorld())
				.as("значение контроллера и сервиса должны совпадать")
				.isEqualTo(service.getHelloMessage());
	}

	@Test
	void serviceTest() {
		assertThat(service.getHelloMessage())
				.as("Сервис должен вернуть ожидаемую строку")
				.isEqualTo("Если вам нужно быстро начать сборку проекта с минимальными усилиями по настройке и использовать " +
						"стандартные соглашения, то Maven может быть лучшим вариантом.\n" +
						"\n" +
						"Если у вас есть сложные требования к сборке, вы хотите большую гибкость в настройке, " +
						"или вам нужно автоматизировать множество различных задач, " +
						"то Gradle может быть более удобным и мощным выбором.");
	}

}
