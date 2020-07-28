package com.ufpb.exe2;

import com.ufpb.exe2.filter.TokenFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	@Bean
	public FilterRegistrationBean<TokenFilter> filterJwt() {
		FilterRegistrationBean<TokenFilter> filterRB = new FilterRegistrationBean<>();
		
		filterRB.setFilter(
			new TokenFilter()
		);

		filterRB.addUrlPatterns(
			"/api/disciplinas",
			"/auth/usuarios/*"
		);

		return filterRB;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
