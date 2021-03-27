package com.tr.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.tr.app.security.config.SecurityConfig;
import com.tr.app.swagger.SwaggerConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
@Import({SecurityConfig.class,SwaggerConfig.class})
public class SpringSecurityJwtIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtIntegrationApplication.class, args);
	}

}
