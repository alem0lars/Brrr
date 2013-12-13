package org.nextreamlabs.bradme.interfaces.dal.descriptors;

import org.nextreamlabs.bradme.interfaces.dal.descriptors.commands.ICommandDescriptor;

public interface IStatusWithCommandDescriptor
    extends IStatusDescriptor {
  public ICommandDescriptor getCommandOnEnter();
}
