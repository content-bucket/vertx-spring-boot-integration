package dev.shivamnagpal.vertxspringboot;

import io.vertx.core.Vertx;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
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
    }

    @PreDestroy
    public void destroy() {
        // Intentionally used System.out.println instead of Logger. By default, Logger registers its own Shutdown hook.
        // When the application receives a shutdown event, it results in a race
        // between the Spring Context Shutdown and the Logger Shutdown.
        // Therefore, it may happen that Logger gets shutdown first and
        // the log statements in the other shutdown hooks doesn't get logged.
        //
        // The solution is to modify the Logger configuration to disable the Shutdown Hook registration and
        // then manually call LogManager.shutdown() in the Application level Shutdown Hook.
        // This solution is out of scope for this demonstration.
        System.out.println("Shutting down the Vert.x");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        vertx.close().onComplete(voidAsyncResult -> {
            if (voidAsyncResult.succeeded()) {
                System.out.println("Successfully shutdown Vert.x");
            }
            countDownLatch.countDown();
        });
        try {
            //noinspection ResultOfMethodCallIgnored
            countDownLatch.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {

        } finally {
            System.out.println("Exiting the application");
        }
    }
}
