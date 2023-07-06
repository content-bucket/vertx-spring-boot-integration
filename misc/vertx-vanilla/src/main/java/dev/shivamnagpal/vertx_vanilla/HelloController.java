package dev.shivamnagpal.vertx_vanilla;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

import java.util.UUID;

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

    this.router.get("/hello-dynamic").handler(routingContext -> {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < 10; i++) {
        stringBuilder.append(UUID.randomUUID());
      }
      HttpServerResponse response = routingContext.response();
      response.setStatusCode(HttpResponseStatus.OK.code())
        .end(stringBuilder.toString());
    });
  }
}
