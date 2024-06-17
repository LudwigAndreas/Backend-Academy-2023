package ru.tinkoff.edu.seminar.lesson1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.tinkoff.edu.seminar.lesson1.service.HelloWorldService;
import ru.tinkoff.edu.seminar.lesson1.web.SampleController;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class Lesson1ApplicationTests {

	public Lesson1ApplicationTests() {
		service = new HelloWorldService("world");
		controller = new SampleController(service);
	}
	private final SampleController controller;
	private final HelloWorldService service;

	@Autowired
	private SampleController contextController;
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
				.isEqualTo("Hello world");
	}

}
