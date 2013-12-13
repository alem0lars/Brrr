package org.nextreamlabs.bradme.interfaces.factories.controllers_factories;

import org.nextreamlabs.bradme.interfaces.controllers.IController;

public interface IControllerFactory<TController extends IController> {
  public TController createController();
}
