package org.nextreamlabs.bradme.factories.controller_factories;

import org.nextreamlabs.bradme.controllers.IController;

public interface IControllerFactory<TController extends IController> {
  public TController createController();
}
