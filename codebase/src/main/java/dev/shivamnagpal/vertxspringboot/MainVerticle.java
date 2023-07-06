package dev.shivamnagpal.vertxspringboot;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MainVerticle extends AbstractVerticle {

    private static final Logger logger = Logger.getLogger(MainVerticle.class.getName());
    private final Router router;
    private final Integer serverPort;

    @Autowired
    public MainVerticle(Router router, @Value("${vertx.server.port}") Integer serverPort) {
        this.router = router;
        this.serverPort = serverPort;
    }


    @Override
    public void start(Promise<Void> startPromise) {
        vertx.createHttpServer().requestHandler(router).listen(serverPort, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                logger.log(Level.INFO, "HTTP server started on port {0}", String.valueOf(serverPort));
            } else {
                startPromise.fail(http.cause());
            }
        });
    }
}
