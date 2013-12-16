package org.nextreamlabs.bradme.implementation.models.commands;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.nextreamlabs.bradme.implementation.commands.RemoteCommandRunner;
import org.nextreamlabs.bradme.interfaces.commands.ICommandRunner;
import org.nextreamlabs.bradme.interfaces.commands.IRemoteCommandRunner;
import org.nextreamlabs.bradme.interfaces.models.commands.IRemoteCommand;

import java.io.IOException;
import java.util.List;

public class RemoteCommand
    extends Command
    implements IRemoteCommand {

  private StringProperty hostName;
  private IntegerProperty port;
  private StringProperty executingUser;
  private final IRemoteCommandRunner runner;

  // { Construction

  protected RemoteCommand(
      ListProperty<StringProperty>commandArgs,
      StringProperty workDir,
      StringProperty hostName,
      IntegerProperty port,
      StringProperty executingUser) throws IOException {

    super(commandArgs, workDir);

    this.hostName = hostName;
    this.port = port;
    this.executingUser = executingUser;
    this.runner = RemoteCommandRunner.create(this);
  }

  public static IRemoteCommand create(
      List<String> commandArgs,
      String workDir,
      String hostName,
      Integer port,
      String executingUser) throws IOException {

    // Transform `commandArgs` into an `ObservableList`.
    ObservableList<StringProperty> obsCommandArgs = FXCollections.observableArrayList();
    for (String arg : commandArgs) {
      obsCommandArgs.add(new SimpleStringProperty(arg));
    }

    return new RemoteCommand(
        new SimpleListProperty<>(obsCommandArgs),
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

  // { ICommand implementation.

  @Override
  public ICommandRunner runner() {
    return this.runner;
  }

  // }

}
