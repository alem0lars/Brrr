package org.nextreamlabs.bradme.models.status;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StatusWithCommand extends Status implements IStatusWithCommand {
  private final StringProperty commandOnEnter;

  // { Construction

  private StatusWithCommand(StringProperty name, StringProperty desc, StringProperty actionName, StringProperty commandOnEnter) {
    super(name, desc, actionName);
    this.commandOnEnter = commandOnEnter;
  }

  public static IStatusWithCommand create(String name, String desc, String actionName, String commandOnEnter) {
    return new StatusWithCommand(new SimpleStringProperty(name), new SimpleStringProperty(desc), new SimpleStringProperty(actionName), new SimpleStringProperty(commandOnEnter));
  }

  // }

  // { IStatusWithCommand implementation

  @Override
  public StringProperty getCommandOnEnter() {
    return this.commandOnEnter;
  }

  @Override
  public boolean equals(Object o) {
    //noinspection InstanceofInterfaces
    if (!(o instanceof StatusWithCommand)) {
      return false;
    }
    IStatusWithCommand otherComponentStatus = (IStatusWithCommand) o;

    return super.equals(o)
        && this.getCommandOnEnter().equals(otherComponentStatus.getCommandOnEnter());
  }

  @Override
  public boolean equalsToStatus(IStatus status) {
    return Status.create(this.name().get(), this.desc().get(), this.actionName().get()).equals(status);
  }

  // }
}
