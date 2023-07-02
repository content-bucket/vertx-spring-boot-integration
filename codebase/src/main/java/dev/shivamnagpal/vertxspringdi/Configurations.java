package dev.shivamnagpal.vertxspringdi;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configurations {

    @Bean
    public Vertx getVertx() {
        return Vertx.vertx();
    }

    @Bean
    public Router getRouter(Vertx vertx) {
        return Router.router(vertx);
    }
}
