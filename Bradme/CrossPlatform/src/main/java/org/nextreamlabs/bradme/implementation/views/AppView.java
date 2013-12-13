package org.nextreamlabs.bradme.implementation.views;

import javafx.scene.Parent;
import org.nextreamlabs.bradme.interfaces.controllers.IAppController;
import org.nextreamlabs.bradme.interfaces.views.IAppView;

public class AppView
    extends ViewWithTemplate<IAppController, Parent>
    implements IAppView {

  // { Construction

  protected AppView(IAppController controller) {
    super("app", controller);
  }

  public static IAppView create(IAppController controller) {
    return new AppView(controller);
  }

  // }

}
