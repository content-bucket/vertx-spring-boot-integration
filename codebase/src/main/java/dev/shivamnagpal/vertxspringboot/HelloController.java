package dev.shivamnagpal.vertxspringboot;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import org.springframework.stereotype.Controller;

@Controller
public class HelloController {
    private final Router router;

    public HelloController(Router router) {
        this.router = router;

        defineEndpoints();
    }

    private void defineEndpoints() {
        this.router.get("/hello").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.setStatusCode(HttpResponseStatus.OK.code())
                    .end("Hello from Vert.x");
        });
    }
}
