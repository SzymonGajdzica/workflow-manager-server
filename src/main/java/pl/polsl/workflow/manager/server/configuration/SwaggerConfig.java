package pl.polsl.workflow.manager.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket getDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(getApiInfo())
                .securityContexts(Collections.singletonList(getSecurityContext()))
                .securitySchemes(Collections.singletonList(getApiKey()))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Workflow manager rest api")
                .description("Workflow manager")
                .license("Licence")
                .licenseUrl("https://opensource.org/licenses/mit-license.html")
                .termsOfServiceUrl(null)
                .version("0.0.1").contact(new Contact("Szymon Gajdzica","", "szymgaj226@student.polsl.pl"))
                .build();
    }

    private ApiKey getApiKey() {
        return new ApiKey("JWT", Parameters.Authorization.HEADER, "header");
    }

    private SecurityContext getSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(getDefaultAuth()))
                .operationSelector(o -> true)
                .build();
    }

    private SecurityReference getDefaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new SecurityReference("JWT", authorizationScopes);
    }

}