package io.spring;

import io.spring.controllers.SmallworldTicsConnectionController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackageClasses = {
    SmallworldTicsConnectionController.class })

/**
 *
 */
public class Server extends SpringBootServletInitializer {

    /**
     *
     */
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    /**
     *
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Server.class);
    }
}
