package org.nextreamlabs.bradme;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.nextreamlabs.bradme.support.Logging;
import org.nextreamlabs.bradme.support.Resources;
import org.nextreamlabs.bradme.support.Templates;

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
    Parent root = FXMLLoader.load(Templates.getTemplateURL("app"));
    primaryStage.setTitle(Resources.t(Resources.Keys.I18N.BRADME_TITLE));
    primaryStage.setScene(new Scene(root, MAIN_SCENE_WIDTH, MAIN_SCENE_HEIGHT));
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
    launch(args);
  }

}
