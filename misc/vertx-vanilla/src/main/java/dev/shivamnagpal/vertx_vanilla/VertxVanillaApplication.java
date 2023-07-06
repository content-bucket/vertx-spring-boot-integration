package dev.shivamnagpal.vertx_vanilla;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class VertxVanillaApplication {
  private static final Logger logger = Logger.getLogger(VertxVanillaApplication.class.getName());

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    getConfig(vertx)
      .compose(config -> vertx.deployVerticle(new MainVerticle(), new DeploymentOptions().setConfig(config)))
      .onSuccess(depId -> logger.log(Level.INFO, "Successfully deployed the Vert.x Verticle"))
      .onFailure(throwable -> {
        logger.log(Level.SEVERE, "Failed to deploy the Vert.x Verticle: {0}", throwable);
        System.exit(-1);
      });
  }

  private static Future<JsonObject> getConfig(Vertx vertx) {
    ConfigStoreOptions configStoreOptions = new ConfigStoreOptions()
      .setType("file")
      .setConfig(new JsonObject().put("path", "config.json"));
    ConfigRetrieverOptions configRetrieverOptions = new ConfigRetrieverOptions().addStore(configStoreOptions);
    return ConfigRetriever.create(vertx, configRetrieverOptions).getConfig();
  }
}
