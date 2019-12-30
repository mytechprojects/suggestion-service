package com.coveo.suggestionservice.config;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfiguration
{
	@Bean
	public Docket createApiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.pathMapping(System.getProperty("swaggerPathMapping", "/"))
				.apiInfo(createApiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.coveo"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo createApiInfo() {
		return new ApiInfoBuilder()
			.title("Subscription Service Api")
			.version("v2")
			.build();
	}
}
