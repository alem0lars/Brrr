package org.nextreamlabs.bradme.implementation.dal.descriptors.commands;

import org.nextreamlabs.bradme.interfaces.dal.descriptors.commands.ILocalCommandDescriptor;

import java.util.List;

public class LocalCommandDescriptor
    extends CommandDescriptor
    implements ILocalCommandDescriptor {

  // { Construction.

  protected LocalCommandDescriptor(List<String> command, String workDir) {
    super(command, workDir);
  }

  public static ILocalCommandDescriptor create(List<String> command, String workDir) {
    return new LocalCommandDescriptor(command, workDir);
  }

  // }

  // { ILocalCommandDescriptor implementation.

  // }

}
