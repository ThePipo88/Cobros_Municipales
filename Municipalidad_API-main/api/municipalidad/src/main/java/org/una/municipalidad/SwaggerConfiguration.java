package org.una.municipalidad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static java.util.Collections.singletonList;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(singletonList(new ApiKey("JWT", "AUTHORIZATION", "HEADER")))
                .securityContexts(singletonList(
                        SecurityContext.builder()
                                .securityReferences(
                                        singletonList(SecurityReference.builder()
                                                .reference("JWT")
                                                .scopes(new AuthorizationScope[0])
                                                .build()
                                        )
                                )
                                .build())
                )
                .select()
                .apis(
                        RequestHandlerSelectors
                                .basePackage("org.una.municipalidad.controllers"))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .apiInfo(apiInfo())
                .tags(new Tag("Seguridad", "Metodos de Seguridad"),
                        new Tag("Usuarios", "Entidad de Usuarios")
                );

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Sistema de cobros municipales",
                "Rest API sistema de cobros municipales",
                "Versión:1.2.0",
                "https://una.ac.cr/api/municipalidad",
                new Contact("UNA Sede Región Brunca", "https://srb.una.ac.cr/index.php/es/", "decanatosrb@una.cr"),
                "Apache-2.0", "http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
    }
}
