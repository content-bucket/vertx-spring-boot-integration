package dev.shivamnagpal.vertxspringboot;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.IntStream;

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

    @Bean
    public List<MainVerticle> getMainVerticles(
            Router router,
            @Value("${vertx.server.port}") Integer serverPort,
            @Value("${vertx.instance.count}") Integer instanceCount
    ) {
        return IntStream.range(0, instanceCount)
                .boxed()
                .map(index -> new MainVerticle(router, serverPort))
                .toList();
    }
}
