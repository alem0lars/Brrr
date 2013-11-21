package org.nextreamlabs.bradme.models.component_status;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ComponentStatus implements IComponentStatus {
  private StringProperty name;
  private StringProperty desc;

  // { Construction

  private ComponentStatus(StringProperty name, StringProperty desc) {
    this.name = name;
    this.desc = desc;
  }

  public static IComponentStatus create(String name, String desc) {
    return new ComponentStatus(new SimpleStringProperty(name), new SimpleStringProperty(desc));
  }

  // }

  // { IComponentStatus implementation

  public StringProperty name() {
    return this.name;
  }

  public StringProperty desc() {
    return this.desc;
  }

  public String getPrettyName() {
    return this.name().get();
  }

  // }

}
