package org.nextreamlabs.bradme.implementation.commands;


import org.nextreamlabs.bradme.interfaces.commands.ICommandRunner;
import org.nextreamlabs.bradme.interfaces.models.commands.ICommand;

import java.io.IOException;

public abstract class CommandRunner <TCommand extends ICommand>
    implements ICommandRunner {

  // { Fields (+Accessors)

  private final TCommand command;
  protected TCommand getCommand() {
    return this.command;
  }

  // }

  // { Construction

  protected CommandRunner(TCommand command) {
    this.command = command;
  }

  // }

  // { ICommandRunner implementation.

  public abstract void start() throws IOException;
  public abstract void stop() throws IOException;

  // }

}
