package org.nextreamlabs.bradme.implementation.models.commands;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.nextreamlabs.bradme.interfaces.models.commands.ICommand;

public abstract class Command implements ICommand {

  private ListProperty<StringProperty> commandArgs;
  private StringProperty workDir;

  // { Construction

  protected Command(ListProperty<StringProperty> commandArgs, StringProperty workDir) {
    this.commandArgs = commandArgs;
    this.workDir = workDir;
  }

  // }

  // { ICommand implementation

  public ListProperty<StringProperty> commandArgs() {
    return this.commandArgs;
  }

  public String commandString() {
    StringBuilder sb = new StringBuilder();
    for (StringProperty argProperty : this.commandArgs().getValue()) {
      sb.append(argProperty.getValue()).append(" ");
    }
    return sb.toString();
  }

  public StringProperty workDir() {
    return this.workDir;
  }

  // }

  /**
   * Two commands are equals if they have:
   * - The same commandArgs
   * - The same workDir
   *
   * @param o The other object to be compared to.
   * @return true if o is equal to this, otherwise false.
   */
  @Override
  public boolean equals(Object o) {
    //noinspection InstanceofInterfaces
    if (!(o instanceof Command)) {
      return false;
    }
    ICommand otherCommand = (ICommand) o;

    return otherCommand.commandArgs().getValue().equals(this.commandArgs().getValue())
        && otherCommand.workDir().getValue().equals(this.workDir().getValue());
  }

}
