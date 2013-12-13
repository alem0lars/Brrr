package org.nextreamlabs.bradme.implementation.dal.descriptors.commands;

import org.nextreamlabs.bradme.interfaces.dal.descriptors.commands.ILocalCommandDescriptor;

public class LocalCommandDescriptor
    extends CommandDescriptor
    implements ILocalCommandDescriptor {

  // { Construction.

  protected LocalCommandDescriptor(String command, String workDir) {
    super(command, workDir);
  }

  public static ILocalCommandDescriptor create(String command, String workDir) {
    return new LocalCommandDescriptor(command, workDir);
  }

  // }

}
