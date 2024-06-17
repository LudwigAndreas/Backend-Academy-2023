package ru.tinkoff.edu.seminar.lesson1.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.tinkoff.edu.seminar.lesson1.service.HelloWorldService;

@Controller
public class SampleController {

	@Autowired
	public SampleController(HelloWorldService helloWorldService){
		this.helloWorldService = helloWorldService;
	}

	private final HelloWorldService helloWorldService;

	private Logger logger = LoggerFactory.getLogger(SampleController.class);

	@RequestMapping("/")
	@ResponseBody
	public String helloWorld() {
		logger.info("Кто-то вызвал контроллер hello world на mvn");
		return this.helloWorldService.getHelloMessage();
	}
}
