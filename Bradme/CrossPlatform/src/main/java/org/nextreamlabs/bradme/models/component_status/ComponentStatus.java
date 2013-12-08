package org.nextreamlabs.bradme.models.component_status;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.nextreamlabs.bradme.models.status.Status;

public class ComponentStatus extends Status implements IComponentStatus {
  private final StringProperty commandOnEnter;

  // { Construction

  private ComponentStatus(StringProperty name, StringProperty desc, StringProperty actionName, StringProperty commandOnEnter) {
    super(name, desc, actionName);
    this.commandOnEnter = commandOnEnter;
  }

  public static IComponentStatus create(String name, String desc, String actionName, String commandOnEnter) {
    return new ComponentStatus(new SimpleStringProperty(name), new SimpleStringProperty(desc), new SimpleStringProperty(actionName), new SimpleStringProperty(commandOnEnter));
  }

  // }

  // { IComponentStatus implementation

  @Override
  public StringProperty getCommandOnEnter() {
    return this.commandOnEnter;
  }

  @Override
  public boolean equals(Object o) {
    //noinspection InstanceofInterfaces
    if (!(o instanceof ComponentStatus)) {
      return false;
    }
    IComponentStatus otherComponentStatus = (IComponentStatus) o;

    return super.equals(o)
        && this.getCommandOnEnter().equals(((IComponentStatus) o).getCommandOnEnter());
  }

  // }
}
