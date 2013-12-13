package org.nextreamlabs.bradme.implementation.models.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.nextreamlabs.bradme.interfaces.models.commands.IRemoteCommand;

public class RemoteCommand
    extends Command
    implements IRemoteCommand {

  private StringProperty hostName;
  private IntegerProperty port;
  private StringProperty executingUser;

  // { Construction

  protected RemoteCommand(
      StringProperty commandString,
      StringProperty workDir,
      StringProperty hostName,
      IntegerProperty port,
      StringProperty executingUser) {

    super(commandString, workDir);

    this.hostName = hostName;
    this.port = port;
    this.executingUser = executingUser;
  }

  public static IRemoteCommand create(
      String commandString,
      String workDir,
      String hostName,
      Integer port,
      String executingUser) {

    return new RemoteCommand(
        new SimpleStringProperty(commandString),
        new SimpleStringProperty(workDir),
        new SimpleStringProperty(hostName),
        new SimpleIntegerProperty(port),
        new SimpleStringProperty(executingUser));

  }

  // }

  @Override
  public StringProperty hostName() {
    return this.hostName;
  }

  @Override
  public IntegerProperty port() {
    return this.port;
  }

  @Override
  public StringProperty executingUser() {
    return this.executingUser;
  }

  /**
   * Two remote commands are equals if they are the same commands and have:
   * - The same hostName
   * - The same port
   * - The same executingUser
   *
   * @param o The other object to be compared to.
   * @return true if o is equal to this, otherwise false.
   */
  @Override
  public boolean equals(Object o) {
    //noinspection InstanceofInterfaces
    if (!(o instanceof RemoteCommand)) {
      return false;
    }
    IRemoteCommand otherCommand = (IRemoteCommand) o;

    return super.equals(o)
        && this.hostName().getValue().equals(otherCommand.hostName().getValue())
        && this.port().getValue().equals(otherCommand.port().getValue())
        && this.executingUser().getValue().equals(otherCommand.executingUser().getValue());
  }

}
