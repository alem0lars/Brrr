package org.nextreamlabs.bradme.implementation.controllers;

import org.nextreamlabs.bradme.interfaces.controllers.ILogViewerController;

public class LogViewerController
    extends Controller
    implements ILogViewerController {

  // { Construction

  private LogViewerController() { }

  public static ILogViewerController create() {
    return new LogViewerController();
  }

  // }

}
