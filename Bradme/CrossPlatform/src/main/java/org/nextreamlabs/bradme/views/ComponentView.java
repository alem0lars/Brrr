package org.nextreamlabs.bradme.views;

import javafx.scene.Node;
import org.nextreamlabs.bradme.controllers.IController;

public class ComponentView extends ViewWithTemplate<Node> implements IComponentView {

  // { Construction

  protected ComponentView(IController controller) {
    super("component", controller);
  }

  public static IComponentView create(IController controller) {
    return new ComponentView(controller);
  }

  // }

}
