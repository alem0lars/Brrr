package org.nextreamlabs.bradme.implementation.models;

import javafx.beans.property.StringProperty;
import org.nextreamlabs.bradme.interfaces.models.ICommand;

public abstract class Command implements ICommand {

  private StringProperty commandString;
  private StringProperty workDir;

  // { Construction

  protected Command(StringProperty commandString, StringProperty workDir) {
    this.commandString = commandString;
    this.workDir = workDir;
  }

  // }

  // { ICommand implementation

  public StringProperty commandString() {
    return this.commandString;
  }

  public StringProperty workDir() {
    return this.workDir;
  }

  // }

  /**
   * Two commands are equals if they have:
   * - The same commandString
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

    return otherCommand.commandString().getValue().equals(this.commandString().getValue())
        && otherCommand.workDir().getValue().equals(this.workDir().getValue());
  }

}
