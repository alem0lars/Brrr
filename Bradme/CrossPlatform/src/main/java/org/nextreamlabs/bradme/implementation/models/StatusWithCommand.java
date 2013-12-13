package org.nextreamlabs.bradme.implementation.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.nextreamlabs.bradme.interfaces.models.commands.ICommand;
import org.nextreamlabs.bradme.interfaces.models.IStatus;
import org.nextreamlabs.bradme.interfaces.models.IStatusWithCommand;

public class StatusWithCommand extends Status implements IStatusWithCommand {

  private final ObjectProperty<ICommand> command;

  // { Construction

  private StatusWithCommand(StringProperty name, StringProperty desc, StringProperty actionName, ObjectProperty<ICommand> command) {
    super(name, desc, actionName);
    this.command = command;
  }

  public static IStatusWithCommand create(String name, String desc, String actionName, ICommand command) {
    return new StatusWithCommand(new SimpleStringProperty(name), new SimpleStringProperty(desc),
        new SimpleStringProperty(actionName), new SimpleObjectProperty<>(command));
  }

  // }

  // { IStatusWithCommand implementation

  public ObjectProperty<ICommand> command() {
    return this.command;
  }

  @Override
  public boolean equalsToStatus(IStatus status) {
    return create(this.name().get(), this.desc().get(), this.actionName().get()).equals(status);
  }

  // }

  @Override
  public boolean equals(Object o) {
    //noinspection InstanceofInterfaces
    if (!(o instanceof StatusWithCommand)) {
      return false;
    }
    IStatusWithCommand otherComponentStatus = (IStatusWithCommand) o;

    return super.equals(o)
        && this.command().getValue().equals(otherComponentStatus.command().getValue());
  }

}
