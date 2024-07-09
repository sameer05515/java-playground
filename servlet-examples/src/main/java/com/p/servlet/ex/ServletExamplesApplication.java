package com.p.servlet.ex;

import com.p.servlet.ex.servlets.AddNumbersServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServletExamplesApplication {

	public static void main(String[] args) {

		SpringApplication.run(ServletExamplesApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean<AddNumbersServlet> addNumbersServletRegistration() {
		ServletRegistrationBean<AddNumbersServlet> registrationBean
				= new ServletRegistrationBean<>(new AddNumbersServlet(), "/add/*");
		return registrationBean;
	}

}
