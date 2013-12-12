package org.nextreamlabs.bradme.models.status;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StatusWithCommand extends Status implements IStatusWithCommand {

  private final StringProperty commandOnEnter;
  private final StringProperty workDir;

  // { Construction

  private StatusWithCommand(StringProperty name, StringProperty desc, StringProperty actionName, StringProperty commandOnEnter, StringProperty workDir) {
    super(name, desc, actionName);
    this.commandOnEnter = commandOnEnter;
    this.workDir = workDir;
  }

  public static IStatusWithCommand create(String name, String desc, String actionName, String commandOnEnter, String workDir) {
    return new StatusWithCommand(new SimpleStringProperty(name), new SimpleStringProperty(desc),
        new SimpleStringProperty(actionName), new SimpleStringProperty(commandOnEnter),
        new SimpleStringProperty(workDir));
  }

  // }

  // { IStatusWithCommand implementation

  public StringProperty getCommandOnEnter() {
    return this.commandOnEnter;
  }

  public StringProperty getWorkDir() {
    return this.workDir;
  }

  @Override
  public boolean equalsToStatus(IStatus status) {
    return Status.create(this.name().get(), this.desc().get(), this.actionName().get()).equals(status);
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
        && this.getCommandOnEnter().equals(otherComponentStatus.getCommandOnEnter())
        && this.getWorkDir().equals(otherComponentStatus.getWorkDir());
  }

}
