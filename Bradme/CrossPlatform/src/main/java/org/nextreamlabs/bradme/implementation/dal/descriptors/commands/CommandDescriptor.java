package org.nextreamlabs.bradme.implementation.dal.descriptors.commands;

import org.nextreamlabs.bradme.interfaces.dal.descriptors.commands.ICommandDescriptor;

public abstract class CommandDescriptor
    implements ICommandDescriptor {

  private final String command;
  private final String workDir;

  // { Construction.

  protected CommandDescriptor(String command, String workDir) {
    this.command = command;
    this.workDir = workDir;
  }

  // }

  // { ICommandDescriptor implementation.

  @Override
  public String getCommand() {
    return this.command;
  }

  @Override
  public String getWorkDir() {
    return this.workDir;
  }

  // }

}
