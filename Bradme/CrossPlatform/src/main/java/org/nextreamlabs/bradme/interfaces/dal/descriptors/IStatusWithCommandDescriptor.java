package org.nextreamlabs.bradme.interfaces.dal.descriptors;

public interface IStatusWithCommandDescriptor
    extends IStatusDescriptor {
  public ICommandDescriptor getCommandOnEnter();
}
