package dev.shivamnagpal.vertxspringdi;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class VertxSpringDiApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(VertxSpringDiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
