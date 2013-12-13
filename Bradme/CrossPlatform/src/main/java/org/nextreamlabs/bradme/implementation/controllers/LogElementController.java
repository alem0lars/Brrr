package org.nextreamlabs.bradme.implementation.controllers;

import org.nextreamlabs.bradme.interfaces.controllers.ILogElementController;

public class LogElementController
    extends Controller
    implements ILogElementController {

  // { Construction

  private LogElementController() { }

  public static ILogElementController create() {
    return new LogElementController();
  }

  // }

}
