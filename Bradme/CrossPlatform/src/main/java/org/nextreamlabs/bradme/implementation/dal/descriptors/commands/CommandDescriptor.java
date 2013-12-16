package org.nextreamlabs.bradme.implementation.dal.descriptors.commands;

import org.nextreamlabs.bradme.interfaces.dal.descriptors.commands.ICommandDescriptor;

import java.util.List;

public abstract class CommandDescriptor
    implements ICommandDescriptor {

  private final List<String> command;
  private final String workDir;

  // { Construction.

  protected CommandDescriptor(List<String> command, String workDir) {
    this.command = command;
    this.workDir = workDir;
  }

  // }

  // { ICommandDescriptor implementation.

  @Override
  public List<String> getCommand() {
    return this.command;
  }

  @Override
  public String getWorkDir() {
    return this.workDir;
  }

  // }

}
