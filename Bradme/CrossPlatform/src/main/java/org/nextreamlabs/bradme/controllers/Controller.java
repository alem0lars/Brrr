package org.nextreamlabs.bradme.controllers;

import org.nextreamlabs.bradme.support.Logging;

public abstract class Controller implements IController {

  private String name;

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

  protected final String getName() {
    return this.name;
  }

  protected final void setName(String name) {
    this.name = name;
  }

}
