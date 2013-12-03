package org.nextreamlabs.bradme.views;

import javafx.scene.Node;
import org.nextreamlabs.bradme.controllers.IController;

public class ComponentView extends ViewWithTemplate<Node> {

  // { Construction

  protected ComponentView(IController controller) {
    super("component", controller);
  }

  public static ComponentView create(IController controller) {
    return new ComponentView(controller);
  }

  // }

}
