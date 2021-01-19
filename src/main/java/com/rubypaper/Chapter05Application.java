package com.rubypaper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Chapter05Application {

	// 이 예제는 jpa 를 서블릿 기반이 아닌 버전으로 테스트
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Chapter05Application.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}
}
