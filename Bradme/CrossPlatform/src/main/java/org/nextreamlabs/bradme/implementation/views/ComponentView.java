package org.nextreamlabs.bradme.implementation.views;

import javafx.scene.Node;
import org.nextreamlabs.bradme.interfaces.controllers.IComponentController;
import org.nextreamlabs.bradme.interfaces.views.IComponentView;

public class ComponentView
    extends ViewWithTemplate<IComponentController, Node>
    implements IComponentView {

  // { Construction

  protected ComponentView(IComponentController controller) {
    super("component", controller);
  }

  public static IComponentView create(IComponentController controller) {
    return new ComponentView(controller);
  }

  // }

}
