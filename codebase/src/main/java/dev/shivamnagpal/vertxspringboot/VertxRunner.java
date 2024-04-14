package dev.shivamnagpal.vertxspringboot;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private final Router router;

    private final Integer serverPort;

    @Autowired
    public VertxRunner(Vertx vertx, Router router, @Value("${vertx.server.port}") Integer serverPort) {
        this.vertx = vertx;
        this.router = router;
        this.serverPort = serverPort;
    }

    @Override
    public void run(String... args) {
        vertx.deployVerticle(() -> new MainVerticle(router, serverPort), new DeploymentOptions().setInstances(10))
                .onSuccess(depId -> logger.log(Level.INFO, "Successfully deployed the Vert.x Verticle"))
                .onFailure(throwable -> {
                    logger.log(Level.SEVERE, "Failed to deploy the Vert.x Verticle", throwable);
                    System.exit(-1);
                });
    }

    @SuppressWarnings("java:S106")
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
            boolean isVertxDown = countDownLatch.await(30, TimeUnit.SECONDS);
            if (!isVertxDown) {
                System.out.println("Couldn't exit the Vert.x gracefully");
            }
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Exiting the application");
        }
    }
}
