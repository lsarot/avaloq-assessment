package com.leonardo.demos.avaloqassessment.configuration.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * http://localhost:8080/swagger-ui/index.html
 * */
@Import({
        BeanValidatorPluginsConfiguration.class, //Support for JavaBeanValidation JSR303
})
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("com.leonardo.demos")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.leonardo.demos.avaloqassessment.controller.rest"))
                .paths(PathSelectors.any())
                //.paths(PathSelectors.regex("/api.*"))
                //.paths(PathSelectors.ant("/api/v1/*"))
                .build()
                ;
    }


    /**
     * API information
     * */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("AVALOQ SIMULATIONS REST API")
                .version("1.0")
                .description("PoC of a REST API")
                .termsOfServiceUrl("http://www.mydomain/terms-of-service")
                .contact(new Contact("contact-name","www.contact.com","user@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

}
