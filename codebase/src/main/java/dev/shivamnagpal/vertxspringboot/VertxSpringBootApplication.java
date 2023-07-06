package dev.shivamnagpal.vertxspringboot;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class VertxSpringBootApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(VertxSpringBootApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
