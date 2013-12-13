package org.nextreamlabs.bradme.implementation.controllers;

import org.nextreamlabs.bradme.implementation.support.Logging;
import org.nextreamlabs.bradme.interfaces.controllers.IController;

public abstract class Controller
    implements IController {

  // { Fields (+Accessors)

  private String name;
  protected final void setName(String name) {
    this.name = name;
  }
  protected final String getName() {
    return this.name;
  }

  // }

  // { Construction

  protected Controller() {
    this.setName(this.getClass().getSimpleName().replace("Controller", ""));
  }

  // }

  // { IController implementation

  public void initialize() {
    Logging.info(String.format("Initializing the controller: %s <#%s>", this.getName(), this.hashCode()));
  }

  // }

}
