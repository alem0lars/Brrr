package org.nextreamlabs.bradme.models.component;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.nextreamlabs.bradme.models.component_status.IComponentStatus;

public class Component implements IComponent {

  private IComponentStatus status;
  private StringProperty name;

  // { Construction

  protected Component(StringProperty name, IComponentStatus status) {
    this.name = name;
    this.status = status;
  }

  public static IComponent create(String name, IComponentStatus initialStatus) {
    return new Component(new SimpleStringProperty(name), initialStatus);
  }

  // }

  // { IComponent implementation

  public StringProperty name() {
    return this.name;
  }

  public IComponentStatus status() {
    return this.status;
  }

  // }

}
