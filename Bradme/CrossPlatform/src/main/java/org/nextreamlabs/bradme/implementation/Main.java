package org.nextreamlabs.bradme.implementation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.nextreamlabs.bradme.implementation.config.ConfigBuilder;
import org.nextreamlabs.bradme.implementation.config.GlobalConfig;
import org.nextreamlabs.bradme.implementation.controllers.AppController;
import org.nextreamlabs.bradme.implementation.support.L10N;
import org.nextreamlabs.bradme.implementation.support.Logging;
import org.nextreamlabs.bradme.implementation.views.AppView;
import org.nextreamlabs.bradme.interfaces.config.IConfiguration;
import org.nextreamlabs.bradme.interfaces.controllers.IAppController;
import org.nextreamlabs.bradme.interfaces.views.IAppView;

/**
 * The application entry point.
 */
@SuppressWarnings({"PublicConstructor", "PublicMethodNotExposedInInterface"})
public class Main extends Application {

  private static final int MAIN_SCENE_WIDTH;
  private static final int MAIN_SCENE_HEIGHT;

  static {
    MAIN_SCENE_WIDTH = 640;
    MAIN_SCENE_HEIGHT = 480;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Logging.info("Application starting");
    IAppController appController = AppController.create();
    IAppView appView = AppView.create(appController);
    primaryStage.setTitle(L10N.t("bradme_title"));
    primaryStage.setScene(new Scene(appView.getRootNode(), MAIN_SCENE_WIDTH, MAIN_SCENE_HEIGHT));
    primaryStage.show();
    Logging.info("Application started");
  }

  @Override
  public void stop() throws Exception {
    Logging.info("Application stopping");
    super.stop();
    Logging.info("Application stopped");
  }

  public static void main(String[] args) {
    IConfiguration config = ConfigBuilder.create().parseArgs(args);
    GlobalConfig.initialize(config);
    launch(args);
  }

}
