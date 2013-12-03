package org.nextreamlabs.bradme.views;

import javafx.scene.Parent;
import org.nextreamlabs.bradme.controllers.IController;

public class AppView extends ViewWithTemplate<Parent> {

  // { Construction

  protected AppView(IController controller) {
    super("app", controller);
  }

  public static AppView create(IController controller) {
    return new AppView(controller);
  }

  // }

}
