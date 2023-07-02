package dev.shivamnagpal.vertxspringdi;

import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class VertxRunner implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(VertxRunner.class.getName());
    private final Vertx vertx;
    private final MainVerticle mainVerticle;

    @Autowired
    public VertxRunner(Vertx vertx, MainVerticle mainVerticle) {
        this.vertx = vertx;
        this.mainVerticle = mainVerticle;
    }

    @Override
    public void run(String... args) {
        vertx.deployVerticle(mainVerticle)
                .onSuccess(depId -> logger.log(Level.INFO, "Successfully deployed the Vert.x Verticle"))
                .onFailure(throwable -> {
                    logger.log(Level.SEVERE, "Failed to deploy the Vert.x Verticle: {0}", throwable);
                    System.exit(-1);
                });

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                super.run();
                logger.log(Level.INFO, "Shutting down the Vert.x");
                vertx.close();
            }
        });
    }
}
